package Main;

import Main.C.C;

public class StartProgram {

    public static void main(String[] args) {
        if (args.length == 2) {
            C.change(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
        } else {
            Main.main(args);
        }
    }

}
