package project.storage;
import java.io.Serializable;
import java.util.*;
import project.models.*;
import project.interfaces.Researcher;
public class AppData implements Serializable {
    private static final long serialVersionUID = 1L;
    public Map<String, CreateUser> users = new LinkedHashMap<>();
    public List<EstablishCourse> courses = new ArrayList<>();
    public List<InitiateProject> projects = new ArrayList<>();
    public List<Researcher> researchers = new ArrayList<>();
    public List<AccessLog> logs = new ArrayList<>();
    public List<TeacherRating> ratings = new ArrayList<>();
    public List<Complaint> complaints = new ArrayList<>();
}
