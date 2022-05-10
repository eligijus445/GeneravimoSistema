package FrontEnd;

import Backend.*;
import com.itextpdf.text.DocumentException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;

public class WorkActUpdateController {

    @FXML
    private Button addMaterialButton;

    @FXML
    private Button buttonSettings;

    @FXML
    private Button cancelButton;

    @FXML
    private TableColumn<Client, String> clientEmail;

    @FXML
    private TableColumn<Client, String> clientName;

    @FXML
    private TableColumn<Client, String> clientPhone;

    @FXML
    private TableColumn<Client, String> clientSurname;

    @FXML
    private TableView<?> clientTableView;

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
    private TextField firmAdressTextField;

    @FXML
    private TextField firmNameTextField;

    @FXML
    private TextField firmNumberTextField;

    @FXML
    private Button generateButton;

    @FXML
    private Text generatingErrorText;

    @FXML
    private TableColumn<Material, String> materialName;

    @FXML
    private TableColumn<Material, Double> materialPrice;

    @FXML
    private Button materialSearchButton;

    @FXML
    private TextField materialSearchTextField;

    @FXML
    private TableView<?> materialTableView;

    @FXML
    private TableColumn<Material, String> materialUnits;

    @FXML
    private TableColumn<Material, Integer> materialid;

    @FXML
    private Button openPDFButton;

    @FXML
    private TextField priceWithuotTAX;

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
    private TableView<?> usedMaterialTableView;

    @FXML
    private TableColumn<UsedMaterial, String> usedmaterialUnits;

    @FXML
    private Text vatNumberText;

    @FXML
    private TextField vatNumberTextField;

    @FXML
    private TextArea workResultTextArea;

    Firm firm = new Firm();
    ArrayList usedMaterialArrayList = new ArrayList();
    double OaverAllPriceWithuotTAX = 0;
    WorkAct workAct = new WorkAct();


    @FXML
    void cancelWorkAct(ActionEvent event) {
        Stage stage= (Stage) vatNumberTextField.getScene().getWindow();
        stage.close();

    }

