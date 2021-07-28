package Main;

public class Window {

    public static boolean add = false;
    public static boolean settings = false;
    public static boolean help = false;

    public static void setAdd(boolean add) {
        Window.add = add;
    }

    public static boolean isAdd() {
        return add;
    }

    public static void setSettings(boolean settings) {
        Window.settings = settings;
    }

    public static boolean isSettings() {
        return settings;
    }

    public static void setHelp(boolean help) {
        Window.help = help;
    }

    public static boolean isHelp() {
        return help;
    }

}
