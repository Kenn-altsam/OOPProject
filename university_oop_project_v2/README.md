# Research-Oriented University System — OOP Console Project

This project is rebuilt to match the uploaded project description, class diagram, use case diagram and object diagram.

## Main mapping to the UML class diagram

| UML name | Java class |
|---|---|
| CreateUser | `project.models.CreateUser` |
| CreateEmployee | `project.models.CreateEmployee` |
| CreateTeacher | `project.models.CreateTeacher` |
| RegisterStudent | `project.models.RegisterStudent` |
| AppointManager | `project.models.AppointManager` |
| AdministerSystem | `project.models.AdministerSystem` |
| EstablishCourse | `project.models.EstablishCourse` |
| ScheduleLesson | `project.models.ScheduleLesson` |
| GenerateGrade | `project.models.GenerateGrade` |
| RegisterResearcher | `project.models.RegisterResearcher` |
| PublishResearchPaper | `project.models.PublishResearchPaper` |
| InitiateProject | `project.models.InitiateProject` |

## Packages

- `project.models` — domain classes from the class/object diagram
- `project.enums` — `TeacherTitle`, `ManagerType`, `LessonType`, etc.
- `project.exceptions` — custom exceptions
- `project.interfaces` — interfaces such as `Researcher`, `CourseObserver`
- `project.patterns` — Factory, Strategy and Decorator pattern classes
- `project.storage` — repository, serialization, database-like storage
- `project.ui` — console menus

## Design patterns

1. Singleton — `DataRepository`
2. Factory — `CreateUserFactory.createUser(...)`
3. Strategy — `PaperSortStrategy`, `SortByCitations`, `SortByDate`, `SortByPages`
4. Observer — `CourseObserver` and `EstablishCourse.attach/detach/notifyObservers`
5. Decorator — `ResearcherDecorator`, `StudentResearcherDecorator`, `TeacherResearcherDecorator`, `EmployeeResearcherDecorator`

## How to run

```bash
javac -d out $(find src -name "*.java")
java -cp out project.Main
```

On Windows PowerShell:

```powershell
Get-ChildItem -Recurse -Filter *.java src | ForEach-Object { $_.FullName } > sources.txt
javac -d out @sources.txt
java -cp out project.Main
```

## Demo accounts

- `admin / admin`
- `student / 123`
- `prof / 123`
- `manager / 123`
- `researcher / 123`
- `bob / 123`

## Implemented requirements

- Authentication before access
- Required classes and UML-style action names
- Course registration with credit limit check
- More than one instructor per course
- Teacher can put marks
- Mark = attestation 1 + attestation 2 + final exam
- Student can view marks and transcript
- Student can rate teacher
- Manager can assign teachers, create reports, view students by GPA
- Admin can add, update, remove users and view logs
- Research papers with title, authors, journal, DOI, date, citations, pages, keywords
- Research paper sorting by citations, date and pages
- Top cited researcher
- Research project rejects non-researchers
- 4th year supervisor h-index rule
- Serialization to `university_data.ser`
- Documentation in `docs/index.html`
