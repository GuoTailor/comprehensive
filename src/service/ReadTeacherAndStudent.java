package service;

import model.Student;
import model.Teacher;

import java.io.*;
import java.util.HashMap;

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
}
