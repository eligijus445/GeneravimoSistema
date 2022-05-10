package FrontEnd;

import Backend.Gener;
import Backend.User;
import Backend.WorkAct;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.sql.SQLException;

public class LoginController {
    @FXML
    private TextField loginField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Button loginButton;
    @FXML
    private Button forgotPasswordButton;
    @FXML
    private Text wrongLogin;


    @FXML
    public void login(ActionEvent event)  {

    }

    @FXML
    public void onLoginButtonClick(javafx.event.ActionEvent event) throws SQLException, IOException {
        Gener gener = new Gener();
        Login feMain = new Login();
        loginField.setText("Eligiminijus");
        passwordField.setText("MkoiUUt5RS1G");

        if (gener.login(loginField.getText(), passwordField.getText())) {
            wrongLogin.setFill(Paint.valueOf("green"));

            wrongLogin.setText("Prisijungta");
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("main.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root1));
            MainController mainController = fxmlLoader.getController();
            fxmlLoader.setController(mainController);
            mainController.setLoggedInUser(gener.loggedIn);
            stage.show();
            Stage stage1 = (Stage) loginButton.getScene().getWindow();
            stage1.close();


        } else {

            wrongLogin.setText("Neteisingi prisijungimo duomenys! Arba vartotojui yra apribota galimybė naudotis sistema.");
        }

    }


    @FXML
    public void onforgotPasswordButton (javafx.event.ActionEvent event)
    {
        wrongLogin.setFill(Paint.valueOf("black"));
        wrongLogin.setText("Pamiršus slaptažodį prašome kreiptis į sistemos administratorių.");
    }








}