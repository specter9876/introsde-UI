package controller;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.DialogPane;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import process.HealthMeasure;
import process.Process;
import process.ProcessService;
import process.User;

public class RegisterController {

	@FXML
	TextField tf_name;

	@FXML
	TextField tf_lastname;

	@FXML
	TextField tf_email;

	@FXML
	PasswordField tf_password;

	@FXML
	DatePicker datePicker_birthdate;

	@FXML
	RadioButton radio_M;

	@FXML
	RadioButton radio_F;

	@FXML
	Spinner<Integer> spinner_weight;

	@FXML
	Spinner<Integer> spinner_height;

	@FXML
	void doRegister(ActionEvent event) {
		User nu = new User();
		nu.setFirstName(tf_name.getText());
		nu.setLastName(tf_lastname.getText());
		nu.setEmail(tf_email.getText());
		nu.setPassword(tf_password.getText());
		nu.setUserName(tf_email.getText());

		// 10 lines of code just for handling date!
		LocalDate localDate = datePicker_birthdate.getValue();
		Instant instant = Instant.from(localDate.atStartOfDay(ZoneId.systemDefault()));
		Date date = Date.from(instant);
		System.out.println(localDate + "\n" + instant + "\n" + date);
		GregorianCalendar c = new GregorianCalendar();
		c.setTime(date);
		XMLGregorianCalendar b_date = null;
		try {
			b_date = DatatypeFactory.newInstance().newXMLGregorianCalendar(c);
		} catch (DatatypeConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		nu.setBirthDate(b_date);
		if (radio_M.isSelected()) {
			nu.setGender("M");
		}
		if (radio_F.isSelected()) {
			nu.setGender("F");
		}
		System.out.println(nu.toString());

		// creating first 2 hmeasures
		GregorianCalendar gregorianCalendar = new GregorianCalendar();
		DatatypeFactory datatypeFactory = null;
		try {
			datatypeFactory = DatatypeFactory.newInstance();
		} catch (DatatypeConfigurationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		XMLGregorianCalendar now = datatypeFactory.newXMLGregorianCalendar(gregorianCalendar);
		Integer weight = spinner_weight.getValue();
		Integer height = spinner_height.getValue();

		HealthMeasure hmw = new HealthMeasure();
		hmw.setType("weight");
		hmw.setValue(weight);

		HealthMeasure hmh = new HealthMeasure();
		hmh.setType("height");
		hmh.setValue(height);
		try {
			Alert alert = new Alert(Alert.AlertType.INFORMATION);
			DialogPane content = FXMLLoader.load(getClass().getResource("/view/ProgressDialog.fxml"));
			alert.getDialogPane().setContent(content);
			alert.setContentText("Registering new user...");
			alert.show();

			Task<User> task = new Task<User>() {
				@Override
				protected User call() throws Exception {
					ProcessService pservice = new ProcessService();
					Process prs = pservice.getProcessImplPort();
					User cu = prs.createUser(nu);
					hmw.setIdUser(cu);
					hmh.setIdUser(cu);
					prs.addHealthMeasure(cu.getIdUser(), hmw);
					prs.addHealthMeasure(cu.getIdUser(), hmh);
					return cu;
				}
			};

			task.addEventHandler(WorkerStateEvent.WORKER_STATE_SUCCEEDED, new EventHandler<WorkerStateEvent>() {
				@Override
				public void handle(WorkerStateEvent t) {
					alert.hide();
					User user = task.getValue();
					System.out.println("UserID: " + user.getIdUser());
					Parent root = null;
					try {
						root = FXMLLoader.load(getClass().getResource("/view/StartingPage.fxml"));
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					Scene scene = new Scene(root, 700, 475);
					Stage primaryStage = (Stage) tf_email.getScene().getWindow();
					primaryStage.setScene(scene);
					primaryStage.show();
				}
			});
			new Thread(task).start();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
