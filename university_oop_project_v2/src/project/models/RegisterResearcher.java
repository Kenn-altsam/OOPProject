package project.models;
import java.util.*;
import project.interfaces.Researcher;

public class RegisterResearcher extends CreateEmployee implements Researcher {
    private static final long serialVersionUID = 1L;
    private int hIndex;
    private final List<PublishResearchPaper> papers = new ArrayList<>();
    public RegisterResearcher(String id, String username, String password, String name, String email, String department, int hIndex) {
        super(id, username, password, name, email, department);
        this.role = project.enums.UserRole.RESEARCHER;
        this.hIndex = hIndex;
    }
    public String getResearcherName(){ return name; }
    public int getHIndex(){ return hIndex; }
    public boolean isResearcher(){ return true; }
    public List<PublishResearchPaper> getResearchPapers(){ return papers; }
    public void addPaper(PublishResearchPaper paper){ papers.add(paper); }
}
