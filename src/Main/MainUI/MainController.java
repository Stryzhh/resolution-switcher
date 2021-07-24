package Main.MainUI;

import Main.C.C;
import Main.Functions;
import Main.Properties;
import com.google.gson.Gson;
import com.jfoenix.controls.JFXButton;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
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
        disableButtons();
        loadResolutions();

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
        File[] matchingFiles = f.listFiles((dir, name) -> name.startsWith(properties.getFileName()) && name.endsWith(".json"));
        assert matchingFiles != null;
        ArrayList<File> files = new ArrayList<>(Arrays.asList(matchingFiles));
        files.removeIf(File::delete);

        if (files.size() == 0) {
            Platform.runLater(this::loadResolutions);
        }
    }

    private void loadResolutions() {
        disableButtons();
        Gson gson = new Gson();

        File[] filesArray = new File("resolutions").listFiles();
        assert filesArray != null;
        ArrayList<File> files = new ArrayList<>(Arrays.asList(filesArray));
        files.removeIf(file -> !file.toString().contains(".json"));

        if (files.size() > 6) {
            files.subList(6, files.size()).clear();
        }

        int count = 0;
        for (File file : files) {
            try {
                Reader reader = new FileReader(file);
                Properties properties = gson.fromJson(reader, Properties.class);
                resolutionProperties[count] = properties;
                Image image = new Image(new File(properties.getImage()).toURI().toString());

                switch (count) {
                    case 0:
                        loadResolution(resolution0, resImage0, properties, image);
                        break;
                    case 1:
                        loadResolution(resolution1, resImage1, properties, image);
                        break;
                    case 2:
                        loadResolution(resolution2, resImage2, properties, image);
                        break;
                    case 3:
                        loadResolution(resolution3, resImage3, properties, image);
                        break;
                    case 4:
                        loadResolution(resolution4, resImage4, properties, image);
                        break;
                    case 5:
                        loadResolution(resolution5, resImage5, properties, image);
                        break;
                }
                
                count++;
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void disableButtons() {
        resolution0.setVisible(false);
        resolution0.setDisable(true);
        resImage0.setImage(null);
        resolution1.setVisible(false);
        resolution1.setDisable(true);
        resImage1.setImage(null);
        resolution2.setVisible(false);
        resolution2.setDisable(true);
        resImage2.setImage(null);
        resolution3.setVisible(false);
        resolution3.setDisable(true);
        resImage3.setImage(null);
        resolution4.setVisible(false);
        resolution4.setDisable(true);
        resImage4.setImage(null);
        resolution5.setVisible(false);
        resolution5.setDisable(true);
        resImage5.setImage(null);
    }

    private void loadResolution(JFXButton button, ImageView image, Properties properties, Image newImage) {
        button.setVisible(true);
        button.setDisable(false);
        button.setText(properties.getTitle());
        image.setImage(newImage);
    }

    public void openResolution() throws IOException {
        Functions.openWindow("Main/Resolution/resolution.fxml", "Resolutions");
    }

    public void openHelp() throws IOException {
        Functions.openWindow("Main/Help/help.fxml", "Help");
    }

    public void openSettings() throws IOException {
        Functions.openWindow("Main/Settings/settings.fxml", "Settings");
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
