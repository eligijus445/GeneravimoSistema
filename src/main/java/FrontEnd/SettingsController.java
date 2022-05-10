package FrontEnd;

import Backend.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class SettingsController implements Initializable {

    User loggedInUser = new User();
    @FXML
    private Button goToPasswordResetButton;
    @FXML
    private Pane resetPasswordPane;
    @FXML
    private PasswordField loggedinOldPassword;
    @FXML
    private PasswordField loggedinNewPassword;
    @FXML
    private PasswordField loggedinNewPasswordRE;
    @FXML
    private Text loggedInErrorText;
    @FXML
    private TableColumn<User, Boolean> active;
    @FXML
    private TableColumn<Firm, String> firmAdress;
    @FXML
    private Button buttonSettings;

    @FXML
    private CheckBox checkBoxActive;
    @FXML
    private TableColumn<Client, String> clientEmail;
    @FXML
    private TextField clientEmailTextField;
    @FXML
    private Text clientErrorText;
    @FXML
    private TableColumn<Client, String> clientName;
    @FXML
    private TextField clientNameTextField;
    @FXML
    private TableColumn<Client, String> clientPhone;
    @FXML
    private TextField clientPhoneTextField;
    @FXML
    private TableColumn<Client, String> clientSurname;
    @FXML
    private TextField clientSurnameTextField;
    @FXML
    private TableView<?> clientTableView;
    @FXML
    private Pane clientsSettingsPane;
    @FXML
    private TextField companyBankNameTextField;
    @FXML
    private TextField companyEmailTextField;
    @FXML
    private Text companyErrorText;
    @FXML
    private TextField companyIBanTextField;
    @FXML
    private TextField companyInvoiceSieriesTextField;
    @FXML
    private PasswordField companyPasswordTextField;
    @FXML
    private PasswordField companyRepaswordTextField;
    @FXML
    private TextField companySieriesPathTextField;
    @FXML
    private TextField companyWorkActSavePathTextField;
    @FXML
    private TextField companyWorkActSeriesTextField;
    @FXML
    private ComboBox<String> createComboBoxRole;
    @FXML
    private TextField createTextFieldEmail;
    @FXML
    private TextField createTextFieldName;
    @FXML
    private TextField createTextFieldSurname;
    @FXML
    private TextField createTextFieldUsername;
    @FXML
    private TextField createTextfieldPhone;
    @FXML
    private Button createinvoicebutton;
    @FXML
    private Text deleteMaterialTextField;

    @FXML
    private Text deleteUserText;
    @FXML
    private TableColumn<User, String> email;
    @FXML
    private TextField firmName;
    @FXML
    private TableColumn<Firm, Integer> firmNumber;
    @FXML
    private Pane firmSettingsPane;
    @FXML
    private TableView<?> firmsTableView;
    @FXML
    private TableColumn<Firm, String> formName;
    @FXML
    private Button goToClientSettings;
    @FXML
    private Button goToCompanySettings;
    @FXML
    private Button goToInvoicesButton;
    @FXML
    private Button goToMaterialsSettings;
    @FXML
    private TableColumn<User, Integer> id;
    @FXML
    private TableColumn<Firm, Integer> firmID;
    @FXML
    private Text materialErrorText;
    @FXML
    private TableColumn<Material, String> materialName;
    @FXML
    private TextField materialNameTextField;
    @FXML
    private TableColumn<Material, Double> materialPrice;
    @FXML
    private TextField materialPriceTextField;
    @FXML
    private Button materialSearchButton;
    @FXML
    private TextField materialSearchTextField;
    @FXML
    private TableView<?> materialTableView;
    @FXML
    private TableColumn<Material, String> materialUnits;
    @FXML
    private ComboBox<String> materialUnitsComboBox;
    @FXML
    private TextField materialUpdateNameTextField;
    @FXML
    private TextField materialUpdatePriceTextField;
    @FXML
    private TableColumn<Material, Integer> materialid;
    @FXML
    private Pane materialsSettingsPane;
    @FXML
    private TableColumn<User, String> name;
    @FXML
    private TableColumn<Firm, String> firmNameCol;
    @FXML
    private TableColumn<User, String> phone;
    @FXML
    private TableColumn<User, Boolean> role;
    @FXML
    private TextField searchFirmNameTextField;
    @FXML
    private TextField searchFirmNumberTextField;
    @FXML
    private Button selectFirmButton;
    @FXML
    private Button shearch;
    @FXML
    private Button shearchFirmButton;
    @FXML
    private TextField searchUserTextField;
    @FXML
    private TableColumn<User, String> surname;
    @FXML
    private TableView<?> tableView;
    @FXML
    private ComboBox<String> updateComboBox;
    @FXML
    private TextField updateFieldEmail;
    @FXML
    private TextField updateFieldName;
    @FXML
    private TextField updateFieldPhone;
    @FXML
    private TextField updateFieldSurname;
    @FXML
    private ComboBox<String> updateMaterialUnitsComboBox;
    @FXML
    private Button updateUserButton;
    @FXML
    private Text userSettingsErrorText;
    @FXML
    private Pane usersSettingsPane;
    @FXML
    private Button workactsbutton;

    @FXML
    private TextField useridTextField;
    @FXML
    private Text companyPasswordText;
    @FXML
    private Text companyrePasswordText;
    @FXML
    private TextField materialIdTextField;
    @FXML
    private Button deleteMaterialButton;
    @FXML
    private Button cancelMaterialButton;
    @FXML
    private Button cancelClientDeleteButton;
    @FXML
    private Button deleteClientButton;
    @FXML
    private Text clientDeleteTextField;
    @FXML
    private TextField firmIdTextField;
    @FXML
    private Button askResetButton;
    @FXML
    private Button resetPasswordButton;
    @FXML
    private Button cancelPasswordResetButton;
    @FXML
    private Text resetPasswordText;





    public void setLoggedInUser(User user) {
        this.loggedInUser = user;

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {



        buttonSettings.setDisable(true);
        resetPasswordButton.setVisible(false);
        cancelPasswordResetButton.setVisible(false);
        resetPasswordText.setVisible(false);
        usersSettingsPane.setVisible(true);
        firmSettingsPane.setVisible(false);
        materialsSettingsPane.setVisible(false);
        clientsSettingsPane.setVisible(false);
        resetPasswordPane.setVisible(false);


        id.setCellValueFactory(new PropertyValueFactory<User, Integer>("id"));
        name.setCellValueFactory(new PropertyValueFactory<User, String>("name"));
        surname.setCellValueFactory(new PropertyValueFactory<User, String>("surname"));
        email.setCellValueFactory(new PropertyValueFactory<User, String>("email"));
        phone.setCellValueFactory(new PropertyValueFactory<User, String>("phone"));
        active.setCellValueFactory(new PropertyValueFactory<User, Boolean>("active"));
        role.setCellValueFactory(new PropertyValueFactory<User, Boolean>("role"));
        try {
            fillUsersTable();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        updateComboBox.getItems().addAll("Administratorius", "Vadovas", "Darbuotojas");
        createComboBoxRole.getItems().addAll("Administratorius", "Vadovas", "Darbuotojas");

    }


    @FXML
    void addMaterial(ActionEvent event) throws SQLException {
        Gener gener = new Gener();
        if (!gener.isValidName(materialNameTextField.getText())) {
            setMaterialErrorText("Klaidingai įvestas pavadinimas");

        } else if (materialUnitsComboBox.getItems() == null) {
            setMaterialErrorText("Prašome pasirinkti vienetus.");

        } else try {
            Double.parseDouble(materialPriceTextField.getText());
            gener.addMaterial(materialNameTextField.getText(), materialUnitsComboBox.getValue(), Double.parseDouble(materialPriceTextField.getText()));
            materialErrorText.setText("Medžiaga sėkmingai pridėta į  sąrašą.");
            materialErrorText.setFill(Color.GREEN);

            materialNameTextField.setText("");
            materialUnitsComboBox.setValue(null);
            materialPriceTextField.setText("");
            materialTableView.getItems().clear();
            materialTableView.getItems().addAll(gener.uploadAllMaterials());

        } catch (NumberFormatException e) {
            setMaterialErrorText("Blogai įvesta vieneto kaina.");
        }
    }

    @FXML
    void askDeleteClient(ActionEvent event) {
        if(clientTableView.getSelectionModel().getSelectedItem() != null)
        {
            clientDeleteTextField.setVisible(true);
            deleteClientButton.setVisible(true);
            cancelClientDeleteButton.setVisible(true);

        }
        else
        {
            setClientErrorText("Prašome pasirinkti klientą iš sąrašo.");
        }


    }

    @FXML
    void askFormMaterialDelete(ActionEvent event) {
        if (materialTableView.getSelectionModel().getSelectedItem() == null) {
            setMaterialErrorText("Prašome pasirinkti medžiagą");
        } else {
            deleteMaterialTextField.setVisible(true);
            deleteMaterialButton.setVisible(true);
            cancelMaterialButton.setVisible(true);
        }


    }

    @FXML
    void cancelClientDelete(ActionEvent event) {
        clientDeleteTextField.setVisible(false);
        deleteClientButton.setVisible(false);
        cancelClientDeleteButton.setVisible(false);
    }

    @FXML
    void cancelMaterialDelete(ActionEvent event) {
        deleteMaterialTextField.setVisible(false);
        deleteMaterialButton.setVisible(false);
        cancelMaterialButton.setVisible(false);

    }

    @FXML
    void chooseCompanyInvoiceSavingPath(MouseEvent event) {

    }

    @FXML
    void createNewUser(ActionEvent event) throws SQLException, IOException {
        Gener gener = new Gener();


        if (gener.isSameUsername(createTextFieldUsername.getText())) {
            setUserSettingsErrorText("Vartotojas su tokiu slapyvardžiu jau egzistuoja");
        } else if (!gener.isValidUsername(createTextFieldUsername.getText())) {
            setUserSettingsErrorText("Vartotojo slapyvardis turi būti sudarytas nuo 4 iki 32 simbolių.");
        } else if (!gener.isValidName(createTextFieldName.getText())) {
            setUserSettingsErrorText("Vartotjo vardas turi būti sudarytas bent iš 3 raidžių.");
        } else if (!gener.isValidName(createTextFieldSurname.getText())) {
            setUserSettingsErrorText("Vartotjo pavardė turi būti sudarytas bent iš 3 raidžių.");
        } else if (!gener.isValidEmailAddress(createTextFieldEmail.getText())) {
            setUserSettingsErrorText("Neteisingai įvestas el. paštas.");
        } else if (!gener.isValidPhoneNumber(createTextfieldPhone.getText())) {
            setUserSettingsErrorText("Neteisingai įvestas telefono numeris. (pvz.: +3706XXXXXXX)");
        } else if (createComboBoxRole.getValue() == null) {
            setUserSettingsErrorText("Pasirinkite vartotojo rolę.");
        } else {
            String pass = gener.generatePassword();
            gener.registration(createTextFieldUsername.getText(), pass, createTextFieldName.getText(), createTextFieldSurname.getText(), createTextFieldEmail.getText(), createTextfieldPhone.getText(), String.valueOf(createComboBoxRole.getValue()));
            gener.sendUserPassword(createTextFieldEmail.getText(), createTextFieldUsername.getText(), pass);
            userSettingsErrorText.setText("Vartotojas sėkmingai sukurtas.\nVartotojo prisijungimo duomenys išsiųsti el. paštu");
            userSettingsErrorText.setFill(Color.GREEN);
        }

        fillUsersTable();


    }

    @FXML
    void deleteClient(ActionEvent event) throws SQLException {
        Gener gener = new Gener();
        Client client = (Client) clientTableView.getSelectionModel().getSelectedItem();
        gener.deleteClientFromDb(client.getId());
        clientDeleteTextField.setVisible(false);
        deleteClientButton.setVisible(false);
        cancelClientDeleteButton.setVisible(false);
        clientErrorText.setFill(Color.GREEN);
        clientErrorText.setText("Klientas sėkmingas pašalintas iš sąrašo.");

        clientTableView.getItems().clear();
        clientTableView.getItems().addAll(gener.uploadClientFromDbByFirmID(Integer.parseInt(firmIdTextField.getText())));
    }

    @FXML
    void deleteMAterial(ActionEvent event) throws SQLException {

        Gener gener = new Gener();
        gener.deleteMaterial(Integer.parseInt(materialIdTextField.getText()));
        materialErrorText.setText("Medžiaga sėkmingai pašalinta iš sąrašo");
        materialErrorText.setFill(Color.GREEN);
        materialTableView.getItems().clear();
        materialTableView.getItems().addAll(gener.uploadAllMaterials());

    }







    @FXML
    void fillClientTable(MouseEvent event) throws SQLException {
        Gener gener = new Gener();
        Firm firm = (Firm) firmsTableView.getSelectionModel().getSelectedItem();
        clientTableView.getItems().clear();
        clientTableView.getItems().addAll(gener.uploadClientFromDbByFirmID(firm.getId()));
        firmName.setText(firm.getName());
        clientNameTextField.setText("");
        clientSurnameTextField.setText("");
        clientEmailTextField.setText("");
        clientPhoneTextField.setText("");
        firmIdTextField.setText("");
    }
    @FXML
    void  fillClientDetails (MouseEvent event) throws SQLException {
        Gener gener = new Gener();
        Client client = (Client) clientTableView.getSelectionModel().getSelectedItem();
        Firm firm = gener.getFirmByFirmId(client.getFirmif());

        firmName.setText(firm.getName());
        clientNameTextField.setText(client.getName());
        clientSurnameTextField.setText(client.getSurname());
        clientEmailTextField.setText(client.getEmail());
        clientPhoneTextField.setText(client.getPhone());
        firmIdTextField.setText(String.valueOf(client.getFirmif()));

    }

    @FXML
    public void fillUsersTable() throws SQLException {
        Gener gener = new Gener();
        tableView.getItems().clear();
        tableView.getItems().addAll(gener.upploadAllUsersFromDataBase());
    }

    private void setClientErrorText(String text) {
        clientErrorText.setText(text);
        clientErrorText.setFill(Color.RED);
    }

    @FXML
    void findFirm(ActionEvent event) throws SQLException {
        Gener gener = new Gener();
        firmsTableView.getItems().clear();

        if(searchFirmNameTextField.getText().isEmpty()){
            setClientErrorText("Pra6ome užpildyti įmonės paieškos laukelį.");
        }
        else
        {
            System.out.println(gener.getFirmBySearchtext(searchFirmNameTextField.getText()));

            firmsTableView.getItems().addAll(gener.getFirmBySearchtext(searchFirmNameTextField.getText()));
        }

    }

    @FXML
    void findMaterial(ActionEvent event) throws SQLException {
        Gener gener = new Gener();
        if (materialSearchTextField.getText().isEmpty()) {
            materialTableView.getItems().clear();
            materialTableView.getItems().addAll(gener.uploadAllMaterials());

        } else {

            materialTableView.getItems().clear();
            materialTableView.getItems().addAll(gener.findMaterialByName(materialSearchTextField.getText()));
        }


    }

    private void setMaterialErrorText(String text) {
        materialErrorText.setText("text");
        materialErrorText.setFill(Color.RED);

    }

    @FXML
    void fillMaterialUpdateDetails(MouseEvent event) {

        if (materialTableView.getSelectionModel().getSelectedItem() != null) {
            Material material = (Material) materialTableView.getSelectionModel().getSelectedItem();
            materialUpdateNameTextField.setText(material.getName());
            updateMaterialUnitsComboBox.setValue(material.getUnit());
            materialUpdatePriceTextField.setText(String.valueOf(material.getPrice()));
            materialIdTextField.setText(String.valueOf(material.getId()));


        } else {
            setMaterialErrorText("Prašome pasirinkti medžiagą.");
        }

    }

    @FXML
    void goToAddClient(ActionEvent event) throws IOException, SQLException {
        Firm firm = (Firm) firmsTableView.getSelectionModel().getSelectedItem();
        Gener gener = new Gener();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("createclientworkact.fxml"));
        Parent root1 = (Parent) fxmlLoader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root1));
        CreateClientController creatematerialController = fxmlLoader.getController();
        fxmlLoader.setController(creatematerialController);
        creatematerialController.setFirmName(firm);
        stage.showAndWait();

        clientTableView.getItems().clear();
        clientTableView.getItems().addAll(gener.uploadClientFromDbByFirmID(firm.getId()));

    }



    @FXML
    void goToMaterialsSettings(ActionEvent event) throws SQLException {
        usersSettingsPane.setVisible(false);
        firmSettingsPane.setVisible(false);
        materialsSettingsPane.setVisible(true);
        clientsSettingsPane.setVisible(false);
        resetPasswordPane.setVisible(false);

        materialid.setCellValueFactory(new PropertyValueFactory<Material, Integer>("id"));
        materialName.setCellValueFactory(new PropertyValueFactory<Material, String>("name"));
        materialUnits.setCellValueFactory(new PropertyValueFactory<Material, String>("unit"));
        materialPrice.setCellValueFactory(new PropertyValueFactory<Material, Double>("price"));

        updateMaterialUnitsComboBox.getItems().addAll("vnt.", "m", "cm", "kg", "g");
        materialUnitsComboBox.getItems().addAll("vnt.", "m", "cm", "kg", "g");
        deleteMaterialTextField.setVisible(false);
        deleteMaterialButton.setVisible(false);
        cancelMaterialButton.setVisible(false);

        Gener gener = new Gener();
        materialTableView.getItems().clear();
        materialTableView.getItems().addAll(gener.uploadAllMaterials());
    }

    @FXML
    void goToCompanySettings(ActionEvent event) throws SQLException {
        usersSettingsPane.setVisible(false);
        firmSettingsPane.setVisible(true);
        materialsSettingsPane.setVisible(false);
        clientsSettingsPane.setVisible(false);
        resetPasswordPane.setVisible(false);
        fillCompanyDetails();

        companyPasswordText.setVisible(false);
        companyrePasswordText.setVisible(false);
        companyPasswordTextField.setVisible(false);
        companyRepaswordTextField.setVisible(false);


    }

    private void fillCompanyDetails() throws SQLException {
        Gener gener = new Gener();
        companyWorkActSeriesTextField.setText(gener.getWorkActIndexFromDB());
        companyWorkActSavePathTextField.setText(gener.getWorkActFilePath());
        companyInvoiceSieriesTextField.setText(gener.getInvoiceSeries());
        companySieriesPathTextField.setText(gener.getInvoicePath());
        companyBankNameTextField.setText(gener.getCompanyBankName());
        companyIBanTextField.setText(gener.getCompanyiBan());
        companyEmailTextField.setText(gener.getCompanyEmail());
    }



    @FXML
    void goToUserSettings(ActionEvent event) {

        usersSettingsPane.setVisible(true);
        firmSettingsPane.setVisible(false);
        materialsSettingsPane.setVisible(false);
        clientsSettingsPane.setVisible(false);
        resetPasswordPane.setVisible(false);

    }


    @FXML
    void saveCompanySettings(ActionEvent event) throws SQLException {
        Gener gener = new Gener();

        if (!gener.isValidWorkActSeries(companyWorkActSeriesTextField.getText())) {
            setCompanyErrorText("Blogai įvesta darbų aktų serija. Serija turi būti sudaryta iš 2 raidžių.");
        } else if (companyWorkActSavePathTextField.getText().isEmpty()) {
            setCompanyErrorText("Blogai pasirinkta darbų aktų saugojimo vieta.");
        } else if (!gener.isValidInvoiceSeries(companyInvoiceSieriesTextField.getText())) {
            setCompanyErrorText("Blogai įvesta sąskaitų faktūrų serija. Serija turi būti sudaryta iš 2 raidžių.");
        } else if (companySieriesPathTextField.getText().isEmpty()) {
            setCompanyErrorText("Blogai pasirinkta sąskaitų faktūrų saugojimo vieta.");
        } else if (companyBankNameTextField.getText().isEmpty()) {
            setCompanyErrorText("Prašome įvesti banko pavadinimą.");
        } else if (gener.isValidIBan(companyIBanTextField.getText())) {
            setCompanyErrorText("Blogai įvestas banko sąskaitos numeris.");
        } else if (!gener.isValidEmailAddress(companyEmailTextField.getText())) {
            setCompanyErrorText("Blogai įvestas el. paštas.");
        } else {
            gener.setCompanyinfo(companyWorkActSeriesTextField.getText(), companyWorkActSavePathTextField.getText(), companyInvoiceSieriesTextField.getText(), companySieriesPathTextField.getText(), companyBankNameTextField.getText(), companyIBanTextField.getText(), companyEmailTextField.getText());
            companyErrorText.setFill(Color.GREEN);
            companyErrorText.setText("Duomenys sėkmingai išsaugoti");

            if (companyPasswordTextField.getText() == companyRepaswordTextField.getText() && !companyPasswordTextField.getText().isEmpty()) {
                gener.setCompanyEmailPassword(companyPasswordTextField.getText());
            }

        }

    }

    private void setCompanyErrorText(String text) {
        companyErrorText.setText(text);
        companyErrorText.setFill(Color.RED);

    }

    @FXML
    void shearchUser(ActionEvent event) throws SQLException {

        if (searchUserTextField.getText().isEmpty()) {
            fillUsersTable();
        } else if (!searchUserTextField.getText().isEmpty()) {
            Gener gener = new Gener();
            tableView.getItems().clear();
            tableView.getItems().addAll(gener.getUserBySearchText(searchUserTextField.getText()));
        }

    }

    private void setUserSettingsErrorText(String text) {
        userSettingsErrorText.setText("");
        userSettingsErrorText.setText(text);
        userSettingsErrorText.setFill(Color.RED);

    }


    @FXML
    void fillUserSelectedUserDetails(MouseEvent event) {
        Gener gener = new Gener();

        User selectedUser = (User) tableView.getSelectionModel().getSelectedItem();
        updateFieldName.setText(selectedUser.getName());
        updateFieldSurname.setText(selectedUser.getSurname());
        updateFieldEmail.setText(selectedUser.getEmail());
        updateFieldPhone.setText(selectedUser.getPhone());
        updateComboBox.setValue(selectedUser.getRole());
        useridTextField.setText(String.valueOf(selectedUser.getId()));

        if (selectedUser.isActive()) {
            checkBoxActive.setSelected(true);
        } else checkBoxActive.setSelected(false);
    }

    @FXML
    void showCompanychangeEmailPassword(ActionEvent event) {

        companyPasswordText.setVisible(true);
        companyrePasswordText.setVisible(true);
        companyPasswordTextField.setVisible(true);
        companyRepaswordTextField.setVisible(true);

    }

    @FXML
    void updateClient(ActionEvent event) throws SQLException {
        Gener gener = new Gener();

        if(!gener.isValidName(clientNameTextField.getText()))
        {
            setClientErrorText("Blogas kliento vardas");
        }
        else if(!gener.isValidName(clientSurnameTextField.getText()))
        {
            setClientErrorText("Bloga kliento pavarė");
        }
        else if(!gener.isValidEmailAddress(clientEmailTextField.getText()))
        {
            setClientErrorText("Blogai įvestas kliento el. paštas.");
        }
        else if (!gener.isValidPhoneNumber(clientPhoneTextField.getText()))
        {
            setClientErrorText("Blogai įvestas kliento telefono numeris. (+3706*******");
        }
        else
        {
            gener.updtateClientInDb(Integer.parseInt(firmIdTextField.getText()),clientNameTextField.getText(),clientSurnameTextField.getText(),
                    clientEmailTextField.getText(),clientPhoneTextField.getText());
        }
    }

    @FXML
    void updateMaterial(ActionEvent event) throws SQLException {
        Gener gener = new Gener();
        if (materialUpdateNameTextField.getText().isEmpty() || materialUpdateNameTextField.getText().length() < 2) {
            setMaterialErrorText("Netinkamai įvestas medžiagos pavadinimas");
        } else if (updateMaterialUnitsComboBox.getValue() == null) {
            setMaterialErrorText("Prašome pasirinkti medžiagos matavimo vienetus.");
        } else if (!gener.isNumeric(materialUpdatePriceTextField.getText()) || Double.parseDouble(materialUpdatePriceTextField.getText()) <= 0) {
            setMaterialErrorText("Blogai įvesta medžiagos kaina.");
        } else {
            gener.updateMaterial(Integer.parseInt(materialIdTextField.getText()), materialUpdateNameTextField.getText(), updateMaterialUnitsComboBox.getValue(), Double.parseDouble(materialUpdatePriceTextField.getText()));
            materialErrorText.setFill(Color.GREEN);
            materialErrorText.setText("Medžiaga sėkmingai atnaujinta.");

            materialIdTextField.setText("");
            materialUpdatePriceTextField.setText("");
            materialUpdateNameTextField.setText("");
            materialUnitsComboBox.setValue(null);
            materialTableView.getItems().clear();
            materialTableView.getItems().addAll(gener.uploadAllMaterials());
        }

    }

    @FXML
    void updateUserInfo(ActionEvent event) throws SQLException {

        Gener gener = new Gener();

        if (!gener.isValidName(updateFieldName.getText())) {
            setUserSettingsErrorText("Vartotjo vardas turi būti sudarytas bent iš 3 raidžių.");
        } else if (!gener.isValidName(updateFieldSurname.getText())) {
            setUserSettingsErrorText("Vartotjo pavardė turi būti sudarytas bent iš 3 raidžių.");
        } else if (!gener.isValidEmailAddress(updateFieldEmail.getText())) {
            setUserSettingsErrorText("Neteisingai įvestas el. paštas.");
        } else if (!gener.isValidPhoneNumber(updateFieldPhone.getText())) {
            setUserSettingsErrorText("Neteisingai įvestas telefono numeris. (pvz.: +3706XXXXXXX)");
        } else if (updateComboBox.getValue() == null) {
            setUserSettingsErrorText("Pasirinkite vartotojo rolę.");
        } else {


            gener.uploadUserdataToDB(Integer.parseInt(useridTextField.getText()), updateFieldName.getText(), updateFieldSurname.getText(), updateFieldEmail.getText(), updateFieldPhone.getText(), checkBoxActive.isSelected(), updateComboBox.getValue());
            updateFieldName.setText(null);
            updateFieldSurname.setText(null);
            updateFieldEmail.setText(null);
            updateFieldPhone.setText(null);
            checkBoxActive.setSelected(false);
            updateComboBox.setValue(null);

            userSettingsErrorText.setFill(Color.GREEN);
            userSettingsErrorText.setText("Vartotojas sėkmingai atnaujintas.");

        }

        fillUsersTable();

    }

    @FXML
    void goToClientSettings(ActionEvent event) {
        clientDeleteTextField.setVisible(false);
        deleteClientButton.setVisible(false);
        cancelClientDeleteButton.setVisible(false);

        usersSettingsPane.setVisible(false);
        firmSettingsPane.setVisible(false);
        materialsSettingsPane.setVisible(false);
        clientsSettingsPane.setVisible(true);
        resetPasswordPane.setVisible(false);

        firmID.setCellValueFactory(new PropertyValueFactory<Firm, Integer>("id"));
        firmNumber.setCellValueFactory(new PropertyValueFactory<Firm, Integer>("firmNumber"));
        firmNameCol.setCellValueFactory(new PropertyValueFactory<Firm, String>("name"));
        firmAdress.setCellValueFactory(new PropertyValueFactory<Firm, String>("adress"));
        formName.setCellValueFactory(new PropertyValueFactory<Firm, String>("formName"));

        clientName.setCellValueFactory(new PropertyValueFactory<Client, String>("name"));
        clientSurname.setCellValueFactory(new PropertyValueFactory<Client, String>("surname"));
        clientEmail.setCellValueFactory(new PropertyValueFactory<Client, String>("email"));
        clientPhone.setCellValueFactory(new PropertyValueFactory<Client, String>("phone"));


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
        Stage stage1 = (Stage) buttonSettings.getScene().getWindow();
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
        Stage stage1 = (Stage) buttonSettings.getScene().getWindow();
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
        Stage stage1 = (Stage) buttonSettings.getScene().getWindow();
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
        Stage stage1 = (Stage) buttonSettings.getScene().getWindow();
        stage1.close();
    }

    @FXML
    void askForResetPassword(ActionEvent event)
    {
        if(tableView.getSelectionModel().getSelectedItem() != null)
        {
            resetPasswordButton.setVisible(true);
            cancelPasswordResetButton.setVisible(true);
            resetPasswordText.setVisible(true);

        }else
        {
            setUserSettingsErrorText("Prašome pasirinkti vartotoją.");
        }

    }

    @FXML
    void cancelPasswordReset (ActionEvent event)
    {
        resetPasswordButton.setVisible(false);
        cancelPasswordResetButton.setVisible(false);
        resetPasswordText.setVisible(false);

    }


    @FXML
    void resetPassword(ActionEvent event) throws SQLException, IOException {
        User user = (User) tableView.getSelectionModel().getSelectedItem();
        Gener gener = new Gener();
        String password = gener.generatePassword();
        gener.updateUserPassword(user,password);
        gener.sendUserPassword(user.getEmail(),gener.getUserUsernameByUserID(user.getId()),password);
        resetPasswordButton.setVisible(false);
        cancelPasswordResetButton.setVisible(false);
        resetPasswordText.setVisible(false);
        userSettingsErrorText.setFill(Color.GREEN);
        userSettingsErrorText.setText("Naujas laptažodis sėkmingi išsiųstas vartotojui.");

    }





    @FXML
    void goToPasswordReset (ActionEvent event)
    {
        usersSettingsPane.setVisible(false);
        firmSettingsPane.setVisible(false);
        materialsSettingsPane.setVisible(false);
        clientsSettingsPane.setVisible(false);
        resetPasswordPane.setVisible(true);
    }
    @FXML
    void loggedinPasswordreset (ActionEvent event) throws SQLException {
        Gener gener = new Gener();
        if(!gener.isSamePassword(loggedInUser.getId(),loggedinOldPassword.getText()))
        {
            setloggedInErrorText("Netinkamas senas vartotojo slaptažodis.");
        }
        else if(!gener.isvalidPassword(loggedinNewPassword.getText()))
        {
            setloggedInErrorText("Netinkamas naujas vartotojo slaptažodis.");
        }
        else if(!loggedinNewPassword.getText().equals(loggedinNewPasswordRE.getText()))
        {
            setloggedInErrorText("Slaptažodžiai nesutampa.");
        }
        else
        {
            gener.updateUserPassword(loggedInUser,loggedinNewPassword.getText());
            loggedInErrorText.setFill(Color.GREEN);
            loggedInErrorText.setText("Slaptažodis sėkmingai pakeistas.");
            loggedinNewPassword.setText("");
            loggedinNewPasswordRE.setText("");
            loggedinNewPasswordRE.setText("");
        }



    }
    private void setloggedInErrorText (String text)
    {
        loggedInErrorText.setText(text);
        loggedInErrorText.setFill(Color.RED);

    }



}
