package bcvt.edu.vn;

import java.io.Serializable;

public class Student implements Serializable {
    int id;
    String name;
    String address;
    int gender;


    public Student() {

    }

    public Student(int id, String name, String address, int gender) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.gender = gender;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getGender() {
        return gender;
    }

    public String getAddress() {
        return address;
    }
}
