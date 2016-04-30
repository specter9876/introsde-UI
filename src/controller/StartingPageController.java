package controller;

import java.io.IOException;
import java.util.List;

import application.Main;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.DialogPane;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import process.Activity;
import process.Goal;
import process.HealthMeasure;
import process.Process;
import process.ProcessService;
import process.User;

public class StartingPageController {

	@FXML
	PasswordField passwordFields;

	@FXML
	TextField userField;

	@FXML
	Pane startingPane;

	@FXML
	ProgressBar pb;

	@FXML
	void doLogin(ActionEvent event) {

		String username = userField.getText();
		String password = passwordFields.getText();
		System.out.println("Login with user: " + username + " and pswd: " + password);

		// eseguiamo il login con tanto di chiamata ad heroku

		try {
			Alert alert = new Alert(Alert.AlertType.INFORMATION);
			DialogPane content = FXMLLoader.load(getClass().getResource("/view/ProgressDialog.fxml"));
			alert.getDialogPane().setContent(content);
			alert.setContentText("Logging user...");
			alert.show();

			Task<User> task = new Task<User>() {
				@Override
				protected User call() throws Exception {
					ProcessService pservice = new ProcessService();
					Process prs = pservice.getProcessImplPort();
					User user = prs.login(username, password);
					// caricare e mettere in cache le cose che servono in
					// homepage
                    
                    if(user==null){
                        
                        System.out.println("wrong user");
                        return null;
                        
                    }
                        
					Main.cache.put("user", user);

					List<HealthMeasure> recMeasures = prs.getRecentHealthMeasureByUser(user.getIdUser());
					Main.cache.put("recentMeasures", recMeasures);

					List<Activity> doneActs = prs.getMyDoneActivity(user.getIdUser());
					Main.cache.put("doneActs", doneActs);

					List<Goal> toDoGoal = prs.toDoGoal(user.getIdUser());
					Main.cache.put("toDoGoal", toDoGoal);

					List<Goal> doneGoal = prs.getGoalAchieved(user.getIdUser());
					Main.cache.put("doneGoal", doneGoal);
					return user;
                    
                    
				}
			};

			task.addEventHandler(WorkerStateEvent.WORKER_STATE_SUCCEEDED, new EventHandler<WorkerStateEvent>() {
				@Override
				public void handle(WorkerStateEvent t) {
					alert.hide();
					User user = task.getValue();
                    
                    if(user==null){
                        System.out.println("wrong ueser or password");
                        Alert alert=new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Virtual Coach");
                        alert.setHeaderText("Error");
                        alert.setContentText("Wrong login");
                        alert.showAndWait();
                        return;
                    }
                    
					System.out.println("UserID: " + user.getIdUser());
					Parent root = null;
					try {
						root = FXMLLoader.load(getClass().getResource("/view/HomePage.fxml"));
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					Scene scene = new Scene(root, 700, 475);
					Stage primaryStage = (Stage) userField.getScene().getWindow();
					primaryStage.setScene(scene);
					primaryStage.show();
				}
			});
			new Thread(task).start();

		} catch (Exception e) {
        
			e.printStackTrace();
            
		}

	}

	@FXML
	void doRegister(ActionEvent event) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("/view/RegisterPage.fxml"));
			Scene scene = new Scene(root, 500, 375);
			Stage primaryStage = (Stage) userField.getScene().getWindow();
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
