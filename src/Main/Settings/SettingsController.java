package Main.Settings;

import Main.Functions;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class SettingsController implements Initializable {

    @FXML private AnchorPane window;
    @FXML private ImageView logo;
    @FXML private ImageView minimize;
    @FXML private ImageView close;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        logo.setImage(new Image(new File("images/logo.png").toURI().toString()));
        minimize.setImage(new Image(new File("images/minimize.png").toURI().toString()));
        close.setImage(new Image(new File("images/close.png").toURI().toString()));
    }

    public void drag() {
        Functions.dragWindow(window);
    }

    public void minimize() {
        Functions.minimize(window);
    }

    public void close() {
        Stage thisWindow = (Stage) window.getScene().getWindow();
        thisWindow.close();
    }

}
