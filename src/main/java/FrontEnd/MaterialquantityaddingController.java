package FrontEnd;

import Backend.Material;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class MaterialquantityaddingController {

    double typedQuantity;
    Material material = new Material();


    @FXML
    private TextField ID;

    @FXML
    private Text errorText;

    @FXML
    private TextField name;

    @FXML
    private TextField price;

    @FXML
    private TextField quantity;

    @FXML
    private TextField units;



    @FXML
    void addQuantity(ActionEvent event) {


        try{
            typedQuantity= Double.parseDouble(quantity.getText());
            Stage stage = (Stage) ID.getScene().getWindow();
            stage.close();
        }
        catch (NumberFormatException e){
            errorText.setText("Blogai įvestas kiekis.");
        }

    }

    public double getQuantity() {
        return typedQuantity;
    }
    public void setMaterial (Material material){
        this.material = material;
        ID.setText(String.valueOf(material.getId()));
        name.setText(material.getName());
        units.setText(material.getUnit());
        price.setText(String.valueOf(material.getPrice()));
    }
    @FXML
    void closeWindow(ActionEvent event) {
        Stage stage = (Stage) ID.getScene().getWindow();
        stage.close();

    }


}
