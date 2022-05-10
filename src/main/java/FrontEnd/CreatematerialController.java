package FrontEnd;

import Backend.Gener;
import Backend.UsedMaterial;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class CreatematerialController implements Initializable {

    @FXML
    private Text erroeText;

    @FXML
    private AnchorPane fillPane;

    @FXML
    private TextField name;

    @FXML
    private TextField price;

    @FXML
    private TextField quantity;

    @FXML
    private ComboBox<String> units;

    UsedMaterial usedMaterial = new UsedMaterial();







    @FXML
    void cancel(ActionEvent event) {
        Stage stage = (Stage) name.getScene().getWindow();
        stage.close();
    }

    @FXML
    void goToAskWindow(ActionEvent event) throws SQLException {
        Gener gener = new Gener();
        if(!gener.isValidName(name.getText()))
        {
            setErrorText("Klaidingai įvestas pavadinimas");

        }
        else if(units.getItems()==null){
            setErrorText("Prašome pasirinkti vienetus.");

        }
        else
            try
            {
                Double.parseDouble(price.getText());
                try {
                    Double.parseDouble(quantity.getText());
                    usedMaterial.setName(name.getText());
                    usedMaterial.setUnit(units.getValue());

                    usedMaterial.setQuantity(Double.parseDouble(quantity.getText()));
                    usedMaterial.setPrice(Double.parseDouble(price.getText())* usedMaterial.getQuantity());
                    gener.addMaterial(name.getText(),units.getValue(),Double.parseDouble(price.getText()));
                    usedMaterial.setMaterialID(gener.getLastMaterialID());


                    Stage stage = (Stage) name.getScene().getWindow();
                    stage.close();


                }
                catch (NumberFormatException e){
                    setErrorText("Blogai įvestas kiekis.");
                }
            }
            catch (NumberFormatException e){
                setErrorText("Blogai įvesta vieneto kaina.");
            }

    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        fillPane.setVisible(true);
        fillPane.setDisable(false);
        units.getItems().addAll("Vnt.", "m","cm","g","kg");

    }
    private void setErrorText(String text){
        erroeText.setText("");
        erroeText.setText(text);
    }


    public UsedMaterial getMaterial() {

        return usedMaterial;

    }
}
