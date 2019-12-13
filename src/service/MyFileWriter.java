package service;

import java.io.*;
import java.util.*;

import model.Course;
import model.Student;

/**
 * @author pinnuli
 */
public class MyFileWriter implements Serializable {
    public static final MyFileWriter instance = new MyFileWriter();

    private static HashMap<Student, ArrayList<Course>> map = MyFileReader.show();
    private ArrayList<String> list = MyFileReader.getList();

    public void save(HashMap<Student, ArrayList<Course>> map) {
        int num = 0;
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter("src/scorefile/16计机4班成绩.txt"));
            for (String s : list) {
                bw.write(s + ",");
            }
            bw.newLine();
            for (Map.Entry<Student, ArrayList<Course>> m : map.entrySet()) {
                Student student = m.getKey();
                bw.write(student.getStudentId() + "," + student.getName() + "," + student.getAttendenceScore() + ","
                        + student.getTestScore() + "," + student.getHomeworkScore() + ","
                        + student.getFinalTestScore() + "," + student.getFinalScore());
                ArrayList<Course> list = m.getValue();
                if (list != null) {
                    bw.write("#");
                    for (Course course : list) {
                        bw.write(course.getCourseName() + "=" + course.getScore() + ",");
                    }
                } else {
                    bw.write(list.get(num++) + "=" + 0 + ",");
                }
                bw.newLine();
            }
            bw.flush();
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void addCourse(String name) {
        list.add(name);
        for (Map.Entry<Student, ArrayList<Course>> m : map.entrySet()) {
            m.getValue().add(new Course(name, 0));
        }
        this.save(map);
    }

    public void update(Student student) {
        ArrayList<Course> course = new ArrayList<>();
        for (String s : list) {
            course.add(new Course(s, 0));
        }
        map.put(student, course);
//        for (int i = 0;i < list.size();i++) {
//            course.add(new Course(,0));
//        }
    }

    public void alter(Student student, String courseName, String score) {
        ArrayList<Course> lis = map.get(student);
        for (Course course : lis) {
            if (courseName.equals(course.getCourseName())) {
                try {
                    course.setScore(Integer.parseInt(score));
                } catch (NumberFormatException e) {
                    System.err.println("!请输入数字!");
                }
            }
        }
    }

    public int get(Student student, String courseName) {
        ArrayList<Course> lis = map.get(student);
        for (Course course : lis) {
            if (courseName.equals(course.getCourseName())) {
                return course.getScore();
            }
        }
        return 0;
    }
}