    @FXML
    void createClient (ActionEvent event) throws IOException, SQLException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("createclientworkact.fxml"));
        Parent root1 = (Parent) fxmlLoader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root1));
        CreateClientController creatematerialController = fxmlLoader.getController();
        fxmlLoader.setController(creatematerialController);
        creatematerialController.setFirmName(firm);
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
        clientTableView.getItems().setAll(gener.uploadClientFromDbByFirmID(firm.getId()));

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
        refresh(usedMaterial);

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
    void setWorkActErrorText(String errorText) {
        generatingErrorText.setText("");
        generatingErrorText.setText(errorText);
    }

    @FXML
    void updateWorkAct(ActionEvent event) throws SQLException, ParseException, IOException, DocumentException {
        setWorkActErrorText("");
        Gener gener = new Gener();


        int timeValid = gener.isValidDateAndTime(startTime.getText(),startDate.getValue(), endTime.getText(), endDate.getValue());
        if(gener.isValidTimeFormat(startTime.getText()) == false || gener.isValidTimeFormat(endTime.getText()) == false)
        {
            setWorkActErrorText("Blogai įvestas laikas.");
        }
        else if(timeValid == 0){
            setWorkActErrorText("Blogai įvesta laikas ar data. Darbų pradžios laikas prasideda vėliau nei dabrų pabaigos laikas.");
        }
        else if(timeValid == 2)
        {
            setWorkActErrorText("Blogai įvestas laikas ar data. Darbų pradžios laikas ir pabaigos laikas vienodi.");
        }
        else if(timeValid == 3 )
        {
            setWorkActErrorText("Blogai įvestas laikas ar data");
        }
        else if(gener.isValidDescription(workResultTextArea.getText())== false)
        {
            setWorkActErrorText("Blogai įvestas darbo rezultatas.");
        }
        else if(!gener.isValidVatNumber(vatNumberTextField.getText()) && vatNumberTextField.isDisable()== false)
        {
            setWorkActErrorText("Blogai įvestas įmonės PVM mokėtojo kodas.");
        }
        else if(clientTableView.getSelectionModel().getSelectedItem()==null){
            setWorkActErrorText("Prašome pasirinkti anstakingą asmenį arbą jį prdėti");
        }
        else if(timeValid == 1)
        {
            User user = gener.getUserByID(workAct.getUserID());
            Client selectedClient = (Client) clientTableView.getSelectionModel().getSelectedItem();

            gener.updateWorkActToDB(workAct.getId(),workAct.getNumber(),
                    workResultTextArea.getText(),
                    startDate.getValue()+" "+startTime.getText(),
                    endDate.getValue()+" "+endTime.getText(),
                    gener.getDateAndTime(),
                    gener.getWorkActFilePath(),
                    firm.getId(), selectedClient.getId(),user.getId());


            uploadUsedMaterials();
            gener.createWorkActPDF(gener.uploadWorkActFromDbByWorkActNumber(workAct.getNumber()));
            openPDFButton.setDisable(false);
            generateButton.setDisable(true);
        }

    }
    private void uploadUsedMaterials() throws SQLException {


        Gener gener = new Gener();
        gener.deleteAllUsedMaterialsFromDBByWorkActID(workAct.getId());
        int a=0;
        if(usedMaterialArrayList.get(a)!=null) {
            try {
                while (usedMaterialArrayList.get(a) != null) {
                    UsedMaterial usedMaterial = (UsedMaterial) usedMaterialArrayList.get(a);
                    gener.uploadUsedMaterialsToDb(usedMaterial.getQuantity(),
                            workAct.getId(), usedMaterial.getMaterialID());
                    a++;
                }
            } catch (Exception e) {
            }
        }

    }


    @FXML
    void openPDF(ActionEvent event) throws SQLException {
        Gener gener =new Gener();

        gener.openWorkActPDF(workAct.getNumber());

    }
    @FXML
    void addMaterial(ActionEvent event) throws IOException, SQLException {
        Gener gener = new Gener();
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


    public void setWorkAct(WorkAct workAct) throws SQLException, IOException {
        Gener gener = new Gener();
        this.workAct = workAct;
        this.firm =  gener.getFirmByFirmId(workAct.getFirmId());
        if (!gener.isValidVatNumber(firm.getVatNumber()))
        {
            setWorkActErrorText("Prašome įvesti PVM mokėtojo kodą.");
            vatNumberText.setFill(Color.RED);

        }

        firmNameTextField.setText(firm.getName());
        firmAdressTextField.setText(firm.getAdress());
        firmNumberTextField.setText(String.valueOf(firm.getFirmNumber()));
        vatNumberTextField.setText(firm.getVatNumber());

        workResultTextArea.setText(workAct.getDescription());

        startDate.setValue(LocalDate.parse(workAct.getStartTimeDate().substring(0,10)));
        endDate.setValue(LocalDate.parse(workAct.getEndTimeDate().substring(0,10)));
        startTime.setText(workAct.getStartTimeDate().substring(11,16));
        endTime.setText(workAct.getEndTimeDate().substring(11,16));

        materialid.setCellValueFactory(new PropertyValueFactory<Material, Integer>("id"));
        materialName.setCellValueFactory(new PropertyValueFactory<Material, String>("name"));
        materialUnits.setCellValueFactory(new PropertyValueFactory<Material, String>("unit"));
        materialPrice.setCellValueFactory(new PropertyValueFactory<Material, Double>("price"));

        fillMaterialTable();

        usedMaterialId.setCellValueFactory(new PropertyValueFactory<UsedMaterial, Integer>("materialID"));
        usedMaterialName.setCellValueFactory(new PropertyValueFactory<UsedMaterial, String>("name"));
        usedmaterialUnits.setCellValueFactory(new PropertyValueFactory<UsedMaterial, String>("unit"));
        usedMaterialPrice.setCellValueFactory(new PropertyValueFactory<UsedMaterial, Double>("price"));
        usedMaterialQuantity.setCellValueFactory(new PropertyValueFactory<UsedMaterial, Double>("quantity"));


       try
       {
           int a=0;
           ArrayList arrayList = gener.getUsedMaterialsFromDBByWorkActID(workAct.getId());
           while (arrayList.get(a) != null)
           {
               UsedMaterial usedMaterial = (UsedMaterial) arrayList.get(a);
               usedMaterial.setPrice(usedMaterial.getPrice() * usedMaterial.getQuantity());
               refresh(usedMaterial);

               a++;
           }

       }catch (Exception e) {}

        fillUsedMaterialTable();
        fillClientTable();

    }
    private void fillMaterialTable() throws SQLException {
        Gener gener = new Gener();
        materialTableView.getItems().clear();
        materialTableView.getItems().addAll(gener.uploadAllMaterials());
    }
    private void fillUsedMaterialTable()  {

        usedMaterialTableView.getItems().clear();
        usedMaterialTableView.getItems().addAll(usedMaterialArrayList);

    }
    private void refresh( UsedMaterial usedMaterial) throws SQLException {
        usedMaterialArrayList.add(usedMaterial);

        OaverAllPriceWithuotTAX += usedMaterial.getPrice();
        fillUsedMaterialTable();
        fillOverAllPrice();
        fillMaterialTable();

    }
    private void fillOverAllPrice()
    {Gener gener = new Gener();
        this.priceWithuotTAX.setText(String.valueOf(gener.priceRound(OaverAllPriceWithuotTAX)));
    }

}
