package service;

import java.util.ArrayList;

import model.Student;

/**
 * 
 * @author pinnuli
 */
public class SearchService {

	public ArrayList<Student> search(ArrayList<Student> students, String message) {
		ArrayList<Student> studentsTemp = new ArrayList<Student>();
		for (Student student : students) {
			if (student.toString().contains(message))/*�����Ƿ�����ؼ���*/
				studentsTemp.add(student);
		}
		return studentsTemp;
	}
}
