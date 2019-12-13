/**
 * 
 */
package service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.io.Serializable;
import java.util.ArrayList;

import model.Course;
import model.Student;
import view.MessageView;

/**
 * @author pinnuli
 *
 */
public class MyFileUpdate implements Serializable{

	public void updateFile(File file, Course course, ArrayList<Student> students) {
		try {
			if (file.getName().toLowerCase().endsWith(".txt")) {
				BufferedWriter bw = null;
				bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "GBK"));
				
					
						bw.write(course.getCourseName() + ","
								+ course.getCourseTeacher() + ","
								+ course.getCourseClass());
						bw.newLine();
						/*先写入课程信息*/
					for (Student student : students) {
						bw.write(student.getStudentId() + ","
								+ student.getName() + "," 
								+ student.getAttendenceScore() + ","
								+ student.getTestScore() + ","
								+ student.getHomeworkScore() + ","
								+ student.getFinalTestScore() + ","
								+ student.getFinalScore());
						bw.newLine();
					}
					/*写入学生信息*/
					bw.flush();
					bw.close();
					MessageView.createView("修改成功!");
			} else if (file.getName().toLowerCase().endsWith(".dat")) {
				FileOutputStream fos = null;
				fos = new FileOutputStream(file);
				ObjectOutputStream oos = new ObjectOutputStream(fos);
				oos.writeObject(course);
				oos.writeObject(students);
				/*.dat文件写入Course对象和Student对象*/
				oos.flush();
				oos.close();
				MessageView.createView("修改成功!");
			} else {
				MessageView.createView("后缀名错误!");
			}
		} catch (FileNotFoundException e) {
			MessageView.createView("未找到文件!");
			e.printStackTrace();
		} catch (IOException e) {
			MessageView.createView("修改失败!");
			e.printStackTrace();
		}
		
	}

}
