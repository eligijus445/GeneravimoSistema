package Backend;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class User {


    int id;
    String name;
    String surname;
    String email;
    String phone;
    boolean active;

    String role;


    public User(int id, String userFirst_name, String userLirst_name, String email, String phone) {
    }

    public User() {
    }






    @Override
    public String toString() {
        return "User{" + "id=" + id + ", name='" + name + '\'' + ", surname='" + surname + '\'' + ", email='" + email + '\'' + ", phone='" + phone + '\'' + ", active=" + active + '}';
    }

    public void addUser(int id, String userFirst_name, String userLast_name, String email, String phone, boolean active, String role) {
        this.email = email;
        this.name = userFirst_name;
        this.surname = userLast_name;
        this.phone = phone;
        this.id = id;
        this.role = role;
        this.active = active;

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

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
