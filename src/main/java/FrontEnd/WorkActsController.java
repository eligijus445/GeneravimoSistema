package FrontEnd;

import Backend.Firm;
import Backend.Gener;
import Backend.User;
import Backend.WorkAct;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.stage.Stage;

import static java.time.temporal.ChronoUnit.*;

public class WorkActsController implements Initializable {

public class WorkActTableRow  {
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


    @FXML
    private TableColumn<WorkActTableRow, String> adress;

    @FXML
    private Button buttonSettings;

    @FXML
    private AnchorPane createWorkact;

    @FXML
    private Button createinvoicebutton;

    @FXML
    private TableColumn<WorkActTableRow, String> description;

    @FXML
    private DatePicker endDate;

    @FXML
    private Text errorText;

    @FXML
    private TableColumn<WorkActTableRow, String> firmName;

    @FXML
    private TableColumn<WorkActTableRow, String> generateDate;

    @FXML
    private DatePicker startDate;

    @FXML
    private TableColumn<WorkActTableRow, String> user;

    @FXML
    private TableColumn<WorkActTableRow, Integer> workActID;

    @FXML
    private TableColumn<WorkActTableRow, String> workActNumber;

    @FXML
    private TableView<?> workActTableView;

    @FXML
    private BarChart<String,Number > workActsPerYearBarChart;
    @FXML
    private NumberAxis ValueYaxis;
    @FXML
    private CategoryAxis monthsXaxis;
    @FXML
    private TextField averageWorkActsperDay;
    @FXML
    private TextField averageWorkHours;
    @FXML
            private  Button workactsbutton;


    User loggedInUser = new User();
    public void setLoggedInUser (User user)
    {
        this.loggedInUser = user;

    }

    @FXML
    void fillterWorkAct(ActionEvent event) throws SQLException, ParseException {
        Gener gener = new Gener();
        ArrayList <WorkAct> workActs = gener.getWorkActsFilteredByDate(startDate.getValue(),endDate.getValue());
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
                LocalDateTime starttime= LocalDateTime.parse(workAct.getStartTimeDate(),df);
                LocalDateTime endtime= LocalDateTime.parse(workAct.getEndTimeDate(),df);
                minutes += (double) MINUTES.between(starttime,endtime)/60;
                i++;
            }

        } catch (Exception e) {


        }

        averageWorkActsperDay.setText(String.valueOf(gener.priceRound((double) tableRows.stream().count()/(DAYS.between(startDate.getValue(), endDate.getValue())+1)))+"   d.a/d");
        averageWorkHours.setText(String.valueOf(gener.priceRound(minutes/(DAYS.between(startDate.getValue(), endDate.getValue())+1)))+"   val./d");


        workActTableView.getItems().clear();
        workActTableView.getItems().addAll(tableRows);

    }

    @FXML
    void generateWorkActReport(ActionEvent event) {

    }


    @FXML
    void openWorkAct(MouseEvent event) {

        if(event.getClickCount() == 2 )
        {
            try{
                Gener gener = new Gener();
                WorkActTableRow workActTableRow = (WorkActTableRow) workActTableView.getSelectionModel().getSelectedItem();
                gener.openWorkActPDF(workActTableRow.getActNumber());
                setErrorText("");

            } catch (Exception e)
            {
                setErrorText("Darbų aktas PDF formatu nerastas.");

            }
        }

    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Gener gener = new Gener();
        workActID.setCellValueFactory(new PropertyValueFactory<WorkActTableRow,Integer>("id"));
        workActNumber.setCellValueFactory(new PropertyValueFactory<WorkActTableRow,String>("ActNumber"));
        firmName.setCellValueFactory(new PropertyValueFactory<WorkActTableRow,String>("firmName"));
        adress.setCellValueFactory(new PropertyValueFactory<WorkActTableRow,String>("firmAdress"));
        generateDate.setCellValueFactory(new PropertyValueFactory<WorkActTableRow,String>("date"));
        user.setCellValueFactory(new PropertyValueFactory<WorkActTableRow,String>("userName"));
        description.setCellValueFactory(new PropertyValueFactory<WorkActTableRow,String>("description"));
        workactsbutton.setDisable(true);




        try {
            fillWorkActsTabale();
        } catch (SQLException e) {
            e.printStackTrace();
        }


        XYChart.Series<String,Number> series = new XYChart.Series<>();
        series.setName("Mėnesis");

        try {
            double counted [] = gener.getLast6MonthsWorkactWorkHours();
            String months [] = gener.getLast6MonthsinWords();
            for (int i = 5; i>-1; i--)
            {
                series.getData().add(new XYChart.Data<>(months[i],counted[i]));

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


        workActsPerYearBarChart.getData().add(series);











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

    private void setErrorText (String text){
        errorText.setText("");
        errorText.setFill(Color.RED);
        errorText.setText(text);
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

}
