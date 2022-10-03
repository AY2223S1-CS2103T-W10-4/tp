package modtrekt.logic.commands;

import static modtrekt.logic.commands.CommandTestUtil.assertCommandSuccess;

import org.junit.jupiter.api.Test;

import modtrekt.model.Model;
import modtrekt.model.ModelManager;

public class HelpCommandTest {
    private Model model = new ModelManager();
    private Model expectedModel = new ModelManager();

    @Test
    public void execute_help_success() {
        CommandResult expectedCommandResult = new CommandResult(HelpCommand.SHOWING_HELP_MESSAGE, true, false);
        CommandTestUtil.assertCommandSuccess(new HelpCommand(), model, expectedCommandResult, expectedModel);
    }
}
