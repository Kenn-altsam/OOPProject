package project.models;
import java.util.*;
import project.enums.UserRole;
import project.exceptions.*;
import project.interfaces.CourseObserver;
import project.interfaces.Researcher;

public class RegisterStudent extends CreateUser implements CourseObserver, Researcher {
    private static final long serialVersionUID = 1L;
    private int year;
    private String major;
    private int currentCredits;
    private int failCount;
    private double gpa;
    private Researcher supervisor;
    private boolean researcher;
    private int hIndex;
    private final List<EstablishCourse> courses = new ArrayList<>();
    private final Map<EstablishCourse, GenerateGrade> transcript = new LinkedHashMap<>();
    private final List<PublishResearchPaper> papers = new ArrayList<>();
    private final List<String> notifications = new ArrayList<>();

    public RegisterStudent(String id, String username, String password, String name, String email, int year, String major, boolean researcher, int hIndex) {
        super(id, username, password, name, email, UserRole.STUDENT);
        this.year=year; this.major=major; this.researcher=researcher; this.hIndex=hIndex;
    }
    public int getYear(){ return year; }
    public int getCurrentCredits(){ return currentCredits; }
    public int getFailCount(){ return failCount; }
    public String getMajor(){ return major; }
    public double getGpa(){ return gpa; }
    public List<EstablishCourse> getCourses(){ return courses; }
    public Map<EstablishCourse, GenerateGrade> getTranscriptMap(){ return transcript; }
    public List<String> getNotifications(){ return notifications; }
    public void setFailCount(int failCount){ this.failCount = failCount; }
    public void assignSupervisor(Researcher supervisor) throws InsufficientHIndexException {
        if(year == 4 && supervisor.getHIndex() < 3) throw new InsufficientHIndexException("4th year student's supervisor must have h-index >= 3.");
        this.supervisor = supervisor;
    }
    public boolean canRegister(EstablishCourse course) throws CreditExceedException, FailLimitException {
        if(failCount > 3) throw new FailLimitException("Student cannot register because failCount > 3.");
        if(currentCredits + course.getCredits() > 21) throw new CreditExceedException("Student cannot register for more than 21 credits.");
        return !courses.contains(course);
    }
    public void registerForCourse(EstablishCourse course) throws CreditExceedException, FailLimitException {
        if(canRegister(course)) {
            courses.add(course); currentCredits += course.getCredits(); course.enroll(this); course.attach(this); transcript.putIfAbsent(course, null);
        }
    }
    public String viewCourses(){ return courses.isEmpty()?"No registered courses.":courses.toString(); }
    public void putGrade(EstablishCourse course, GenerateGrade grade){ transcript.put(course, grade); recalculateGpa(); }
    public String viewMarks(){
        StringBuilder sb = new StringBuilder();
        for(Map.Entry<EstablishCourse, GenerateGrade> e: transcript.entrySet()) sb.append(e.getKey().getName()).append(" -> ").append(e.getValue()==null?"No mark":e.getValue()).append('\n');
        return sb.length()==0?"No marks yet.":sb.toString();
    }
    public String getTranscript(){ return viewMarks() + "GPA=" + String.format("%.2f", gpa); }
    public void rateTeacher(CreateTeacher teacher, int score){ teacher.addRating(score); }
    public void update(String message){ notifications.add(message); }
    public void recalculateGpa(){
        double points=0; int count=0;
        for(GenerateGrade g: transcript.values()) if(g != null) { points += g.toGpaPoint(); count++; }
        gpa = count == 0 ? 0 : points / count;
    }
    public String getResearcherName(){ return name; }
    public int getHIndex(){ return hIndex; }
    public boolean isResearcher(){ return researcher; }
    public List<PublishResearchPaper> getResearchPapers(){ return papers; }
    public void addPaper(PublishResearchPaper paper){ if(researcher) papers.add(paper); }
    public String toString(){ return super.toString() + " year=" + year + ", major=" + major + ", credits=" + currentCredits + ", failCount=" + failCount + ", GPA=" + String.format("%.2f", gpa); }
}
