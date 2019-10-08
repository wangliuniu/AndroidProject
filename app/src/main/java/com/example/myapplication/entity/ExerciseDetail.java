package com.example.myapplication.entity;

import java.io.Serializable;

public class ExerciseDetail implements Serializable {
    private int exercise_id;
    private int subjectId;
    private String subject;
    private String a;
    private String b;
    private String c;
    private String d;
    private int answer;
    private int select;
    @Override
    public String toString() {
        return "ExerciseDetail{" +
                "exercise_id=" + exercise_id +
                ", subjectId=" + subjectId +
                ", subject='" + subject + '\'' +
                ", a='" + a + '\'' +
                ", b='" + b + '\'' +
                ", c='" + c + '\'' +
                ", d='" + d + '\'' +
                ", answer=" + answer +
                ", select=" + select +
                '}';
    }

    public ExerciseDetail() {
    }


    public ExerciseDetail(int exercise_id, int subjectId, String subject, String a, String b, String c, String d, int answer, int select) {
        this.exercise_id = exercise_id;
        this.subjectId = subjectId;
        this.subject = subject;
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
        this.answer = answer;
        this.select = select;
    }



    public int getExercise_id() {
        return exercise_id;
    }

    public void setExercise_id(int exercise_id) {
        this.exercise_id = exercise_id;
    }

    public int getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(int subjectId) {
        this.subjectId = subjectId;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getA() {
        return a;
    }

    public void setA(String a) {
        this.a = a;
    }

    public String getB() {
        return b;
    }

    public void setB(String b) {
        this.b = b;
    }

    public String getC() {
        return c;
    }

    public void setC(String c) {
        this.c = c;
    }

    public String getD() {
        return d;
    }

    public void setD(String d) {
        this.d = d;
    }

    public int getAnswer() {
        return answer;
    }

    public void setAnswer(int answer) {
        this.answer = answer;
    }

    public int getSelect() {
        return select;
    }

    public void setSelect(int select) {
        this.select = select;
    }


}
