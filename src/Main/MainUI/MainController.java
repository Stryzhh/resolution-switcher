package Main.MainUI;

import Main.Functions;
import Main.Resolution.Properties;
import com.google.gson.Gson;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

public class MainController implements Initializable {

    @FXML private AnchorPane window;
    @FXML private ImageView logo;
    @FXML private ImageView settings;
    @FXML private ImageView help;
    @FXML private ImageView minimize;
    @FXML private ImageView close;
    @FXML private BorderPane grid;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Gson gson = new Gson();

        File[] filesArray = new File("resolutions").listFiles();
        assert filesArray != null;
        ArrayList<File> files = new ArrayList<>(Arrays.asList(filesArray));

        files.removeIf(file -> !file.toString().contains(".json"));

        for (File file : files) {
            try {
                Reader reader = new FileReader(file);
                Properties properties = gson.fromJson(reader, Properties.class);
                ImageView imageView = new ImageView();
                imageView.setImage(new Image(new File(properties.getImage()).toURI().toString()));
                grid.getChildren().add(imageView);
            } catch (IOException e) {
                //e.printStackTrace();
            }
        }

        logo.setImage(new Image(new File("images/logo.png").toURI().toString()));
        settings.setImage(new Image(new File("images/settings.png").toURI().toString()));
        help.setImage(new Image(new File("images/help.png").toURI().toString()));
        minimize.setImage(new Image(new File("images/minimize.png").toURI().toString()));
        close.setImage(new Image(new File("images/close.png").toURI().toString()));
    }

    public void openResolution() throws IOException {
        Functions.openWindow("Main/Resolution/resolution.fxml", "Confirm Dialog");
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
