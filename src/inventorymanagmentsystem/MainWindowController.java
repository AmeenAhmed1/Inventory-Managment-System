package inventorymanagmentsystem;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author AmeenAhmed
 */
public class MainWindowController {

    @FXML
    Label lbl;

    /*
    *Style
     */
    private double xOffset = 0;
    private double yOffset = 0;

    private void Style(Stage stage, Parent root) throws IOException {

        stage.initStyle(StageStyle.UNDECORATED);

        stage.setX(110);
        stage.setY(100);
        //To Drage the window any where
        root.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                xOffset = event.getSceneX();
                yOffset = event.getSceneY();
            }
        });
        root.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                stage.setX(event.getScreenX() - xOffset);
                stage.setY(event.getScreenY() - yOffset);
            }
        });

        stage.getIcons().add(new Image(this.getClass().getResourceAsStream("/icons/icons8_Forumbee_96px.png")));
        root.requestFocus();
        stage.show();
    }

    /*
    *End Style
     */
    public void btnLogout(ActionEvent event) throws IOException {
        //((Node) event.getSource()).getScene().getWindow().hide();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Do you want to Exit !!",
                ButtonType.YES, ButtonType.NO);
        alert.showAndWait();
        if (alert.getResult() == ButtonType.YES) {
            System.exit(0);
        }
        /*FXMLLoader loadMain = new FXMLLoader(getClass().getResource("LoginForm.fxml"));
        Parent rootMain = (Parent) loadMain.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(rootMain));
        Style(stage, rootMain);*/
    }

    // customer function
    Stage customerStage = new Stage();
    boolean customerCount = true;

    public void btnCustomer(ActionEvent e) throws IOException {
        //System.out.println(customerCount);
        //Stage stage = new Stage();
        if (!importsCount) {
            importsCount = true;
            importsStage.hide();
            importsStage = new Stage();
        } else if (!exportsCount) {
            exportsCount = true;
            exportsStage.hide();
            exportsStage = new Stage();
        } else if (!reportCount) {
            reportCount = true;
            reportStage.hide();
            reportStage = new Stage();
        }

        if (customerCount) {
            customerCount = false;
            try {
                FXMLLoader loadCustomer = new FXMLLoader(getClass().getResource("/Customer/customerData.fxml"));
                Parent rootCustomer = (Parent) loadCustomer.load();
                customerStage.setScene(new Scene(rootCustomer));
                rootCustomer.requestFocus();

                Style(customerStage, rootCustomer);
            } catch (IOException ex) {
                System.out.println(ex);
            }
        } else {
            customerStage.hide();
            customerStage = new Stage();
            customerCount = true;
        }
    }

    // Imports function
    Stage importsStage = new Stage();
    boolean importsCount = true;

    public void btnImports(ActionEvent e) throws IOException {
        //System.out.println(customerCount);
        //Stage stage = new Stage();
        if (!customerCount) {
            customerCount = true;
            customerStage.hide();
            customerStage = new Stage();
        } else if (!exportsCount) {
            exportsCount = true;
            exportsStage.hide();
            exportsStage = new Stage();
        } else if (!reportCount) {
            reportCount = true;
            reportStage.hide();
            reportStage = new Stage();
        }

        if (importsCount) {
            importsCount = false;
            try {
                FXMLLoader loadImports = new FXMLLoader(getClass().getResource("/Imports/importsData.fxml"));
                Parent rootImports = (Parent) loadImports.load();
                importsStage.setScene(new Scene(rootImports));
                rootImports.requestFocus();

                Style(importsStage, rootImports);

            } catch (IOException ex) {
                System.out.println(ex);
            }
        } else {
            importsStage.hide();
            importsStage = new Stage();
            importsCount = true;
        }
    }

    //Exports Function
    Stage exportsStage = new Stage();
    boolean exportsCount = true;

    public void btnExports(ActionEvent e) throws IOException {
        if (!customerCount) {
            customerCount = true;
            customerStage.hide();
            customerStage = new Stage();
        } else if (!importsCount) {
            importsCount = true;
            importsStage.hide();
            importsStage = new Stage();
        } else if (!reportCount) {
            reportCount = true;
            reportStage.hide();
            reportStage = new Stage();
        }

        if (exportsCount) {
            exportsCount = false;
            try {
                FXMLLoader loadExports = new FXMLLoader(getClass().getResource("/Exports/exportsData.fxml"));
                Parent rootExports = (Parent) loadExports.load();
                exportsStage.setScene(new Scene(rootExports));
                rootExports.requestFocus();

                Style(exportsStage, rootExports);
            } catch (IOException ex) {
                System.out.println(ex);
            }

        } else {
            exportsStage.hide();
            exportsStage = new Stage();
            exportsCount = true;
        }
    }

    //Report Function
    Stage reportStage = new Stage();
    boolean reportCount = true;

    public void btnReport() {

        if (!customerCount) {
            customerCount = true;
            customerStage.hide();
            customerStage = new Stage();
        } else if (!importsCount) {
            importsCount = true;
            importsStage.hide();
            importsStage = new Stage();
        } else if (!exportsCount) {
            exportsCount = true;
            exportsStage.hide();
            exportsStage = new Stage();
        }

        if (reportCount) {
            reportCount = false;
            try {
                FXMLLoader loadReport = new FXMLLoader(getClass().getResource("/Report/reportData.fxml"));
                Parent rootReport = (Parent) loadReport.load();
                reportStage.setScene(new Scene(rootReport));
                rootReport.requestFocus();

                Style(reportStage, rootReport);
            } catch (IOException ex) {
                System.out.println(ex);
            }

        } else {
            reportStage.hide();
            reportStage = new Stage();
            reportCount = true;
        }
    }
}
