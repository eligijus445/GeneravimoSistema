package FrontEnd;

import Backend.Gener;
import Backend.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.fxml.Initializable;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class DeleteUserWindowController{
    User selectedUser = new User();


    @FXML
    private Button noButton;

    @FXML
    private Text userText;

    @FXML
    private Button yesButton;



    @FXML
    public void colseWindow(ActionEvent event) {
        Stage stage = (Stage) noButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void deleteUser(ActionEvent event) throws SQLException, IOException {

        Gener gener =new Gener();

        gener.deleteUser(selectedUser.getId());

        Login login = new Login();
        login.changeLoginsceneToMain("settings.fxml");


        Stage stage = (Stage) yesButton.getScene().getWindow();
        stage.close();



    }
    public void MF (User deletingUser ){
        selectedUser = deletingUser;

        userText.setText("Vartotojas : " +deletingUser.getName()+" "+ deletingUser.getSurname()+" \n El. paštas: "+deletingUser.getEmail()+" \n Telefono nr.: "+ deletingUser.getPhone()+" \n Grupė: "+deletingUser.getRole());
    }




}
