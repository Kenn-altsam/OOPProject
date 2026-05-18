package project.patterns;

import project.models.RegisterStudent;

public class StudentResearcherDecorator extends ResearcherDecorator {
    private static final long serialVersionUID = 1L;
    private final RegisterStudent student;

    public StudentResearcherDecorator(RegisterStudent student) {
        super(student);
        this.student = student;
    }

    public String getResearcherName() {
        return "Student-Researcher: " + student.getName();
    }
}
