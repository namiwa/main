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
    public DoAfter(String child, LocalDateTime date) {
        super(child);
        this.date = date;
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
