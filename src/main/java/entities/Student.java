package entities;

import java.util.Date;

public class Student extends Person{
    private String groupNum;
    private int durationOfEducation;
    private Date startEducation;

    Student(){super(); groupNum=""; durationOfEducation=0; startEducation = new Date();}
    Student(String name, String lastname, String email, String gender, int age,String password, String groupNum,  int durationOfEducation, Date startEducation) {
        super(name, lastname, email, gender, age,password);
        this.groupNum = groupNum;
        this.durationOfEducation = durationOfEducation;
        this.startEducation = startEducation;
    }

    @Override
    public int getAge() {
        return super.getAge();
    }

    @Override
    public int getId() {
        return super.getId();
    }

    @Override
    public void setId(int id) {
        super.setId(id);
    }

    @Override
    public String getEmail() {
        return super.getEmail();
    }

    @Override
    public String getGender() {
        return super.getGender();
    }

    @Override
    public String getLastname() {
        return super.getLastname();
    }

    @Override
    public String getName() {
        return super.getName();
    }

    public Date getStartEducation() {
        return startEducation;
    }

    public int getDurationOfEducation() {
        return durationOfEducation;
    }


    public String getGroupNum() {
        return groupNum;
    }

    @Override
    public void setGender(String gender) {
        super.setGender(gender);
    }

    @Override
    public void setAge(int age) {
        super.setAge(age);
    }

    public void setStartEducation(Date startEducation) {
        this.startEducation = startEducation;
    }

    @Override
    public void setEmail(String email) {
        super.setEmail(email);
    }

    @Override
    public void setLastname(String lastname) {
        super.setLastname(lastname);
    }

    @Override
    public void setName(String name) {
        super.setName(name);
    }

    public void setDurationOfEducation(int durationOfEducation) {
        this.durationOfEducation = durationOfEducation;
    }


    public void setGroupNum(String groupNum) {
        this.groupNum = groupNum;
    }
}
