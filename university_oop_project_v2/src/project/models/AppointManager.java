package project.models;
import java.util.*;
import project.enums.ManagerType;
import project.enums.UserRole;
import project.interfaces.ReportGenerator;

public class AppointManager extends CreateEmployee implements ReportGenerator {
    private static final long serialVersionUID = 1L;
    private ManagerType managerType;
    public AppointManager(String id, String username, String password, String name, String email, String department, ManagerType managerType) {
        super(id, username, password, name, email, department, UserRole.MANAGER);
        this.managerType = managerType;
    }
    public ManagerType getManagerType(){ return managerType; }
    public void approveRegistration(RegisterStudent student, EstablishCourse course) throws Exception { student.registerForCourse(course); }
    public void assignCourseToTeacher(EstablishCourse course, CreateTeacher teacher){ course.addInstructor(teacher); teacher.addCourse(course); }
    public void addCourseForRegistration(List<EstablishCourse> courses, EstablishCourse course){ if(!courses.contains(course)) courses.add(course); }
    public String createReport(EstablishCourse course){
        int total = course.getEnrolledStudents().size(); double sum = 0; int marked = 0;
        for(RegisterStudent s: course.getEnrolledStudents()){
            GenerateGrade g = s.getTranscriptMap().get(course);
            if(g != null){ sum += g.calculateTotal(); marked++; }
        }
        return "Report for " + course.getName() + ": students=" + total + ", marked=" + marked + ", average=" + (marked==0?"N/A":String.format("%.2f", sum/marked));
    }
    public List<RegisterStudent> viewStudentsByGpa(List<RegisterStudent> students){
        List<RegisterStudent> copy = new ArrayList<>(students);
        copy.sort(Comparator.comparingDouble(RegisterStudent::getGpa).reversed().thenComparing(RegisterStudent::getName));
        return copy;
    }
    public String toString(){ return super.toString() + " managerType=" + managerType; }
}
