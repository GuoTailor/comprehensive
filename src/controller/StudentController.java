package controller;

import javafx.beans.property.ReadOnlyIntegerWrapper;
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
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;
import javafx.util.Callback;
import javafx.util.StringConverter;
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
import java.util.List;
import java.util.Map;
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
    private Analysis analysis = null;
    /*����������ı���*/

    @FXML
    private void openFile(ActionEvent event) {

        MyFileReader fileLloader = new MyFileReader();
        fileLloader.setFile(MyFileChooser.chooseFile());/*��ȡ�ļ�*/
        if (fileLloader.getFile() != null) {
            try {
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
        analysis = analysisService.analyseFile(students.get(0), "java");

        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(2);

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

    public List<TableColumn<Student, ?>> tableColumnList = new ArrayList<>();

    public TableColumn<Student, String> studentId = new TableColumn<>("ѧ��");
    public TableColumn<Student, String> name = new TableColumn<>("����");
    public TableColumn<Student, Integer> attendenceScore = new TableColumn<>("����");
    public TableColumn<Student, Integer> finalScore = new TableColumn<>("����");


    @FXML
    private ObservableList<Student> columns = FXCollections
            .observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("init" + new File("./").getAbsolutePath());
        Map<Student, ArrayList<Course>> datas = MyFileReader.show();

        students = new ArrayList<>(datas.keySet());

        if (students.size() != 0) {
            ObservableList<Student> data = FXCollections.observableArrayList(students);

            AnalysisStudents(students);/*����ѧ���ɼ�*/
            studentView.setItems(data);/*�������*/
            status.setText("_��" + analysis.getTotalNum() + "��");
        }

        tableViewinitialize();

    }

    @SuppressWarnings("unchecked")
    private void tableViewinitialize() {
        studentId.setCellValueFactory(new PropertyValueFactory<>("studentId"));
        studentId.setPrefWidth(125);/*���ñ���ʼ��*/

        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        name.setPrefWidth(60);

        attendenceScore.setCellValueFactory(new PropertyValueFactory<>("attendenceScore"));
        attendenceScore.setPrefWidth(60);

        finalScore.setCellValueFactory(new PropertyValueFactory<>("finalScore"));
        finalScore.setPrefWidth(61);


        studentView.getColumns().clear();
        studentView.setEditable(true);/*���ÿɱ༭���޸Ĺ���*/

        studentId.setEditable(true);
        attendenceScore.setEditable(true);

        tableColumnList.add(studentId);
        tableColumnList.add(name);
        tableColumnList.add(attendenceScore);
        tableColumnList.add(finalScore);
        for (String str: MyFileReader.getList()) {
            TableColumn<Student, Integer> item = new TableColumn<>(str);
            item.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Student, Integer>, ObservableValue<Integer>>() {
                @Override
                public ObservableValue<Integer> call(TableColumn.CellDataFeatures<Student, Integer> param) {
                    return (ObservableValue)new ReadOnlyIntegerWrapper(MyFileWriter.instance.get(param.getValue(), str));
                }
            });
            item.setPrefWidth(60);
            tableColumnList.add(item);
        }
        studentView.getColumns().addAll(tableColumnList);
    }

}
