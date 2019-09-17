package duke.tasks;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import duke.exceptions.DukeInvalidTimeException;
import duke.util.DateTimeParser;

public class DoAfter extends Task {

    private Task parent;
    private LocalDateTime date;

    /**
     * Direct Constructor for do after tasks.
     * @param child Current name of do after tasks.
     * @param parent Parent of do after tasks.
     */
    public DoAfter(String child, Task parent) {
        super(child);
        this.parent = parent;
    }

    /**
     * Constructor for do after tasks based on time.
     * @param child Current name of do after tasks.
     */
    public DoAfter(String... child) {
        super(child[0]);
        setDateAndTime(child[child.length - 1]);
    }

    /**
     * Specific to events, the date data has to be stored
     * into LocalDateTime object.
     * @param dateAndTime String date and time associated with the task.
     */
    private void setDateAndTime(String dateAndTime) {
        try {
            date = DateTimeParser.getStringToDate(dateAndTime);
        } catch (DukeInvalidTimeException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void setTaskDone() {
        if (parent != null) {
            if (parent.getDone()) {
                this.setTaskDone();
            }
        } else {
            if (date != null) {
                if (LocalDateTime.now().isAfter(date)) {
                    this.setTaskDone();
                }
            }
        }
    }

    @Override
    public String writingFile() {
        if (date != null) {
            return "DA"
                    + "|"
                    + super.writingFile()
                    + "|"
                    + date.format(DateTimeFormatter.ofPattern("dd-MM-yyyy [HH:mm]"));
        } else {
            return "DA" + "|" + super.writingFile();
        }
    }

    @Override
    public String toString() {
        if (date != null) {
            return "[DA]"
                    + super.toString()
                    + " (at: "
                    + date.format(DateTimeFormatter.ofPattern("dd-MM-yyyy [HH:mm]"))
                    + ")";
        } else {
            return "[DA]" + super.toString();
        }
    }


}
