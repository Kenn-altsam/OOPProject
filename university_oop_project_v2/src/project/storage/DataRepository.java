package project.storage;
import java.io.IOException;
import java.time.LocalDate;
import java.util.*;
import project.enums.*;
import project.exceptions.AuthenticationException;
import project.interfaces.Researcher;
import project.models.*;
import project.patterns.*;

public class DataRepository {
    private static DataRepository instance;
    private final FileStorage storage = new FileStorage("university_data.ser");
    private AppData data;
    private DataRepository(){ loadOrSeed(); }
    public static DataRepository getInstance(){ if(instance == null) instance = new DataRepository(); return instance; }
    public AppData getData(){ return data; }

    private void loadOrSeed(){
        try { data = storage.load(); } catch(Exception e){ data = null; }
        if(data == null){ data = new AppData(); seed(); save(); }
    }
    public void save(){ try { storage.save(data); } catch(IOException e){ System.out.println("Save error: " + e.getMessage()); } }
    public void reset(){ data = new AppData(); seed(); save(); }
    public CreateUser authenticate(String username, String password) throws AuthenticationException {
        for(CreateUser user: data.users.values()){
            if(user.getUsername().equals(username) && user.login(password)) { log(user.getId(), "Login"); return user; }
        }
        throw new AuthenticationException("Wrong username or password.");
    }
    public void log(String userId, String action){ data.logs.add(new AccessLog(userId, action)); }
    public List<RegisterStudent> getStudents(){ List<RegisterStudent> r = new ArrayList<>(); for(CreateUser u: data.users.values()) if(u instanceof RegisterStudent) r.add((RegisterStudent)u); return r; }
    public List<CreateTeacher> getTeachers(){ List<CreateTeacher> r = new ArrayList<>(); for(CreateUser u: data.users.values()) if(u instanceof CreateTeacher) r.add((CreateTeacher)u); return r; }
    public List<AppointManager> getManagers(){ List<AppointManager> r = new ArrayList<>(); for(CreateUser u: data.users.values()) if(u instanceof AppointManager) r.add((AppointManager)u); return r; }
    public List<AdministerSystem> getAdmins(){ List<AdministerSystem> r = new ArrayList<>(); for(CreateUser u: data.users.values()) if(u instanceof AdministerSystem) r.add((AdministerSystem)u); return r; }
    public List<Researcher> getResearchers(){
        if(data.researchers == null) data.researchers = new ArrayList<>();
        return data.researchers;
    }
    public List<PublishResearchPaper> getAllPapers(PaperSortStrategy strategy){ List<PublishResearchPaper> papers = new ArrayList<>(); for(Researcher r: getResearchers()) papers.addAll(r.getResearchPapers()); papers.sort(strategy); return papers; }
    public Researcher topCitedResearcher(){
        Researcher best = null; int bestCitations = -1;
        for(Researcher r: getResearchers()){
            int total = r.getResearchPapers().stream().mapToInt(PublishResearchPaper::getCitations).sum();
            if(total > bestCitations){ bestCitations = total; best = r; }
        }
        return best;
    }
    public void addUser(CreateUser user){ data.users.put(user.getId(), user); log(user.getId(), "User added"); }
    public Optional<CreateUser> findUser(String id){ return Optional.ofNullable(data.users.get(id)); }
    public Optional<EstablishCourse> findCourse(String id){ return data.courses.stream().filter(c -> c.getId().equalsIgnoreCase(id)).findFirst(); }

    private void seed(){
        AdministerSystem admin = (AdministerSystem)CreateUserFactory.createUser("admin", "A0011", "admin", "admin", "Jamie Lee", "admin@uni.edu", "IT");
        RegisterStudent alice = (RegisterStudent)CreateUserFactory.createUser("student", "S10045", "student", "123", "Alice Chen", "alice@uni.edu", "", 4, "Computer Science", true, 4);
        RegisterStudent bob = (RegisterStudent)CreateUserFactory.createUser("student", "S10046", "bob", "123", "Bob Stone", "bob@uni.edu", "", 2, "Computer Science", false, 0);
        CreateTeacher prof = (CreateTeacher)CreateUserFactory.createUser("teacher", "T2031", "prof", "123", "Prof. Tan Wei", "tan@uni.edu", "CS", TeacherTitle.PROFESSOR, 12, true);
        CreateTeacher lector = (CreateTeacher)CreateUserFactory.createUser("teacher", "T2039", "lector", "123", "Dr. Maya Nova", "nova@uni.edu", "CS", TeacherTitle.LECTOR, 2, false);
        AppointManager manager = (AppointManager)CreateUserFactory.createUser("manager", "M0501", "manager", "123", "R. Santos", "santos@uni.edu", "Dean Office", ManagerType.DEAN_OFFICE);
        RegisterResearcher researcher = (RegisterResearcher)CreateUserFactory.createUser("researcher", "R7782", "researcher", "123", "Dr. External Researcher", "researcher@uni.edu", "Research Center", 7);
        data.users.put(admin.getId(), admin); data.users.put(alice.getId(), alice); data.users.put(bob.getId(), bob); data.users.put(prof.getId(), prof); data.users.put(lector.getId(), lector); data.users.put(manager.getId(), manager); data.users.put(researcher.getId(), researcher);
        EstablishCourse oop = new EstablishCourse("CS101", "Object-Oriented Programming", 5);
        EstablishCourse db = new EstablishCourse("CS202", "Database Systems", 4);
        data.courses.add(oop); data.courses.add(db);
        manager.assignCourseToTeacher(oop, prof); manager.assignCourseToTeacher(oop, lector); manager.assignCourseToTeacher(db, prof);
        oop.addLesson(new ScheduleLesson(LessonType.LECTURE, LocalDate.of(2026, 5, 20), "Inheritance and Polymorphism"));
        oop.addLesson(new ScheduleLesson(LessonType.PRACTICE, LocalDate.of(2026, 5, 21), "UML and Console Demo"));
        PublishResearchPaper p1 = new PublishResearchPaper("AI in Education", Arrays.asList(prof.getName(), alice.getName()), "IEEE Education", "10.0000/ai-edu", LocalDate.of(2025, 7, 10), 60, 12, Arrays.asList("AI", "Education"));
        PublishResearchPaper p2 = new PublishResearchPaper("Research-Oriented University Systems", Arrays.asList(researcher.getName()), "University Journal", "10.0000/uni-sys", LocalDate.of(2026, 1, 15), 25, 9, Arrays.asList("University", "OOP"));
        Researcher decoratedAlice = new StudentResearcherDecorator(alice);
        Researcher decoratedProf = new TeacherResearcherDecorator(prof);
        Researcher decoratedExternal = new EmployeeResearcherDecorator(researcher);
        data.researchers.add(decoratedAlice);
        data.researchers.add(decoratedProf);
        data.researchers.add(decoratedExternal);
        decoratedProf.addPaper(p1); decoratedAlice.addPaper(p1); decoratedExternal.addPaper(p2);
        try { alice.assignSupervisor(prof); alice.registerForCourse(oop); prof.putMark(alice, oop, new GenerateGrade(28, 27, 35)); } catch(Exception ignored) { }
        data.logs.add(new AccessLog(admin.getId(), "Seed data created"));
    }
}
