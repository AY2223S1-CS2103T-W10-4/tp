package modtrekt.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import com.beust.jcommander.Parameter;

import modtrekt.commons.core.Messages;
import modtrekt.commons.core.index.Index;
import modtrekt.logic.commands.exceptions.CommandException;
import modtrekt.logic.parser.CliSyntax;
import modtrekt.logic.parser.converters.IndexConverter;
import modtrekt.model.Model;
import modtrekt.model.module.Module;

/**
 * Deletes a module identified using it's displayed index from the module list.
 */
public class RemoveCommand extends Command {
    public static final String COMMAND_WORD = "remove module";
    public static final String COMMAND_WORD_SHORTHAND = "remove mod";
    public static final String COMMAND_WORD_SHORTHAND_2 = "rm module";
    public static final String COMMAND_WORD_SHORTHAND_3 = "rm mod";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the task/module identified by the index number.\n"
            + "Prefixes: " + CliSyntax.PREFIX_MODULE + ": Modules, " + CliSyntax.PREFIX_TASK + ": Tasks\n"
            + "Format: " + COMMAND_WORD + " " + CliSyntax.PREFIX_MODULE + " <INDEX>";

    public static final String MESSAGE_DELETE_MODULE_SUCCESS = "I successfully deleted the module: %1$s!";

    @Parameter(description = "index", required = true,
        converter = IndexConverter.class)
    private Index targetIndex;

    /**
     *     Empty constructor that instantiates a RemoveCommand object, for use with JCommander.
     */
    public RemoveCommand() {}

    public RemoveCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Module> lastShownList = model.getFilteredModuleList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_MODULE_DISPLAYED_INDEX);
        }

        Module moduleToDelete = lastShownList.get(targetIndex.getZeroBased());
        model.deleteModule(moduleToDelete);
        model.deleteTasksOfModule(moduleToDelete);
        return new CommandResult(String.format(MESSAGE_DELETE_MODULE_SUCCESS, moduleToDelete));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof RemoveCommand // instanceof handles nulls
                && targetIndex.equals(((RemoveCommand) other).targetIndex)); // state check
    }
}
