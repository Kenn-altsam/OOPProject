package project.models;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import project.enums.UserRole;
import project.interfaces.CanSendMessage;

public class CreateEmployee extends CreateUser implements CanSendMessage {
    private static final long serialVersionUID = 1L;
    protected String department;
    protected final List<Message> inbox = new ArrayList<>();
    protected final List<Complaint> complaints = new ArrayList<>();

    public CreateEmployee(String id, String username, String password, String name, String email, String department) {
        this(id, username, password, name, email, department, UserRole.EMPLOYEE);
    }
    protected CreateEmployee(String id, String username, String password, String name, String email, String department, UserRole role) {
        super(id, username, password, name, email, role);
        this.department = department;
    }
    public String getDepartment() { return department; }
    public List<Message> getInbox() { return inbox; }
    public List<Complaint> getComplaints() { return complaints; }
    @Override public Message sendMessage(CreateEmployee to, String text) {
        Message message = new Message("MSG-" + System.nanoTime(), this, to, "Message", text, LocalDateTime.now());
        to.inbox.add(message);
        return message;
    }
    public Complaint sendComplaint(String subject, CreateUser target) {
        Complaint complaint = new Complaint("CMP-" + System.nanoTime(), this, target, subject);
        complaints.add(complaint);
        return complaint;
    }
}
