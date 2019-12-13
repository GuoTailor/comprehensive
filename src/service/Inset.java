package service;

import model.Student;

public class Inset {
    public void insert(String studentId, String name, int attendenceScore, int testScore, int homeworkScore, int finalTestScore,int finalScore){
        Student student = new Student(studentId,name,attendenceScore,testScore,homeworkScore,finalTestScore,finalScore);
        new InsertStudent().updateFile(student);
    }

}
