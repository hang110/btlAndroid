package com.btl.model;

public class Student {
    private int id;
    private String name, date, gender;
    private float GPA;

    public Student(int id, String name, String date, String gender, float GPA) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.gender = gender;
        this.GPA = GPA;
    }

    public Student(String name, String date, String gender, float GPA) {
        this.name = name;
        this.date = date;
        this.gender = gender;
        this.GPA = GPA;
    }

    public Student() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public float getGPA() {
        return GPA;
    }

    public void setGPA(float GPA) {
        this.GPA = GPA;
    }
}
