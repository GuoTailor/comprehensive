package service;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import model.Analysis;
import model.Course;
import model.Student;
import view.MessageView;

/**
 * @author pinnuli
 */
public class AnalysisService {

    private static HashMap<Student, ArrayList<Course>> map = MyFileReader.show();

    public HashMap<String, Integer> getTotalScore() {
        HashMap<String, Integer> smap = new HashMap<>();
        for (Map.Entry<Student, ArrayList<Course>> m : map.entrySet()) {
            Student student = m.getKey();
            Integer score = student.getFinalScore();
            for (Course course : m.getValue()) {
                score += course.getScore();
            }
            smap.put(student.getName(), score);
        }
        return smap;
    }

    private ArrayList<Integer> getScore(String courseName) {
        ArrayList<Integer> list = new ArrayList<>();
        for (Map.Entry<Student,ArrayList<Course>> m : map.entrySet()) {
            ArrayList<Course> courses = m.getValue();
            for (Course course : courses) {
                if (courseName.equals(course.getCourseName())) {
                    list.add(course.getScore());
                }
            }
        }
        return list;
    }


    public Analysis analyseFile(String courseName) {
        Analysis analysisModel = new Analysis();
        ArrayList<Integer> list = this.getScore(courseName);
        int max = Collections.max(list);
        int min = Collections.min(list);
        int totalNum = list.size();
        int excellent = 0;
        int well = 0;
        int mid = 0;
        int pass = 0;
        int fail = 0;
        int total = 0;
        for (Integer integer : list) {
            total += integer;

            if (integer >= 90 && integer <= 100)
                excellent++;
            else if (integer >= 80 && integer <= 89)
                well++;
            else if (integer >= 70 && integer <= 79)
                mid++;
            else if (integer >= 60 && integer <= 69)
                pass++;
            else if (integer >= 0 && integer <= 59)
                fail++;
            else {
                MessageView.createView("ÔØÈë·ÖÊý´íÎó" + integer);
            }
        }
        analysisModel.setMax(max);
        analysisModel.setMin(min);
        analysisModel.setAvg(total * 1.0 / totalNum);
        analysisModel.setExcellent(excellent);
        analysisModel.setWell(well);
        analysisModel.setMid(mid);
        analysisModel.setPass(pass);
        analysisModel.setFail(fail);
        analysisModel.setTotalNum(totalNum);
        return analysisModel;
    }
}
