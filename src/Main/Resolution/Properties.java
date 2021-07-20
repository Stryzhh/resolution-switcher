package Main.Resolution;

public class Properties {

    private final String image;
    private final String width;
    private final String height;

    public Properties(String image, String width, String height) {
        super();
        this.image = image;
        this.width = width;
        this.height = height;
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
