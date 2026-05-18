package project.patterns;
import project.models.PublishResearchPaper;
public class SortByCitations implements PaperSortStrategy {
    public int compare(PublishResearchPaper a, PublishResearchPaper b){ return Integer.compare(b.getCitations(), a.getCitations()); }
}
