package FrontEnd;

import Backend.User;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;


import javafx.scene.control.Button;

public class MainController  {


    @FXML
    private Button createWorkActButton;
    @FXML
            private TextField name;
    @FXML
    private TextField surname;


    User loggedInUser = new User();
    public void setLoggedInUser (User user)
    {
        this.loggedInUser = user;
        name.setText(loggedInUser.getName());
        surname.setText(loggedInUser.getSurname());

    }


    @FXML
    public void goToSettings(ActionEvent event) throws IOException {
        System.out.println(loggedInUser.getName());
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("settings.fxml"));
        Parent root1 = (Parent) fxmlLoader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root1));
        SettingsController settingsController = fxmlLoader.getController();
        fxmlLoader.setController(settingsController);
        settingsController.setLoggedInUser(loggedInUser);
        stage.show();
        Stage stage1 = (Stage) createWorkActButton.getScene().getWindow();
        stage1.close();
    }

    @FXML
    public void goToCreateWorkAct(ActionEvent event) throws IOException {


        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("workactcreate.fxml"));
        Parent root1 = (Parent) fxmlLoader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root1));
        workactcreateController workactcreateController = fxmlLoader.getController();
        fxmlLoader.setController(workactcreateController);
        workactcreateController.setLoggedInUser(loggedInUser);
        stage.show();
        Stage stage1 = (Stage) createWorkActButton.getScene().getWindow();
        stage1.close();

    }

    @FXML
    public void goToCreateInvoice(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("invoicecreate.fxml"));
        Parent root1 = (Parent) fxmlLoader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root1));
        InvoiceCreateController invoiceCreateController = fxmlLoader.getController();
        fxmlLoader.setController(invoiceCreateController);
        invoiceCreateController.setLoggedInUser(loggedInUser);
        stage.show();
        Stage stage1 = (Stage) createWorkActButton.getScene().getWindow();
        stage1.close();

    }

    @FXML
    public void goToWorkActs(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("workacts.fxml"));
        Parent root1 = (Parent) fxmlLoader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root1));
        WorkActsController workactscontroller = fxmlLoader.getController();
        fxmlLoader.setController(workactscontroller);
        workactscontroller.setLoggedInUser(loggedInUser);
        stage.show();
        Stage stage1 = (Stage) createWorkActButton.getScene().getWindow();
        stage1.close();

    }


    @FXML
    public void goToInvoices(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("invoices.fxml"));
        Parent root1 = (Parent) fxmlLoader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root1));
        InvoicesController invoiceCreateController = fxmlLoader.getController();
        fxmlLoader.setController(invoiceCreateController);
        invoiceCreateController.setLoggedInUser(loggedInUser);
        stage.show();
        Stage stage1 = (Stage) createWorkActButton.getScene().getWindow();
        stage1.close();
    }





}
