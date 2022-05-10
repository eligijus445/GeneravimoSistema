package Backend;

public class Firm  {

    int id;
    int firmNumber;
    String vatNumber;
    String name;
    String adress;
    String formName;
    int state;
    String email;


    @Override
    public String toString() {
        return "Firm{" +
                "id=" + id +
                ", firmNumber=" + firmNumber +
                ", vatNumber='" + vatNumber + '\'' +
                ", name='" + name + '\'' +
                ", adress='" + adress + '\'' +
                ", formName='" + formName + '\'' +
                ", state=" + state +
                ", email='" + email + '\'' +
                '}';
    }

    public void addFirm(int id, int number, String vatNumber, String name, String adress, String formName, int state, String email) {
        this.id = id;
        this.firmNumber = number;
        this.vatNumber = vatNumber;
        this.name = name;
        this.formName = formName;
        this.state = state;
        this.adress= adress;
        this.email =email;
    }

    public Firm() {

    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFirmNumber() {
        return firmNumber;
    }

    public void setFirmNumber(int firmNumber) {
        this.firmNumber = firmNumber;
    }

    public String getVatNumber() {
        return vatNumber;
    }

    public void setVatNumber(String vatNumber) {
        this.vatNumber = vatNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFormName() {
        return formName;
    }

    public void setFormName(String formName) {
        this.formName = formName;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

}
