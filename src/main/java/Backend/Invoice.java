package Backend;

public class Invoice {

    int id;
    String number;
    String date;
    int discount;
    boolean discountBeforeTax;
    int workActID;
    int UserID;
    int vat;


    public Invoice() {
    }

    public void  addInvoice(int id, String number, String date, int disciuont, boolean discountBeforeTax, int workActID, int userID, int vat) {
        this.id = id;
        this.number = number;
        this.date = date;
        this.discount = disciuont;
        this.workActID = workActID;
        this.discountBeforeTax = discountBeforeTax;
        this.UserID = userID;
        this.vat = vat;
    }

    @Override
    public String toString() {
        return "Invoice{" +
                "id=" + id +
                ", number='" + number + '\'' +
                ", date='" + date + '\'' +
                ", discount=" + discount +
                ", discountBeforeTax=" + discountBeforeTax +
                ", workActID=" + workActID +
                ", UserID=" + UserID +
                ", vat=" + vat +
                '}';
    }

    public int getVat() {
        return vat;
    }

    public void setVat(int vat) {
        this.vat = vat;
    }

    public boolean isDiscountBeforeTax() {
        return discountBeforeTax;
    }

    public void setDiscountBeforeTax(boolean discountBeforeTax) {
        this.discountBeforeTax = discountBeforeTax;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public int getWorkActID() {
        return workActID;
    }

    public void setWorkActID(int workActID) {
        this.workActID = workActID;
    }

    public int getUserID() {
        return UserID;
    }

    public void setUserID(int userID) {
        UserID = userID;
    }
}
