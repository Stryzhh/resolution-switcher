package Main.C;

import java.io.File;

public class C {

    public static native long change(int width, int height);

    private static final String changeAbsolute = new File("src/Main/C/change.dll").getAbsolutePath();

    static {
        System.load(changeAbsolute);
    }

}
