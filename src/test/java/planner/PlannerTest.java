//@@author namiwa

package planner;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import planner.main.CliLauncher;

public class PlannerTest extends InputTest {

    private static final String LINE = "_______________________________";

    /**
     * Testing the Planner class with bye as the only input
     * and checking the expected output.
     * outContent must have it's carriage return removed due
     * to windows/linus newline differences.
     */
    @DisplayName("Termination Test")
    @Test
    public void testPlan() {
        final String test = "password\n bye";
        provideInput(test);
        final String[] hold = {""};
        CliLauncher.main(hold);
        String expected =
                "Please enter your password to continue:\n"
                + LINE
                + "\n"
                + "Welcome to ModPlanner, your one stop solution to module planning!\n"
                + "Begin typing to get started!\n"
                + LINE
                + "\n"
                + LINE
                + "\n"
                + "Thanks for using ModPlanner!\n"
                + "Your data will be stored in file shortly!\n"
                + LINE
                + "\n"
                + LINE
                + "\n";
        assertEquals(expected, outContent.toString().replaceAll("\r", ""));
    }
}
