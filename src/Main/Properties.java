package Main;

import java.io.File;

public class Properties {

    private final String image;
    private final int width;
    private final int height;
    private final String batch;

    public Properties(String image, int width, int height, String batch) {
        super();
        this.image = image;
        this.width = width;
        this.height = height;
        this.batch = batch;
    }

    public String getBatch() {
        return batch;
    }

    public String getTitle() {
        return "\n\n\n\n\n" + width + "x" + height;
    }

    public String getFileName() {
        return width + "x" + height;
    }

    public String getImage() {
        return image;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public String getFile() {
        return new File("resolutions\\" + width + "x" + height + ".exe").getAbsolutePath();
    }

    public String getBatchFile() {
        return new File("resolutions\\" + width + "x" + height + ".bat").getAbsolutePath();
    }

}
