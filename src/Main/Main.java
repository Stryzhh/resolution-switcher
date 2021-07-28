package Main;

import com.google.gson.Gson;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javax.swing.*;

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
    public void start(Stage window) throws Exception {
        Parent startWindow = FXMLLoader.load(Main.class.getResource("MainUI/main.fxml"));

        //adds tray icon
        PopupMenu popupMenu = new PopupMenu();
        ImageIcon logo = new ImageIcon("src/logo.png");
        Image image = logo.getImage();

        SystemTray tray = SystemTray.getSystemTray();
        Image trayImage = image.getScaledInstance(tray.getTrayIconSize().width,
                tray.getTrayIconSize().height, java.awt.Image.SCALE_SMOOTH);
        TrayIcon trayIcon = new TrayIcon(trayImage, "resolution-switcher", popupMenu);

        MenuItem add = new MenuItem("Add Resolution");
        MenuItem settings = new MenuItem("Settings");
        MenuItem help = new MenuItem("Help");
        MenuItem exit = new MenuItem("Exit");

        add.addActionListener(e -> Platform.runLater(() -> {
                    if (Functions.resolutionProperties[5] == null) {
                        try {
                            Functions.openWindow("Main/Resolution/resolution.fxml", "Resolutions", Window.isAdd());
                            Window.setAdd(true);
                        } catch (IOException ioException) {
                            ioException.printStackTrace();
                        }
                    }
                }
        ));
        settings.addActionListener(e -> Platform.runLater(() -> {
                    try {
                        Functions.openWindow("Main/SettingsUI/settings.fxml", "Settings", Window.isSettings());
                        Window.setSettings(true);
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                }
        ));
        help.addActionListener(e -> Platform.runLater(() -> {
                    try {
                        Functions.openWindow("Main/Help/help.fxml", "Help", Window.isHelp());
                        Window.setHelp(true);
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                }
        ));
        exit.addActionListener(e -> {
            tray.remove(trayIcon);
            System.exit(0);
        });

        popupMenu.add(add);
        popupMenu.add(settings);
        popupMenu.add(help);
        popupMenu.add(exit);
        tray.add(trayIcon);

        window.initStyle(StageStyle.UNDECORATED);
        window.setScene(new Scene(startWindow, 655, 350));
        window.getIcons().add(new javafx.scene.image.Image("logo.png"));
        window.show();
    }

}
