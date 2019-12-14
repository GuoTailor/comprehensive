package service;

import java.io.*;
import java.util.*;
import java.util.function.Predicate;

import model.Course;
import model.Student;
import view.MessageView;

public class MyFileWriter implements Serializable {
    public static final MyFileWriter instance = new MyFileWriter();

    private static HashMap<Student, ArrayList<Course>> map = MyFileReader.show();
    private ArrayList<String> list = MyFileReader.getList();

    /**
     * 保存
     *
     * @param map
     */
    public boolean save(HashMap<Student, ArrayList<Course>> map) {
        int num = 0;
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter("src/scorefile/16计机4班成绩.txt"));
            for (String s : list) {
                bw.write(s + ",");
            }
            bw.newLine();
            for (Map.Entry<Student, ArrayList<Course>> m : map.entrySet()) {
                Student student = m.getKey();
                bw.write(student.getStudentId() + "," + student.getName() + "," + student.getAttendenceScore() + "," + student.getFinalScore());
                ArrayList<Course> list = m.getValue();
                if (list != null) {
                    bw.write("#");
                    for (Course course : list) {
                        bw.write(course.getCourseName() + "=" + course.getScore() + "=" + course.getCourseId() + "="
                                + course.getStudyTime() + "=" + course.getStudyScore() + ",");
                    }
                } else {
                    bw.write(this.list.get(num++) + "=" + "0" + "=" + "0" + "=" + 0 + "=" + "0" + ",");
                }
                bw.newLine();
            }
            bw.flush();
            bw.close();
            MessageView.createView("保存成功!");
        } catch (IOException e) {
            e.printStackTrace();
            MessageView.createView("保存失败!");
            return false;
        }
        return true;
    }

    /**
     * @param option true代表添加课程  false代表删除
     * @param name   添加或者修改的课程名字
     */
    public boolean isDeleteOrAddCourse(boolean option, String name) {
        boolean flag = false;
        for (String subject : list) {
            if (name.equals(subject) && option) {
                return false;
            }
            if (name.equals(subject)) {
                flag = true;
                break;
            }
        }
        if (option)
            list.add(name);
        else if (flag)
            list.remove(name);
        else
            return false;

        for (Map.Entry<Student, ArrayList<Course>> m : map.entrySet()) {
            if (option) {//true
                m.getValue().add(new Course(name, 0, "0", 0, 0));
            } else {//false
                m.getValue().removeIf(new Predicate<Course>() {
                    @Override
                    public boolean test(Course course) {
                        return name.equals(course.getCourseName());
                    }
                });
            }
        }
        return this.save(map);
    }

    public void delete(Student student) {
        map.remove(student);
    }

    public void update(Student student) {
        ArrayList<Course> course = new ArrayList<>();
        for (String s : list) {
            course.add(new Course(s, 0, "0", 0, 0));
        }
        map.put(student, course);
//        for (int i = 0;i < list.size();i++) {
//            course.add(new Course(,0));
//        }
    }

    public void alter(Student student, String courseName, String courseInfo, String result) {
        ArrayList<Course> lis = map.get(student);
        for (Course course : lis) {
            if (courseName.equals(course.getCourseName())) {
                if (courseInfo.equals(course.getCourseName())) {
                    course.setCourseName(result);
                } else if (courseInfo.equals(course.getScore())) {
                    course.setScore(Integer.parseInt(result));
                } else if (courseInfo.equals(course.getStudyTime())) {
                    course.setStudyTime(Integer.parseInt(result));
                } else if (courseInfo.equals(course.getCourseId())) {
                    course.setCourseId(result);
                } else {
                    course.setStudyScore(Integer.parseInt(result));
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

    public Course getScoreInfo(Student student, String courseName) {
        ArrayList<Course> lis = map.get(student);
        for (Course course : lis) {
            if (courseName.equals(course.getCourseName())) {
                return course;
            }
        }
        return null;
    }

}
