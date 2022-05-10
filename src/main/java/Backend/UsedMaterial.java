package Backend;

public class UsedMaterial {



    int id;
    int materialID;
    int workActID;
    String name;
    String unit;
    double quantity;
    double price;

    public void addUsedMaterial(int id, int materialID, int workActID, String name, String unit, double quantity, double price) {
        this.id = id;
        this.materialID = materialID;
        this.workActID = workActID;
        this.name = name;
        this.unit = unit;
        this.quantity = quantity;
        this.price = price;
    }

    public UsedMaterial() {
    }



    public void addSelectedUsedMaterial(int materialID, String name, String unit, double quantity, double price) {
        this.materialID = materialID;
        this.name = name;
        this.unit = unit;
        this.quantity = quantity;
        this.price = price;
    }

    @Override
    public String toString() {
        return "UsedMaterial{" +
                "id=" + id +
                ", materialID=" + materialID +
                ", workActID=" + workActID +
                ", name='" + name + '\'' +
                ", unit='" + unit + '\'' +
                ", quantity=" + quantity +
                ", price=" + price +
                '}';
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMaterialID() {
        return materialID;
    }

    public void setMaterialID(int materialID) {
        this.materialID = materialID;
    }

    public int getWorkActID() {
        return workActID;
    }

    public void setWorkActID(int workActID) {
        this.workActID = workActID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
