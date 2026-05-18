package project.models;
import java.util.ArrayList;
import java.util.List;
import project.enums.TeacherTitle;
import project.enums.UserRole;
import project.interfaces.Researcher;

public class CreateTeacher extends CreateEmployee implements Researcher {
    private static final long serialVersionUID = 1L;
    private TeacherTitle title;
    private final List<EstablishCourse> courses = new ArrayList<>();
    private final List<PublishResearchPaper> papers = new ArrayList<>();
    private int hIndex;
    private boolean researcher;
    private double ratingSum = 0;
    private int ratingCount = 0;

    public CreateTeacher(String id, String username, String password, String name, String email, String department, TeacherTitle title, int hIndex, boolean researcher) {
        super(id, username, password, name, email, department, UserRole.TEACHER);
        this.title = title;
        this.researcher = researcher || title == TeacherTitle.PROFESSOR;
        this.hIndex = title == TeacherTitle.PROFESSOR ? Math.max(3, hIndex) : hIndex;
    }
    public TeacherTitle getTitle(){ return title; }
    public List<EstablishCourse> getCourses(){ return courses; }
    public void addCourse(EstablishCourse course){ if(!courses.contains(course)) courses.add(course); }
    public String viewCourses(){ return courses.isEmpty()?"No courses assigned.":courses.toString(); }
    public void putMark(RegisterStudent student, EstablishCourse course, GenerateGrade mark){ student.putGrade(course, mark); }
    public List<RegisterStudent> viewStudents(EstablishCourse course){ return course.getEnrolledStudents(); }
    public void addRating(int score){ if(score >= 1 && score <= 5){ ratingSum += score; ratingCount++; } }
    public double getAverageRating(){ return ratingCount == 0 ? 0 : ratingSum / ratingCount; }
    public String getResearcherName(){ return name; }
    public int getHIndex(){ return hIndex; }
    public boolean isResearcher(){ return researcher; }
    public List<PublishResearchPaper> getResearchPapers(){ return papers; }
    public void addPaper(PublishResearchPaper paper){ if(researcher) papers.add(paper); }
    public String toString(){ return super.toString() + " title=" + title + ", hIndex=" + hIndex + ", researcher=" + researcher + ", rating=" + String.format("%.2f", getAverageRating()); }
}
