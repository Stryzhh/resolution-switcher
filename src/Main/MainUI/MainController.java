package Main.MainUI;

import Main.C.C;
import Main.Functions;
import Main.Properties;
import com.jfoenix.controls.JFXButton;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import net.jimmc.jshortcut.JShellLink;

public class MainController implements Initializable {

    @FXML
    private AnchorPane window;
    @FXML
    private ImageView logo;
    @FXML
    private ImageView settings;
    @FXML
    private ImageView help;
    @FXML
    private ImageView minimize;
    @FXML
    private ImageView close;

    @FXML
    private JFXButton resolution0;
    @FXML
    private JFXButton resolution1;
    @FXML
    private JFXButton resolution2;
    @FXML
    private JFXButton resolution3;
    @FXML
    private JFXButton resolution4;
    @FXML
    private JFXButton resolution5;
    @FXML
    private ImageView resImage0;
    @FXML
    private ImageView resImage1;
    @FXML
    private ImageView resImage2;
    @FXML
    private ImageView resImage3;
    @FXML
    private ImageView resImage4;
    @FXML
    private ImageView resImage5;

    private final Properties[] resolutionProperties = new Properties[6];

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setVariables();
        Functions.disableButtons();
        Functions.loadResolutions();

        //click actions
        mouseClick(resolution0);
        mouseClick(resolution1);
        mouseClick(resolution2);
        mouseClick(resolution3);
        mouseClick(resolution4);
        mouseClick(resolution5);

        logo.setImage(new Image(new File("images/logo.png").toURI().toString()));
        settings.setImage(new Image(new File("images/settings.png").toURI().toString()));
        help.setImage(new Image(new File("images/help.png").toURI().toString()));
        minimize.setImage(new Image(new File("images/minimize.png").toURI().toString()));
        close.setImage(new Image(new File("images/close.png").toURI().toString()));
    }

    private void setVariables() {
        Functions.resolutionProperties = resolutionProperties;
        Functions.resolution0 = resolution0;
        Functions.resolution1 = resolution1;
        Functions.resolution2 = resolution2;
        Functions.resolution3 = resolution3;
        Functions.resolution4 = resolution4;
        Functions.resolution5 = resolution5;
        Functions.resImage0 = resImage0;
        Functions.resImage1 = resImage1;
        Functions.resImage2 = resImage2;
        Functions.resImage3 = resImage3;
        Functions.resImage4 = resImage4;
        Functions.resImage5 = resImage5;
    }

    private void mouseClick(JFXButton button) {
        button.setOnMouseClicked(mouseEvent -> {
            if (mouseEvent.getButton() == MouseButton.PRIMARY) {
                changeResolution(button);
            } else if (mouseEvent.getButton() == MouseButton.SECONDARY) {
                final ContextMenu contextMenu = new ContextMenu();
                final AnchorPane pane = new AnchorPane();

                MenuItem change = new MenuItem("Change Resolution");
                MenuItem desktopShortcut = new MenuItem("Create Desktop Shortcut");
                MenuItem startupShortcut = new MenuItem("Create Startup Shortcut");
                MenuItem delete = new MenuItem("Delete Resolution");

                contextMenu.getItems().addAll(change, desktopShortcut, startupShortcut, delete);
                button.setContextMenu(contextMenu);
                contextMenu.show(pane, mouseEvent.getScreenX(), mouseEvent.getScreenY());

                change.setOnAction(actionEvent -> changeResolution(button));
                desktopShortcut.setOnAction(actionEvent -> createDesktopShortcut(button));
                delete.setOnAction(actionEvent -> deleteResolution(button));
                startupShortcut.setOnAction(actionEvent -> createStartupShortcut(button));
            }
        });
    }

    private Properties getResolution(JFXButton button) {
        Properties properties = null;

        if (resolution0.equals(button)) {
            properties = resolutionProperties[0];
        } else if (resolution1.equals(button)) {
            properties = resolutionProperties[1];
        } else if (resolution2.equals(button)) {
            properties = resolutionProperties[2];
        } else if (resolution3.equals(button)) {
            properties = resolutionProperties[3];
        } else if (resolution4.equals(button)) {
            properties = resolutionProperties[4];
        } else if (resolution5.equals(button)) {
            properties = resolutionProperties[5];
        }

        return properties;
    }

    private void changeResolution(JFXButton button) {
        Properties properties = getResolution(button);

        assert properties != null;
        int width = properties.getWidth();
        int height = properties.getHeight();

        if (C.change(1024, 768) == 1) {
            System.out.println("Successful");
        } else {
            System.out.println("Not Successful");
        }
    }

    private void createDesktopShortcut(JFXButton button) {
        Properties properties = getResolution(button);

        JShellLink link = new JShellLink();
        String filePath = JShellLink.getDirectory("") + properties.getBatchFile();
        link.setFolder(JShellLink.getDirectory("desktop"));
        link.setName(properties.getFileName());
        link.setPath(filePath);
        link.save();
    }

    private void createStartupShortcut(JFXButton button) {
        Properties properties = getResolution(button);
        File file = new File(System.getProperty("user.home") + "/AppData/Roaming/Microsoft/Windows/Start Menu/Programs/");

        JShellLink link = new JShellLink();
        String filePath = JShellLink.getDirectory("") + properties.getBatchFile();
        link.setFolder(file.getAbsolutePath());
        link.setName(properties.getFileName());
        link.setPath(filePath);
        link.save();
    }

    private void deleteResolution(JFXButton button) {
        Properties properties = getResolution(button);

        File f = new File("resolutions/");
        File[] matchingFiles = f.listFiles((dir, name) -> name.startsWith(properties.getFileName()));
        assert matchingFiles != null;
        ArrayList<File> files = new ArrayList<>(Arrays.asList(matchingFiles));
        files.removeIf(File::delete);
        deleteDesktopShortcut(properties.getFileName());
        deleteStartMenuShortcut(properties.getFileName());

        if (files.size() == 0) {
            Platform.runLater(Functions::loadResolutions);
        }
    }

    private void deleteDesktopShortcut(String fileName) {
        File f = new File(JShellLink.getDirectory("desktop"));
        File[] matchingFiles = f.listFiles((dir, name) -> name.startsWith(fileName));
        assert matchingFiles != null;
        ArrayList<File> files = new ArrayList<>(Arrays.asList(matchingFiles));
        files.removeIf(File::delete);
    }

    private void deleteStartMenuShortcut(String fileName) {
        File f = new File(System.getProperty("user.home") + "/AppData/Roaming/Microsoft/Windows/Start Menu/Programs/");
        File[] matchingFiles = f.listFiles((dir, name) -> name.startsWith(fileName));
        assert matchingFiles != null;
        ArrayList<File> files = new ArrayList<>(Arrays.asList(matchingFiles));
        files.removeIf(File::delete);
    }

    public void openResolution() throws IOException {
        Functions.openWindow("Main/Resolution/resolution.fxml", "Resolutions");
    }

    public void openHelp() throws IOException {
        Functions.openWindow("Main/Help/help.fxml", "Help");
    }

    public void openSettings() throws IOException {
        Functions.openWindow("Main/SettingsUI/settings.fxml", "Settings");
    }

    public void drag() {
        Functions.dragWindow(window);
    }

    public void minimize() {
        Functions.minimize(window);
    }

    public void exit() {
        System.exit(0);
    }

}
