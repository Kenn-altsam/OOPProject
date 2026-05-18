package project.patterns;

import project.models.RegisterResearcher;

public class EmployeeResearcherDecorator extends ResearcherDecorator {
    private static final long serialVersionUID = 1L;
    private final RegisterResearcher employee;

    public EmployeeResearcherDecorator(RegisterResearcher employee) {
        super(employee);
        this.employee = employee;
    }

    public String getResearcherName() {
        return "Employee-Researcher: " + employee.getName();
    }
}
