package project.models;
import java.io.Serializable;
import java.time.LocalDate;
import project.enums.RegistrationStatus;
public class Registration implements Serializable {
    private static final long serialVersionUID = 1L;
    private final String id;
    private final RegisterStudent student;
    private final EstablishCourse course;
    private RegistrationStatus status = RegistrationStatus.PENDING;
    private final LocalDate date = LocalDate.now();
    public Registration(String id, RegisterStudent student, EstablishCourse course){ this.id=id; this.student=student; this.course=course; }
    public void approve(){ status = RegistrationStatus.APPROVED; }
    public void reject(){ status = RegistrationStatus.REJECTED; }
    public String toString(){ return id + " | " + student.getName() + " -> " + course.getName() + " | " + status + " | " + date; }
}
