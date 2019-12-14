package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import service.LoginServer;
import view.Main;
import view.MessageView;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    @FXML
    public TextField userName;

    @FXML
    public PasswordField password;

    @FXML
    public RadioButton teacherButton;

    @FXML
    public RadioButton studentButton;

    @FXML
    public Button login;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initView();
    }

    private void initView() {
        password.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.ENTER) {
                    onLong();
                }
            }
        });
        login.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                onLong();
            }
        });
    }

    public void onLong() {
        String name = userName.getText();
        String passwordd = password.getText();
        //从单选框得到是学生还是老师(path = teacher  or  student)
        String path = "src/scorefile/" + (teacherButton.isSelected() ? teacherButton.getUserData() : studentButton.getUserData()) + ".txt";
        System.out.println(path);
        LoginServer.Login message = new LoginServer().login(name, passwordd, path);
        if (teacherButton.isSelected() && message.isLogin) {
            startTeacher();
        } else if (studentButton.isSelected() && message.isLogin) {
            startStudent(message.mag);
        } else {
            //弹出对话框
            MessageView.createView(message.mag);
        }
    }

    public void startStudent(String name) {
        try {
            String AppCss = Main.class.getResource("MainUI.css").toExternalForm();
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("StudentUI.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage primaryStage = new Stage();
            primaryStage.setScene(scene);
            primaryStage.setResizable(false);
            scene.getStylesheets().add(AppCss);
            primaryStage.setTitle("STUDENT SCORE MANAGE");
            StudentController controller = loader.getController();
            controller.initData(name);
            primaryStage.show();
            Stage stage = (Stage) login.getScene().getWindow();
            stage.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void startTeacher() {
        try {
            String AppCss = Main.class.getResource("MainUI.css").toExternalForm();
            Parent root = FXMLLoader.load(Main.class.getResource("MainUI.fxml"));
            Scene scene = new Scene(root);
            Stage primaryStage = new Stage();
            primaryStage.setScene(scene);
            primaryStage.setResizable(false);
            scene.getStylesheets().add(AppCss);
            primaryStage.setTitle("STUDENT SCORE MANAGE");
            primaryStage.show();
            Stage stage = (Stage) login.getScene().getWindow();
            stage.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
