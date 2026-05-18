package project.patterns;

import project.enums.*;
import project.models.*;

public class CreateUserFactory {
    public static CreateUser createUser(String type, String id, String username,
            String password, String name, String email, String department, Object... extras) {
        switch (type.toLowerCase()) {
            case "student": {
                int year = (Integer) extras[0];
                String major = (String) extras[1];
                boolean researcher = (Boolean) extras[2];
                int hIndex = (Integer) extras[3];
                return createStudent(id, username, password, name, email, year, major, researcher, hIndex);
            }
            case "teacher": {
                TeacherTitle title = (TeacherTitle) extras[0];
                int hIndex = (Integer) extras[1];
                boolean researcher = (Boolean) extras[2];
                return createTeacher(id, username, password, name, email, department, title, hIndex, researcher);
            }
            case "manager": {
                ManagerType managerType = (ManagerType) extras[0];
                return createManager(id, username, password, name, email, department, managerType);
            }
            case "admin":
                return createAdmin(id, username, password, name, email, department);
            case "researcher": {
                int hIndex = (Integer) extras[0];
                return createResearcher(id, username, password, name, email, department, hIndex);
            }
            default:
                throw new IllegalArgumentException("Unknown type: " + type);
        }
    }

    public static CreateUser createStudent(String id, String username, String password, String name, String email, int year, String major, boolean researcher, int hIndex){
        return new RegisterStudent(id, username, password, name, email, year, major, researcher, hIndex);
    }
    public static CreateUser createTeacher(String id, String username, String password, String name, String email, String department, TeacherTitle title, int hIndex, boolean researcher){
        return new CreateTeacher(id, username, password, name, email, department, title, hIndex, researcher);
    }
    public static CreateUser createManager(String id, String username, String password, String name, String email, String department, ManagerType type){
        return new AppointManager(id, username, password, name, email, department, type);
    }
    public static CreateUser createAdmin(String id, String username, String password, String name, String email, String department){
        return new AdministerSystem(id, username, password, name, email, department);
    }
    public static CreateUser createResearcher(String id, String username, String password, String name, String email, String department, int hIndex){
        return new RegisterResearcher(id, username, password, name, email, department, hIndex);
    }
}
