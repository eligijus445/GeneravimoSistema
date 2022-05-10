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
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class InvoiceCreateController implements Initializable {
    User loggedInUser = new User();
    @FXML
    private TableColumn<WorkActTableRow, String> adress;
    @FXML
    private CheckBox clientEMailCheckBox;
    @FXML
    private TextField clientEMailTextField;
    @FXML
    private Text errorText;
    @FXML
    private Button createinvoicebutton;

    @FXML
    private AnchorPane createWorkact;

    @FXML
    private TableColumn<WorkActTableRow, String> description;

    @FXML
    private TextField discountTextField;
    @FXML
    private TableView workActTableView;

    @FXML
    private TableColumn<WorkActTableRow, String> firmName;

    @FXML
    private TableColumn<WorkActTableRow, String> generateDate;

    @FXML
    private TextField priceWhitTAXTextFlield;

    @FXML
    private TextField priceWhitoutTaxTextField;

    @FXML
    private TextField searchTextField;

    @FXML
    private TextField taxPriceTexField;

    @FXML
    private TableColumn<WorkActTableRow, String> user;

    @FXML
    private TableColumn<WorkActTableRow, Integer> workActID;
    @FXML
    private TableColumn<WorkActTableRow, String> workActNumber;
    @FXML
    private CheckBox discuontCheckBox;
    @FXML
    private DatePicker startDate;
    @FXML
    private DatePicker endDate;
    @FXML
    private Button openPDFButton;
    @FXML
    private Text invoiceGenerationErrorText;
    @FXML
    private Button noButton;
    @FXML
    private Button yesButton;
    @FXML
    private ChoiceBox <Integer> vatChoiceBox;

    public void setLoggedInUser(User user) {
        this.loggedInUser = user;

    }

    @FXML
    void generateInvoiceWhitoutVatNumber(ActionEvent event) throws SQLException, IOException, DocumentException {
        Gener gener = new Gener();
        WorkActTableRow workActTableRow = (WorkActTableRow) workActTableView.getSelectionModel().getSelectedItem();

        WorkAct workAct = gener.getWorkActByID(workActTableRow.getId());
        Firm firm = gener.getFirmByFirmId(workAct.getFirmId());

        Invoice invoice = new Invoice();
        if (gener.isCreatedInvoiceForWorkAct(workAct.getId())) {
            setErrorText("Šiam dabrų aktui jau buvo sugeneruota sąskaita faktūra");
            invoice = gener.getInvoiceFromDBByWorkActID(workAct.getId());
            gener.openInvoicePDF(invoice.getNumber());
            invoiceGenerationErrorText.setVisible(false);
            noButton.setVisible(false);
            yesButton.setVisible(false);
        } else if (clientEMailCheckBox.isSelected() && gener.isValidEmailAddress(clientEMailTextField.getText())) {
            String invoiceNumber = gener.generateInvoiceNumber(gener.getInvoiceSeries());
            gener.uploadinvoiceToDB(invoiceNumber, gener.getDateAndTime(), Integer.parseInt(discountTextField.getText()), discuontCheckBox.isSelected(), workAct.getId(), workAct.getUserID(), vatChoiceBox.getValue());
            errorText.setFill(Color.GREEN);
            errorText.setText("Sąskaita faktūra sėkmingai sukurta");
            invoice = gener.getInvoiceFromDBByWorkActID(workAct.getId());
            gener.createInvoicePDF(invoice);
            gener.openInvoicePDF(invoice.getNumber());
            gener.sendIvoicePDFviaEmail(clientEMailTextField.getText(), invoice);
            gener.uploadFirmEmailToDb(firm.getId(), clientEMailTextField.getText());
            invoiceGenerationErrorText.setVisible(false);
            noButton.setVisible(false);
            yesButton.setVisible(false);

        } else if (!clientEMailCheckBox.isSelected()) {
            String invoiceNumber = gener.generateInvoiceNumber(gener.getInvoiceSeries());
            gener.uploadinvoiceToDB(invoiceNumber, gener.getDateAndTime(), Integer.parseInt(discountTextField.getText()), discuontCheckBox.isSelected(), workAct.getId(), workAct.getUserID(), vatChoiceBox.getValue());
            errorText.setFill(Color.GREEN);
            errorText.setText("Sąskaita faktūra sėkmingai sukurta");
            invoice = gener.getInvoiceFromDBByWorkActID(workAct.getId());
            gener.createInvoicePDF(invoice);
            gener.openInvoicePDF(invoice.getNumber());
            invoiceGenerationErrorText.setVisible(false);
            noButton.setVisible(false);
            yesButton.setVisible(false);
        } else {
            setErrorText("Blogai įvestas gavėjo el. paštas.");
        }


    }

    @FXML
    void openWorkActEditor(ActionEvent event) throws SQLException, IOException {
        Gener gener = new Gener();
        WorkActTableRow workActTableRow = (WorkActTableRow) workActTableView.getSelectionModel().getSelectedItem();
        WorkAct selectedWorkAct = gener.getWorkActByID(workActTableRow.getId());
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("workactupdate.fxml"));
        Parent root1 = (Parent) fxmlLoader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root1));
        WorkActUpdateController workActUpdateController = fxmlLoader.getController();
        fxmlLoader.setController(workActUpdateController);
        workActUpdateController.setWorkAct(selectedWorkAct);
        stage.showAndWait();

    }

    @FXML
    void generateInvoice(ActionEvent event) throws SQLException, DocumentException, IOException {


        if (workActTableView.getSelectionModel().getSelectedItem() == null) {
            setErrorText("Prašome pasirinkti darbų aktą iš kurio norite generuoti sąskaitą fatūrą");

        } else {

            Gener gener = new Gener();
            WorkActTableRow workActTableRow = (WorkActTableRow) workActTableView.getSelectionModel().getSelectedItem();

            WorkAct workAct = gener.getWorkActByID(workActTableRow.getId());
            Firm firm = gener.getFirmByFirmId(workAct.getFirmId());
            if (gener.isValidVatNumber(firm.getVatNumber())) {
                Invoice invoice = new Invoice();
                if (gener.isCreatedInvoiceForWorkAct(workAct.getId())) {
                    setErrorText("Šiam dabrų aktui jau buvo sugeneruota sąskaita faktūra");
                    invoice = gener.getInvoiceFromDBByWorkActID(workAct.getId());
                    gener.openInvoicePDF(invoice.getNumber());
                } else if (clientEMailCheckBox.isSelected() && gener.isValidEmailAddress(clientEMailTextField.getText())) {
                    String invoiceNumber = gener.generateInvoiceNumber(gener.getInvoiceSeries());
                    gener.uploadinvoiceToDB(invoiceNumber, gener.getDateAndTime(), Integer.parseInt(discountTextField.getText()), discuontCheckBox.isSelected(), workAct.getId(), workAct.getUserID(), vatChoiceBox.getValue());
                    errorText.setFill(Color.GREEN);
                    errorText.setText("Sąskaita faktūra sėkmingai sukurta");
                    invoice = gener.getInvoiceFromDBByWorkActID(workAct.getId());
                    gener.createInvoicePDF(invoice);
                    ;
                    gener.openInvoicePDF(invoice.getNumber());
                    gener.sendIvoicePDFviaEmail(clientEMailTextField.getText(), invoice);
                    gener.uploadFirmEmailToDb(firm.getId(), clientEMailTextField.getText());

                } else if (!clientEMailCheckBox.isSelected()) {
                    String invoiceNumber = gener.generateInvoiceNumber(gener.getInvoiceSeries());
                    gener.uploadinvoiceToDB(invoiceNumber, gener.getDateAndTime(), Integer.parseInt(discountTextField.getText()), discuontCheckBox.isSelected(), workAct.getId(), workAct.getUserID(), vatChoiceBox.getValue());
                    errorText.setFill(Color.GREEN);
                    errorText.setText("Sąskaita faktūra sėkmingai sukurta");
                    invoice = gener.getInvoiceFromDBByWorkActID(workAct.getId());
                    gener.createInvoicePDF(invoice);
                    ;
                    gener.openInvoicePDF(invoice.getNumber());
                } else {
                    setErrorText("Blogai įvestas gavėjo el. paštas.");
                }
            } else {
                invoiceGenerationErrorText.setVisible(true);
                noButton.setVisible(true);
                yesButton.setVisible(true);
            }
        }
    }

    @FXML
    void goToEditWorkAct(ActionEvent event) throws IOException, SQLException {
        Gener gener = new Gener();
        WorkActTableRow workActTableRow = (WorkActTableRow) workActTableView.getSelectionModel().getSelectedItem();

        WorkAct selectedWorkAct = gener.getWorkActByID(workActTableRow.getId());
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("workactupdate.fxml"));
        Parent root1 = (Parent) fxmlLoader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root1));
        WorkActUpdateController workActUpdateController = fxmlLoader.getController();
        fxmlLoader.setController(workActUpdateController);
        workActUpdateController.setWorkAct(selectedWorkAct);
        stage.showAndWait();


    }

    @FXML
    void goToGeneratedInvoices(ActionEvent event) {

    }

    @FXML
    void goToGeneratedWorkActs(ActionEvent event) {

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
        Stage stage1 = (Stage) createinvoicebutton.getScene().getWindow();
        stage1.close();
    }

    @FXML
    void openPDF(ActionEvent event) {

    }

    @FXML
    void refreshPrices(KeyEvent event) throws SQLException {
        fillPrices();
    }

    @FXML
    void selectedChoiceBox(MouseEvent event) throws SQLException {
        fillPrices();
    }
    @FXML
    void selectedVat(ActionEvent event) throws SQLException {

            fillPrices();

    }

    @FXML
    void searchWorkAct(ActionEvent event) throws SQLException {
        Gener gener = new Gener();
        if (searchTextField.getText() != null && searchTextField.getText() != "") {

            ArrayList<WorkAct> workActs = gener.searchWorkActsForTableByText(searchTextField.getText());
            ArrayList tableRows = new ArrayList();
            int i = 0;

            WorkActTableRow workActTableRow = null;
            try {
                while (workActs.get(i) != null) {

                    WorkAct workAct = workActs.get(i);

                    User user = gener.getUserByID(workAct.getUserID());
                    Firm firm = gener.getFirmByFirmId(workAct.getFirmId());
                    workActTableRow = new WorkActTableRow(workAct.getId(), workAct.getNumber(), firm.getName(),
                            firm.getAdress(), workAct.getCreatedTime(), user.getName() + " " + user.getSurname(), workAct.getDescription());
                    tableRows.add(workActTableRow);
                    i++;
                }

            } catch (Exception e) {

            }


            workActTableView.getItems().clear();
            workActTableView.getItems().addAll(tableRows);
        } else {
            fillWorkActsTabale();

        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        workActID.setCellValueFactory(new PropertyValueFactory<WorkActTableRow, Integer>("id"));
        workActNumber.setCellValueFactory(new PropertyValueFactory<WorkActTableRow, String>("ActNumber"));
        firmName.setCellValueFactory(new PropertyValueFactory<WorkActTableRow, String>("firmName"));
        adress.setCellValueFactory(new PropertyValueFactory<WorkActTableRow, String>("firmAdress"));
        generateDate.setCellValueFactory(new PropertyValueFactory<WorkActTableRow, String>("date"));
        user.setCellValueFactory(new PropertyValueFactory<WorkActTableRow, String>("userName"));
        description.setCellValueFactory(new PropertyValueFactory<WorkActTableRow, String>("description"));

        createinvoicebutton.setDisable(true);
        invoiceGenerationErrorText.setVisible(false);
        noButton.setVisible(false);
        yesButton.setVisible(false);
        vatChoiceBox.getItems().setAll(21,9,5,0);
        vatChoiceBox.setValue(21);


        try {
            fillWorkActsTabale();
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    private void fillWorkActsTabale() throws SQLException {

        Gener gener = new Gener();
        ArrayList<WorkAct> workActs = gener.getAllWorkActsForTable();

        ArrayList tableRows = new ArrayList();
        int i = 0;

        WorkActTableRow workActTableRow = null;
        try {
            while (workActs.get(i) != null) {

                WorkAct workAct = workActs.get(i);

                User user = gener.getUserByID(workAct.getUserID());
                Firm firm = gener.getFirmByFirmId(workAct.getFirmId());
                workActTableRow = new WorkActTableRow(workAct.getId(), workAct.getNumber(), firm.getName(),
                        firm.getAdress(), workAct.getCreatedTime(), user.getName() + " " + user.getSurname(), workAct.getDescription());
                tableRows.add(workActTableRow);
                i++;
            }

        } catch (Exception e) {

        }


        workActTableView.getItems().clear();
        workActTableView.getItems().addAll(tableRows);


    }

    @FXML
    void openWorkAct(MouseEvent event) throws SQLException {

        if (event.getClickCount() == 2) {
            try {
                Gener gener = new Gener();
                WorkActTableRow workActTableRow = (WorkActTableRow) workActTableView.getSelectionModel().getSelectedItem();
                gener.openWorkActPDF(workActTableRow.getActNumber());
                setErrorText("");

            } catch (Exception e) {
                setErrorText("Darbų aktas PDF formatu nerastas.");

            }


        } else if (event.getClickCount() == 1) {
            fillPrices();

        }


    }

    private void setErrorText(String text) {
        errorText.setText("");
        errorText.setFill(Color.RED);
        errorText.setText(text);
    }

    private void fillPrices() throws SQLException {
        Gener gener = new Gener();


        WorkActTableRow workActTableRow = (WorkActTableRow) workActTableView.getSelectionModel().getSelectedItem();
        WorkAct workAct = gener.getWorkActByID(workActTableRow.getId());
        ArrayList usedMaterials = gener.uploadUsedMaterialsFromDBByWorkActNr(workAct.getNumber());

        if (gener.isNumeric(discountTextField.getText())) {
            if (discuontCheckBox.isSelected())//taikoma nuolaida kainai be pvm
            {
                double priceWhitoutTax = gener.getDiscuontedPrice(Double.valueOf(gener.getUsedMaterialPriceWhitoutTAX(usedMaterials)), Integer.valueOf(discountTextField.getText()));
                double taxPrice = gener.getDiscuontedPrice(priceWhitoutTax * ((double) vatChoiceBox.getValue()/100), Integer.parseInt(discountTextField.getText()));
                double priceWhitTax = priceWhitoutTax + taxPrice;
                priceWhitoutTaxTextField.setText(gener.priceRound(priceWhitoutTax));
                taxPriceTexField.setText(gener.priceRound(taxPrice));
                priceWhitTAXTextFlield.setText(gener.priceRound(priceWhitTax));


            } else {
                double priceWhitoutTax = Double.parseDouble(gener.getUsedMaterialPriceWhitoutTAX(usedMaterials));
                double taxPrice = priceWhitoutTax * ((double) vatChoiceBox.getValue()/100);
                double priceWhitTax = gener.getDiscuontedPrice(priceWhitoutTax + taxPrice, Integer.parseInt(discountTextField.getText()));

                priceWhitoutTaxTextField.setText(gener.priceRound(priceWhitoutTax));
                taxPriceTexField.setText(gener.priceRound(taxPrice));
                priceWhitTAXTextFlield.setText(gener.priceRound(priceWhitTax));
            }

        } else {
            priceWhitoutTaxTextField.setText(gener.priceRound(Double.valueOf(gener.getUsedMaterialPriceWhitoutTAX(usedMaterials))));
            taxPriceTexField.setText(gener.priceRound(Double.parseDouble(gener.getUsedMaterialTAXprice(usedMaterials, vatChoiceBox.getValue()))));
            priceWhitTAXTextFlield.setText(gener.getUsedMaterialPriceWhitTAX(usedMaterials, vatChoiceBox.getValue()));
        }


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
        Stage stage1 = (Stage) createinvoicebutton.getScene().getWindow();
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
        Stage stage1 = (Stage) createinvoicebutton.getScene().getWindow();
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
        Stage stage1 = (Stage) createinvoicebutton.getScene().getWindow();
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
        Stage stage1 = (Stage) createinvoicebutton.getScene().getWindow();
        stage1.close();
    }

    @FXML
    void showClientEmailTextField(ActionEvent event) throws SQLException {
        if (clientEMailCheckBox.isSelected()) {
            clientEMailTextField.setDisable(false);
            Gener gener = new Gener();
            WorkActTableRow workActTableRow = (WorkActTableRow) workActTableView.getSelectionModel().getSelectedItem();
            WorkAct workAct = gener.getWorkActByID(workActTableRow.getId());
            Firm firm = gener.getFirmByFirmId(workAct.getFirmId());
            try {
                clientEMailTextField.setText(firm.getEmail());
            } catch (Exception e) {

            }
        } else {
            clientEMailTextField.setDisable(true);

            clientEMailTextField.setText("");

        }

    }

    @FXML
    void filterInvoices(ActionEvent event) throws SQLException, ParseException {
        Gener gener = new Gener();
        ArrayList<WorkAct> workActs = gener.getWorkActsFilteredByDate(startDate.getValue(), endDate.getValue());
        double minutes = 0;

        ArrayList tableRows = new ArrayList();
        int i = 0;

        WorkActTableRow workActTableRow = null;
        try {
            while (workActs.get(i) != null) {
                WorkAct workAct = workActs.get(i);
                User user = gener.getUserByID(workAct.getUserID());
                Firm firm = gener.getFirmByFirmId(workAct.getFirmId());
                workActTableRow = new WorkActTableRow(workAct.getId(), workAct.getNumber(), firm.getName(),
                        firm.getAdress(), workAct.getCreatedTime(), user.getName() + " " + user.getSurname(), workAct.getDescription());
                tableRows.add(workActTableRow);
                DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                LocalDateTime starttime = LocalDateTime.parse(workAct.getStartTimeDate(), df);
                LocalDateTime endtime = LocalDateTime.parse(workAct.getEndTimeDate(), df);
                i++;
            }

        } catch (Exception e) {


        }

        workActTableView.getItems().clear();
        workActTableView.getItems().addAll(tableRows);
    }

    public class WorkActTableRow {
        int id;
        String ActNumber;
        String firmName;
        String firmAdress;
        String date;
        String userName;
        String description;

        public WorkActTableRow() {
        }

        public WorkActTableRow(int id, String actNumber, String firmName, String firmAdress, String date, String userName, String description) {
            this.id = id;
            ActNumber = actNumber;
            this.firmName = firmName;
            this.firmAdress = firmAdress;
            this.date = date;
            this.userName = userName;
            this.description = description;
        }

        @Override
        public String toString() {
            return "workActTableRow{" +
                    "id=" + id +
                    ", ActNumber='" + ActNumber + '\'' +
                    ", firmName='" + firmName + '\'' +
                    ", firmAdress='" + firmAdress + '\'' +
                    ", date='" + date + '\'' +
                    ", userName='" + userName + '\'' +
                    ", description='" + description + '\'' +
                    '}';
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getActNumber() {
            return ActNumber;
        }

        public void setActNumber(String actNumber) {
            ActNumber = actNumber;
        }

        public String getFirmName() {
            return firmName;
        }

        public void setFirmName(String firmName) {
            this.firmName = firmName;
        }

        public String getFirmAdress() {
            return firmAdress;
        }

        public void setFirmAdress(String firmAdress) {
            this.firmAdress = firmAdress;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }
    }


}
