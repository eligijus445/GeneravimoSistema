package FrontEnd;

import Backend.Firm;
import Backend.Gener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class CreateClientController implements Initializable {

    @FXML
    private Text createClientErrorText;

    @FXML
    private TextField email;

    @FXML
    private TextField firmName;

    @FXML
    private TextField name;

    @FXML
    private TextField phone;

    @FXML
    private TextField surname;
    Firm clientFirm = new Firm();


    @FXML
    void addClient(ActionEvent event) throws SQLException {
        Gener gener = new Gener();

        if(!gener.isValidUsername(name.getText()))
        {
            setErrorText("Blogas kliento vardas");
        }
        else if(!gener.isValidUsername(surname.getText()))
        {
            setErrorText("Bloga kliento pavarė");
        }
        else if(!gener.isValidEmailAddress(email.getText()))
        {
            setErrorText("Blogai įvestas kliento el. paštas.");
        }
        else if (!gener.isValidPhoneNumber(phone.getText()))
        {
            setErrorText("Blogai įvestas kliento telefono numeris. (+3706*******");
        }
        else
        {
            gener.uploadClientToDb( clientFirm.getId(),name.getText(),surname.getText(),email.getText(),phone.getText());
            Stage stage = (Stage) name.getScene().getWindow();
            stage.close();
        }

    }

    @FXML
    void cancel(ActionEvent event) {
        Stage stage = (Stage) name.getScene().getWindow();
        stage.close();

    }

    @FXML
    private void setErrorText(String text){
        createClientErrorText.setText("");
        createClientErrorText.setText(text);

    }


    public void setFirmName(Firm firm){
        this.clientFirm= firm;
        firmName.setText(clientFirm.getName());
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
