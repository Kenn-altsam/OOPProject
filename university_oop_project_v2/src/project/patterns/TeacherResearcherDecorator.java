package project.patterns;

import project.models.CreateTeacher;

public class TeacherResearcherDecorator extends ResearcherDecorator {
    private static final long serialVersionUID = 1L;
    private final CreateTeacher teacher;

    public TeacherResearcherDecorator(CreateTeacher teacher) {
        super(teacher);
        this.teacher = teacher;
    }

    public String getResearcherName() {
        return "Teacher-Researcher: " + teacher.getName();
    }
}
