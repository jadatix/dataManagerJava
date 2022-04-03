package entities;

public class Person {
    private String name;
    private int id; // no in constructor
    private String lastname;
    private String email;
    private String gender;
    private int age;
    private String password;


    Person(String name, String lastname, String email, String gender, int age, String password){
        this.name = name;
        this.lastname = lastname;
        this.email = email;
        this.gender = gender;
        this.password=password;
        this.age = age;
    }
    Person(){password=""; name = ""; lastname=""; email="";gender="";age = 0;}

    public int getAge(){return age;}
    public String getName(){return name;}
    public String getLastname(){return lastname;}
    public String getEmail(){return email;}

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getGender() {
        return gender;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
