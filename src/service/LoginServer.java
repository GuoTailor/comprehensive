package service;

import model.Student;
import java.util.HashMap;

public class LoginServer {
    private ReadTeacherAndStudent readTeacher = new ReadTeacherAndStudent();

    public Login login(String name, String password, String path) {
        HashMap<String, Object> map = readTeacher.readInfo(path);
        Object obj = map.get(name);
        if (obj != null) {
            Student student = (Student) obj;
            if (name.equals(student.getStudentId()) && password.equals(student.getPassword())) {
                return new Login(student.getName(), true);
            }
        }
        return new Login("’À∫≈ªÚ’ﬂ√‹¬Î¥ÌŒÛ", false);
    }

    public static class Login {
        public String mag;
        public boolean isLogin;

        public Login(String msg, boolean isLogin) {
            this.mag = msg;
            this.isLogin = isLogin;
        }
    }
}
