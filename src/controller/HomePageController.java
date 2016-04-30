package controller;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import application.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DialogPane;
import javafx.scene.control.ListView;
import javafx.scene.control.Slider;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import process.Activity;
import process.Food;
import process.Goal;
import process.HealthMeasure;
import process.Process;
import process.ProcessService;
import process.User;

public class HomePageController implements Initializable {
	Process prs;
	User sessionUser;
    
	@FXML
	Parent tabPane;
	@FXML
	TextField tf_measureValue;
    
	@FXML
	ComboBox<String> combo_measureType;
    
	@FXML
	ListView<HealthMeasure> listview_lifestatus;
	List<HealthMeasure> recMeasures;
    
	@FXML
	ComboBox<String> combo_activity;
    
	@FXML
	Spinner<Integer> spinner_activityDuration;
    
	@FXML
	ListView<Activity> listview_doneActivities;
	List<Activity> doneActs;
    
	@FXML
	TextField tf_food;
    
	@FXML
	Slider cal_slider;
    
	@FXML
	ComboBox<String> combo_goalDesc;
    
	@FXML
	TextField tf_goalEndValue;
    
	@FXML
	ListView<Goal> listview_todoGoal;
	List<Goal> toDoGoal;
    
	@FXML
	ListView<Goal> listview_doneGoal;
	List<Goal> doneGoal;
    
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		System.out.println("Called before loading fxml related to homepage!");
		ProcessService pservice = new ProcessService();
		prs = pservice.getProcessImplPort();
        
		sessionUser = Main.getSessionUser();
        
		// controllare nella cache le cose che servono e caricarle dentro gli
		// elementi grafici
		recMeasures = (List<HealthMeasure>) Main.cache.get("recentMeasures");
		if (!recMeasures.isEmpty()) {
			ObservableList<HealthMeasure> items = FXCollections.observableArrayList(recMeasures);
			listview_lifestatus.setItems(items);
			for (HealthMeasure healthMeasure : recMeasures) {
				System.out.println(healthMeasure.getType() + " " + healthMeasure.getValue());
			}
		}
        
		doneActs = (List<Activity>) Main.cache.get("doneActs");
		if (!doneActs.isEmpty()) {
			ObservableList<Activity> items = FXCollections.observableArrayList(doneActs);
			listview_doneActivities.setItems(items);
			for (Activity act : doneActs) {
				System.out.println(act.getType() + " " + act.getValue());
			}
		}
        
		toDoGoal = (List<Goal>) Main.cache.get("toDoGoal");
		if (!toDoGoal.isEmpty()) {
			ObservableList<Goal> items = FXCollections.observableArrayList(toDoGoal);
			listview_todoGoal.setItems(items);
			for (Activity act : doneActs) {
				System.out.println(act.getType() + " " + act.getValue());
			}
		}
        
