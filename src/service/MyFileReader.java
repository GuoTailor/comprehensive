package service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import model.Course;
import model.Student;
import view.MessageView;

/**
 * 
 * @author pinnuli
 */
public class MyFileReader {

	private File file;
	ArrayList<Student> students = new ArrayList<Student>();
	Course course;
	Object firstLine;

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}
	@SuppressWarnings("unchecked")  
	/*��ȡѧ����Ϣ*/
	public ArrayList<Student> getFileStudents() throws NumberFormatException {
		
		if (file.getName().toLowerCase().endsWith(".txt")) {
			try {
				BufferedReader br = null;
				br = new BufferedReader(new InputStreamReader(
						new FileInputStream(file), "GBK"));
				String line = null;
				if((line = br.readLine()) != null) {
					while ((line = br.readLine()) != null) {
						String[]obj = line.split(",");/*�ԡ������ָ�*/
						if (obj.length == 7) {
							Student student = new Student(obj[0], obj[1],
									Integer.parseInt(obj[2]),Integer.parseInt(obj[3]),
									Integer.parseInt(obj[4]),Integer.parseInt(obj[5]),
									Integer.parseInt(obj[6]));
							students.add(student);
						}
				}
				
				}
				br.close();
			} catch (FileNotFoundException e) {
				MessageView.createView("�Ҳ����ļ�!");
				e.printStackTrace();
			} catch (UnsupportedEncodingException e) {
				MessageView.createView("��֧��GBK�������");
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ArrayIndexOutOfBoundsException e) {
				MessageView.createView("txt�ļ����ݸ�ʽ����");
				e.printStackTrace();
			}
			return students;
		} else if (file.getName().toLowerCase().endsWith(".dat")) {
			FileInputStream fis = null;
			try {
				fis = new FileInputStream(file);
				ObjectInputStream ois = new ObjectInputStream(fis);
				if((firstLine= ois.readObject()) instanceof Course){
					course = (Course)firstLine;
				};
				students = (ArrayList<Student>) ois.readObject();
				ois.close();
			} catch (FileNotFoundException e) {
				MessageView.createView("�Ҳ����ļ�!");
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			return students;
		} else {
			MessageView.createView("�ļ���׺������");
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")  
	/*��ȡ�γ���Ϣ�洢��course��*/
	public Course getFileCourse() throws NumberFormatException {
		if (file.getName().toLowerCase().endsWith(".txt")) {
			try {
				BufferedReader br = null;
				br = new BufferedReader(new InputStreamReader(
						new FileInputStream(file), "GBK"));
				String line = null;
				if((line = br.readLine()) != null) {
					String[]obj = line.split(",");
					if(obj.length == 3) {
						course = new Course(obj[0], obj[1], obj[2]);
				}
				
				}
				br.close();
				if(course == null)
					MessageView.createView("�γ���Ϣδ����!");
			} catch (FileNotFoundException e) {
				MessageView.createView("�Ҳ����ļ�!");
				e.printStackTrace();
			} catch (UnsupportedEncodingException e) {
				MessageView.createView("��֧��GBK�������");
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ArrayIndexOutOfBoundsException e) {
				MessageView.createView("txt�ļ����ݸ�ʽ����");
				e.printStackTrace();
			}
			return course;
		} else if (file.getName().toLowerCase().endsWith(".dat")) {
			FileInputStream fis = null;
			try {
				fis = new FileInputStream(file);
				ObjectInputStream ois = new ObjectInputStream(fis);
				if((firstLine= ois.readObject()) instanceof Course){
					course = (Course)firstLine;
				};
				students = (ArrayList<Student>) ois.readObject();
				ois.close();
				MessageView.createView("��ȡ�ɹ�!");
			} catch (FileNotFoundException e) {
				MessageView.createView("�Ҳ����ļ�!");
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			return course;
		} else {
			MessageView.createView("�ļ���׺������");
		}
		return null;
	}
}
