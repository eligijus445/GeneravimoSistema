package Backend;

public class Client {
    int id ;
    int firmif;
    String name ;
    String surname ;
    String email ;
    String phone ;

    public Client() {
    }

    public void addClient(int id, int firmif, String name, String surname, String email, String phone) {
        this.id = id;
        this.firmif = firmif;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", firmif=" + firmif +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFirmif() {
        return firmif;
    }

    public void setFirmif(int firmif) {
        this.firmif = firmif;
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
}
