package duke.command;

import java.time.LocalDateTime;

import duke.tasks.DoAfter;
import duke.tasks.Task;
import duke.util.Storage;
import duke.util.TaskList;
import duke.util.Ui;

public class DoAfterCommand extends Command {

    //TODO should it be parsed in the class or before allocation
    private String taskName;
    private Task parent;
    private LocalDateTime date;

    /**
     * Overloaded constructor for Do After tasks if the parent is another task.
     * @param taskName name of do after task.
     * @param parent Parent task which do after must be done after.
     */
    public DoAfterCommand(String taskName, Task parent) {
        this.taskName = taskName;
        this.parent = parent;
    }

    /**
     * Overloaded constructor for Do After task if the task must occur after some another time.
     * @param taskName name of do after task.
     * @param date Time and date where the task must be done after.
     */
    public DoAfterCommand(String taskName, LocalDateTime date) {
        this.taskName = taskName;
        this.date = date;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage store) {
        Task task;
        if (date != null) {
            task = new DoAfter(taskName, date);
        } else {
            task = new DoAfter(taskName, parent);
        }
        tasks.getTasks().add(task);
        ui.addedTaskMsg();
        store.readData();
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
