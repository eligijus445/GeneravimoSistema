package FrontEnd;

import Backend.*;
import com.itextpdf.text.DocumentException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.awt.image.FilteredImageSource;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class InvoicesController  implements Initializable {
    User loggedInUser = new User();
    public void setLoggedInUser (User user)
    {
        this.loggedInUser = user;

    }
    public class InvoicesTableRow
    {
        int id;
        String InvoiceNumber;
        String firmName;
        String firmAdress;
        String date;
        String userName;
        double sumWhitoutTax;
        String workActNumber;

        public InvoicesTableRow() {
        }

        public InvoicesTableRow(int id, String invoiceNumber, String firmName, String firmAdress, String date, String userName, double sumWhitoutTax, String workActNumber) {
            this.id = id;
            InvoiceNumber = invoiceNumber;
            this.firmName = firmName;
            this.firmAdress = firmAdress;
            this.date = date;
            this.userName = userName;
            this.sumWhitoutTax = sumWhitoutTax;
            this.workActNumber = workActNumber;
        }

        @Override
        public String toString() {
            return "InvoicesTableRow{" +
                    "id=" + id +
                    ", InvoiceNumber='" + InvoiceNumber + '\'' +
                    ", firmName='" + firmName + '\'' +
                    ", firmAdress='" + firmAdress + '\'' +
                    ", date='" + date + '\'' +
                    ", userName='" + userName + '\'' +
                    ", sumWhitoutTax=" + sumWhitoutTax +
                    ", workActNumber='" + workActNumber + '\'' +
                    '}';
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getInvoiceNumber() {
            return InvoiceNumber;
        }

        public void setInvoiceNumber(String invoiceNumber) {
            InvoiceNumber = invoiceNumber;
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

        public double getSumWhitoutTax() {
            return sumWhitoutTax;
        }

        public void setSumWhitoutTax(double sumWhitoutTax) {
            this.sumWhitoutTax = sumWhitoutTax;
        }

        public String getWorkActNumber() {
            return workActNumber;
        }

        public void setWorkActNumber(String workActNumber) {
            this.workActNumber = workActNumber;
        }
    }


    @FXML
    private NumberAxis ValueYaxis;
    @FXML
    private CheckBox clientEMailCheckBox;
    @FXML
    private TextField clientEMailTextField;
    @FXML
    private Button sendEmailButton;




    @FXML
    private TextField invoicesPriceSumTextField;

    @FXML
    private Button buttonSettings;

    @FXML
    private AnchorPane createWorkact;


    @FXML
    private Button createinvoicebutton;

    @FXML
    private DatePicker endDate;

    @FXML
    private Text errorText;

    @FXML
    private TableColumn<InvoicesTableRow, String> firmAdress;

    @FXML
    private TableColumn<InvoicesTableRow, String> firmName;

    @FXML
    private TableColumn<InvoicesTableRow, String> generateDate;

    @FXML
    private TableColumn<InvoicesTableRow, Integer> invoiceID;

    @FXML
    private TableColumn<InvoicesTableRow, String> invoiceNumber;

    @FXML
    private BarChart<String, Double> invoicesBarChart;

    @FXML
    private CategoryAxis monthsXaxis;

    @FXML
    private DatePicker startDate;

    @FXML
    private TableColumn<InvoicesTableRow, Double> sum;

    @FXML
    private TableColumn<InvoicesTableRow, String> user;

    @FXML
    private TableColumn<InvoicesTableRow, String> workActNumber;

    @FXML
    private TableView<?> invoicesTableView;
    @FXML
    private Pane PDFquestionPane;

    @FXML
    private Button workactsbutton;

    @FXML
    private TextField averangePriceSumTextField;
    @FXML
    private TextField invoicesquantityTextField;
    @FXML
    private Button goToInvoicesButton;

    @FXML
    void filterInvoices(ActionEvent event) throws SQLException {

        Gener gener = new Gener();
        ArrayList invoices = gener.getFilteredInvoicesFromDBByDate(startDate.getValue(),endDate.getValue());
        double priceSum = 0;
        ArrayList tableRows = new ArrayList();
        int i = 0;


        try {
            while (invoices.get(i) != null) {

                Invoice invoice = (Invoice) invoices.get(i);

                User user = gener.getUserByID(invoice.getUserID());
                WorkAct workAct = gener.getWorkActByID(invoice.getWorkActID());
                Firm firm = gener.getFirmByFirmId(workAct.getFirmId());
                ArrayList usedMaterials = gener.uploadUsedMaterialsFromDBByWorkActNr(workAct.getNumber());

                double priceWhitoutTax = gener.getDiscuontedPrice(
                        Double.parseDouble(gener.getUsedMaterialPriceWhitoutTAX(usedMaterials)) ,invoice.getDiscount());

                InvoicesTableRow invoicesTableRow = new InvoicesTableRow(invoice.getId(), invoice.getNumber(), firm.getName(),
                        firm.getAdress(), invoice.getDate(), user.getName() + " " + user.getSurname(), priceWhitoutTax, workAct.getNumber());
                tableRows.add(invoicesTableRow);
                priceSum+= priceWhitoutTax;
                i++;
            }

        } catch (Exception e) {


        }

        invoicesquantityTextField.setText(String.valueOf(i));
        averangePriceSumTextField.setText(gener.priceRound(priceSum/i));
        invoicesPriceSumTextField.setText(gener.priceRound(priceSum));
        invoicesTableView.getItems().clear();
        invoicesTableView.getItems().addAll(tableRows);


    }
@FXML
    private void createInvoicePDF (ActionEvent event) throws SQLException, DocumentException, IOException {

        Gener gener = new Gener();
        InvoicesTableRow invoicesTableRow = (InvoicesTableRow) invoicesTableView.getSelectionModel().getSelectedItem();
        Invoice invoice = gener.getInvoiceFromDBByID(invoicesTableRow.getId());
        gener.createInvoicePDF(invoice);
        gener.openInvoicePDF(invoice.getNumber());
        PDFquestionPane.setVisible(false);
        invoicesTableView.setVisible(true);
        setErrorText("");
    }
    @FXML
    private void cancelCreateInvoicePDF (ActionEvent event)
    {
        PDFquestionPane.setVisible(false);
        invoicesTableView.setVisible(true);

    }

    @FXML
    void generateInvoiceReport(ActionEvent event) {

    }


    @FXML
    void openInvoice(MouseEvent event) {
        if(event.getClickCount() == 2 )
        {
            try{
                Gener gener = new Gener();
                InvoicesTableRow invoicesTableRow = (InvoicesTableRow) invoicesTableView.getSelectionModel().getSelectedItem();
                gener.openInvoicePDF(invoicesTableRow.getInvoiceNumber());
                setErrorText("");
            } catch (Exception e)
            {
                setErrorText("Sąskaita faktūra PDF formatu nerasta.");
                invoicesTableView.setVisible(false);
                PDFquestionPane.setVisible(true);



            }


        } else if(event.getClickCount() == 1)
        {


        }

    }
    @FXML
    void openWorkAct(ActionEvent event) throws SQLException {
        Gener gener = new Gener();
        if(invoicesTableView.getSelectionModel().getSelectedItem() == null)
        {

            setErrorText("Pasirinkite sąskaitą faktūrą");
        }else
        {
            try
            {
                InvoicesTableRow invoicesTableRow = (InvoicesTableRow) invoicesTableView.getSelectionModel().getSelectedItem();
                gener.openWorkActPDF(invoicesTableRow.getWorkActNumber());
                setErrorText("");
            }
            catch (Exception e)
            {
                setErrorText("Darbų aktas pdf formatu nerastas.");
            }
        }

    }



    private void setErrorText (String text){
        errorText.setText("");
        errorText.setFill(Color.RED);
        errorText.setText(text);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        invoiceID.setCellValueFactory(new PropertyValueFactory<InvoicesTableRow,Integer>("id"));
        invoiceNumber.setCellValueFactory(new PropertyValueFactory<InvoicesTableRow,String>("InvoiceNumber"));
        firmName.setCellValueFactory(new PropertyValueFactory<InvoicesTableRow,String>("firmName"));
        firmAdress.setCellValueFactory(new PropertyValueFactory<InvoicesTableRow,String>("firmAdress"));
        generateDate.setCellValueFactory(new PropertyValueFactory<InvoicesTableRow,String>("date"));
        user.setCellValueFactory(new PropertyValueFactory<InvoicesTableRow,String>("userName"));
        sum.setCellValueFactory(new PropertyValueFactory<InvoicesTableRow,Double>("sumWhitoutTax"));
        workActNumber.setCellValueFactory(new PropertyValueFactory<InvoicesTableRow,String>("workActNumber"));

        PDFquestionPane.setVisible(false);
        goToInvoicesButton.setDisable(true);

        try {
            fillInvoicesTabale();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Gener gener = new Gener();
        XYChart.Series<String,Double> series = new XYChart.Series<>();
        series.setName("Mėnesis");

        try {
            double[] monthSum = gener.getLast6MonthsInvoicePriceSum();
            String months [] = gener.getLast6MonthsinWords();
            for (int i = 5; i>-1; i--)
            {
                series.getData().add(new XYChart.Data<>(months[i],monthSum[i]));

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        invoicesBarChart.getData().add(series);

    }

    private void fillInvoicesTabale() throws SQLException {
        Gener gener = new Gener();
       ArrayList invoices = gener.getAllInvoicesFromDB();

        ArrayList tableRows = new ArrayList();
        int i = 0;

        InvoicesTableRow invoicesTableRow = new InvoicesTableRow();
        try {
            while (invoices.get(i) != null) {

                Invoice invoice = (Invoice) invoices.get(i);

                User user = gener.getUserByID(invoice.getUserID());
                WorkAct workAct = gener.getWorkActByID(invoice.getWorkActID());
                Firm firm = gener.getFirmByFirmId(workAct.getFirmId());
                ArrayList usedMaterials = gener.uploadUsedMaterialsFromDBByWorkActNr(workAct.getNumber());

                double priceWhitoutTax = gener.getDiscuontedPrice(
                        Double.parseDouble(gener.getUsedMaterialPriceWhitoutTAX(usedMaterials)) ,invoice.getDiscount());

                invoicesTableRow = new InvoicesTableRow(invoice.getId(), invoice.getNumber(), firm.getName(),
                        firm.getAdress(), invoice.getDate(), user.getName() + " " + user.getSurname(), priceWhitoutTax, workAct.getNumber());
                tableRows.add(invoicesTableRow);
                i++;
            }

        } catch (Exception e) {

        }
        invoicesTableView.getItems().clear();
        invoicesTableView.getItems().addAll(tableRows);
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
    public void goToCreateInvoice(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("invoicecreate.fxml"));
        Parent root1 = (Parent) fxmlLoader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root1));
        InvoiceCreateController invoiceCreateController = fxmlLoader.getController();
        fxmlLoader.setController(invoiceCreateController);
        invoiceCreateController.setLoggedInUser(loggedInUser);
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
    void showClientEmailTextField(ActionEvent event) throws SQLException {
        Gener gener = new Gener();
        if(clientEMailCheckBox.isSelected())
        {
            clientEMailTextField.setDisable(false);
            sendEmailButton.setDisable(false);

        }
        else
        {
            clientEMailTextField.setDisable(true);
            sendEmailButton.setDisable(true);
            InvoicesTableRow invoicesTableRow = (InvoicesTableRow) invoicesTableView.getSelectionModel().getSelectedItem();
            Invoice invoice = gener.getInvoiceFromDBByID(invoicesTableRow.getId());
            WorkAct workAct = gener.getWorkActByID(invoice.getWorkActID());
            Firm firm = gener.getFirmByFirmId(workAct.getFirmId());
            clientEMailTextField.setText(firm.getEmail());
            try{
                clientEMailTextField.setText(firm.getEmail());
            }
            catch (Exception e)
            {

            }
        }

    }
    @FXML
    public void sendEmail (ActionEvent event) throws SQLException, IOException {
        Gener gener = new Gener();
        if(invoicesTableView.getSelectionModel().getSelectedItem() == null)
        {
            setErrorText("Prašome iš sąrašo pasirinkti sąskaitą fatūrą.");
        }
        else if(!gener.isValidEmailAddress(clientEMailTextField.getText()))
        {
            setErrorText("Blogai įvestas el. pašto adresas.");
        }
        else
        {
            InvoicesTableRow invoicesTableRow = (InvoicesTableRow) invoicesTableView.getSelectionModel().getSelectedItem();
            Invoice invoice = gener.getInvoiceFromDBByID(invoicesTableRow.getId());
            WorkAct workAct = gener.getWorkActByID(invoice.getWorkActID());
            Firm firm = gener.getFirmByFirmId(workAct.getFirmId());
            gener.sendIvoicePDFviaEmail(clientEMailTextField.getText(),invoice);
            errorText.setFill(Color.GREEN);
            errorText.setText("Sąskaita faktūrą sėkmingai išsiųsta");
            gener.uploadFirmEmailToDb(firm.getId(),clientEMailTextField.getText());
        }
    }



}
