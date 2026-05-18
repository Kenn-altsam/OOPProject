package project.ui;
import java.time.LocalDate;
import java.util.*;
import project.enums.*;
import project.exceptions.*;
import project.interfaces.Researcher;
import project.models.*;
import project.patterns.*;
import project.storage.*;

public class ConsoleApp {
    private final Scanner in = new Scanner(System.in);
    private final DataRepository repo = DataRepository.getInstance();
    private CreateUser current;

    public void run(){
        System.out.println("=== Research-Oriented University System ===");
        System.out.println("Demo accounts: admin/admin | student/123 | prof/123 | manager/123 | researcher/123 | bob/123");
        while(true){
            if(current == null) loginMenu(); else mainMenu();
        }
    }
    private void loginMenu(){
        System.out.println("\n1. Login\n2. Reset demo data\n0. Exit");
        int c = readInt("Choose: ");
        if(c == 0){ repo.save(); System.out.println("Bye."); System.exit(0); }
        if(c == 2){ repo.reset(); System.out.println("Demo data reset."); return; }
        String username = readLine("Username: "); String password = readLine("Password: ");
        try { current = repo.authenticate(username, password); System.out.println("Logged in as " + current.getName() + " (" + current.getRole() + ")"); }
        catch(AuthenticationException e){ System.out.println(e.getMessage()); }
    }
    private void mainMenu(){
        System.out.println("\n--- Main menu for " + current.getName() + " ---");
        System.out.println("1. View courses");
        System.out.println("2. Student: register for course");
        System.out.println("3. Student: view marks/transcript");
        System.out.println("4. Student: rate teacher");
        System.out.println("5. Teacher: view students and put mark");
        System.out.println("6. Employee: send message / complaint");
        System.out.println("7. Manager: assign teacher / report / GPA list");
        System.out.println("8. Admin: add/update/remove user / logs");
        System.out.println("9. Research: papers, project, top cited");
        System.out.println("10. Save data");
        System.out.println("11. Logout");
        System.out.println("0. Exit");
        int c = readInt("Choose: ");
        try {
            switch(c){
                case 1: viewCourses(); break;
                case 2: studentRegisterCourse(); break;
                case 3: studentTranscript(); break;
                case 4: studentRateTeacher(); break;
                case 5: teacherActions(); break;
                case 6: employeeCommunication(); break;
                case 7: managerActions(); break;
                case 8: adminActions(); break;
                case 9: researchActions(); break;
                case 10: repo.save(); System.out.println("Saved."); break;
                case 11: repo.log(current.getId(), "Logout"); current = null; break;
                case 0: repo.save(); System.out.println("Saved. Bye."); System.exit(0); break;
                default: System.out.println("Wrong option.");
            }
        } catch(Exception e){ System.out.println("Error: " + e.getMessage()); }
    }
    private void viewCourses(){ repo.getData().courses.forEach(System.out::println); }
    private EstablishCourse chooseCourse(){ viewCourses(); String id = readLine("Course id: "); return repo.findCourse(id).orElse(null); }
    private CreateTeacher chooseTeacher(){ List<CreateTeacher> ts = repo.getTeachers(); for(int i=0;i<ts.size();i++) System.out.println((i+1)+". "+ts.get(i)); int n=readInt("Teacher number: "); return n>=1&&n<=ts.size()?ts.get(n-1):null; }
    private RegisterStudent chooseStudent(){ List<RegisterStudent> ss = repo.getStudents(); for(int i=0;i<ss.size();i++) System.out.println((i+1)+". "+ss.get(i)); int n=readInt("Student number: "); return n>=1&&n<=ss.size()?ss.get(n-1):null; }
    private void studentRegisterCourse() throws Exception {
        if(!(current instanceof RegisterStudent)){ System.out.println("Only student can use this."); return; }
        EstablishCourse c = chooseCourse(); if(c==null){ System.out.println("Course not found."); return; }
        ((RegisterStudent)current).registerForCourse(c); repo.log(current.getId(), "Registered for " + c.getId()); System.out.println("Registration successful.");
    }
    private void studentTranscript(){
        if(!(current instanceof RegisterStudent)){ System.out.println("Only student can use this."); return; }
        System.out.println(((RegisterStudent)current).getTranscript());
    }
    private void studentRateTeacher(){
        if(!(current instanceof RegisterStudent)){ System.out.println("Only student can use this."); return; }
        CreateTeacher t = chooseTeacher(); if(t==null) return; int score = readInt("Score 1-5: ");
        TeacherRating rating = new TeacherRating((RegisterStudent)current, t, score); repo.getData().ratings.add(rating); repo.log(current.getId(), "Rated teacher " + t.getId()); System.out.println(rating);
    }
    private void teacherActions(){
        if(!(current instanceof CreateTeacher)){ System.out.println("Only teacher can use this."); return; }
        CreateTeacher teacher = (CreateTeacher)current;
        EstablishCourse c = chooseCourse(); if(c==null) return;
        System.out.println("Students: " + teacher.viewStudents(c));
        RegisterStudent s = chooseStudent(); if(s==null) return;
        double a1=readDouble("Att1: "), a2=readDouble("Att2: "), fin=readDouble("Final: ");
        teacher.putMark(s, c, new GenerateGrade(a1,a2,fin)); repo.log(current.getId(), "Put mark to " + s.getId()); System.out.println("Mark saved.");
    }
    private void employeeCommunication(){
        if(!(current instanceof CreateEmployee)){ System.out.println("Only employee can use this."); return; }
        CreateEmployee e = (CreateEmployee)current;
        System.out.println("1. Send message to teacher\n2. Send complaint about user\n3. View my inbox");
        int c = readInt("Choose: ");
        if(c==1){ CreateTeacher t = chooseTeacher(); String text=readLine("Text: "); System.out.println(e.sendMessage(t,text)); }
        else if(c==2){ RegisterStudent s = chooseStudent(); String subject=readLine("Subject: "); Complaint cmp=e.sendComplaint(subject, s); repo.getData().complaints.add(cmp); System.out.println(cmp); }
        else if(c==3){ e.getInbox().forEach(System.out::println); }
    }
    private void managerActions(){
        if(!(current instanceof AppointManager)){ System.out.println("Only manager can use this."); return; }
        AppointManager m = (AppointManager)current;
        System.out.println("1. Assign teacher to course\n2. Create course report\n3. View students by GPA\n4. Add lesson to course");
        int c=readInt("Choose: ");
        if(c==1){ EstablishCourse course=chooseCourse(); CreateTeacher t=chooseTeacher(); if(course!=null && t!=null){ m.assignCourseToTeacher(course,t); System.out.println("Assigned."); } }
        else if(c==2){ EstablishCourse course=chooseCourse(); if(course!=null) System.out.println(m.createReport(course)); }
        else if(c==3){ m.viewStudentsByGpa(repo.getStudents()).forEach(System.out::println); }
        else if(c==4){ EstablishCourse course=chooseCourse(); if(course!=null){ LessonType type = readLine("Type LECTURE/PRACTICE: ").equalsIgnoreCase("PRACTICE")?LessonType.PRACTICE:LessonType.LECTURE; String date=readLine("Date yyyy-mm-dd: "); String topic=readLine("Topic: "); course.addLesson(new ScheduleLesson(type, LocalDate.parse(date), topic)); System.out.println("Lesson added and observers notified."); } }
    }
    private void adminActions(){
        if(!(current instanceof AdministerSystem)){ System.out.println("Only admin can use this."); return; }
        AdministerSystem admin = (AdministerSystem)current;
        System.out.println("1. Add student\n2. Update user\n3. Remove user\n4. View logs\n5. View users");
        int c=readInt("Choose: ");
        if(c==1){ String id=readLine("Id: "), username=readLine("Username: "), name=readLine("Name: "), email=readLine("Email: "); int year=readInt("Year: "); RegisterStudent s = new RegisterStudent(id, username, "123", name, email, year, "Computer Science", false, 0); admin.addUser(repo.getData(), s); System.out.println("Student added with password 123."); }
        else if(c==2){ String id=readLine("Id: "), name=readLine("New name: "), email=readLine("New email: "); System.out.println(admin.updateUser(repo.getData(), id, name, email)?"Updated":"Not found"); }
        else if(c==3){ String id=readLine("Id: "); System.out.println(admin.removeUser(repo.getData(), id)?"Removed":"Not found"); }
        else if(c==4){ admin.viewLogs(repo.getData()).forEach(System.out::println); }
        else if(c==5){ repo.getData().users.values().stream().sorted().forEach(System.out::println); }
    }
    private void researchActions() throws Exception {
        System.out.println("1. Print all papers by citations\n2. Print all papers by date\n3. Print all papers by pages\n4. Add paper to current researcher\n5. Create research project\n6. Top cited researcher\n7. Test bad supervisor exception");
        int c=readInt("Choose: ");
        if(c==1) repo.getAllPapers(new SortByCitations()).forEach(System.out::println);
        else if(c==2) repo.getAllPapers(new SortByDate()).forEach(System.out::println);
        else if(c==3) repo.getAllPapers(new SortByPages()).forEach(System.out::println);
        else if(c==4){
            if(!(current instanceof Researcher) || !((Researcher)current).isResearcher()){ System.out.println("Current user is not a researcher."); return; }
            String title=readLine("Title: "); int citations=readInt("Citations: "); int pages=readInt("Pages: ");
            PublishResearchPaper paper = new PublishResearchPaper(title, Arrays.asList(current.getName()), "Demo Journal", "10.demo/"+System.nanoTime(), LocalDate.now(), citations, pages, Arrays.asList("OOP","University"));
            ((Researcher)current).addPaper(paper); System.out.println("Paper added.");
        }
        else if(c==5){
            String topic=readLine("Topic: "); InitiateProject p = new InitiateProject(topic, LocalDate.now()); p.addParticipant(current); repo.getData().projects.add(p); System.out.println("Project created: " + p);
        }
        else if(c==6){ Researcher r=repo.topCitedResearcher(); System.out.println(r==null?"No researchers":r.getResearcherName()+" h-index="+r.getHIndex()); }
        else if(c==7){ RegisterStudent s = chooseStudent(); CreateTeacher t = chooseTeacher(); if(s!=null&&t!=null){ s.assignSupervisor(t); System.out.println("Supervisor assigned."); } }
    }
    private String readLine(String prompt){ System.out.print(prompt); return in.nextLine().trim(); }
    private int readInt(String prompt){ while(true){ try { return Integer.parseInt(readLine(prompt)); } catch(Exception e){ System.out.println("Enter integer."); } } }
    private double readDouble(String prompt){ while(true){ try { return Double.parseDouble(readLine(prompt)); } catch(Exception e){ System.out.println("Enter number."); } } }
}
