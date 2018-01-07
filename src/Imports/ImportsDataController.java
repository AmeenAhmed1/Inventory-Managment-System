package Imports;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;

import com.mysql.jdbc.PreparedStatement;
import database.DBConnection;
import static java.lang.Math.abs;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author AmeenAhmed
 */
public class ImportsDataController implements Initializable {

    @FXML
    private Label lbl;

    @FXML
    private TextField txtCode, txtSearch, txtQuantity;

    @FXML
    private ComboBox<String> boxCustomer;

    @FXML
    private DatePicker dataPicker;

    @FXML
    private TableView<Imports> importsTable;

    @FXML
    private TableColumn<Imports, String> BarCode;

    @FXML
    private TableColumn<Imports, Integer> Quantity;

    @FXML
    private TableColumn<Imports, String> Customer;

    @FXML
    private TableColumn<Imports, String> Date;

    private ObservableList<Imports> data = FXCollections.observableArrayList();

    //private LocalDate D = dataPicker.getValue();
    Connection conn;
    PreparedStatement PS;
    ResultSet RS;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        try {
            conn = DBConnection.DBConnect();

            /*Test
            String sql = "select Quantity from imports where BarCode = 600";
            PS = (PreparedStatement) conn.prepareStatement(sql);
            RS = PS.executeQuery();
            
            int x = 0;
            while(RS.next()){
                x+= (RS.getInt("Quantity"));
            }
            System.out.println(x);
            //end Test*/
            setTable();
            dataClear();
            customerBox();

        } catch (SQLException ex) {
            Logger.getLogger(ImportsDataController.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, ex);
        }

    }

    //DataTable
    public void setTable() throws SQLException {
        try {
            String sql = "SELECT * FROM `imports` WHERE 1";
            PS = (PreparedStatement) conn.prepareStatement(sql);
            RS = PS.executeQuery();

            data.clear();

            while (RS.next()) {
                data.add(new Imports(RS.getInt(1),
                        RS.getString(2),
                        RS.getInt(3),
                        RS.getString(4),
                        RS.getString(5)
                ));
            }

            BarCode.setCellValueFactory(new PropertyValueFactory<Imports, String>("Code"));
            Quantity.setCellValueFactory(new PropertyValueFactory<Imports, Integer>("Quantity"));
            Customer.setCellValueFactory(new PropertyValueFactory<Imports, String>("Customer"));
            Date.setCellValueFactory(new PropertyValueFactory<Imports, String>("Date"));

            importsTable.setItems(data);

        } catch (SQLException ex) {
            Logger.getLogger(ImportsDataController.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, ex);
        } finally {
            PS.close();
            RS.close();
        }
    }

    //clear fields
    public void dataClear() {
        txtCode.clear();
        txtSearch.clear();
        txtQuantity.setText("1");
        dataPicker.setValue(LocalDate.now());
        boxCustomer.setValue("Choose");
        lbl.setText("");
    }

    // Q is the selected value id of the value
    int q;
    //To get the old Quantity if you will update or delete
    int currentQuantity;
    String currentBarCode;

    //Show data
    public void show() throws SQLException {

        currentQuantity = 0;
        currentBarCode = null;

        try {
            Imports imports = (Imports) importsTable.getSelectionModel().getSelectedItem();
            String sql = "SELECT * FROM `imports` WHERE 1";
            PS = (PreparedStatement) conn.prepareStatement(sql);

            txtCode.setText(imports.getCode());
            txtQuantity.setText(String.valueOf(imports.getQuantity()));
            boxCustomer.setValue(imports.getCustomer());
            dataPicker.setValue(LocalDate.parse(imports.getDate()));

            currentQuantity = Integer.parseInt(txtQuantity.getText());
            currentBarCode = txtCode.getText();

            System.out.println("Code : " + currentBarCode);
            System.out.println("Quantity : " + currentQuantity);

            q = imports.getId();

        } catch (SQLException e) {
            System.out.println("Show method");
            System.out.println(e);
        } finally {
            PS.close();
            RS.close();

            //currentQuantity = 0;
            //currentBarCode = null;
            //setTable();
            //dataClear();
        }
    }

    //Delete import
    public void deleteImport() throws SQLException {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Do you want to delete " + txtCode.getText() + " ?!",
                ButtonType.YES, ButtonType.NO);
        alert.showAndWait();
        if (alert.getResult() == ButtonType.YES) {
            try {

                Imports im = (Imports) importsTable.getSelectionModel().getSelectedItem();
                String sql = "DELETE FROM `imports` WHERE ID = '" + q + "'";
                PS = (PreparedStatement) conn.prepareStatement(sql);
                PS.execute();
                lbl.setText("Deleted");

            } catch (SQLException e) {
                System.out.println(e);
            } finally {
                PS.close();
                RS.close();

                selectReport();

                while (selectRS.next()) {
                    if (selectRS.getString("BarCode").equals(currentBarCode)) {
                        String sql = "update `report` set Quantity = ? where BarCode = '" + currentBarCode + "'";
                        PreparedStatement P = (PreparedStatement) conn.prepareStatement(sql);

                        P.setInt(1, (selectRS.getInt("Quantity") - currentQuantity));
                        P.execute();

                        P.close();
                        break;
                    }
                }

                setTable();
                dataClear();
            }

        }
    }

    //Update Import
    public void updateImport() throws SQLException {

        //Variable Q is the selected value
        String sql = "update `imports` set BarCode=?, Quantity=?, Customer=?, Date=?  where ID='" + q + "'";

        int x = Integer.parseInt(txtQuantity.getText()) - currentQuantity;
        try {
            PS = (PreparedStatement) conn.prepareStatement(sql);
            //PS = (PreparedStatement) conn.prepareStatement(sql);
            PS.setString(1, txtCode.getText());
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

            selectReport();
            while (selectRS.next()) {
                if (selectRS.getString("BarCode").equals(currentBarCode)) {
                    String s = "update `report` set Quantity = ? where BarCode = '" + currentBarCode + "'";
                    PreparedStatement P = (PreparedStatement) conn.prepareStatement(s);

                    P.setInt(1, (selectRS.getInt("Quantity") + x));
                    P.execute();

                    P.close();
                    break;
                }
            }

            setTable();
            dataClear();
        }
    }

    //Select all items in report table database
    PreparedStatement selectPS, insertPS;
    ResultSet selectRS;

    public void selectReport() throws SQLException {
        String selectSql = "SELECT * FROM `report` WHERE 1";

        selectPS = (PreparedStatement) conn.prepareStatement(selectSql);
        selectRS = selectPS.executeQuery();
    }

    // full inventory function counts the last value in every code
    public void fullImport() throws SQLException {
        PreparedStatement importPS;
        String reportSql = "INSERT INTO `report`(`BarCode`, `Quantity`) VALUES (?,?)";
        importPS = (PreparedStatement) conn.prepareStatement(reportSql);

        importPS.setString(1, txtCode.getText());
        importPS.setInt(2, Integer.parseInt(txtQuantity.getText()));

        importPS.execute();

        importPS.close();
    }

    //Insert new imports function
    public void insertImports() throws SQLException {

        if (txtCode.getText().trim().isEmpty() || txtQuantity.getText().trim().isEmpty() || boxCustomer.getValue().equals("Choose")) {
            lbl.setText("Please fill the fields");
        } else {
            try {

                String sql = "INSERT INTO `imports`(`BarCode`, `Quantity`, `Customer`, `Date`) "
                        + "VALUES (?,?,?,?)";

                //System.out.println(spQuantity.getValue());
                //String d = dataPicker.getValue().toString();
                PS = (PreparedStatement) conn.prepareStatement(sql);
                PS.setString(1, txtCode.getText());
                PS.setInt(2, Integer.parseInt(txtQuantity.getText()));
                //System.out.println("q");
                PS.setString(3, boxCustomer.getValue());
                PS.setString(4, dataPicker.getValue().toString());

                PS.execute();

                lbl.setText("Inserted");
                System.out.println("Inserted");

            } catch (SQLException ex) {
                System.out.println("LL" + ex);
                System.out.println("Insert Method");
                Logger.getLogger(ImportsDataController.class.getName()).log(Level.SEVERE, null, ex);
            } finally {

                PS.close();

                //To validate if the result set is ends
                boolean Flag = true;

                selectReport();

                if (!selectRS.next()) {
                    fullImport();
                    System.out.println("Report Inserted Empty");
                } else {
                    //System.out.println("Else:");
                    do {
                        //System.out.println("Do While");
                        //System.out.println(selectRS.getString("BarCode"));

                        if (selectRS.getString("BarCode").equals(txtCode.getText())) {
                            int n = selectRS.getInt("Quantity");
                            n += Integer.parseInt(txtQuantity.getText());

                            String s = "UPDATE `report` SET Quantity = ? WHERE BarCode = '" + txtCode.getText() + "'";
                            insertPS = (PreparedStatement) conn.prepareStatement(s);
                            insertPS.setInt(1, n);

                            insertPS.execute();
                            insertPS.close();
                            System.out.println("Report Updated");
                            Flag = false;
                            dataClear();
                            break;
                        }
                    } while (selectRS.next());

                    if (Flag) {
                        fullImport();
                    }

                    selectPS.close();
                    selectRS.close();
                }

                setTable();
                dataClear();
            }
        }
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
    }

    //Search function
    public void searchImports() {
        FilteredList<Imports> filteredData = new FilteredList<>(data, e -> true);

        txtSearch.textProperty().addListener((observableValue, oldValue, newValue) -> {
            filteredData.setPredicate((Predicate<? super Imports>) imports -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                return false;
            });
        });
        SortedList<Imports> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(importsTable.comparatorProperty());
        importsTable.setItems(sortedData);
    }
}
