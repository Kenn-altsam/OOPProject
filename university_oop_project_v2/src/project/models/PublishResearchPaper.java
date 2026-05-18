package project.models;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.*;
public class PublishResearchPaper implements Comparable<PublishResearchPaper>, Serializable {
    private static final long serialVersionUID = 1L;
    private String title;
    private List<String> authors;
    private String journal;
    private String doi;
    private LocalDate datePublished;
    private int citations;
    private int pages;
    private List<String> keywords;
    public PublishResearchPaper(String title, List<String> authors, String journal, String doi, LocalDate datePublished, int citations, int pages, List<String> keywords){
        this.title=title; this.authors=new ArrayList<>(authors); this.journal=journal; this.doi=doi; this.datePublished=datePublished; this.citations=citations; this.pages=pages; this.keywords=new ArrayList<>(keywords);
    }
    public String getTitle(){ return title; }
    public LocalDate getDatePublished(){ return datePublished; }
    public int getCitations(){ return citations; }
    public int getPages(){ return pages; }
    public int compareTo(PublishResearchPaper other){ return this.title.compareToIgnoreCase(other.title); }
    public String toString(){ return title + " | citations=" + citations + " | pages=" + pages + " | date=" + datePublished + " | journal=" + journal + " | doi=" + doi; }
}
