package service;

import java.util.ArrayList;

import model.Analysis;
import model.Student;
import view.MessageView;
/**
 * 
 * @author pinnuli
 */
public class AnalysisService {

	public Analysis analyseFile(ArrayList<Student> students) {
		Analysis analysisModel = new Analysis();
		int max = 0;
		int min = 2147483647;
		double totalScore = 0;
		int excellent = 0;
		int well = 0;
		int mid = 0;
		int pass = 0;
		int fail = 0;
		int totalNum = students.size();
		for (Student student : students) {
			int score = student.getFinalScore();
			if (score > max)
				max = score;
			if (score < min)
				min = score;
			totalScore += score;
			if (score >= 90 && score <= 100)
				excellent++;
			else if (score >= 80 && score <= 89)
				well++;
			else if (score >= 70 && score <= 79)
				mid++;
			else if (score >= 60 && score <= 69)
				pass++;
			else if (score >= 0 && score <= 59)
				fail++;
			else {
				MessageView.createView("ÔØÈë·ÖÊý´íÎó" + score);
			}
		}
		analysisModel.setMax(max);
		analysisModel.setMin(min);
		analysisModel.setAvg(totalScore / totalNum);
		analysisModel.setExcellent(excellent);
		analysisModel.setWell(well);
		analysisModel.setMid(mid);
		analysisModel.setPass(pass);
		analysisModel.setFail(fail);
		analysisModel.setTotalNum(totalNum);
		return analysisModel;
	}

}
