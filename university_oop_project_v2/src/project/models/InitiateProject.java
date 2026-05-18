package project.models;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.*;
import project.exceptions.NotResearcherException;
import project.interfaces.Researcher;

public class InitiateProject implements Serializable {
    private static final long serialVersionUID = 1L;
    private String topic;
    private final List<Researcher> participants = new ArrayList<>();
    private final List<PublishResearchPaper> papers = new ArrayList<>();
    private LocalDate startDate;
    public InitiateProject(String topic, LocalDate startDate){ this.topic=topic; this.startDate=startDate; }
    public String getTopic(){ return topic; }
    public LocalDate getStartDate(){ return startDate; }
    public List<Researcher> getParticipants(){ return participants; }
    public List<PublishResearchPaper> getPapers(){ return papers; }
    public void addParticipant(Object person) throws NotResearcherException {
        if(!(person instanceof Researcher) || !((Researcher)person).isResearcher()) throw new NotResearcherException("Only a real researcher can join a research project.");
        Researcher researcher = (Researcher) person;
        if(!participants.contains(researcher)) participants.add(researcher);
    }
    public void addPaper(PublishResearchPaper paper){ papers.add(paper); }
    public String toString(){ return "Project{" + topic + ", start=" + startDate + ", participants=" + participants.size() + ", papers=" + papers.size() + "}"; }
}
