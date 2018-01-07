package inventorymanagmentsystem;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author AmeenAhmed
 */
public class InventoryManagmentSystem extends Application {

   

    private double xOffset = 0;
    private double yOffset = 0;

    @Override
    public void start(Stage stage) throws Exception {

        Parent root = FXMLLoader.load(getClass().getResource("LoginForm.fxml"));

        //to make the window withOut borders
        stage.initStyle(StageStyle.UNDECORATED);

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

        Scene scene = new Scene(root);

        //Program icon on taskbar
        stage.getIcons().add(new Image(this.getClass().getResourceAsStream("/icons/icons8_Forumbee_96px.png")));

        stage.setScene(scene);
        stage.show();
        root.requestFocus();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
