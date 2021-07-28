package Main.Help;

import Main.Functions;
import Main.Window;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class HelpController implements Initializable {

    @FXML private AnchorPane window;
    @FXML private ImageView logo;
    @FXML private ImageView github;
    @FXML private ImageView issues;
    @FXML private ImageView minimize;
    @FXML private ImageView close;

    private final Desktop desktop = Desktop.getDesktop();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        logo.setImage(new Image(new File("images/logo.png").toURI().toString()));
        github.setImage(new Image(new File("images/github.png").toURI().toString()));
        issues.setImage(new Image(new File("images/issues.png").toURI().toString()));
        minimize.setImage(new Image(new File("images/minimize.png").toURI().toString()));
        close.setImage(new Image(new File("images/close.png").toURI().toString()));
    }

    public void openGithub() throws URISyntaxException, IOException {
        desktop.browse(new URI("https://github.com/Stryzhh/resolution-switcher"));
    }

    public void openIssues() throws URISyntaxException, IOException {
        desktop.browse(new URI("https://github.com/Stryzhh/resolution-switcher/issues"));
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
        Window.setHelp(false);
    }

}
