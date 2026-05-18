package project.models;

import java.io.Serializable;
import java.time.LocalDate;
import project.enums.LessonType;

public class ScheduleLesson implements Serializable {
    private static final long serialVersionUID = 1L;
    private LessonType type;
    private LocalDate date;
    private String topic;

    public ScheduleLesson(LessonType type, LocalDate date, String topic) {
        this.type = type;
        this.date = date;
        this.topic = topic;
    }

    public LessonType getType(){ return type; }
    public LocalDate getDate(){ return date; }
    public String getTopic(){ return topic; }
    public String toString(){ return type + " | " + date + " | " + topic; }
}
