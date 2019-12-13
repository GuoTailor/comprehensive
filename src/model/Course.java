/**
 * 
 */
package model;

import java.io.Serializable;

/**
 * @author pinnuli
 *
 */
public class Course implements Serializable, Cloneable {
	private String courseName;
	private String courseTeacher;
	private String courseClass;
	
	public Course(String courseName, String courseTeacher, String courseClass) {
		this.courseName = courseName;
		this.courseTeacher = courseTeacher;
		this.courseClass = courseClass;
	}
	
	public String getCourseName() {
		return courseName;
	}
	
	public String getCourseTeacher() {
		return courseTeacher;
	}
	
	public String getCourseClass() {
		return courseClass;
	}
	
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	
	public void setCourseTeacher(String courseTeacher) {
		this.courseTeacher = courseTeacher;
	}
	
	public void setCourseClass(String courseClass) {
		this.courseClass = courseClass;
	}
	
	@Override
	public Object clone() throws CloneNotSupportedException {
		Course course = new Course(this.courseName, this.courseTeacher, this.courseClass);
		return course;
	}

	@Override
	public String toString() {
		return courseName + courseTeacher + courseClass;
	}
}
