package com.mobile.rrqquiz.model;

import java.io.Serializable;

public class RRQDetailModel implements Serializable {

    private String rrqID;
    private String subjectName;
    private int numQuestions;
    private String studentID;
    private String studentName;
    private String StudentImageURL;

    public String getRrqID() {
        return rrqID;
    }

    public void setRrqID(String rrqID) {
        this.rrqID = rrqID;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public int getNumQuestions() {
        return numQuestions;
    }

    public void setNumQuestions(int numQuestions) {
        this.numQuestions = numQuestions;
    }

    public String getStudentID() {
        return studentID;
    }

    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getStudentImageURL() {
        return StudentImageURL;
    }

    public void setStudentImageURL(String studentImageURL) {
        StudentImageURL = studentImageURL;
    }

}
