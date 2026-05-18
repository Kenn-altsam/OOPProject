package project.interfaces;
import java.io.Serializable;
import java.util.List;
import java.util.Comparator;
import project.models.PublishResearchPaper;
public interface Researcher extends Serializable {
    String getResearcherName();
    int getHIndex();
    boolean isResearcher();
    List<PublishResearchPaper> getResearchPapers();
    void addPaper(PublishResearchPaper paper);
    default void printPapers(Comparator<PublishResearchPaper> comparator) {
        getResearchPapers().stream().sorted(comparator).forEach(System.out::println);
    }
}
