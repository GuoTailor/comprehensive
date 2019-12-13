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
						/*��д��γ���Ϣ*/
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
					/*д��ѧ����Ϣ*/
					bw.flush();
					bw.close();
					MessageView.createView("�޸ĳɹ�!");
			} else if (file.getName().toLowerCase().endsWith(".dat")) {
				FileOutputStream fos = null;
				fos = new FileOutputStream(file);
				ObjectOutputStream oos = new ObjectOutputStream(fos);
				oos.writeObject(course);
				oos.writeObject(students);
				/*.dat�ļ�д��Course�����Student����*/
				oos.flush();
				oos.close();
				MessageView.createView("�޸ĳɹ�!");
			} else {
				MessageView.createView("��׺������!");
			}
		} catch (FileNotFoundException e) {
			MessageView.createView("δ�ҵ��ļ�!");
			e.printStackTrace();
		} catch (IOException e) {
			MessageView.createView("�޸�ʧ��!");
			e.printStackTrace();
		}
		
	}

}
