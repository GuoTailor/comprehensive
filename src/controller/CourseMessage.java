package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import model.Course;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by gyh on 2019/12/15.
 */
public class CourseMessage implements Initializable {
    private Course course;
    @FXML
    private TextField courseName;
    @FXML
    private TextField courseId;
    @FXML
    private TextField studyScore;
    @FXML
    private TextField studyTime;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        courseName.setEditable(false);
        courseId.setEditable(false);
        studyScore.setEditable(false);
        studyTime.setEditable(false);
    }

    public void initData(Course course) {
        this.course = course;
        courseName.setText(course.getCourseName());
        courseId.setText(course.getCourseId());
        studyScore.setText(course.getStudyScore().toString());
        studyTime.setText(course.getStudyTime().toString());
    }
}
