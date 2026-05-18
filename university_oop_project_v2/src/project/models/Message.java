package project.models;
import java.io.Serializable;
import java.time.LocalDateTime;
public class Message implements Serializable {
    private static final long serialVersionUID = 1L;
    private final String id;
    private final CreateEmployee from;
    private final CreateEmployee to;
    private final String subject;
    private final String text;
    private final LocalDateTime date;
    public Message(String id, CreateEmployee from, CreateEmployee to, String subject, String text, LocalDateTime date) {
        this.id=id; this.from=from; this.to=to; this.subject=subject; this.text=text; this.date=date;
    }
    public String getId(){ return id; }
    public String toString(){ return id + " | from=" + from.getName() + " to=" + to.getName() + " | " + subject + ": " + text + " | " + date; }
}
