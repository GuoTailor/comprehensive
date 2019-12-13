/**
 * 
 */
package service;

import java.io.*;
import java.util.ArrayList;

import model.Course;
import model.Student;
import view.MessageView;

/**
 * @author gyh
 *
 */
public class InsertStudent {

	public void updateFile(Student student) {
		try {
			PrintWriter p = new PrintWriter(new FileOutputStream("src/scorefile/16¼Æ»ú4°à³É¼¨.txt"));
			p.println(student.getStudentId()+","+student.getName()+","+student.getAttendenceScore()+","+student.getHomeworkScore()
			+","+student.getFinalTestScore()+","+student.getFinalScore());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

}
