package Customer;

import com.mysql.jdbc.PreparedStatement;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import database.DBConnection;
import java.io.IOException;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author AmeenAhmed
 */
public class CustomerDataController implements Initializable {

    @FXML
    private Label lbl;

    @FXML
    private TextField txtName, txtPhone, txtEmail, txtCompany, txtSearch;

    @FXML
    private TextArea txtAddress;

    @FXML
    private ComboBox<String> boxGender;

    @FXML
    private TableView<Customers> customerTable;

    @FXML
    private TableColumn<Customers, String> Name;

    @FXML
    private TableColumn<Customers, String> Gender;

    @FXML
    private TableColumn<Customers, String> Phone;

    @FXML
    private TableColumn<Customers, String> Address;

    @FXML
    private TableColumn<Customers, String> Email;

    @FXML
    private TableColumn<Customers, String> Company;

    private ObservableList<Customers> data = FXCollections.observableArrayList();

    FilteredList<Customers> filteredData = new FilteredList<>(data, e -> true);

    Connection conn = null;
    PreparedStatement PS = null;
    ResultSet RS = null;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            // TODO
            conn = DBConnection.DBConnect();
            setTable();
            boxGender.getItems().addAll("Male", "Femail");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
    }

    //fill table with data from database Customers data
    public void setTable() throws SQLException {

        try {
            String sql = "SELECT * FROM `customers` WHERE 1";
            PS = (PreparedStatement) conn.prepareStatement(sql);
            RS = PS.executeQuery();

            data.clear();

            while (RS.next()) {
                data.add(new Customers(RS.getInt(1),
                        RS.getString(2),
                        RS.getString(3),
                        RS.getString(4),
                        RS.getString(5),
                        RS.getString(6),
                        RS.getString(7)
                )
                );
            }

            Name.setCellValueFactory(new PropertyValueFactory<Customers, String>("name"));
            Gender.setCellValueFactory(new PropertyValueFactory<Customers, String>("gender"));
            Phone.setCellValueFactory(new PropertyValueFactory<Customers, String>("phone"));
            Address.setCellValueFactory(new PropertyValueFactory<Customers, String>("address"));
            Email.setCellValueFactory(new PropertyValueFactory<Customers, String>("email"));
            Company.setCellValueFactory(new PropertyValueFactory<Customers, String>("company"));

            customerTable.setItems(data);

            PS.close();
            RS.close();
        } catch (Exception e) {
            System.out.println("Set Table Method");
            System.out.println(e);
        }
    }

    //Clear all fields
    public void dataClear() {
        txtAddress.clear();
        txtCompany.clear();
        txtEmail.clear();
        txtName.clear();
        txtPhone.clear();
        lbl.setText("");
        //customerTable.requestFocus();
    }

    //Insert New Customer Function
    public void insertCustomer() throws SQLException, IOException {

        //Check if all feilds are not empty
        if (txtName.getText().trim().isEmpty() || txtAddress.getText().trim().isEmpty() || txtCompany.getText().trim().isEmpty()
                || txtEmail.getText().trim().isEmpty() || txtPhone.getText().trim().isEmpty()) {

            lbl.setText("Please fill the fields");
        } else {
            lbl.setText("");

            //Validate if the Customer is exist or not
            String sqlValidate = "SELECT `Name`, `Phone` FROM `customers`";
            PS = (PreparedStatement) conn.prepareStatement(sqlValidate);
            RS = PS.executeQuery();
            boolean flag = true;

            while (RS.next()) {
                if (RS.getString("Name").equals(txtName.getText()) && RS.getString("Phone").equals(txtPhone.getText())) {
                    flag = false;
                    break;
                }
            }

            PS.close();
            RS.close();
            
            if (flag) {
                try {
                    String sql = "INSERT INTO `customers`(`Name`, `Gender`, `Phone`, `Address`, `Email`, `Company`)"
                            + "VALUES(?,?,?,?,?,?)";

                    PS = (PreparedStatement) conn.prepareStatement(sql);
                    PS.setString(1, txtName.getText());
                    PS.setString(2, boxGender.getValue());
                    PS.setString(3, txtPhone.getText());
                    PS.setString(4, txtAddress.getText());
                    PS.setString(5, txtEmail.getText());
                    PS.setString(6, txtCompany.getText());
                    PS.execute();

                    lbl.setText("Inserted");
                } catch (Exception e) {
                    System.out.println("insert Method");
                    System.out.println(e);
                } finally {
                    PS.close();
                    setTable();
                    dataClear();
                }
            } else {
                lbl.setText("Customer is exist");
            }
        }
        /*System.out.println(name);
        System.out.println(phone);
        System.out.println(address);
        System.out.println(company);
        System.out.println(email);
        System.out.println(gender);*/
    }

    //The Phone variable for update the customer
    String U;

    //Show data
    public void show() throws SQLException {

        try {
            Customers user = (Customers) customerTable.getSelectionModel().getSelectedItem();
            String sql = "SELECT * FROM `customers` WHERE 1";
            PS = (PreparedStatement) conn.prepareStatement(sql);

            txtName.setText(user.getName());
            txtPhone.setText(user.getPhone());
            txtAddress.setText(user.getAddress());
            txtCompany.setText(user.getCompany());
            txtEmail.setText(user.getEmail());
            boxGender.setValue(user.getGender());

            U = txtPhone.getText();

        } catch (Exception e) {
            System.out.println("Show method");
            System.out.println(e);
        } finally {
            PS.close();
            RS.close();
            //setTable();
            //dataClear();
        }
    }

    //Delete Customer
    public void deleteCustomer() throws SQLException {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Do you want to delete " + txtName.getText() + " ?!",
                ButtonType.YES, ButtonType.NO);
        alert.showAndWait();
        if (alert.getResult() == ButtonType.YES) {
            try {
                Customers cust = (Customers) customerTable.getSelectionModel().getSelectedItem();
                String sql = "DELETE FROM `customers` WHERE Name=?";
                PS = (PreparedStatement) conn.prepareStatement(sql);
                PS.setString(1, cust.getName());
                PS.execute();
                lbl.setText("Deleted");
            } catch (SQLException e) {
                System.out.println(e);
            } finally {
                PS.close();
                RS.close();
                setTable();
                dataClear();
            }

        }
    }

    //Update Customer
    public void UpdateUser() throws SQLException {

        //Varible U is the selected user
        String sql = "update customers set Name=?, Gender=?,Phone=?,Address=?,Email=?,Company=? where Phone='" + U + "'";

        try {
            PS = (PreparedStatement) conn.prepareStatement(sql);
            PS.setString(1, txtName.getText());
            PS.setString(2, boxGender.getValue());
            PS.setString(3, txtPhone.getText());
            PS.setString(4, txtAddress.getText());
            PS.setString(5, txtEmail.getText());
            PS.setString(6, txtCompany.getText());
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

    //Search function
    public void searchCustomer() {
        txtSearch.textProperty().addListener((observableValue, oldValue, newValue) -> {
            filteredData.setPredicate((Predicate<? super Customers>) user -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                if (user.getName().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (user.getName().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }
                return false;
            });
        });
        SortedList<Customers> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(customerTable.comparatorProperty());
        customerTable.setItems(sortedData);
    }
}
