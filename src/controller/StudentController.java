package controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;
import javafx.util.Callback;
import model.Analysis;
import model.Course;
import model.Student;
import service.AnalysisService;
import service.MyFileReader;
import service.MyFileWriter;
import service.SearchService;
import view.*;

import java.io.File;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * @author pinnuli
 */
public class StudentController implements Initializable {
    String fileName;
    String filePath;
    @FXML
    private Text status;

    private ArrayList<Student> students = null;
    private Course course = null;
    private Analysis analysis = null;
    /*����������ı���*/

    @FXML
    private TextField max;
    @FXML
    private TextField min;
    @FXML
    private TextField avg;
    @FXML
    private TextField excellent;
    @FXML
    private TextField well;
    @FXML
    private TextField mid;
    @FXML
    private TextField pass;
    @FXML
    private TextField fail;
    @FXML
    private TextField excellentPercentage;
    @FXML
    private TextField wellPercentage;
    @FXML
    private TextField midPercentage;
    @FXML
    private TextField passPercentage;
    @FXML
    private TextField failPercentage;


    @FXML
    private void openFile(ActionEvent event) {

        MyFileReader fileLloader = new MyFileReader();
        fileLloader.setFile(MyFileChooser.chooseFile());/*��ȡ�ļ�*/
        if (fileLloader.getFile() != null) {
            try {
                course = fileLloader.getFileCourse();/*��ȡ�ļ��еĿγ���Ϣ*/
                students = fileLloader.getFileStudents();/*��ȡ�ļ��е�ѧ����Ϣ*/
            } catch (NumberFormatException e) {
                MessageView.createView("�ļ���ʽ���������±����ļ���ʽ��");
                e.printStackTrace();
            }
            if (students.size() != 0) {
                ObservableList<Student> data = FXCollections
                        .observableArrayList(students);

                AnalysisStudents(students);/*����ѧ���ɼ�*/
                studentView.setItems(data);/*�������*/
                status.setText(fileLloader.getFile().getPath() + "_��"
                        + analysis.getTotalNum() + "��");
                String temp = fileLloader.getFile().getName();
                filePath = fileLloader.getFile().getPath();/*��ȡ�ļ�·���������޸��ļ�ʹ��*/
                fileName = temp.substring(0, temp.lastIndexOf("."));
            }
        } else {
            MessageView.createView("�ļ�Ϊ��");
        }
    }

    public void initData(String name) {
        SearchService searchService = new SearchService();
        ArrayList<Student> studentsTemp = null;
        if (name.equals("")) {
            studentsTemp = students;
        } else {
            studentsTemp = searchService.search(students, name);
        }
        /*��ȡƥ����Ϣ*/
        ObservableList<Student> data = FXCollections
                .observableArrayList(studentsTemp);
        studentView.setItems(data);
        courseTeacher.setText(name);
    }

    private void AnalysisStudents(ArrayList<Student> students) {
        AnalysisService analysisService = new AnalysisService();
        analysis = analysisService.analyseFile(students);

        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(2);

        max.setText(((Integer) analysis.getMax()).toString());
        min.setText(((Integer) analysis.getMin()).toString());
        avg.setText(df.format(analysis.getAvg()));

        excellent.setText(((Integer) analysis.getExcellent()).toString());
        well.setText(((Integer) analysis.getWell()).toString());
        mid.setText(((Integer) analysis.getMid()).toString());
        pass.setText(((Integer) analysis.getPass()).toString());
        fail.setText(((Integer) analysis.getFail()).toString());

        double excellentPercentageTempDouble = ((double) analysis
                .getExcellent()) / analysis.getTotalNum() * 100;
        double wellPercentageTempDouble = ((double) analysis.getWell())
                / analysis.getTotalNum() * 100;
        double midPercentageTempDouble = ((double) analysis.getMid())
                / analysis.getTotalNum() * 100;
        double passPercentageTempDouble = ((double) analysis.getPass())
                / analysis.getTotalNum() * 100;
        double failPercentageTempDouble = ((double) analysis.getFail())
                / analysis.getTotalNum() * 100;

        excellentPercentage.setText(df.format(excellentPercentageTempDouble));
        wellPercentage.setText(df.format(wellPercentageTempDouble));
        midPercentage.setText(df.format(midPercentageTempDouble));
        passPercentage.setText(df.format(passPercentageTempDouble));
        failPercentage.setText(df.format(failPercentageTempDouble));

        courseName.setText(course.getCourseName());
//        courseTeacher.setText(course.getCourseTeacher());
        courseClass.setText(course.getCourseClass());
    }

