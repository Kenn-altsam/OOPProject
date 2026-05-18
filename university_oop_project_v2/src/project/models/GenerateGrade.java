package project.models;
import java.io.Serializable;
public class GenerateGrade implements Serializable {
    private static final long serialVersionUID = 1L;
    private double att1;
    private double att2;
    private double finalExam;
    public GenerateGrade(double att1, double att2, double finalExam){ this.att1=att1; this.att2=att2; this.finalExam=finalExam; }
    public double calculateTotal(){ return att1 + att2 + finalExam; }
    public String getLetterGrade(){
        double t = calculateTotal();
        if(t >= 95) return "A"; if(t >= 90) return "A-"; if(t >= 85) return "B+"; if(t >= 80) return "B"; if(t >= 75) return "B-"; if(t >= 70) return "C+"; if(t >= 65) return "C"; if(t >= 60) return "C-"; if(t >= 55) return "D+"; if(t >= 50) return "D"; return "F";
    }
    public double toGpaPoint(){
        String g = getLetterGrade();
        switch(g){ case "A": return 4.0; case "A-": return 3.67; case "B+": return 3.33; case "B": return 3.0; case "B-": return 2.67; case "C+": return 2.33; case "C": return 2.0; case "C-": return 1.67; case "D+": return 1.33; case "D": return 1.0; default: return 0.0; }
    }
    public String toString(){ return "att1=" + att1 + ", att2=" + att2 + ", final=" + finalExam + ", total=" + calculateTotal() + ", grade=" + getLetterGrade(); }
}
