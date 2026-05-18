package project.models;
import java.io.Serializable;
import java.util.*;
import project.interfaces.CourseObserver;

public class EstablishCourse implements Serializable {
    private static final long serialVersionUID = 1L;
    private String id;
    private String name;
    private int credits;
    private final List<CreateTeacher> instructors = new ArrayList<>();
    private final List<RegisterStudent> students = new ArrayList<>();
    private final List<ScheduleLesson> lessons = new ArrayList<>();
    private final List<CourseObserver> observers = new ArrayList<>();

    public EstablishCourse(String id, String name, int credits){ this.id=id; this.name=name; this.credits=credits; }
    public String getId(){ return id; }
    public String getName(){ return name; }
    public int getCredits(){ return credits; }
    public void addInstructor(CreateTeacher teacher){ if(!instructors.contains(teacher)) instructors.add(teacher); }
    public void enroll(RegisterStudent student){ if(!students.contains(student)) students.add(student); }
    public void addLesson(ScheduleLesson lesson){ lessons.add(lesson); notifyObservers("New lesson added to " + name + ": " + lesson.getTopic()); }
    public void attach(CourseObserver observer){ if(!observers.contains(observer)) observers.add(observer); }
    public void detach(CourseObserver observer){ observers.remove(observer); }
    public void notifyObservers(String message){ for(CourseObserver o: observers) o.update(message); }
    public List<CreateTeacher> getCourseInstructors(){ return Collections.unmodifiableList(instructors); }
    public List<RegisterStudent> getEnrolledStudents(){ return students; }
    public List<ScheduleLesson> getLessons(){ return lessons; }
    public String toString(){ return id + " - " + name + " (" + credits + " credits, instructors=" + instructors.size() + ", students=" + students.size() + ")"; }
    public boolean equals(Object o){ return o instanceof EstablishCourse && Objects.equals(id, ((EstablishCourse)o).id); }
    public int hashCode(){ return Objects.hash(id); }
}