		doneGoal = (List<Goal>) Main.cache.get("doneGoal");
		if (!doneGoal.isEmpty()) {
			ObservableList<Goal> items = FXCollections.observableArrayList(doneGoal);
			listview_doneGoal.setItems(items);
			for (Activity act : doneActs) {
				System.out.println(act.getType() + " " + act.getValue());
			}
		}
	}
    
	// Health related interactions
	@FXML
	void doAddMeasure(ActionEvent event) {
		System.out.println("Adding new measure");
        
		HealthMeasure hm = new HealthMeasure();
        
		String measuretype = combo_measureType.getValue();
		String value = tf_measureValue.getText();
		hm.setType(measuretype);
		hm.setValue(Double.parseDouble(value));
		hm.setIdUser(sessionUser);
		//
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		try {
			DialogPane content;
			content = FXMLLoader.load(getClass().getResource("/view/ProgressDialog.fxml"));
			alert.getDialogPane().setContent(content);
			alert.setContentText("Adding Measure user...");
			alert.show();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
        
		Task<String> task = new Task<String>() {
			@Override
			protected String call() throws Exception {
				String s = prs.addHealthMeasure(sessionUser.getIdUser(), hm);
                
                toDoGoal = prs.toDoGoal(sessionUser.getIdUser());
				doneGoal = prs.getGoalAchieved(sessionUser.getIdUser());

				recMeasures = prs.getRecentHealthMeasureByUser(sessionUser.getIdUser());
                
                
                
				return s;
			}
		};
        
		task.addEventHandler(WorkerStateEvent.WORKER_STATE_SUCCEEDED, new EventHandler<WorkerStateEvent>() {
			@Override
			public void handle(WorkerStateEvent t) {
				alert.hide();
				String s = task.getValue();
				Stage primaryStage = (Stage) tabPane.getScene().getWindow();
				Alert alert1 = new Alert(AlertType.INFORMATION);
				alert1.setTitle("Virtual Coach");
				alert1.setHeaderText("HealthMeasure recorded!");
				alert1.setContentText(s);
				alert1.initOwner(primaryStage);
                
                ObservableList<Goal> obs2 = FXCollections.observableArrayList(toDoGoal);
				listview_todoGoal.setItems(obs2);
				ObservableList<Goal> obs1 = FXCollections.observableArrayList(doneGoal);
				listview_doneGoal.setItems(obs1);
                
				ObservableList<HealthMeasure> obs = FXCollections.observableArrayList(recMeasures);
				listview_lifestatus.setItems(obs);
				alert1.showAndWait();
                
			}
		});
		new Thread(task).start();
	}
    
	@FXML
	void doGetAllMeasures(ActionEvent event) {
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		try {
			DialogPane content;
			content = FXMLLoader.load(getClass().getResource("/view/ProgressDialog.fxml"));
			alert.getDialogPane().setContent(content);
			alert.setContentText("Show all Measure user...");
			alert.show();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
        
		Task<List<HealthMeasure>> task = new Task<List<HealthMeasure>>() {
			@Override
			protected List<HealthMeasure> call() throws Exception {
				List<HealthMeasure> list = prs.getHistoryOfAllHealthMeasureByUser(sessionUser.getIdUser());
				return list;
			}
		};
        
		task.addEventHandler(WorkerStateEvent.WORKER_STATE_SUCCEEDED, new EventHandler<WorkerStateEvent>() {
			@Override
			public void handle(WorkerStateEvent t) {
				alert.hide();
				List<HealthMeasure> list = task.getValue();
				Stage primaryStage = (Stage) tabPane.getScene().getWindow();
				Alert alert1 = new Alert(AlertType.INFORMATION);
				alert1.setTitle("Virtual Coach");
				alert1.setHeaderText("HealthMeasure recorded!");
				GridPane pane = new GridPane();
				ObservableList<HealthMeasure> items = FXCollections.observableArrayList(list);
				ListView<HealthMeasure> lv = new ListView<HealthMeasure>();
				lv.setItems(items);
				pane.add(lv, 0, 0);
				alert1.getDialogPane().setContent(pane);
				alert1.initOwner(primaryStage);
				alert1.showAndWait();
			}
		});
		new Thread(task).start();
        
	}
    
	// Activities related interactions
	@FXML
	void doAddActivity(ActionEvent event) {
		System.out.println("Adding new activity");
        
		Activity act = new Activity();
        
		String actType = combo_activity.getValue();
		Double value = new Double(spinner_activityDuration.getValue());
        
		act.setType(actType);
		act.setDuration(value);
		act.setIdUser(sessionUser);
        
		//
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		try {
			DialogPane content;
			content = FXMLLoader.load(getClass().getResource("/view/ProgressDialog.fxml"));
			alert.getDialogPane().setContent(content);
			alert.setContentText("Adding Activity user...");
			alert.show();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
        
		Task<String> task = new Task<String>() {
			@Override
			protected String call() throws Exception {
				String s = prs.addMyActivity(act, sessionUser.getIdUser());
                
                toDoGoal = prs.toDoGoal(sessionUser.getIdUser());
				doneGoal = prs.getGoalAchieved(sessionUser.getIdUser());

				doneActs = prs.getMyDoneActivity(sessionUser.getIdUser());
				return s;
			}
		};
        
		task.addEventHandler(WorkerStateEvent.WORKER_STATE_SUCCEEDED, new EventHandler<WorkerStateEvent>() {
			@Override
			public void handle(WorkerStateEvent t) {
				alert.hide();
				String s = task.getValue();
				Stage primaryStage = (Stage) tabPane.getScene().getWindow();
				Alert alert1 = new Alert(AlertType.INFORMATION);
				alert1.setTitle("Virtual Coach");
				alert1.setHeaderText("Activity recorded!");
				alert1.setContentText(s);
				alert1.initOwner(primaryStage);
                
                ObservableList<Goal> obs2 = FXCollections.observableArrayList(toDoGoal);
				listview_todoGoal.setItems(obs2);
				ObservableList<Goal> obs1 = FXCollections.observableArrayList(doneGoal);
				listview_doneGoal.setItems(obs1);
                
                
				ObservableList<Activity> obs = FXCollections.observableArrayList(doneActs);
				listview_doneActivities.setItems(obs);
				alert1.showAndWait();
			}
		});
		new Thread(task).start();
        
	}
    
	// Food related interactions
	@FXML
	void doSuggestFoodWithCalBound(ActionEvent event) {
		System.out.println("Asking food hint with calories bound");
		String food = tf_food.getText();
		double calBound = cal_slider.getValue();
        
		//
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		try {
			DialogPane content;
			content = FXMLLoader.load(getClass().getResource("/view/ProgressDialog.fxml"));
			alert.getDialogPane().setContent(content);
			alert.setContentText("Asking food hint with calories bound...");
			alert.show();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
        
		Task<List<Food>> task = new Task<List<Food>>() {
			@Override
			protected List<Food> call() throws Exception {
				List<Food> s = prs.suggestFoodByCaloriesBound(food, new Double(calBound));
				return s;
			}
		};
        
		task.addEventHandler(WorkerStateEvent.WORKER_STATE_SUCCEEDED, new EventHandler<WorkerStateEvent>() {
			@Override
			public void handle(WorkerStateEvent t) {
				alert.hide();
				List<Food> list = task.getValue();
				Stage primaryStage = (Stage) tabPane.getScene().getWindow();
				Alert alert1 = new Alert(AlertType.INFORMATION);
				alert1.setTitle("Virtual Coach");
				alert1.setHeaderText("Here some hint for you!");
				GridPane pane = new GridPane();
				ObservableList<Food> items = FXCollections.observableArrayList(list);
				ListView<Food> lv = new ListView<>();
				lv.setItems(items);
				pane.add(lv, 0, 0);
				alert1.getDialogPane().setContent(pane);
				alert1.initOwner(primaryStage);
				alert1.showAndWait();
			}
		});
		new Thread(task).start();
	}
    
	@FXML
	void doSuggestFood(ActionEvent event) {
		System.out.println("Asking food hint with calories bound");
		String food = tf_food.getText();
		// Integer calBound=spinner_foodCal.getValue();
        
		//
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		try {
			DialogPane content;
			content = FXMLLoader.load(getClass().getResource("/view/ProgressDialog.fxml"));
			alert.getDialogPane().setContent(content);
			alert.setContentText("Asking food hint...");
			alert.show();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
        
		Task<List<Food>> task = new Task<List<Food>>() {
			@Override
			protected List<Food> call() throws Exception {
				List<Food> s = prs.suggestFoodByType(food);
				return s;
			}
		};
        
		task.addEventHandler(WorkerStateEvent.WORKER_STATE_SUCCEEDED, new EventHandler<WorkerStateEvent>() {
			@Override
			public void handle(WorkerStateEvent t) {
				alert.hide();
				List<Food> list = task.getValue();
				Stage primaryStage = (Stage) tabPane.getScene().getWindow();
				Alert alert1 = new Alert(AlertType.INFORMATION);
				alert1.setTitle("Virtual Coach");
				alert1.setHeaderText("Here some hint for you!");
				GridPane pane = new GridPane();
				ObservableList<Food> items = FXCollections.observableArrayList(list);
				ListView<Food> lv = new ListView<>();
				lv.setItems(items);
				lv.setPrefWidth(600);
				pane.add(lv, 0, 0);
				pane.setPrefSize(700, 400);
				alert1.getDialogPane().setContent(pane);
				alert1.getDialogPane().getScene().getWindow().sizeToScene();
				alert1.initOwner(primaryStage);
				alert1.showAndWait();
			}
		});
		new Thread(task).start();
	}
    
	// Goal related methods
	@FXML
	void doFollowGoal(ActionEvent event) {
		// sar fatto ora vado a prendere il bus
		System.out.println("Following new goal");
		String gdesc = combo_goalDesc.getValue();
		// Integer calBound=spinner_foodCal.getValue();
		Goal goal = new Goal();
		goal.setDescription(gdesc);
		String gtype = "";
		switch (gdesc) {
            case "weight":
                gtype = "Health";
                break;
            case "height":
                gtype = "Health";
                break;
            case "steps":
                gtype = "Health";
                break;
            case "distance":
                gtype = "Health";
                break;
            case "Ski":
                gtype = "Activity";
                break;
            case "Swim":
                gtype = "Activity";
                break;
            case "Run":
                gtype = "Activity";
                break;
            case "Tennis":
                gtype = "Activity";
                break;
            case "bicycle":
                gtype = "Activity";
                break;
            default:
                gtype = "Activity";
                break;
		}
		goal.setType(gtype);
		Double endvalue = Double.parseDouble(tf_goalEndValue.getText());
		goal.setEndValue(endvalue);
		goal.setIdUser(sessionUser);
		//
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		try {
			DialogPane content;
			content = FXMLLoader.load(getClass().getResource("/view/ProgressDialog.fxml"));
			alert.getDialogPane().setContent(content);
			alert.setContentText("Following new goal...");
			alert.show();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
        
		Task<Goal> task = new Task<Goal>() {
			@Override
			protected Goal call() throws Exception {
				Goal s = prs.followGoal(sessionUser.getIdUser(), goal);
				toDoGoal = prs.toDoGoal(sessionUser.getIdUser());
				doneGoal = prs.getGoalAchieved(sessionUser.getIdUser());
				return s;
			}
		};
        
		task.addEventHandler(WorkerStateEvent.WORKER_STATE_SUCCEEDED, new EventHandler<WorkerStateEvent>() {
			@Override
			public void handle(WorkerStateEvent t) {
				alert.hide();
				Goal g = task.getValue();
				Stage primaryStage = (Stage) tabPane.getScene().getWindow();
				Alert alert1 = new Alert(AlertType.INFORMATION);
				alert1.setTitle("Virtual Coach");
				alert1.setHeaderText("New Goal recorded!");
				alert1.initOwner(primaryStage);
				ObservableList<Goal> obs = FXCollections.observableArrayList(toDoGoal);
				listview_todoGoal.setItems(obs);
				ObservableList<Goal> obs1 = FXCollections.observableArrayList(doneGoal);
				listview_doneGoal.setItems(obs1);
				alert1.showAndWait();
			}
		});
		new Thread(task).start();
		// fine cut paste
        
	}
    
	@FXML
	void doLogout(ActionEvent event) {
		// during logout the cache should be cleared and we go back to the
		// starting page
		Main.cache.clear();
		Parent root = null;
		try {
			root = FXMLLoader.load(getClass().getResource("/view/StartingPage.fxml"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Scene scene = new Scene(root, 500, 375);
		Stage primaryStage = (Stage) tabPane.getScene().getWindow();
		primaryStage.setScene(scene);
		primaryStage.show();
	}
    
}
