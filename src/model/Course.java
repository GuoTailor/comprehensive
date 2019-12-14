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
	private Integer studyScore;
	private Integer studyTime;
	private String courseId;
	
	public Course(String courseName, Integer score,String courseId,Integer studyTime,Integer studyScore) {
		this.courseId = courseId;
		this.studyTime = studyTime;
		this.studyScore = studyScore;
		this.courseName = courseName;
		this.score = score;
	}

	public Course(String courseName, Integer studyScore, Integer studyTime, String courseId) {
		this.courseName = courseName;
		this.studyScore = studyScore;
		this.studyTime = studyTime;
		this.courseId = courseId;
	}

	public Integer getStudyScore() {
		return studyScore;
	}

	public void setStudyScore(Integer studyScore) {
		this.studyScore = studyScore;
	}

	public Integer getStudyTime() {
		return studyTime;
	}

	public void setStudyTime(Integer studyTime) {
		this.studyTime = studyTime;
	}

	public String getCourseId() {
		return courseId;
	}

	public void setCourseId(String courseId) {
		this.courseId = courseId;
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
		Course course = new Course(this.courseName, this.score, this.courseId,this.studyTime,this.studyScore);
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
