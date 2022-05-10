package FrontEnd;

import Backend.*;
import com.itextpdf.text.DocumentException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import static javafx.scene.paint.Color.RED;

public class workactcreateController implements Initializable {

    @FXML
    private Button addMaterialButton;


    @FXML
    private ComboBox<String> clientComboBox;

    @FXML
    private ChoiceBox clientChoiceBox;

    @FXML
    private TableColumn<Firm, String> adress;

    @FXML
    private Button buttonSettings;

    @FXML
    private Button cancelButton;


    @FXML
    private Button createMaterialButton;

    @FXML
    private Pane createWorkActPane;

    @FXML
    private AnchorPane createWorkact;

    @FXML
    private Button deleteMaterialButton;

    @FXML
    private DatePicker endDate;

    @FXML
    private TextField endTime;
    @FXML
    private TextField vatNumberTextField;
    @FXML
    private TextField firmNumberTextField;

    @FXML
    private TextField firmAdressTextField;

    @FXML
    private TextField firmNameTextField;

    @FXML
    private TableColumn<Firm, Integer> firmNumber;

    @FXML
    private TableView<?> firmsTableView;

    @FXML
    private TableView<?> clientTableView;

    @FXML
    private TableView<?> materialTableView;
    @FXML
    private TableView<?> usedMaterialTableView;

    @FXML
    private TableColumn<Firm,String> formName;
    @FXML
    private TableColumn<Client,String> clientName;
    @FXML
    private TableColumn<Client,String> clientSurname;
    @FXML
    private TableColumn<Client,String> clientEmail;
    @FXML
    private TableColumn<Client,String> clientPhone;

    @FXML
    private Button generateButton;



    @FXML
    private Text generatingErrorText;

    @FXML
    private TableColumn<Firm, Integer> id;

    @FXML
    private TableColumn<Material, String> materialName;

    @FXML
    private TableColumn<Material, Double> materialPrice;

    @FXML
    private TableColumn<Material, String> materialUnits;

    @FXML
    private Button materialSearchButton;

    @FXML
    private TextField materialSearchTextField;

    @FXML
    private TableColumn<Material, Integer> materialid;

    @FXML
    private TableColumn<Firm, String> name;

    @FXML
    private TextField priceWithuotTAX;

    @FXML
    private Pane seachFirmPane;

    @FXML
    private TextField searchFirmNameTextField;

    @FXML
    private TextField searchFirmNumberTextField;

    @FXML
    private Button selectFirmButton;

    @FXML
    private Text selectFirmErrorText;
    @FXML
    private Text vatNumberText;

    @FXML
    private Button shearchFirmButton;
    @FXML
    private  Button createWorkActButton;

    @FXML
    private Button openPDFButton;

    @FXML
    private DatePicker startDate;

    @FXML
    private TextField startTime;

    @FXML
    private TableColumn<UsedMaterial, Integer> usedMaterialId;

    @FXML
    private TableColumn<UsedMaterial, String> usedMaterialName;

    @FXML
    private TableColumn<UsedMaterial, Double> usedMaterialPrice;

    @FXML
    private TableColumn<UsedMaterial, Double> usedMaterialQuantity;

    @FXML
    private TableColumn<UsedMaterial, String> usedmaterialUnits;

    @FXML
    private TextArea workResultTextArea;
    @FXML
    private Button sendWorkActPDF;


    ArrayList usedMaterialArrayList = new ArrayList();
    double OaverAllPriceWithuotTAX = 0;
    Firm selectedFirm= new Firm();
    Gener gener = new Gener();
    String workActNumber = gener.generateWorkActNumber(gener.getWorkActIndexFromDB());
    private static final DecimalFormat df = new DecimalFormat("0.00");

    User loggedInUser = new User();
    public void setLoggedInUser (User user)
    {
        this.loggedInUser = user;

    }

    public workactcreateController() throws SQLException {
    }


