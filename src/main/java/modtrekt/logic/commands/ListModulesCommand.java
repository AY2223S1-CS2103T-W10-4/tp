package modtrekt.logic.commands;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;

import modtrekt.logic.commands.exceptions.CommandException;
import modtrekt.model.Model;

/**
 * Lists all tasks.
 */
@Parameters(commandDescription = "List tasks in the task book.")
public class ListCommand extends Command {
    public static final String COMMAND_WORD = "ls";

    @Parameter(names = "-a", description = "Show all tasks")
    private boolean areDoneTasksShown;

    /**
     * Returns a new ListTasksCommand object, with no fields initialized, for use with JCommander.
     */
    public ListCommand() {
    }

    public ListCommand(boolean areDoneTasksShown) {
        this.areDoneTasksShown = areDoneTasksShown;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        if (areDoneTasksShown) {
            model.updateFilteredTaskList(Model.PREDICATE_SHOW_ALL_TASKS);
            return new CommandResult("Here is the list of all tasks, including those marked as done!");
        } else {
            model.updateFilteredTaskList(Model.PREDICATE_HIDE_DONE_TASKS);
            return new CommandResult("Here is the list of all active tasks!");
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ListCommand // instanceof handles nulls
                && areDoneTasksShown == (((ListCommand) other).areDoneTasksShown));
    }
}
