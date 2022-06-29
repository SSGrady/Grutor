package com.example.grutor.Modals;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseUser;

import java.util.Date;

@ParseClassName("Lessons")
public class Lessons extends ParseObject {
    public static final String KEY_TYPE_OF_LESSON = "type";
    public static final String KEY_ASSIGNMENT_LENGTH = "assignmentLength";
    public static final String KEY_TOPIC = "topic";
    public static final String KEY_SUBJECT = "subject";
    public static final String KEY_DESCRIPTION = "description";
    public static final String KEY_URGENCY = "urgency";
    public static final String KEY_STUDENT_POINTER = "student";
    public static final String KEY_STUDENT_TUTOR_POINTER = "studentTutor";

    public String getTypeOfLesson() {return getString(KEY_TYPE_OF_LESSON);}
    public void setTypeOfLesson(String lessonType) { put(KEY_TYPE_OF_LESSON, lessonType);}
    public String getAssignmentLength() {return getString(KEY_ASSIGNMENT_LENGTH);}
    public void setAssignmentLength(String numberOfProblems) { put(KEY_ASSIGNMENT_LENGTH, numberOfProblems);}
    public String getTutoringSubject() { return getString(KEY_SUBJECT);}
    public void setTutoringSubject(String subject) { put(KEY_SUBJECT, subject);}
    public String getTutoringTopic() { return getString(KEY_TOPIC);}
    public void setTutoringTopic(String topic) {put(KEY_TOPIC, topic); }
    public String getTutoringDescription() {return getString(KEY_DESCRIPTION);}
    public void setTutoringDescription(String description) {put(KEY_DESCRIPTION, description);}
    public String getCalendarDate() {return getString(KEY_URGENCY);}
    public void setCalendarDate(String date) {put(KEY_URGENCY, date);}
    public ParseUser getStudent() {return getParseUser(KEY_STUDENT_POINTER);}
    public void setStudent(ParseUser student) {put(KEY_STUDENT_POINTER, student);}
    public ParseUser getStudentTutor() {return getParseUser(KEY_STUDENT_TUTOR_POINTER);}
    public void setStudentTutor(ParseUser studentTutor) {put(KEY_STUDENT_TUTOR_POINTER, studentTutor);}
}
