package Main.SettingsUI;

import Main.Functions;
import Main.Settings;
import Main.Window;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jfoenix.controls.JFXCheckBox;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import net.jimmc.jshortcut.JShellLink;

public class SettingsController implements Initializable {

    @FXML private AnchorPane window;
    @FXML private Label note;
    @FXML private ImageView logo;
    @FXML private ImageView minimize;
    @FXML private ImageView close;
    @FXML private JFXCheckBox startup;
    @FXML private JFXCheckBox desktopShortcut;
    @FXML private JFXCheckBox startShortcut;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Gson gson = new Gson();
        File file = new File("settings.json");

        try {
            Reader reader = new FileReader(file);
            Settings settings = gson.fromJson(reader, Settings.class);

            startup.setSelected(settings.isStartup());
            desktopShortcut.setSelected(settings.isDesktop());
            startShortcut.setSelected(settings.isStartMenu());
            reader.close();
            setCurrent();
        } catch (IOException e) {
            e.printStackTrace();
        }

        startup.selectedProperty().addListener((ObservableValue<? extends Boolean> ov, Boolean old_val, Boolean new_val) -> amendSettings());
        desktopShortcut.selectedProperty().addListener((ObservableValue<? extends Boolean> ov, Boolean old_val, Boolean new_val) -> amendSettings());
        startShortcut.selectedProperty().addListener((ObservableValue<? extends Boolean> ov, Boolean old_val, Boolean new_val) -> amendSettings());
        startup.selectedProperty().addListener((ObservableValue<? extends Boolean> ov, Boolean old_val, Boolean new_val) -> {
            try {
                setStartup(startup.isSelected());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        logo.setImage(new Image(new File("images/logo.png").toURI().toString()));
        minimize.setImage(new Image(new File("images/minimize.png").toURI().toString()));
        close.setImage(new Image(new File("images/close.png").toURI().toString()));
    }

    private void setStartup(boolean selected) throws IOException {
        File folder = new File(System.getProperty("user.home") + "/AppData/Roaming/Microsoft/Windows/Start Menu/Programs/Startup");
        File batch = new File("resolution-switcher.bat");

        if (selected) {
            FileOutputStream output = new FileOutputStream(batch);
            DataOutputStream input = new DataOutputStream(output);
            input.writeBytes("javaw -Xmx200m -jar resolution-switcher.jar");
            input.close();
            output.close();

            JShellLink link = new JShellLink();
            String filePath = JShellLink.getDirectory("") + batch.getAbsolutePath();
            link.setFolder(folder.getAbsolutePath());
            link.setName(batch.getName());
            link.setPath(filePath);
            link.save();
        } else {
            File startupFile = new File(System.getProperty("user.home") +
                    "/AppData/Roaming/Microsoft/Windows/Start Menu/Programs/Startup/resolution-switcher.bat.lnk");
            if (!startupFile.delete() || !batch.delete()) {
                note.setText("Couldn't delete files");
            }
        }
    }

    private void setCurrent() {
        Settings.currentStartup = startup.isSelected();
        Settings.currentDesktop = desktopShortcut.isSelected();
        Settings.currentStartMenu = startShortcut.isSelected();
    }

    private void amendSettings() {
        try {
            FileWriter writer = new FileWriter("settings.json");
            Gson g = new GsonBuilder().setPrettyPrinting().create();
            writer.write(g.toJson(new Settings(startup.isSelected(), desktopShortcut.isSelected(), startShortcut.isSelected())));
            writer.close();
            setCurrent();
        } catch (IOException e) {
            e.printStackTrace();
        }
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
        Window.setSettings(false);
    }

}
