// @@author namiwa

package planner.logic.command;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.HashMap;

import planner.credential.user.User;
import planner.logic.exceptions.legacy.ModEmptyCommandException;
import planner.logic.exceptions.legacy.ModException;
import planner.logic.modules.module.ModuleInfoDetailed;
import planner.ui.cli.PlannerUi;
import planner.util.crawler.JsonWrapper;
import planner.util.datetime.NattyWrapper;
import planner.util.storage.Storage;

public class UpdateModuleInfo extends ModuleCommand {

    public UpdateModuleInfo(Arguments args) {
        super(args);
    }

    @Override
    public void execute(
            HashMap<String, ModuleInfoDetailed> detailedMap,
            PlannerUi plannerUi,
            Storage store,
            JsonWrapper jsonWrapper,
            User profile) throws ModException {
        if (args.get("module").equals("module")) {
            NattyWrapper natty = new NattyWrapper();
            LocalDateTime start = natty.dateToLocalDateTime("this year");
            LocalDateTime end = natty.dateToLocalDateTime("next year");
            String startYear = start.format(DateTimeFormatter.ofPattern("yyyy"));
            String endYear = end.format(DateTimeFormatter.ofPattern("yyyy"));
            String year = startYear + "-" + endYear;
            jsonWrapper.runRequests(year, store);
            detailedMap.putAll(jsonWrapper.getModuleDetailedMap());
            profile.getModules().setTasks(jsonWrapper.readJsonTaskList(store));
            plannerUi.showUpdatedMsg();
        } else {
            throw new ModEmptyCommandException();
        }
    }
}
