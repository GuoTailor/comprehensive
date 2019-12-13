package controller;

import java.io.File;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.function.Consumer;

import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;
import javafx.util.Callback;
import javafx.beans.value.ObservableValue;

import model.*;
import service.*;
import view.*;

/**
 * @author pinnuli
 */
public class MainController implements Initializable {
    String fileName;
    String filePath;
    @FXML
    private Text status;

    private ArrayList<Student> students = null;
    private Course course = null;
    private Analysis analysis = null;
    /*定义三个类的变量*/

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
    private SearchBox searchBox;

    @FXML
    private void openFile() {

        MyFileReader fileLloader = new MyFileReader();
        fileLloader.setFile(MyFileChooser.chooseFile());/*读取文件*/
        if (fileLloader.getFile() != null) {
            try {
                course = fileLloader.getFileCourse();/*读取文件中的课程信息*/
                students = fileLloader.getFileStudents();/*读取文件中的学生信息*/
            } catch (NumberFormatException e) {
                MessageView.createView("文件格式错误，请重新编排文件格式！");
                e.printStackTrace();
            }
            if (students.size() != 0) {
                ObservableList<Student> data = FXCollections
                        .observableArrayList(students);

                AnalysisStudents(students);/*分析学生成绩*/
                studentView.setItems(data);/*填充数据*/
                status.setText(fileLloader.getFile().getPath() + "_共"
                        + analysis.getTotalNum() + "人");
                String temp = fileLloader.getFile().getName();
                filePath = fileLloader.getFile().getPath();/*获取文件路径，后面修改文件使用*/
                fileName = temp.substring(0, temp.lastIndexOf("."));
            }
        } else {
            MessageView.createView("文件为空");
        }
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

//        courseName.setText(course.getCourseName());
    }

    @FXML
    private void saveFile() {
        MyFileWriter myFileWriter = new MyFileWriter();
        if (students == null || students.isEmpty()) {
            MessageView.createView("学生信息未载入");
            return;
        }
        if (course == null) {
            MessageView.createView("课程信息未载入");
            return;
        }
        File file;
        file = MyFileChooser.chooseSaveFile(fileName);
        if (file != null) {
            myFileWriter.saveFile(file, course, students);/*将数据输出到文件*/
        }
    }

    @FXML
    private void updateFile() {
        MyFileWriter myFileWriter = new MyFileWriter();
        if (students == null || students.isEmpty()) {
            MessageView.createView("学生信息未载入");
            return;
        }
        if (course == null) {
            MessageView.createView("课程信息未载入");
            return;
        }
        File file = new File(filePath);
        myFileWriter.saveFile(file, course, students);
    }

    @FXML
    private void closeAll() {
        System.exit(0);
    }

    @FXML
    public TableView<Student> studentView;

    public TableColumn<Student, String> studentId = new TableColumn<>(
            "学号");
    public TableColumn<Student, String> name = new TableColumn<>(
            "姓名");
    public TableColumn<Student, Integer> attendenceScore = new TableColumn<>(
            "考勤");
    public TableColumn<Student, Integer> testScore = new TableColumn<>(
            "测验");
    public TableColumn<Student, Integer> homeworkScore = new TableColumn<>(
            "作业");
    public TableColumn<Student, Integer> finalTestScore = new TableColumn<>(
            "期末");
    public TableColumn<Student, Integer> finalScore = new TableColumn<>(
            "总评");

    @FXML
    private void search() {
        if (students == null) {
            return;
        }
        String message = searchBox.getTextBox().getText();/*获取搜索关键字*/
        SearchService searchService = new SearchService();
        ArrayList<Student> studentsTemp = null;
        if (message.equals("")) {
            studentsTemp = students;
        } else {
            studentsTemp = searchService.search(students, message);
        }
        /*获取匹配信息*/
        ObservableList<Student> data = FXCollections
                .observableArrayList(studentsTemp);
        studentView.setItems(data);

    }

    @FXML
    private void clearText() {
        if (students != null) {
            ObservableList<Student> data = FXCollections
                    .observableArrayList(students);
            studentView.setItems(data);
        }
    }

