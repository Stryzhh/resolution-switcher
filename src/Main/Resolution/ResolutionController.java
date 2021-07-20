package Main.Resolution;

import Main.Functions;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

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
        String widthValue = "";
        String heightValue = "";
        String imageLocation;

        boolean getImage = false;
        if (list.getSelectionModel().getSelectedItem() != null) {
            String item = list.getSelectionModel().getSelectedItem();
            String[] parts = item.split("x");
            widthValue = parts[0];
            heightValue = parts[1];
            getImage = true;
        } else if (width.getSelectionModel().getSelectedItem() != null
                && height.getSelectionModel().getSelectedItem() != null) {
            widthValue = width.getSelectionModel().getSelectedItem();
            heightValue = height.getSelectionModel().getSelectedItem();
            getImage = true;
        }

        if (getImage) {
            imageLocation = Functions.CreateLogo(widthValue, heightValue);
            Properties properties = new Properties(imageLocation, widthValue, heightValue);

            FileWriter writer = new FileWriter("resolutions/" + widthValue + "x" + heightValue + ".json");
            Gson g = new GsonBuilder().setPrettyPrinting().create();
            writer.write(g.toJson(properties));
            writer.close();
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

    public void close() throws IOException {
        Functions.close(window);
    }


}
