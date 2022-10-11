package modtrekt.logic.commands;

import static java.util.Objects.requireNonNull;

import modtrekt.logic.commands.exceptions.CommandException;
import modtrekt.logic.parser.CliSyntax;
import modtrekt.model.Model;
import modtrekt.model.module.Module;

/**
 * Adds a person to the address book.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE_1 = COMMAND_WORD + ": Adds a module to the module list. "
            + "Parameters: "
            + CliSyntax.PREFIX_MOD_NAME + "NAME "
            + CliSyntax.PREFIX_MOD_CODE + "CODE "
            + CliSyntax.PREFIX_MOD_CREDIT + "CREDIT "
            + "Example: " + COMMAND_WORD + " -m "
            + CliSyntax.PREFIX_MOD_NAME + "Data Structures and Algorithms "
            + CliSyntax.PREFIX_MOD_CODE + "CS2040S "
            + CliSyntax.PREFIX_MOD_CREDIT + "4 ";

    public static final String MESSAGE_USAGE_2 = COMMAND_WORD + ": Adds a module to the module list. "
            + "Parameters: "
            + CliSyntax.PREFIX_MOD_CODE + "CODE "
            + "Example: " + COMMAND_WORD + " -m "
            + CliSyntax.PREFIX_MOD_CODE + "CS2040S ";

    public static final String MESSAGE_SUCCESS = "New module added: %1$s";
    public static final String MESSAGE_DUPLICATE_MODULE = "This module already exists in the module list";

    private final Module toAdd;

    /**
     * Creates an AddCommand to add the specified {@code Module}
     */
    public AddCommand(Module module) {
        requireNonNull(module);
        toAdd = module;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasModule(toAdd) || model.hasModuleWithModCode(toAdd.getCode())) {
            throw new CommandException(MESSAGE_DUPLICATE_MODULE);
        }

        model.addModule(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddCommand // instanceof handles nulls
                && toAdd.equals(((AddCommand) other).toAdd));
    }
}
