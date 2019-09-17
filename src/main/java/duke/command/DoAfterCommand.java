package duke.command;

import java.time.LocalDateTime;

import duke.tasks.Task;
import duke.util.Storage;
import duke.util.TaskList;
import duke.util.Ui;

public class DoAfterCommand extends Command {

    private Task parent;
    private LocalDateTime date;

    @Override
    public void execute(TaskList tasks, Ui ui, Storage store) {

    }

    @Override
    public boolean isExit() {
        return false;
    }
}
