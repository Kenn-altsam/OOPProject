package project.patterns;
import project.models.PublishResearchPaper;
public class SortByPages implements PaperSortStrategy {
    public int compare(PublishResearchPaper a, PublishResearchPaper b){ return Integer.compare(b.getPages(), a.getPages()); }
}
