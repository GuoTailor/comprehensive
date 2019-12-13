package view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
/**
 * 
 * @author pinnuli
 */
public class Main extends Application {
	private static final String AppCss = Main.class.getResource("MainUI.css").toExternalForm();
	Stage stage=new Stage();
	@Override
	public void start(Stage primaryStage) throws Exception {
//		Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));
		Parent root = FXMLLoader.load(getClass().getResource("MainUI.fxml"));
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.setResizable(false);
		scene.getStylesheets().add(AppCss);
		primaryStage.setTitle("STUDENT SCORE MANAGE");
		primaryStage.show();
	}
	public void show() throws Exception {
		start(stage);
	}
	public static void main(String[] args) {
		launch(args);
	}
}
