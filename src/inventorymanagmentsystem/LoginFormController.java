package inventorymanagmentsystem;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.mysql.jdbc.PreparedStatement;
import database.DBConnection;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import javax.swing.JOptionPane;
import tray.animations.AnimationType;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;

/**
 *
 * @author AmeenAhmed
 */
public class LoginFormController implements Initializable {
    
    @FXML
    private JFXTextField txtUsername;

    @FXML
    private JFXPasswordField txtPassword;

    @FXML
    private Label ValidationLabel;

    //Connection Variable
    Connection conn = null;
    String user, pass;

    //Login Button configuration
    @FXML
    void LoginAction(ActionEvent event) throws SQLException, IOException, InterruptedException {
        
        user = txtUsername.getText();
        pass = txtPassword.getText();

        if (txtUsername.getText().trim().isEmpty() || txtPassword.getText().trim().isEmpty()) {
            ValidationLabel.setText("Fill Username And Password");
        } else {
            String SQL = "SELECT * FROM `userlogin` WHERE Username = ? and Password = ?";
            PreparedStatement PS = null;
            ResultSet RS = null;
            try {
                PS = (PreparedStatement) conn.prepareStatement(SQL);
                PS.setString(1, user);
                PS.setString(2, pass);
                RS = PS.executeQuery();
                if (!RS.next()) {
                    //ValidationImage.setImage(new Image("icons/Wrong.png"));
                    ValidationLabel.setText("Invalid Username or Password !!");
                    //JOptionPane.showMessageDialog(null, "Invalid username or password");
                } else {
                    //Notification Slider
                    TrayNotification tray = new TrayNotification();
                    tray.setNotificationType(NotificationType.SUCCESS);
                    tray.setTitle("Login Success");
                    tray.setMessage("Welcome, " + txtUsername.getText());
                    tray.setAnimationType(AnimationType.SLIDE);
                    tray.showAndDismiss(Duration.millis(1500));
                    
                    //ValidationLabel.setText("Correct");
                    //MainWindowController M = new MainWindowController(user);
                    //ValidationImage.setImage(new Image("icons/Correct.png"));
                    //System.out.println(user);
                    
                    ((Node) event.getSource()).getScene().getWindow().hide();
                    FXMLLoader loadMain = new FXMLLoader(getClass().getResource("MainWindow.fxml"));
                    Parent rootMain = (Parent) loadMain.load();
                    Stage stage = new Stage();
                    stage.setScene(new Scene(rootMain));
                    stage.setX(300);
                    stage.setY(5);
                    stage.initStyle(StageStyle.UNDECORATED);
                    stage.show();
                    stage.getIcons().add(new Image(this.getClass().getResourceAsStream("/icons/icons8_Forumbee_96px.png")));

                    //JOptionPane.showMessageDialog(null, "Ok");
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, e);
            }
        }

    }

    //Exit Button 
    @FXML
    void ExitAction(ActionEvent event) throws SQLException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Do you want to Exit !!",
                ButtonType.YES, ButtonType.NO);
        alert.showAndWait();
        if (alert.getResult() == ButtonType.YES) {
            conn.close();
            System.exit(0);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        try {
            conn = DBConnection.DBConnect();
        } catch (SQLException ex) {
            System.out.println("Login form : " + ex);
            JOptionPane.showMessageDialog(null, ex);
        }
    }

}
