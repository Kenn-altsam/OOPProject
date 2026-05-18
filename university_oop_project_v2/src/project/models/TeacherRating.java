package project.models;
import java.io.Serializable;
public class TeacherRating implements Serializable {
    private static final long serialVersionUID = 1L;
    private final RegisterStudent student;
    private final CreateTeacher teacher;
    private final int score;
    public TeacherRating(RegisterStudent student, CreateTeacher teacher, int score){ this.student=student; this.teacher=teacher; this.score=score; teacher.addRating(score); }
    public String toString(){ return student.getName() + " rated " + teacher.getName() + " = " + score + "/5"; }
}