    @FXML
    void addMaterial(ActionEvent event) throws IOException, SQLException {
        if (materialTableView.getSelectionModel().getSelectedItem() == null) {
            setWorkActErrorText("Prašome pasirinkti medžiagą.");
        } else {
            Material selectedMaterial = (Material) materialTableView.getSelectionModel().getSelectedItem();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("materialquantityadding.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root1));
            MaterialquantityaddingController materialquantityaddingController = fxmlLoader.getController();
            fxmlLoader.setController(materialquantityaddingController);
            materialquantityaddingController.setMaterial(selectedMaterial);
            stage.showAndWait();

            if (materialquantityaddingController.getQuantity() > 0) {
                UsedMaterial usedMaterial = new UsedMaterial();
                usedMaterial.setMaterialID(selectedMaterial.getId());
                usedMaterial.setName(selectedMaterial.getName());
                usedMaterial.setUnit(selectedMaterial.getUnit());
                usedMaterial.setQuantity(materialquantityaddingController.getQuantity());
                usedMaterial.setPrice(Double.parseDouble(gener.priceRound(selectedMaterial.getPrice() * materialquantityaddingController.getQuantity())));
                refresh(usedMaterial);
            }

        }

    }

   private void fillOverAllPrice() {
        this.priceWithuotTAX.setText(String.valueOf(gener.priceRound(OaverAllPriceWithuotTAX)));
    }

    private void refresh( UsedMaterial usedMaterial) throws SQLException {
        usedMaterialArrayList.add(usedMaterial);
        OaverAllPriceWithuotTAX += usedMaterial.getPrice();
        fillUsedMaterialTable();
        fillOverAllPrice();
        fillMaterialTable();

    }


    void setWorkActErrorText(String errorText) {
        generatingErrorText.setText("");
        generatingErrorText.setText(errorText);
    }

    @FXML
    void cancelWorkAct(ActionEvent event) throws IOException {
        seachFirmPane.setDisable(false);
        seachFirmPane.setVisible(true);
        createWorkActPane.setVisible(false);
        createWorkActPane.setDisable(true);
        Login feMain = new Login();
        feMain.changeLoginsceneToMain("main.fxml");

    }

    @FXML
    void createMaterial(ActionEvent event) throws IOException, SQLException {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("creatematerial.fxml"));
        Parent root1 = (Parent) fxmlLoader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root1));
        CreatematerialController creatematerialController = fxmlLoader.getController();
        fxmlLoader.setController(creatematerialController);
        stage.showAndWait();

        UsedMaterial usedMaterial = creatematerialController.getMaterial();
        if(usedMaterial.getQuantity()>0)
        refresh(usedMaterial);

    }
    @FXML
    void createClient (ActionEvent event) throws IOException, SQLException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("createclientworkact.fxml"));
        Parent root1 = (Parent) fxmlLoader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root1));
        CreateClientController creatematerialController = fxmlLoader.getController();
        fxmlLoader.setController(creatematerialController);
        creatematerialController.setFirmName(selectedFirm);
        stage.showAndWait();

        fillClientTable();

    }
    private void fillClientTable() throws SQLException {
        Gener gener = new Gener();

        clientName.setCellValueFactory(new PropertyValueFactory<Client,String>("name"));
        clientSurname.setCellValueFactory(new PropertyValueFactory<Client,String>("surname"));
        clientEmail.setCellValueFactory(new PropertyValueFactory<Client,String>("email"));
        clientPhone.setCellValueFactory(new PropertyValueFactory<Client,String>("phone"));

        clientTableView.getItems().clear();
        clientTableView.getItems().setAll(gener.uploadClientFromDbByFirmID(selectedFirm.getId()));

    }

    @FXML
    void deleteMaterial(ActionEvent event) throws SQLException {
        if ( usedMaterialTableView.getSelectionModel().getSelectedItem() == null) {
            setWorkActErrorText("Prašome pasirinkti medžiagą.");
        } else {
            UsedMaterial selectedMaterial = (UsedMaterial) usedMaterialTableView.getSelectionModel().getSelectedItem();
            usedMaterialArrayList.remove(selectedMaterial);
            OaverAllPriceWithuotTAX -= selectedMaterial.getPrice();
            fillOverAllPrice();
            fillUsedMaterialTable();
        }
    }


    @FXML
    void findFirm(ActionEvent event) throws SQLException {
        String firmNumber = searchFirmNumberTextField.getText();
        String firmName = searchFirmNameTextField.getText();
        selectFirmErrorText.setText("");
        firmsTableView.getItems().clear();
        Gener gener = new Gener();
        WorkAct workAct = new WorkAct();

        if (gener.isFirmNameValid(firmName) && gener.isFirmNumberValid(String.valueOf(firmNumber))) //jeigu ivesti abu duomenys teisingi
        {
            if (gener.findFirmByNamefromDb(firmName).isEmpty() && gener.findFirmByfirmNumberfromDb(Integer.parseInt(firmNumber)).isEmpty()) //jeigunieko nerasta pagal pateiktus duomenis
            {

                selectFirmErrorText.setText("Pagal teiktus duomenis įmonė nerasta");
            } else if (gener.findFirmByfirmNumberfromDb(Integer.parseInt(firmNumber)).isEmpty() == false && gener.findFirmByNamefromDb(firmName).isEmpty() == false) {
                firmsTableView.getItems().clear();
                firmsTableView.getItems().addAll(gener.findFirmByfirmNumberfromDb(Integer.parseInt(firmNumber)));

            } else if (gener.findFirmByNamefromDb(firmName).isEmpty() && gener.findFirmByfirmNumberfromDb(Integer.parseInt(firmNumber)).isEmpty() == false)//jeigu rasta pagal numeri
            {
                firmsTableView.getItems().addAll(gener.findFirmByfirmNumberfromDb(Integer.parseInt(firmNumber)));
            } else if (gener.findFirmByfirmNumberfromDb(Integer.parseInt(firmNumber)).isEmpty() && gener.findFirmByNamefromDb(firmName).isEmpty() == false)//jeigu rasta pagal pavadinima
            {
                firmsTableView.getItems().addAll(gener.findFirmByNamefromDb(firmName));
            }

        } else if (gener.isFirmNumberValid(firmNumber).equals(true) && gener.isFirmNameValid(firmName).equals(false)) {
            if (gener.findFirmByfirmNumberfromDb(Integer.parseInt(firmNumber)).isEmpty()) {


                selectFirmErrorText.setText("Pagal pateiktus duomenis nepavyko rasti įmonės");
            } else {

                firmsTableView.getItems().addAll(gener.findFirmByfirmNumberfromDb(Integer.parseInt(firmNumber)));

            }


        } else if (gener.isFirmNumberValid(firmNumber).equals(false) && gener.isFirmNameValid(firmName).equals(true)) {


            if (gener.findFirmByNamefromDb(firmName).isEmpty()) {

                selectFirmErrorText.setText("Pagal pateiktus duomenis nepavyko rasti įmonės");
            } else {

                firmsTableView.getItems().addAll(gener.findFirmByNamefromDb(firmName));
            }

        } else if (gener.isFirmNumberValid(firmNumber).equals(false) && gener.isFirmNameValid(firmName).equals(false)) {
            firmsTableView.getItems().clear();
            selectFirmErrorText.setText("Klaidingai įvesti paieškos duomenys");

        }



    }

    @FXML
    void goToWorkAkctPane(ActionEvent event) throws SQLException, IOException, InterruptedException {

        if (((Firm) firmsTableView.getSelectionModel().getSelectedItem()) == null) {
            selectFirmErrorText.setText("");
            selectFirmErrorText.setText("Prašome pasirinkti norimą įmonę.");

        } else {
            Gener gener = new Gener();
            selectedFirm = (Firm) firmsTableView.getSelectionModel().getSelectedItem();
            seachFirmPane.setDisable(true);
            seachFirmPane.setVisible(false);
            createWorkActPane.setVisible(true);
            createWorkActPane.setDisable(false);

            firmNameTextField.setText(selectedFirm.getName());
            firmAdressTextField.setText(selectedFirm.getAdress());
            firmNumberTextField.setText(String.valueOf(selectedFirm.getFirmNumber()));
            firmNumberTextField.setDisable(true);


            if(gener.isVatNumberInDb(selectedFirm.getFirmNumber()) )
            {
                vatNumberTextField.setText(selectedFirm.getVatNumber());
                vatNumberTextField.setDisable(true);


            }else if(gener.findVatByFirmID(selectedFirm.getFirmNumber())!=null) {
                gener.updateVatNumber(selectedFirm.getFirmNumber());
                vatNumberTextField.setText(gener.getVatNumberByFirmID(selectedFirm.getFirmNumber()));
                vatNumberTextField.setDisable(true);
            }
            else{
             vatNumberText.setFill(RED);
             vatNumberTextField.setDisable(false);
                setWorkActErrorText("Nerastas PVM mokėtojo kodas. Prašome įvesti.");
            }


            materialid.setCellValueFactory(new PropertyValueFactory<Material, Integer>("id"));
            materialName.setCellValueFactory(new PropertyValueFactory<Material, String>("name"));
            materialUnits.setCellValueFactory(new PropertyValueFactory<Material, String>("unit"));
            materialPrice.setCellValueFactory(new PropertyValueFactory<Material, Double>("price"));

            fillMaterialTable();
            fillClientTable();


        }


    }

    private void fillMaterialTable() throws SQLException {
        Gener gener = new Gener();
        materialTableView.getItems().clear();
        materialTableView.getItems().addAll(gener.uploadAllMaterials());
    }

    private void fillUsedMaterialTable() throws SQLException {

        usedMaterialTableView.getItems().clear();
        usedMaterialTableView.getItems().addAll(usedMaterialArrayList);

    }


    @FXML
    void findMaterial(ActionEvent event) throws SQLException {

        if (materialSearchTextField.getText().isEmpty() )
        {
            setWorkActErrorText("Prašome įvesti medžiagos pavadinimą");
        }
        else
        {
            Gener gener =new Gener();
            materialTableView.getItems().clear();
            materialTableView.getItems().addAll(gener.findMaterialByName(materialSearchTextField.getText()));
        }



    }

    @FXML
    void openPDF(ActionEvent event) throws IOException, SQLException, DocumentException, ParseException {
        gener.openWorkActPDF(workActNumber);
    }

    @FXML
    void generateWorkAct(ActionEvent event) throws ParseException, IOException, SQLException, DocumentException {

        setWorkActErrorText("");


        Gener gener = new Gener();

        int timeValid = gener.isValidDateAndTime(startTime.getText(), startDate.getValue(), endTime.getText(), endDate.getValue());
        if (gener.isValidTimeFormat(startTime.getText()) == false || gener.isValidTimeFormat(endTime.getText()) == false) {
            setWorkActErrorText("Blogai įvestas laikas.");
        } else if (timeValid == 0) {
            setWorkActErrorText("Blogai įvesta laikas ar data. Darbų pradžios laikas prasideda vėliau nei dabrų pabaigos laikas.");
        } else if (timeValid == 2) {
            setWorkActErrorText("Blogai įvestas laikas ar data. Darbų pradžios laikas ir pabaigos laikas vienodi.");
        } else if (timeValid == 3) {
            setWorkActErrorText("Blogai įvestas laikas ar data");
        } else if (gener.isValidDescription(workResultTextArea.getText()) == false) {
            setWorkActErrorText("Blogai įvestas darbo rezultatas.");
        } else if (clientTableView.getSelectionModel().getSelectedItem() == null) {
            setWorkActErrorText("Prašome pasirinkti anstakingą asmenį arbą jį prdėti");
        } else if (timeValid == 1) {



            Client selectedClient = (Client) clientTableView.getSelectionModel().getSelectedItem();

            gener.uploadWorkActToDB(workActNumber,
                    workResultTextArea.getText(),
                    startDate.getValue() + " " + startTime.getText(),
                    endDate.getValue() + " " + endTime.getText(),
                    gener.getDateAndTime(),
                    gener.getWorkActFilePath(),
                    selectedFirm.getId(), selectedClient.getId(), loggedInUser.getId());


            uploadUsedMaterials();
            gener.createWorkActPDF(gener.uploadWorkActFromDbByWorkActNumber(workActNumber));
            openPDFButton.setDisable(false);
            generateButton.setDisable(true);
            sendWorkActPDF.setDisable(false);
            generatingErrorText.setFill(Color.GREEN);
            generatingErrorText.setText("Darbų aktas sėkmingai sukurtas.");


        }


    }

    @FXML
    private void sendEmail (ActionEvent event) throws SQLException, IOException {
        Gener gener = new Gener();
        WorkAct workAct = gener.uploadWorkActFromDbByWorkActNumber(workActNumber);
        gener.sendWorkActPDFviaEmail(workAct);
        sendWorkActPDF.setDisable(true);
        setWorkActErrorText("Darbų aktas sėkmingai išsųstas.");
        generatingErrorText.setFill(Color.GREEN);
    }



    private void uploadUsedMaterials(){

        Gener gener = new Gener();
        int a=0;
        if(usedMaterialArrayList.get(a)!=null) {
            try {
                while (usedMaterialArrayList.get(a) != null) {
                    UsedMaterial usedMaterial = (UsedMaterial) usedMaterialArrayList.get(a);
                    gener.uploadUsedMaterialsToDb(usedMaterial.getQuantity(),
                            gener.getWorkActIDFromDbByWorkActNumber(workActNumber), usedMaterial.getMaterialID());
                    a++;
                }
            } catch (Exception e) {
            }
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        seachFirmPane.setDisable(false);
        seachFirmPane.setVisible(true);
        createWorkActPane.setVisible(false);
        createWorkActPane.setDisable(true);

        id.setCellValueFactory(new PropertyValueFactory<Firm, Integer>("id"));
        firmNumber.setCellValueFactory(new PropertyValueFactory<Firm, Integer>("firmNumber"));
        name.setCellValueFactory(new PropertyValueFactory<Firm, String>("name"));
        adress.setCellValueFactory(new PropertyValueFactory<Firm, String>("adress"));
        formName.setCellValueFactory(new PropertyValueFactory<Firm, String>("formName"));


        usedMaterialId.setCellValueFactory(new PropertyValueFactory<UsedMaterial, Integer>("materialID"));
        usedMaterialName.setCellValueFactory(new PropertyValueFactory<UsedMaterial, String>("name"));
        usedmaterialUnits.setCellValueFactory(new PropertyValueFactory<UsedMaterial, String>("unit"));
        usedMaterialPrice.setCellValueFactory(new PropertyValueFactory<UsedMaterial, Double>("price"));
        usedMaterialQuantity.setCellValueFactory(new PropertyValueFactory<UsedMaterial, Double>("quantity"));
        createWorkActButton.setDisable(true);


    }



    @FXML

    public void goToSettings(ActionEvent event) throws IOException {
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

    @FXML
    public void goToMain(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("main.fxml"));
        Parent root1 = (Parent) fxmlLoader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root1));
        MainController mainController = fxmlLoader.getController();
        fxmlLoader.setController(mainController);
        mainController.setLoggedInUser(loggedInUser);
        stage.show();
        Stage stage1 = (Stage) createWorkActButton.getScene().getWindow();
        stage1.close();
    }

}
