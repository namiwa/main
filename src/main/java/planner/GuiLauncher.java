package duke;

import planner.gui.Main;
import javafx.application.Application;

/**
 * A launcher class to workaround classpath issues.
 */
public class GuiLauncher {
    public static void main(String[] args) {
        Application.launch(Main.class, args);
    }
}