package project.models;
import java.io.Serializable;
import java.time.LocalDateTime;
public class AccessLog implements Serializable {
    private static final long serialVersionUID = 1L;
    private final String userId;
    private final String action;
    private final LocalDateTime timestamp;
    public AccessLog(String userId, String action){ this.userId=userId; this.action=action; this.timestamp=LocalDateTime.now(); }
    public String toString(){ return timestamp + " | user=" + userId + " | " + action; }
}
