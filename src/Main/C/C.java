package Main.C;

import java.io.File;

public class C {

    public static native long change(int width, int height);

    private static final String changeAbsolute = new File("src/Main/C/change.dll").getAbsolutePath();

    static {
        System.load(changeAbsolute);
    }

    public static void main(String[] args) {
        if (args.length == 2) {
            change(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
        }
    }

}
