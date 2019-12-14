package service;

import model.Course;
import model.Student;
import model.Teacher;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ReadTeacherAndStudent {

    public HashMap<String, Object> readInfo(String info) {
        HashMap<String, Object> map = new HashMap<>();
        Object obj = null;
        try {
            BufferedReader bf = new BufferedReader(new InputStreamReader(new FileInputStream(info)));
            String message = bf.readLine();
            while (message != null) {
                String[] values = message.split(",");
                obj = new Student(values[0], values[1], values[2]);
                map.put(values[0], obj);
                message = bf.readLine();
            }
            bf.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return map;
    }

    public static void writeStudent() throws IOException {
        HashMap<Student, ArrayList<Course>> map = MyFileReader.show();
        PrintWriter p = new PrintWriter(new FileWriter("src/scorefile/student.txt"));
        for (Map.Entry<Student, ArrayList<Course>> m : map.entrySet()) {
            Student student = m.getKey();
            p.println(student.getStudentId() + "," + student.getName() + "," + "123456");
        }
        p.close();
    }
}
