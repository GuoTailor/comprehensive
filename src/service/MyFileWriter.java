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
				}
				bw.newLine();
			}
			bw.flush();
            bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }


    public void saveFile(File file, Course course, ArrayList<Student> students) {
        try {
            if (file.getName().toLowerCase().endsWith(".txt")) {
                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "GBK"));

                bw.write(course.getCourseName() + "," + course.getScore());
                bw.newLine();

                for (Student student : students) {
                    bw.write(student.getStudentId() + "," + student.getName() + "," + student.getAttendenceScore() + ","
                            + student.getTestScore() + "," + student.getHomeworkScore() + ","
                            + student.getFinalTestScore() + "," + student.getFinalScore());
                    bw.newLine();
                }
                bw.flush();
                bw.close();
                MessageView.createView("保存成功!");
            } else if (file.getName().toLowerCase().endsWith(".dat")) {
                FileOutputStream fos = null;
                fos = new FileOutputStream(file);
                ObjectOutputStream oos = new ObjectOutputStream(fos);
                oos.writeObject(course);
                oos.writeObject(students);
                oos.flush();
                oos.close();
                MessageView.createView("保存成功!!");
            } else {
                MessageView.createView("后缀名错误!");
            }
        } catch (FileNotFoundException e) {
            MessageView.createView("未找到文件!");
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
            MessageView.createView("保存失败!");
        }
    }
}
