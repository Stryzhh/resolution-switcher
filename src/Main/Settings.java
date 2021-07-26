package Main;

public class Settings {

    public static boolean currentStartup;
    public static boolean currentDesktop;
    public static boolean currentStartMenu;

    private final boolean startup;
    private final boolean desktop;
    private final boolean startMenu;

    public Settings(boolean startup, boolean desktop, boolean startMenu) {
        super();
        this.startup = startup;
        this.desktop = desktop;
        this.startMenu = startMenu;
    }

    public boolean isStartup() {
        return startup;
    }

    public boolean isDesktop() {
        return desktop;
    }

    public boolean isStartMenu() {
        return startMenu;
    }
}
