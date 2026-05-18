package project.patterns;
import project.models.PublishResearchPaper;
public class SortByDate implements PaperSortStrategy {
    public int compare(PublishResearchPaper a, PublishResearchPaper b){ return b.getDatePublished().compareTo(a.getDatePublished()); }
}
