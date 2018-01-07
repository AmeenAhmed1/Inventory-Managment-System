package Exports;

import Imports.ImportsDataController;
import com.mysql.jdbc.PreparedStatement;
import database.DBConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author AmeenAhmed
 */
public class ExportsDataController implements Initializable {

    @FXML
    private Label lbl;

    @FXML
    private TextField txtSearch, txtQuantity, txtAvailable;

    @FXML
    private ComboBox<String> boxCustomer, boxBarCode;

    @FXML
    private DatePicker dataPicker;

    @FXML
    private TableView<Exports> exportsTable;

    @FXML
    private TableColumn<Exports, String> BarCode;

    @FXML
    private TableColumn<Exports, Integer> Quantity;

    @FXML
    private TableColumn<Exports, String> Customer;

    @FXML
    private TableColumn<Exports, String> Date;

    private ObservableList<Exports> data = FXCollections.observableArrayList();

    Connection conn;
    PreparedStatement PS;
    ResultSet RS;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        try {
            conn = DBConnection.DBConnect();
            setTable();
            dataClear();
            customerBox();
            codeBox();
        } catch (SQLException ex) {
            System.out.println(ex);
            JOptionPane.showMessageDialog(null, ex);
        }
    }

    //DataTable
    public void setTable() {
        try {
            String sql = "SELECT * FROM `exports` WHERE 1";
            PS = (PreparedStatement) conn.prepareStatement(sql);
            RS = PS.executeQuery();

            data.clear();

            while (RS.next()) {
                data.add(new Exports(RS.getInt(1),
                        RS.getString(2),
                        RS.getInt(3),
                        RS.getString(4),
                        RS.getString(5)
                ));
            }

            BarCode.setCellValueFactory(new PropertyValueFactory<Exports, String>("Code"));
            Quantity.setCellValueFactory(new PropertyValueFactory<Exports, Integer>("Quantity"));
            Customer.setCellValueFactory(new PropertyValueFactory<Exports, String>("Customer"));
            Date.setCellValueFactory(new PropertyValueFactory<Exports, String>("Date"));

            exportsTable.setItems(data);

            PS.close();
            RS.close();

        } catch (SQLException ex) {
            Logger.getLogger(ImportsDataController.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, ex);
        }
    }

    //clear fields
    public void dataClear() {
        txtSearch.clear();
        txtQuantity.setText("1");
        dataPicker.setValue(LocalDate.now());
        boxCustomer.setValue("Choose");
        //boxBarCode.setValue("Code");
        //boxBarCode.valueProperty().set(null);
        //txtAvailable.setText("0");
        lbl.setText("");
    }

    //Fill the customer combobox
    public void customerBox() throws SQLException {

        String sql = "SELECT `Name` FROM `Customers`";
        PS = (PreparedStatement) conn.prepareStatement(sql);
        RS = PS.executeQuery();

        ObservableList<String> customer = FXCollections.observableArrayList();

        while (RS.next()) {
            customer.add(RS.getString("Name"));
        }

        boxCustomer.setItems(null);
        boxCustomer.setItems(customer);

        PS.close();
        RS.close();
    }

    //Fill the BarCode combobox
    public void codeBox() throws SQLException {

        String sql = "SELECT `BarCode` FROM `report`";
        PS = (PreparedStatement) conn.prepareStatement(sql);
        RS = PS.executeQuery();

        ObservableList<String> Code = FXCollections.observableArrayList();

        while (RS.next()) {
            Code.add(RS.getString("BarCode"));
        }

        boxBarCode.setItems(null);
        boxBarCode.setItems(Code);

        PS.close();
        RS.close();
    }

    //Get the availalbe quantity
    public void getCodeQuantity() throws SQLException {
        try {
            //System.out.println("Value : " + boxBarCode.getValue());
            //int Q = Integer.parseInt(boxBarCode.getValue());
            if (!(boxBarCode.getValue().equals("Code"))) {
                String sql = "SELECT Quantity FROM report WHERE BarCode = '" + boxBarCode.getValue() + "'";
                PS = (PreparedStatement) conn.prepareStatement(sql);
                RS = PS.executeQuery();
                RS.next();
                txtAvailable.setText(String.valueOf(RS.getInt("Quantity")));
            }
        } catch (SQLException e) {
            System.out.println("Getting Code Quantity Method");
            System.out.println(e);
        } finally {
            PS.close();
            RS.close();
        }
    }

    // Q is the selected value id of the value
    int q;
    int currentQuantity;
    String currentBarCode;

    //Show data
    public void show() throws SQLException {

        currentQuantity = 0;
        currentBarCode = null;
        
        try {
            Exports export = (Exports) exportsTable.getSelectionModel().getSelectedItems();
            //System.out.println("1");
            String sql = "SELECT * FROM `exports` WHERE 1";
            PS = (PreparedStatement) conn.prepareStatement(sql);

            System.out.println("1");
            boxBarCode.setValue(export.getCode());
            System.out.println("2");
            boxCustomer.setValue(export.getCustomer());
            System.out.println("3");
            txtQuantity.setText(String.valueOf(export.getQuantity()));
            System.out.println("4");
            dataPicker.setValue(LocalDate.parse(export.getDate()));
            System.out.println("5");
            
            currentQuantity = Integer.parseInt(txtQuantity.getText());
            currentBarCode = boxBarCode.getValue();

            System.out.println(currentQuantity);
            System.out.println(currentBarCode);
            
            q = export.getId();

        } catch (Exception e) {
            System.out.println("Show method");
            System.out.println(e);
        } finally {
            PS.close();
            RS.close();
        }
    }

    //Update Export
    public void updateExport() throws SQLException {

        //Variable Q is the selected value
        String sql = "update `exports` set BarCode=?, Quantity=?, Customer=?, Date=?  where ID='" + q + "'";

        try {
            PS = (PreparedStatement) conn.prepareStatement(sql);
            PS.setString(1, boxBarCode.getValue());
            PS.setInt(2, Integer.parseInt(txtQuantity.getText()));
            PS.setString(3, boxCustomer.getValue());
            PS.setString(4, dataPicker.getValue().toString());
            PS.execute();

            lbl.setText("Updated");
        } catch (SQLException e) {
            System.out.println("Update Method");
            System.out.println(e);
        } finally {
            PS.close();
            RS.close();
            setTable();
            dataClear();
        }
    }

    //Insert new exports function
    public void insertExports() throws SQLException {

        /*if (txtQuantity.getText().trim().isEmpty()
                || txtQuantity.getText().equals("0") || boxCustomer.getValue().equals("Choose")) {

            lbl.setText("Please fill the fields");    
        }*/
        System.out.println(Integer.parseInt(txtQuantity.getText()));
        System.out.println(Integer.parseInt(txtAvailable.getText()));
        try {
            String sql = "INSERT INTO `exports`(`BarCode`, `Quantity`, `Customer`, `Date`) "
                    + "VALUES (?,?,?,?)";

            int x = Integer.parseInt(txtQuantity.getText());
            int y = Integer.parseInt(txtAvailable.getText());

            if (x <= y && y != 0) {

                PS = (PreparedStatement) conn.prepareStatement(sql);
                PS.setString(1, boxBarCode.getValue());
                PS.setInt(2, Integer.parseInt(txtQuantity.getText()));
                PS.setString(3, boxCustomer.getValue());
                PS.setString(4, dataPicker.getValue().toString());

                PS.execute();

                lbl.setText("Exported");
                System.out.println("Exported");

                PS.close();

                String query = "update `report` set Quantity=? where BarCode = '" + boxBarCode.getValue() + "'";
                PS = (PreparedStatement) conn.prepareStatement(query);
                PS.setInt(1, (y - x));
                PS.execute();

            } else {
                System.out.println("Can`t add");
                lbl.setText("Quantity Not Available");
            }
        } catch (SQLException ex) {

            System.out.println("LL" + ex);
            System.out.println("Exported Method");

        } finally {

            PS.close();
            //int x = Integer.parseInt(txtAvailable.getText()) - Integer.parseInt(txtQuantity.getText());
        }
        setTable();
        dataClear();

    }
}
