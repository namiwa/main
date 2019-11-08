package planner.main;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;

import planner.credential.user.User;
import planner.logic.command.EndCommand;
import planner.logic.command.ModuleCommand;
import planner.logic.exceptions.legacy.ModEmptyCommandException;
import planner.logic.exceptions.legacy.ModException;
import planner.logic.exceptions.planner.ModFailedJsonException;
import planner.logic.modules.module.ModuleInfoDetailed;
import planner.logic.parser.Parser;
import planner.ui.cli.PlannerUi;
import planner.util.crawler.JsonWrapper;
import planner.util.logger.PlannerLogger;
import planner.util.storage.Storage;

public class CliLauncher {
    /**
     * Classes used for storage of data
     * Ui output and inputs and current
     * active tasks in TaskList and reminder.
     */
    private Storage store;
    private Parser argparser;
    private JsonWrapper jsonWrapper;
    private PlannerUi modUi;
    private HashMap<String, ModuleInfoDetailed> modDetailedMap;
    private User profile;
    private transient ByteArrayOutputStream output;
    public static List<Timer> timerPool = new ArrayList<>();


    /**
     * Constructor for Planner class.
     */
    public CliLauncher(boolean gui) {
        store = new Storage();
        modUi = new PlannerUi();
        argparser = new Parser();
        jsonWrapper = new JsonWrapper();
        if (gui) {
            this.redirectOutput();
            //credential.prompt(modUi);
            modUi.helloMsg();
        }
    }

    public CliLauncher() {
        this(false);
    }

    /**
     * Redirect output for GUI compatibility.
     */
    private void redirectOutput() {
        this.output = new ByteArrayOutputStream();
        PrintStream printStreamGui = new PrintStream(this.output);
        System.setOut(printStreamGui);
        System.setErr(printStreamGui);
    }

    /**
     * Setup data files for module data and logging.
     */
    public void modSetup() {
        try {
            modDetailedMap = jsonWrapper.getModuleDetailedMap(true, store);
            //modTasks.setTasks(jsonWrapper.readJsonTaskList(store));
            profile = User.loadProfile(modDetailedMap, modUi, store, jsonWrapper);
            profile.setSemester(0);
            PlannerLogger.setLogFile(store);
        } catch (ModFailedJsonException ej) {
            ej.getMessage();
            PlannerLogger.log(ej);
        } catch (IOException eio) {
            eio.getStackTrace();
            PlannerLogger.log(eio);
        }
    }

    private void modRunArgparse4j() {
        modSetup();
        modUi.helloMsg();
        boolean isExit = true;
        while (isExit) {
            isExit = this.handleInput();
        }
    }

    private boolean handleInput() {
        try {
            String input = modUi.readInput();
            ModuleCommand c = argparser.parseCommand(input);
            if (c != null) {
                c.call(modDetailedMap, modUi, store, jsonWrapper, profile);
                if (c instanceof EndCommand) {
                    return false;
                }
            }
        } catch (ModException e) {
            System.out.println(e.getMessage());
            PlannerLogger.log(e);
        } finally {
            modUi.showLine();
        }
        return true;
    }

    /**
     * Get output from commands.
     * @param input user input
     * @return command output
     */
    public String getResponse(String input) {
        if (input != null) {
            this.output.reset();
            this.handleInput();
        }
        return this.output.toString();
    }

    /**
     * Main entry point for Duke.
     * @param args Additional command line parameters, unused.
     */
    public static void main(String[] args) {
        //TODO: args flag could be passed into program for optional runs
        CliLauncher planner = new CliLauncher();
        planner.modRunArgparse4j();
    }
}
