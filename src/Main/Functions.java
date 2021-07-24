package Main;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javax.imageio.ImageIO;

public class Functions {

    public static String createBatch(int width, int height) throws IOException {
        String name = "resolutions/" + width + "x" + height + ".bat";
        File file = new File(name);

        FileOutputStream output = new FileOutputStream(file);
        DataOutputStream input = new DataOutputStream(output);
        input.writeBytes("javac resolutions/Main.java " + width + " " + height + "\n");
        input.writeBytes("java resolutions/Main\n");

        return name;
    }

    public static String CreateLogo(int width, int height) throws IOException {
        BufferedImage image = new BufferedImage(64, 64, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = image.createGraphics();

        g2d.setColor(Color.black);
        g2d.fillRect(0, 0, 64, 64);

        g2d.setFont(new Font("TimesRoman", Font.PLAIN, 15));
        g2d.setColor(Color.white);
        g2d.drawString(String.valueOf(width), 16, 20);
        g2d.drawString("X", 27, 37);
        g2d.drawString(String.valueOf(height), 16, 54);
        g2d.dispose();

        String name = "resolutions/" + width + "x" + height + ".png";
        File file = new File(name);
        ImageIO.write(image, "png", file);

        return name;
    }

    public static void dragWindow(AnchorPane window) {
        Stage thisWindow = (Stage) window.getScene().getWindow();

        window.setOnMousePressed(pressEvent -> window.setOnMouseDragged(dragEvent -> {
            thisWindow.setX(dragEvent.getScreenX() - pressEvent.getSceneX());
            thisWindow.setY(dragEvent.getScreenY() - pressEvent.getSceneY());
        }));
    }

    public static void openWindow(String fxml, String title) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Functions.class.getClassLoader().
                getResource(fxml));
        Parent window = fxmlLoader.load();
        Stage newWindow = new Stage();

        newWindow.setAlwaysOnTop(true);
        newWindow.initStyle(StageStyle.UNDECORATED);
        newWindow.setTitle(title);
        newWindow.setScene(new Scene(window));
        newWindow.show();
    }

    public static void minimize(AnchorPane window) {
        Stage thisWindow = (Stage) window.getScene().getWindow();
        thisWindow.setIconified(true);
    }

    public static void close(AnchorPane window) {
        Stage thisWindow = (Stage) window.getScene().getWindow();
        thisWindow.close();
    }

}
