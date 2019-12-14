package view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.Optional;

/**
 * Created by gyh on 2019/12/14.
 */
public class AlertBox {

    public void display(String title, String message) {
        Stage window = new Stage();
        window.setTitle(title);
        //modality要使用Modality.APPLICATION_MODEL
        window.initModality(Modality.APPLICATION_MODAL);
        window.setMinWidth(300);
        window.setMinHeight(150);

        TextField field = new TextField();
        field.setPrefWidth(20);
        field.setMinWidth(100);
        Button button = new Button("确定");
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

            }
        });

        Label label = new Label(message);

        VBox layout = new VBox(10);
        layout.getChildren().addAll(label, field, button);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout);
        window.setScene(scene);
        //使用showAndWait()先处理这个窗口，而如果不处理，main中的那个窗口不能响应
        window.showAndWait();
    }

    public String show() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("文本输入对话框");
        dialog.setHeaderText("输入一个学科名");

// 传统的获取输入值的方法
        Optional<String> result = dialog.showAndWait();

// lambda表达式获取输入值
        return result.orElse(null);
    }

}
