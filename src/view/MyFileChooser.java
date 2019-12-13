package view;

import java.io.File;

import javafx.stage.FileChooser;

/**
 * 
 * @author pinnuli
 */
public class MyFileChooser {

	public static File chooseFile() {
		File file = null;
		try {
			FileChooser fileChooser = new FileChooser();
			fileChooser.getExtensionFilters().addAll(
					new FileChooser.ExtensionFilter("�ı��ļ� (*.*.txt)", "*.txt"),
					new FileChooser.ExtensionFilter("�����ƵĶ����ļ� (*.*.dat)",
							"*.dat"));
						/*����ѡ���ı��ļ��Ͷ������ļ�*/
			file = fileChooser.showOpenDialog(null);
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
		return file;
	}

	public static File chooseSaveFile(String filename) {
		File file = null;
		try {
			FileChooser fileChooser = new FileChooser();
			fileChooser.setInitialFileName(filename);
			fileChooser.getExtensionFilters().addAll(
					new FileChooser.ExtensionFilter("�ı��ļ� (*.*.txt)", "*.txt"),
					new FileChooser.ExtensionFilter("�����ƵĶ����ļ� (*.*.dat)",
							"*.dat"));
			file = fileChooser.showSaveDialog(null);
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
		return file;
	}
}
