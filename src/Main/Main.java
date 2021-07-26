package Main;

import com.google.gson.Gson;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {

    public static void main(String[] args) {
        Gson gson = new Gson();
        File file = new File("settings.json");

        try {
            Reader reader = new FileReader(file);
            Settings settings = gson.fromJson(reader, Settings.class);

            Settings.currentStartup = settings.isStartup();
            Settings.currentDesktop = settings.isDesktop();
            Settings.currentStartMenu = settings.isStartMenu();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        launch(args);
    }

    @Override
    public void start(Stage window) throws Exception{
        Parent startWindow = FXMLLoader.load(Main.class.getResource("MainUI/main.fxml"));

        window.initStyle(StageStyle.UNDECORATED);
        window.setScene(new Scene(startWindow, 655, 350));
        window.show();
    }

}
