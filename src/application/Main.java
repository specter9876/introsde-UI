package application;

import java.util.HashMap;
import java.util.Map;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import process.User;

public class Main extends Application {
	public static Map<String, Object> cache = new HashMap<>();

	@Override
	public void start(Stage primaryStage) {
		try {
			// BorderPane root = new BorderPane();
			Parent root = FXMLLoader.load(getClass().getResource("/view/StartingPage.fxml"));
			Scene scene = new Scene(root, 700, 475);

			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}

	public static User getSessionUser() {
		User u = null;
		try {
			u = (User) cache.get("user");
		} catch (Exception e) {
			return null;
		}
		return u;
	}
}
