package project.models;
import java.io.Serializable;
import java.time.LocalDateTime;
import project.enums.ComplaintStatus;
public class Complaint implements Serializable {
    private static final long serialVersionUID = 1L;
    private final String id;
    private final CreateEmployee sender;
    private final CreateUser target;
    private final String subject;
    private ComplaintStatus status = ComplaintStatus.OPEN;
    private final LocalDateTime createdAt = LocalDateTime.now();
    public Complaint(String id, CreateEmployee sender, CreateUser target, String subject) {
        this.id=id; this.sender=sender; this.target=target; this.subject=subject;
    }
    public void sign(){ status = ComplaintStatus.SIGNED; }
    public void close(){ status = ComplaintStatus.CLOSED; }
    public String toString(){ return id + " | " + sender.getName() + " -> " + target.getName() + " | " + subject + " | " + status + " | " + createdAt; }
}
