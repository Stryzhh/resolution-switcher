package Main;

public class Properties {

    private final String image;
    private final int width;
    private final int height;

    public Properties(String image, int width, int height) {
        super();
        this.image = image;
        this.width = width;
        this.height = height;
    }

    public String getTitle() {
        return "\n\n\n\n\n" + width + "x" + height;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public String getImage() {
        return image;
    }

    public String toString() {
        return "{image = " + image +
                ", width = " + width +
                ", height = " + height + "}";
    }

}
