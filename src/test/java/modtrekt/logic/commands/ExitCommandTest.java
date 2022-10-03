package modtrekt.logic.commands;

import static modtrekt.logic.commands.CommandTestUtil.assertCommandSuccess;

import org.junit.jupiter.api.Test;

import modtrekt.model.Model;
import modtrekt.model.ModelManager;

public class ExitCommandTest {
    private Model model = new ModelManager();
    private Model expectedModel = new ModelManager();

    @Test
    public void execute_exit_success() {
        CommandResult expectedCommandResult = new CommandResult(ExitCommand.MESSAGE_EXIT_ACKNOWLEDGEMENT, false, true);
        CommandTestUtil.assertCommandSuccess(new ExitCommand(), model, expectedCommandResult, expectedModel);
    }
}
