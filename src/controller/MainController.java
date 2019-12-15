package controller;

import java.io.File;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.function.Consumer;

import javafx.beans.property.ReadOnlyIntegerWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
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
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;
import javafx.util.Callback;
import javafx.beans.value.ObservableValue;

import javafx.util.StringConverter;
import model.*;
import service.*;
import view.*;

public class MainController implements Initializable {
    String fileName;
    String filePath;
    @FXML
    private Text status;

    private ArrayList<Student> students = null;
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
    private ComboBox<String> cb;

    @FXML
    private SearchBox searchBox;

    private void AnalysisStudents(ArrayList<Student> students, String courseName) {
        AnalysisService analysisService = new AnalysisService();
        analysis = analysisService.analyseFile(courseName);

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

    }

    @FXML
    private void saveFile() {
        MyFileWriter myFileWriter = new MyFileWriter();
        if (students == null || students.isEmpty()) {
            MessageView.createView("学生信息未载入");
            return;
        }
        File file;
        file = MyFileChooser.chooseSaveFile(fileName);
        if (file != null) {
//            myFileWriter.saveFile(file, course, students);/*将数据输出到文件*/
            myFileWriter.save(MyFileReader.show());
        }
    }

    @FXML
    private void updateFile() {
        MyFileWriter myFileWriter = new MyFileWriter();
        if (students == null || students.isEmpty()) {
            MessageView.createView("学生信息未载入");
            return;
        }
//        File file = new File(filePath);
//        myFileWriter.saveFile(file, course, students);
        myFileWriter.save(MyFileReader.show());
    }

    @FXML
    private void closeAll() {
        System.exit(0);
    }

    @FXML
    public TableView<Student> studentView;
    public List<TableColumn<Student, ?>> tableColumnList = new ArrayList<>();

    public TableColumn<Student, String> studentId = new TableColumn<>("学号");
    public TableColumn<Student, String> name = new TableColumn<>("姓名");
    public TableColumn<Student, Integer> attendenceScore = new TableColumn<>("考勤");
    public TableColumn<Student, Integer> finalScore = new TableColumn<>("总分");

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
        MyFileWriter.instance.delete(sheet);
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
        studentView.getSelectionModel().select(row, pos.getTableColumn());

