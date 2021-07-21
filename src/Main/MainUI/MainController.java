package Main.MainUI;

import Main.Functions;
import Main.Resolution.Properties;
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

    @FXML private JFXButton resolution0;
    @FXML private JFXButton resolution1;
    @FXML private JFXButton resolution2;
    @FXML private JFXButton resolution3;
    @FXML private JFXButton resolution4;
    @FXML private JFXButton resolution5;

    @FXML private ImageView resImage0;
    @FXML private ImageView resImage1;
    @FXML private ImageView resImage2;
    @FXML private ImageView resImage3;
    @FXML private ImageView resImage4;
    @FXML private ImageView resImage5;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Gson gson = new Gson();

        File[] filesArray = new File("resolutions").listFiles();
        assert filesArray != null;
        ArrayList<File> files = new ArrayList<>(Arrays.asList(filesArray));
        files.removeIf(file -> !file.toString().contains(".json"));

        if (files.size() > 5) {
            files.subList(5, files.size()).clear();
        }

        Properties[] resolutionProperties = new Properties[6];
        int count = 0;
        for (File file : files) {
            try {
                Reader reader = new FileReader(file);
                Properties properties = gson.fromJson(reader, Properties.class);
                resolutionProperties[count] = properties;
                Image image = new Image(new File(properties.getImage()).toURI().toString());

                switch(count) {
                    case 0:
                        resolution0.setVisible(true);
                        resolution0.setDisable(false);
                        resolution0.setText(properties.getTitle());
                        resImage0.setImage(image);
                        break;
                    case 1:
                        resolution1.setVisible(true);
                        resolution1.setDisable(false);
                        resolution1.setText(properties.getTitle());
                        resImage1.setImage(image);
                        break;
                    case 2:
                        resolution2.setVisible(true);
                        resolution2.setDisable(false);
                        resolution2.setText(properties.getTitle());
                        resImage2.setImage(image);
                        break;
                    case 3:
                        resolution3.setVisible(true);
                        resolution3.setDisable(false);
                        resolution3.setText(properties.getTitle());
                        resImage3.setImage(image);
                        break;
                    case 4:
                        resolution4.setVisible(true);
                        resolution4.setDisable(false);
                        resolution4.setText(properties.getTitle());
                        resImage4.setImage(image);
                        break;
                    case 5:
                        resolution5.setVisible(true);
                        resolution5.setDisable(false);
                        resolution5.setText(properties.getTitle());
                        resImage5.setImage(image);
                        break;
                }
                count++;

            } catch (IOException e) {
                e.printStackTrace();
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
