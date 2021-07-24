package Main.C;

import java.io.File;

public class C {

    public static native Long change(int width, int height);

    private static final String absolute = new File("resolution/C/change.dll").getAbsolutePath();

    static {
        System.load(absolute);
    }

    public static void main(String[] args) {
        C.change(1024, 768);
    }

}