    @FXML
    private void deleteItem() {
        Student sheet = studentView.getSelectionModel().getSelectedItem();
        System.out.println(sheet);
        studentView.getItems().remove(sheet);
        students.remove(sheet);
    }

    @FXML
    public void addItem(Event event) {
		// get current position
		TablePosition pos = studentView.getFocusModel().getFocusedCell();

		// clear current selection
		studentView.getSelectionModel().clearSelection();

		// create new record and add it to the model
		Student data = new Student("", "", "");
		studentView.getItems().add(data);

		// get last row
		int row = studentView.getItems().size() - 1;
		studentView.getSelectionModel().select( row, pos.getTableColumn());

		// scroll to new row
		studentView.scrollTo(data);
		System.out.println("add");
//		new InsertStudent().updateFile(data);
        students.add(data);
    }

    @FXML
    private void clearTable() {
        max.setText("");
        min.setText("");
        avg.setText("");
        excellent.setText("");
        well.setText("");
        mid.setText("");
        pass.setText("");
        fail.setText("");
        excellentPercentage.setText("");
        wellPercentage.setText("");
        midPercentage.setText("");
        passPercentage.setText("");
        failPercentage.setText("");
        status.setText("");
        if (students != null) {
            students.clear();
            ObservableList<Student> data = FXCollections
                    .observableArrayList(students);
            studentView.setItems(data);
        }
        analysis = null;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("init" + new File("./").getAbsolutePath());
        MyFileReader fileLloader = new MyFileReader();
        fileLloader.setFile(new File("./src/scorefile/16计机4班成绩.txt"));/*读取文件*/
        if (fileLloader.getFile() != null) {
            try {
                course = fileLloader.getFileCourse();/*读取文件中的课程信息*/
                students = fileLloader.getFileStudents();/*读取文件中的学生信息*/
            } catch (NumberFormatException e) {
                MessageView.createView("文件格式错误，请重新编排文件格式！");
                e.printStackTrace();
            }
            if (students.size() != 0) {
                ObservableList<Student> data = FXCollections
                        .observableArrayList(students);

                AnalysisStudents(students);/*分析学生成绩*/
                studentView.setItems(data);/*填充数据*/
                status.setText(fileLloader.getFile().getPath() + "_共"
                        + analysis.getTotalNum() + "人");
                String temp = fileLloader.getFile().getName();
                filePath = fileLloader.getFile().getPath();/*获取文件路径，后面修改文件使用*/
                fileName = temp.substring(0, temp.lastIndexOf("."));
            }
        } else {
            MessageView.createView("文件为空");
        }
        tableViewinitialize();
        searchBox.setEventHandler(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                clearText();
            }
        });

    }

    private void tableViewinitialize() {
        studentId
                .setCellValueFactory(new PropertyValueFactory<>(
                        "studentId"));
        studentId.setPrefWidth(125);/*设置表格初始化*/

        name.setCellValueFactory(new PropertyValueFactory<>(
                "name"));
        name.setPrefWidth(60);

        attendenceScore.setCellValueFactory(new PropertyValueFactory<>(
                "attendenceScore"));
        attendenceScore.setPrefWidth(60);

        testScore.setCellValueFactory(new PropertyValueFactory<>(
                "testScore"));
        testScore.setPrefWidth(60);

        homeworkScore.setCellValueFactory(new PropertyValueFactory<>(
                "homeworkScore"));
        homeworkScore.setPrefWidth(60);

        finalTestScore.setCellValueFactory(new PropertyValueFactory<>(
                "finalTestScore"));
        finalTestScore.setPrefWidth(61);

        finalScore.setCellValueFactory(new PropertyValueFactory<>(
                "finalScore"));
        finalScore.setPrefWidth(61);

        studentView.getColumns().clear();
        studentView.setEditable(true);/*设置可编辑，修改功能*/

        studentId.setEditable(true);
        attendenceScore.setEditable(true);
        testScore.setEditable(true);
        homeworkScore.setEditable(true);
        finalTestScore.setEditable(true);

        Callback<TableColumn<Student, String>, TableCell<Student, String>> cellFactoryString = new Callback<TableColumn<Student, String>, TableCell<Student, String>>() {
            public TableCell<Student, String> call(
                    TableColumn<Student, String> p) {
                return new PersonEditingCell();
            }
        };


        studentId.setCellFactory(cellFactoryString);/*监听表格学生信息并修改对象数据*/
        studentId.setOnEditCommit(new EventHandler<CellEditEvent<Student, String>>() {
            @Override
            public void handle(CellEditEvent<Student, String> score) {
                score.getTableView().getItems()
                        .get(score.getTablePosition().getRow()).setStudentId(score
                        .getNewValue());
                tableViewinitialize();/*表格重新初始化*/
            }
        });

        name.setCellFactory(cellFactoryString);
        name.setOnEditCommit(new EventHandler<CellEditEvent<Student, String>>() {
            @Override
            public void handle(CellEditEvent<Student, String> info) {
                info.getTableView().getItems()
                        .get(info.getTablePosition().getRow()).setName(info
                        .getNewValue());
                tableViewinitialize();
            }
        });

        Callback<TableColumn<Student, Integer>, TableCell<Student, Integer>> cellFactoryInt = new Callback<TableColumn<Student, Integer>, TableCell<Student, Integer>>() {
            public TableCell<Student, Integer> call(
                    TableColumn<Student, Integer> p) {
                return new ScoreEditingCell();
            }
        };

        attendenceScore.setCellFactory(cellFactoryInt);
        attendenceScore.setOnEditCommit(new EventHandler<CellEditEvent<Student, Integer>>() {
            @Override
            public void handle(CellEditEvent<Student, Integer> score) {
                score.getTableView().getItems()
                        .get(score.getTablePosition().getRow()).setAttendenceScore(score
                        .getNewValue());
                score.getTableView().getItems()
                        .get(score.getTablePosition().getRow()).setFinalScore();
                tableViewinitialize();
                AnalysisStudents(students);/*重新分析学生成绩，实时更新各项数据*/
            }
        });

        homeworkScore.setCellFactory(cellFactoryInt);
        homeworkScore.setOnEditCommit(new EventHandler<CellEditEvent<Student, Integer>>() {
            @Override
            public void handle(CellEditEvent<Student, Integer> score) {
                score.getTableView().getItems()
                        .get(score.getTablePosition().getRow()).setHomeworkScore(score
                        .getNewValue());
                score.getTableView().getItems()
                        .get(score.getTablePosition().getRow()).setFinalScore();
                tableViewinitialize();
                AnalysisStudents(students);
            }
        });

        finalTestScore.setCellFactory(cellFactoryInt);
        finalTestScore.setOnEditCommit(new EventHandler<CellEditEvent<Student, Integer>>() {
            @Override
            public void handle(CellEditEvent<Student, Integer> score) {
                score.getTableView().getItems()
                        .get(score.getTablePosition().getRow()).setFinalTestScore(score
                        .getNewValue());
                score.getTableView().getItems()
                        .get(score.getTablePosition().getRow()).setFinalScore();
                tableViewinitialize();
                AnalysisStudents(students);
            }
        });

        testScore.setCellFactory(cellFactoryInt);
        testScore.setOnEditCommit(new EventHandler<CellEditEvent<Student, Integer>>() {
            @Override
            public void handle(CellEditEvent<Student, Integer> score) {
                score.getTableView().getItems()
                        .get(score.getTablePosition().getRow()).setTestScore(score
                        .getNewValue());
                score.getTableView().getItems()
                        .get(score.getTablePosition().getRow()).setFinalScore();
                //tableViewinitialize();
                //AnalysisStudents(students);
                studentView.getColumns().clear();
                studentView.getColumns().addAll(studentId, name, attendenceScore, testScore, homeworkScore, finalTestScore, finalScore);
            }
        });

        studentView.getColumns().addAll(studentId, name, attendenceScore, testScore, homeworkScore, finalTestScore, finalScore);

    }

}
