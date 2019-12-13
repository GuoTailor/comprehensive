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
	private Integer score;
	
	public Course(String courseName, Integer score) {
		this.courseName = courseName;
		this.score = score;
	}

	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}

	public String getCourseName() {
		return courseName;
	}

	
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	
	@Override
	public Object clone() throws CloneNotSupportedException {
		Course course = new Course(this.courseName, this.score);
		return course;
	}

	@Override
	public String toString() {
		return "Course{" +
				"courseName='" + courseName + '\'' +
				", score=" + score +
				'}';
	}
}
