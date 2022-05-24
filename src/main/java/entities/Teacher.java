package entities;

import java.util.ArrayList;
import java.util.List;

public class Teacher extends Person {

    private List<String> subject =new ArrayList<>();
    private String headOf;

    public Teacher(){super(); subject.add("");}
    public Teacher(String name, String lastname, String email, String gender, int age, String password, List<String> subject, String headOf){
        super(name, lastname, email, gender, age,password);
        this.subject.addAll(subject);
        this.headOf=headOf;
    }

    public boolean isHeadOf(){return headOf.isEmpty();}
    public void addSubject(String subject){
        this.subject.add(subject);
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
    public String getName() {
        return super.getName();
    }

    @Override
    public String getLastname() {
        return super.getLastname();
    }

    @Override
    public String getGender() {
        return super.getGender();
    }

    @Override
    public String getEmail() {
        return super.getEmail();
    }

    @Override
    public int getAge() {
        return super.getAge();
    }

    public List<String> getSubject() {
        return subject;
    }

    public String getHeadOf() {
        return headOf;
    }

    @Override
    public void setEmail(String email) {
        super.setEmail(email);
    }

    @Override
    public void setName(String name) {
        super.setName(name);
    }

    @Override
    public void setLastname(String lastname) {
        super.setLastname(lastname);
    }

    @Override
    public void setAge(int age) {
        super.setAge(age);
    }

    @Override
    public void setGender(String gender) {
        super.setGender(gender);
    }

    public void setHeadOf(String headOf) {
        this.headOf = headOf;
    }

    public void setSubject(List<String> subject) {
        this.subject = subject;
    }
}