        // scroll to new row
//		new InsertStudent().updateFile(data);
        students.add(data);
        MyFileWriter.instance.update(data);
        studentView.scrollTo(data);
    }

    @FXML
    private void addSubject() {
        String subject = new AlertBox().show();
        boolean l = MyFileWriter.instance.isDeleteOrAddCourse(true, subject);
        if (l) {
            tableColumnList.clear();
            tableViewinitialize();
        } else {
            MessageView.createView("添加失败，可能重复!");
        }
    }

    @FXML
    private void deleteSubject() {
        String subject = new AlertBox().show();
        boolean l = MyFileWriter.instance.isDeleteOrAddCourse(false, subject);
        if (l) {
            tableColumnList.clear();
            tableViewinitialize();
        } else {
            MessageView.createView("删除失败，可能没有!");
        }
    }

    private void onChoice(String subject) {
        AnalysisStudents(students, subject);
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
        Map<Student, ArrayList<Course>> datas = MyFileReader.show();

        students = new ArrayList<>(datas.keySet());

        if (students.size() != 0) {
            ObservableList<Student> data = FXCollections.observableArrayList(students);

            AnalysisStudents(students, MyFileReader.getList().get(0));/*分析学生成绩*/
            studentView.setItems(data);/*填充数据*/
            status.setText("共" + analysis.getTotalNum() + "人");
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
        cb.setItems(FXCollections.observableArrayList(MyFileReader.getList()));
        cb.setValue(MyFileReader.getList().get(0));
        cb.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue observable, String oldValue, String newValue) {
                onChoice(newValue);
            }
        });

        studentId.setCellValueFactory(new PropertyValueFactory<>("studentId"));
        studentId.setPrefWidth(125);/*设置表格初始化*/

        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        name.setPrefWidth(60);

        attendenceScore.setCellValueFactory(new PropertyValueFactory<>("attendenceScore"));
        attendenceScore.setPrefWidth(60);

        finalScore.setCellValueFactory(new PropertyValueFactory<>("finalScore"));
        finalScore.setPrefWidth(61);


        studentView.getColumns().clear();
        studentView.setEditable(true);/*设置可编辑，修改功能*/

        studentId.setEditable(true);
        attendenceScore.setEditable(true);


        studentId.setCellFactory(TextFieldTableCell.forTableColumn());/*监听表格学生信息并修改对象数据*/
        studentId.setOnEditCommit(new EventHandler<CellEditEvent<Student, String>>() {
            @Override
            public void handle(CellEditEvent<Student, String> score) {
                score.getTableView().getItems()
                        .get(score.getTablePosition().getRow()).setStudentId(score
                        .getNewValue());
                reload();/*表格重新初始化*/
            }
        });

        name.setCellFactory(TextFieldTableCell.forTableColumn());
        name.setOnEditCommit(new EventHandler<CellEditEvent<Student, String>>() {
            @Override
            public void handle(CellEditEvent<Student, String> info) {
                info.getTableView().getItems()
                        .get(info.getTablePosition().getRow()).setName(info
                        .getNewValue());
                reload();
            }
        });

        Callback<TableColumn<Student, Integer>, TableCell<Student, Integer>> cellFactoryInteger = TextFieldTableCell.forTableColumn(new StringConverter<Integer>() {
            @Override
            public String toString(Integer object) {
                return (object != null) ? object.toString() : "";
            }

            @Override
            public Integer fromString(String string) {
                if (string == null || string.isEmpty()) {
                    return 0;
                }
                if (!string.matches("^[1-9]\\d*$"))
                    MessageView.createView("请输入整数");
                else {
                    return Integer.parseInt(string);
                }
                return null;
            }
        });

        attendenceScore.setCellFactory(cellFactoryInteger);
        attendenceScore.setOnEditCommit(new EventHandler<CellEditEvent<Student, Integer>>() {
            @Override
            public void handle(CellEditEvent<Student, Integer> score) {
                score.getTableView().getItems()
                        .get(score.getTablePosition().getRow()).setAttendenceScore(score
                        .getNewValue());
                MyFileWriter.total(score.getTableView().getItems()
                        .get(score.getTablePosition().getRow()));
                reload();
            }
        });

        tableColumnList.add(studentId);
        tableColumnList.add(name);
        tableColumnList.add(attendenceScore);
        for (String str : MyFileReader.getList()) {
            TableColumn<Student, TableColumn<Student, Course>> item = new TableColumn<>(str);

            TableColumn<Student, String> courseId = new TableColumn<>("课程号");
            TableColumn<Student, Integer> studyTime = new TableColumn<>("学时数");
            TableColumn<Student, Integer> studyScore = new TableColumn<>("学分");
            TableColumn<Student, Integer> score = new TableColumn<>("得分");
            item.getColumns().addAll(score, courseId, studyTime, studyScore);

            courseId.setEditable(true);
            courseId.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Student, String>, ObservableValue<String>>() {
                @Override
                public ObservableValue<String> call(TableColumn.CellDataFeatures<Student, String> param) {
                    return new ReadOnlyStringWrapper(MyFileWriter.instance.getScoreInfo(param.getValue(), str).getCourseId());
                }
            });
            courseId.setCellFactory(TextFieldTableCell.forTableColumn());
            courseId.setOnEditCommit(new EventHandler<CellEditEvent<Student, String>>() {
                @Override
                public void handle(CellEditEvent<Student, String> score) {
                    MyFileWriter.instance.alter(score.getTableView().getItems()
                            .get(score.getTablePosition().getRow()), str, "课程号", score.getNewValue());
                    MyFileWriter.total(score.getTableView().getItems()
                            .get(score.getTablePosition().getRow()));
                    reload();
                }
            });
            courseId.setPrefWidth(60);

            studyTime.setEditable(true);
            studyTime.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Student, Integer>, ObservableValue<Integer>>() {
                @Override
                public ObservableValue<Integer> call(TableColumn.CellDataFeatures<Student, Integer> param) {
                    return (ObservableValue) new ReadOnlyIntegerWrapper(MyFileWriter.instance.getScoreInfo(param.getValue(), str).getStudyTime());
                }
            });
            studyTime.setCellFactory(cellFactoryInteger);
            studyTime.setOnEditCommit(new EventHandler<CellEditEvent<Student, Integer>>() {
                @Override
                public void handle(CellEditEvent<Student, Integer> score) {
                    MyFileWriter.instance.alter(score.getTableView().getItems()
                            .get(score.getTablePosition().getRow()), str, "学时数", score.getNewValue().toString());
                    MyFileWriter.total(score.getTableView().getItems()
                            .get(score.getTablePosition().getRow()));
                    reload();
                }
            });
            studyTime.setPrefWidth(60);

            studyScore.setEditable(true);
            studyScore.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Student, Integer>, ObservableValue<Integer>>() {
                @Override
                public ObservableValue<Integer> call(TableColumn.CellDataFeatures<Student, Integer> param) {
                    return (ObservableValue) new ReadOnlyIntegerWrapper(MyFileWriter.instance.getScoreInfo(param.getValue(), str).getStudyScore());
                }
            });
            studyScore.setCellFactory(cellFactoryInteger);
            studyScore.setOnEditCommit(new EventHandler<CellEditEvent<Student, Integer>>() {
                @Override
                public void handle(CellEditEvent<Student, Integer> score) {
                    MyFileWriter.instance.alter(score.getTableView().getItems()
                            .get(score.getTablePosition().getRow()), str, "学分", score.getNewValue().toString());
                    MyFileWriter.total(score.getTableView().getItems()
                            .get(score.getTablePosition().getRow()));
                    reload();
                }
            });
            studyScore.setPrefWidth(60);

            score.setEditable(true);
            score.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Student, Integer>, ObservableValue<Integer>>() {
                @Override
                public ObservableValue<Integer> call(TableColumn.CellDataFeatures<Student, Integer> param) {
                    return (ObservableValue) new ReadOnlyIntegerWrapper(MyFileWriter.instance.getScoreInfo(param.getValue(), str).getScore());
                }
            });
            score.setCellFactory(cellFactoryInteger);
            score.setOnEditCommit(new EventHandler<CellEditEvent<Student, Integer>>() {
                @Override
                public void handle(CellEditEvent<Student, Integer> score) {
                    MyFileWriter.instance.alter(score.getTableView().getItems()
                            .get(score.getTablePosition().getRow()), str, "得分", score.getNewValue().toString());
                    MyFileWriter.total(score.getTableView().getItems()
                            .get(score.getTablePosition().getRow()));
                    reload();
                }
            });
            score.setPrefWidth(60);
            tableColumnList.add(item);
        }
        tableColumnList.add(finalScore);
        studentView.getColumns().addAll(tableColumnList);
    }

    private void reload() {
        AnalysisStudents(students, MyFileReader.getList().get(0));
        studentView.getColumns().clear();
        studentView.getColumns().addAll(tableColumnList);
    }
}
