# Requirements Checklist

| Requirement | Status | Where |
|---|---:|---|
| Console-based project | Done | `project.Main`, `project.ui.ConsoleApp` |
| Authentication before using system | Done | `DataRepository.authenticate()` |
| User / Employee / Teacher / Manager / Student / Admin | Done | `project.models` |
| Course / Mark / Lesson | Done | `EstablishCourse`, `GenerateGrade`, `ScheduleLesson` |
| Researcher / ResearchPaper / ResearchProject | Done | `Researcher`, `ResearcherDecorator`, `PublishResearchPaper`, `InitiateProject` |
| Lesson type lecture/practice enum | Done | `LessonType` |
| Teacher title enum | Done | `TeacherTitle` |
| Manager type enum | Done | `ManagerType` |
| More than 1 instructor per course | Done | `List<CreateTeacher> instructors` |
| 21-credit limit | Done | `RegisterStudent.canRegister()` |
| failCount > 3 exception | Done | `FailLimitException` |
| 4th year supervisor h-index >= 3 | Done | `InsufficientHIndexException` |
| Research project rejects non-researcher | Done | `NotResearcherException` |
| Research paper sorting by citations/date/pages | Done | `SortByCitations`, `SortByDate`, `SortByPages` |
| Top cited researcher | Done | `DataRepository.topCitedResearcher()` |
| Report generation | Done | `AppointManager.createReport()` |
| Comparable / Comparator | Done | `CreateUser`, `PublishResearchPaper`, sort strategies |
| equals / hashCode / toString | Done | Core models |
| Serialization | Done | `FileStorage`, `AppData`, `DataRepository.save()` |
| 4+ design patterns | Done | Singleton, Factory, Strategy, Observer, Decorator |
| Packages | Done | `models`, `enums`, `exceptions`, `interfaces`, `patterns`, `storage`, `ui` |
| HTML documentation | Done | `docs/index.html` |
