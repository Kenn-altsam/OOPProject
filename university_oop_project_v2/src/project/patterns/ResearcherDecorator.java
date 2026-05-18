package project.patterns;

import java.io.Serializable;
import java.util.List;
import project.interfaces.Researcher;
import project.models.PublishResearchPaper;

public abstract class ResearcherDecorator implements Researcher, Serializable {
    private static final long serialVersionUID = 1L;
    protected Researcher wrapped;

    public ResearcherDecorator(Researcher wrapped) {
        this.wrapped = wrapped;
    }

    public String getResearcherName() {
        return wrapped.getResearcherName();
    }

    public int getHIndex() {
        return wrapped.getHIndex();
    }

    public boolean isResearcher() {
        return true;
    }

    public List<PublishResearchPaper> getResearchPapers() {
        return wrapped.getResearchPapers();
    }

    public void addPaper(PublishResearchPaper paper) {
        wrapped.addPaper(paper);
    }
}
