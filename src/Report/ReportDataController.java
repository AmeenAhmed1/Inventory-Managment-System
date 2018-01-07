package Report;

import com.mysql.jdbc.PreparedStatement;
import database.DBConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author AmeenAhmed
 */
public class ReportDataController implements Initializable {

    @FXML
    private Label lblCustomer, lblImports, lblExports;

    @FXML
    private TableView<Report> reportTable;

    @FXML
    private TableColumn<Report, String> BarCode;

    @FXML
    private TableColumn<Report, Integer> Quantity;

    @FXML
    private ProgressIndicator progressMale, progressFemale;

    @FXML
    private PieChart Chart;

    private ObservableList<Report> data = FXCollections.observableArrayList();

    Connection conn;
    PreparedStatement PS;
    ResultSet RS;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            // TODO
            conn = DBConnection.DBConnect();
            setTable();
            importsCount();
            exportsCount();
            customersCount();

            //System.out.println(";;" + (importsCount / (importsCount+exportsCount))*100);
            double x = ( (double)importsCount / (double)(importsCount + exportsCount)) * 100;
            DecimalFormat Di = new DecimalFormat("##.#");
            String i = Di.format(x);

            double y = ( (double)exportsCount / (double)(importsCount + exportsCount)) * 100;
            DecimalFormat De = new DecimalFormat("##.#");
            String e = De.format(y);

            String im = "Imports " + i + "%";
            String ex = "Exports " + e + "%";

            ObservableList<PieChart.Data> pieChart = FXCollections.observableArrayList(
                    new PieChart.Data(im, x),
                    new PieChart.Data(ex, y)
            );

            Chart.setData(pieChart);

        } catch (SQLException ex) {
            System.out.println("make a connection : " + ex);
        }
    }

    public void setTable() throws SQLException {

        String sql = "SELECT * FROM `report`";
        try {

            PS = (PreparedStatement) conn.prepareStatement(sql);
            RS = PS.executeQuery();

            data.clear();

            while (RS.next()) {

                data.add(new Report(RS.getInt(1),
                        RS.getString(2),
                        RS.getInt(3)
                ));
            }

            BarCode.setCellValueFactory(new PropertyValueFactory<Report, String>("BarCode"));
            Quantity.setCellValueFactory(new PropertyValueFactory<Report, Integer>("Quantity"));

            reportTable.setItems(data);

        } catch (SQLException ex) {
            System.out.println("Setting table : " + ex);
        } finally {
            PS.close();
            RS.close();
        }

    }

    //Get Customer count
    int customerCount = 0;
    double Male = 0, Female = 0;

    public void customersCount() throws SQLException {

        String sql = "select gender from customers";
        try {

            PS = (PreparedStatement) conn.prepareStatement(sql);
            RS = PS.executeQuery();

            while (RS.next()) {
                customerCount++;
                if (RS.getString("Gender").equals("Male")) {
                    Male++;
                } else {
                    Female++;
                }
            }

            lblCustomer.setText(String.valueOf(customerCount));

            //progressMale.setProgress(0.5);
            progressMale.setProgress((Male / customerCount));
            progressFemale.setProgress((Female / customerCount));

        } catch (SQLException ex) {
            System.out.println("Customer Method : " + ex);
        } finally {
            PS.close();
            RS.close();
        }
    }

    //Get Imports Count
    int importsCount = 0;

    public void importsCount() throws SQLException {

        String sql = "select id from imports";
        try {

            PS = (PreparedStatement) conn.prepareStatement(sql);
            RS = PS.executeQuery();

            while (RS.next()) {
                importsCount++;
            }

            lblImports.setText(String.valueOf(importsCount));

        } catch (SQLException ex) {
            System.out.println("importsCount : " + ex);
        } finally {
            PS.close();
            RS.close();
        }
    }

    //Get Exports Count
    int exportsCount = 0;

    public void exportsCount() throws SQLException {

        String sql = "select id from exports";

        try {

            PS = (PreparedStatement) conn.prepareStatement(sql);
            RS = PS.executeQuery();

            while (RS.next()) {
                exportsCount++;
            }

            lblExports.setText(String.valueOf(exportsCount));

        } catch (SQLException ex) {
            System.out.println("Exports count : " + ex);
        } finally {
            PS.close();
            RS.close();
        }
    }
}
