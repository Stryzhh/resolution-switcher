package Main.Resolution;

import Main.Functions;
import Main.Properties;
import Main.Settings;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import net.jimmc.jshortcut.JShellLink;

public class ResolutionController implements Initializable {

    @FXML
    private AnchorPane window;
    @FXML
    private ListView<String> list;
    @FXML
    private ComboBox<String> width;
    @FXML
    private ComboBox<String> height;
    @FXML
    private ImageView minimize;
    @FXML
    private ImageView close;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadList();
        loadWidth();
        loadHeight();

        minimize.setImage(new Image(new File("images/grey-minimize.png").toURI().toString()));
        close.setImage(new Image(new File("images/grey-close.png").toURI().toString()));
    }

    public void addResolution() throws IOException {
        int widthValue = 0;
        int heightValue = 0;
        String imageLocation;

        boolean getImage = false;
        if (list.getSelectionModel().getSelectedItem() != null) {
            String item = list.getSelectionModel().getSelectedItem();
            String[] parts = item.split("x");
            widthValue = Integer.parseInt(parts[0]);
            heightValue = Integer.parseInt(parts[1]);
            getImage = true;
        } else if (width.getSelectionModel().getSelectedItem() != null
                && height.getSelectionModel().getSelectedItem() != null) {
            widthValue =  Integer.parseInt(width.getSelectionModel().getSelectedItem());
            heightValue =  Integer.parseInt(height.getSelectionModel().getSelectedItem());
            getImage = true;
        }

        if (getImage) {
            String batchLocation = Functions.createBatch(widthValue, heightValue);
            imageLocation = Functions.CreateLogo(widthValue, heightValue);
            Properties properties = new Properties(imageLocation, widthValue, heightValue, batchLocation);

            FileWriter writer = new FileWriter("resolutions/" + widthValue + "x" + heightValue + ".json");
            Gson g = new GsonBuilder().setPrettyPrinting().create();
            writer.write(g.toJson(properties));
            writer.close();

            if (Settings.currentDesktop) {
                JShellLink link = new JShellLink();
                String filePath = JShellLink.getDirectory("") + properties.getBatchFile();
                link.setFolder(JShellLink.getDirectory("desktop"));
                link.setName(properties.getFileName());
                link.setPath(filePath);
                link.save();
            }
            if (Settings.currentStartMenu) {
                File file = new File(System.getProperty("user.home") + "/AppData/Roaming/Microsoft/Windows/Start Menu/Programs/");

                JShellLink link = new JShellLink();
                String filePath = JShellLink.getDirectory("") + properties.getBatchFile();
                link.setFolder(file.getAbsolutePath());
                link.setName(properties.getFileName());
                link.setPath(filePath);
                link.save();
            }
        }
    }

    private void loadList() {
        //list items
        list.getItems().add("7680x4320");
        list.getItems().add("3840x2160");
        list.getItems().add("2560x1440");
        list.getItems().add("2048x1080");
        list.getItems().add("1920x1080");
        list.getItems().add("1760x1080");
        list.getItems().add("1600x1080");
        list.getItems().add("1440x1080");
        list.getItems().add("1280x1080");
        list.getItems().add("1152x1080");
        list.getItems().add("1080x1080");
        list.getItems().add("1280x720");
        list.getItems().add("1024x720");
        list.getItems().add("960x720");
        list.getItems().add("640x480");
    }

    private void loadHeight() {
        //height items
        height.getItems().add("4320");
        height.getItems().add("2160");
        height.getItems().add("1440");
        height.getItems().add("1080");
        height.getItems().add("1024");
        height.getItems().add("960");
        height.getItems().add("900");
        height.getItems().add("800");
        height.getItems().add("768");
        height.getItems().add("720");
        height.getItems().add("664");
        height.getItems().add("600");
        height.getItems().add("576");
        height.getItems().add("480");
    }

    private void loadWidth() {
        //width items
        width.getItems().add("7680");
        width.getItems().add("3840");
        width.getItems().add("2560");
        width.getItems().add("2048");
        width.getItems().add("1920");
        width.getItems().add("1760");
        width.getItems().add("1600");
        width.getItems().add("1440");
        width.getItems().add("1280");
        width.getItems().add("1152");
        width.getItems().add("1024");
        width.getItems().add("960");
        width.getItems().add("800");
        width.getItems().add("720");
    }

    public void drag() {
        Functions.dragWindow(window);
    }

    public void minimize() {
        Functions.minimize(window);
    }

    public void close() {
        Functions.loadResolutions();
        Functions.close(window);
    }

}
