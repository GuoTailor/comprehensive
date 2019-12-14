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
        //modalityҪʹ��Modality.APPLICATION_MODEL
        window.initModality(Modality.APPLICATION_MODAL);
        window.setMinWidth(300);
        window.setMinHeight(150);

        TextField field = new TextField();
        field.setPrefWidth(20);
        field.setMinWidth(100);
        Button button = new Button("ȷ��");
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
        //ʹ��showAndWait()�ȴ���������ڣ������������main�е��Ǹ����ڲ�����Ӧ
        window.showAndWait();
    }

    public String show() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("�ı�����Ի���");
        dialog.setHeaderText("����һ��ѧ����");

// ��ͳ�Ļ�ȡ����ֵ�ķ���
        Optional<String> result = dialog.showAndWait();

// lambda���ʽ��ȡ����ֵ
        return result.orElse(null);
    }

}
