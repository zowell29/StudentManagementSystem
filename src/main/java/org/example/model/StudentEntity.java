package org.example.model;

import java.time.LocalDate;
import java.sql.Date;

public class StudentEntity {

    private int id;
    private String name;
    private String email;
    private int age;
    private LocalDate enrollment_date;
    private double grade;

    public StudentEntity(int id, String name, String email, int age, LocalDate enrollment_date, double grade) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.age = age;
        this.enrollment_date = enrollment_date;
        this.grade = grade;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public LocalDate getEnrollment_date() {
        return enrollment_date;
    }

    public void setEnrollment_date(LocalDate enrollment_date) {
        this.enrollment_date = enrollment_date;
    }

    public double getGrade() {
        return grade;
    }

    public void setGrade(double grade) {
        this.grade = grade;
    }

    @Override
    public String toString() {
        return "StudentEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", age=" + age +
                ", enrollment_date=" + enrollment_date +
                ", grade=" + grade +
                '}';
    }
}
