package service;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.Course;
import model.Student;
import view.MessageView;

/**
 * @author pinnuli
 */
public class MyFileWriter implements Serializable {

	private static HashMap<Student,ArrayList<Course>> map = MyFileReader.show();

    public void save(HashMap<Student,ArrayList<Course>> map) {
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter("src/scorefile/16计机4班成绩.txt"));
			for (Map.Entry<Student,ArrayList<Course>> m : map.entrySet()) {
				Student student = m.getKey();
				bw.write(student.getStudentId() + "," + student.getName() + "," + student.getAttendenceScore() + ","
						+ student.getTestScore() + "," + student.getHomeworkScore() + ","
						+ student.getFinalTestScore() + "," + student.getFinalScore());
				ArrayList<Course> list = m.getValue();
				if (list != null) {
					bw.write("#");
					for (Course course : list) {
                        bw.write(course.getCourseName() + "=" + course.getScore()+",");
                    }
				}else {
				    bw.write("null"+0+",");
                }
				bw.newLine();
			}
			bw.flush();
            bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

    public void put(Student student){
        ArrayList<String> list = MyFileReader.getList();
        ArrayList<Course> course = new ArrayList<>();
        for (int i = 0;i < list.size();i++) {
            course.add(new Course("null",0));
        }
    }

    public void alter (Student student,String courseName,String score) {
        ArrayList<Course> lis = map.get(student);
        for (Course course : lis) {
            if (courseName.equals(course.getCourseName())) {
                try {
                    course.setScore(Integer.parseInt(score));
                }catch (NumberFormatException e){
                    System.err.println("!请输入数字!");
                }
            }
        }
    }
}
