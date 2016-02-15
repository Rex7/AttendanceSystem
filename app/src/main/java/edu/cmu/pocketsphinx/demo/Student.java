package edu.cmu.pocketsphinx.demo;

/**
 * Created by Regis on 13-02-2016.
 */
public class Student {
    String first, last, emailid, phone, course;
    int rollno;

    public Student(String first, String last, String emailid, String phone, String course, int rollno) {
        this.first = first;
        this.last = last;
        this.emailid = emailid;
        this.phone = phone;
        this.course = course;
        this.rollno = rollno;
    }

    public String getFirst() {
        return first;
    }

    public void setFirst(String first) {
        this.first = first;
    }

    public String getLast() {
        return last;
    }

    public void setLast(String last) {
        this.last = last;
    }

    public String getEmailid() {
        return emailid;
    }

    public void setEmailid(String emailid) {
        this.emailid = emailid;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public int getRollno() {
        return rollno;
    }

    public void setRollno(int rollno) {
        this.rollno = rollno;
    }

}
