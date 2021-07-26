package Main.C;

import java.io.File;

public class C {

    public static native long change(int width, int height);

    private static final String absolute = new File("src/Main/C/resolution.dll").getAbsolutePath();

    static {
        System.load(absolute);
    }

    public static void main(String[] args) {
        C.change(1920, 1080);
    }

}
