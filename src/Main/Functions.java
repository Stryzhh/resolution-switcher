package Main;

import com.google.gson.Gson;
import com.jfoenix.controls.JFXButton;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javax.imageio.ImageIO;
import javax.swing.*;
import net.sf.image4j.codec.ico.ICOEncoder;

public class Functions {

    public static Properties[] resolutionProperties = new Properties[6];
    public static JFXButton resolution0;
    public static JFXButton resolution1;
    public static JFXButton resolution2;
    public static JFXButton resolution3;
    public static JFXButton resolution4;
    public static JFXButton resolution5;
    public static ImageView resImage0;
    public static ImageView resImage1;
    public static ImageView resImage2;
    public static ImageView resImage3;
    public static ImageView resImage4;
    public static ImageView resImage5;

    public static String createBatch(int width, int height) throws IOException {
        String name = width + "x" + height + ".bat";
        File file = new File(name);
        File folder = new File(System.getProperty("user.dir"));

        FileOutputStream output = new FileOutputStream(file);
        DataOutputStream input = new DataOutputStream(output);
        input.writeBytes("javaw -Xmx200m -jar " + folder.getAbsolutePath() + "/resolution-switcher.jar " + width + " " + height);
        input.close();
        output.close();

        return name;
    }

    public static String createIcon(String imageLocation, int width, int height) throws IOException {
        String name = "resolutions/" + width + "x" + height + ".ico";
        java.awt.Image image = new ImageIcon(imageLocation).getImage();
        BufferedImage bi = new BufferedImage(64, 64, 2);

        Graphics2D g2d = bi.createGraphics();
        g2d.drawImage(image, 0, 0, null);
        g2d.dispose();

        ICOEncoder.write(bi, new File(name));
        return name;
    }

    public static String CreateLogo(int width, int height) throws IOException {
        BufferedImage image = new BufferedImage(64, 64, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = image.createGraphics();

        //create 64x64 text logo
        g2d.setColor(Color.black);
        g2d.fillRect(0, 0, 64, 64);
        g2d.setFont(new Font("Arial", Font.PLAIN, 16));
        g2d.setColor(Color.white);
        g2d.drawString(String.valueOf(width), 12, 20);
        g2d.drawString("X", 27, 37);
        g2d.drawString(String.valueOf(height), 12, 54);
        g2d.dispose();

        String name = "resolutions/" + width + "x" + height + ".png";
        File file = new File(name);
        ImageIO.write(image, "png", file);

        return name;
    }

    public static void loadResolutions() {
        disableButtons();
        Arrays.fill(resolutionProperties, null);
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

    private static void loadResolution(JFXButton button, ImageView image, Properties properties, Image newImage) {
        button.setVisible(true);
        button.setDisable(false);
        button.setText(properties.getTitle());
        image.setImage(newImage);
    }

    public static void disableButtons() {
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