    @FXML
    private void closeAll(ActionEvent event) {
        System.exit(0);
    }

    @FXML
    public Text courseName;
    @FXML
    public Text courseTeacher;
    @FXML
    public Text courseClass;

    @FXML
    public TableView<Student> studentView;

    public TableColumn<Student, String> studentId = new TableColumn<Student, String>(
            "ѧ��");
    public TableColumn<Student, String> name = new TableColumn<Student, String>(
            "����");
    public TableColumn<Student, Integer> attendenceScore = new TableColumn<Student, Integer>(
            "����");
    public TableColumn<Student, Integer> testScore = new TableColumn<Student, Integer>(
            "����");
    public TableColumn<Student, Integer> homeworkScore = new TableColumn<Student, Integer>(
            "��ҵ");
    public TableColumn<Student, Integer> finalTestScore = new TableColumn<Student, Integer>(
            "��ĩ");
    public TableColumn<Student, Integer> finalScore = new TableColumn<Student, Integer>(
            "����");

    @FXML
    private ObservableList<Student> columns = FXCollections
            .observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("init" + new File("./").getAbsolutePath());
        MyFileReader fileLloader = new MyFileReader();
        fileLloader.setFile(new File("./src/scorefile/16�ƻ�4��ɼ�.txt"));/*��ȡ�ļ�*/
        if (fileLloader.getFile() != null) {
            try {
                course = fileLloader.getFileCourse();/*��ȡ�ļ��еĿγ���Ϣ*/
                students = fileLloader.getFileStudents();/*��ȡ�ļ��е�ѧ����Ϣ*/
            } catch (NumberFormatException e) {
                MessageView.createView("�ļ���ʽ���������±����ļ���ʽ��");
                e.printStackTrace();
            }
            if (students.size() != 0) {
                ObservableList<Student> data = FXCollections
                        .observableArrayList(students);

                AnalysisStudents(students);/*����ѧ���ɼ�*/
                studentView.setItems(data);/*�������*/
                status.setText(fileLloader.getFile().getPath() + "_��"
                        + analysis.getTotalNum() + "��");
                String temp = fileLloader.getFile().getName();
                filePath = fileLloader.getFile().getPath();/*��ȡ�ļ�·���������޸��ļ�ʹ��*/
                fileName = temp.substring(0, temp.lastIndexOf("."));
            }
        } else {
            MessageView.createView("�ļ�Ϊ��");
        }
        tableViewinitialize();

    }

    @SuppressWarnings("unchecked")
    private void tableViewinitialize() {
        studentId
                .setCellValueFactory(new PropertyValueFactory<Student, String>(
                        "studentId"));
        studentId.setPrefWidth(125);/*���ñ���ʼ��*/

        name.setCellValueFactory(new PropertyValueFactory<Student, String>(
                "name"));
        name.setPrefWidth(60);

        attendenceScore.setCellValueFactory(new PropertyValueFactory<Student, Integer>(
                "attendenceScore"));
        attendenceScore.setPrefWidth(60);

        testScore.setCellValueFactory(new PropertyValueFactory<Student, Integer>(
                "testScore"));
        testScore.setPrefWidth(60);

        homeworkScore.setCellValueFactory(new PropertyValueFactory<Student, Integer>(
                "homeworkScore"));
        homeworkScore.setPrefWidth(60);

        finalTestScore.setCellValueFactory(new PropertyValueFactory<Student, Integer>(
                "finalTestScore"));
        finalTestScore.setPrefWidth(61);

        finalScore.setCellValueFactory(new PropertyValueFactory<Student, Integer>(
                "finalScore"));
        finalScore.setPrefWidth(61);

        studentView.getColumns().clear();
        studentView.setEditable(false);/*���ÿɱ༭���޸Ĺ���*/

        studentId.setEditable(false);
        attendenceScore.setEditable(false);
        testScore.setEditable(false);
        homeworkScore.setEditable(false);
        finalTestScore.setEditable(false);

        studentView.getColumns().addAll(studentId, name, attendenceScore, testScore, homeworkScore, finalTestScore, finalScore);

        courseName.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                course.setCourseName(courseName.getText());
            }
        });
        /*�����ı���γ���Ϣ���޸Ŀγ̶�������*/
        courseTeacher.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                course.setCourseTeacher(courseTeacher.getText());
            }
        });

        courseClass.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                course.setCourseClass(courseClass.getText());
            }
        });
    }

}
