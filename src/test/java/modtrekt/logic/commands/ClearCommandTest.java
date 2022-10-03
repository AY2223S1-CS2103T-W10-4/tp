package modtrekt.logic.commands;

import static modtrekt.logic.commands.CommandTestUtil.assertCommandSuccess;

import modtrekt.testutil.TypicalPersons;
import org.junit.jupiter.api.Test;

import modtrekt.model.AddressBook;
import modtrekt.model.Model;
import modtrekt.model.ModelManager;
import modtrekt.model.UserPrefs;

public class ClearCommandTest {

    @Test
    public void execute_emptyAddressBook_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();

        CommandTestUtil.assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_nonEmptyAddressBook_success() {
        Model model = new ModelManager(TypicalPersons.getTypicalAddressBook(), new UserPrefs());
        Model expectedModel = new ModelManager(TypicalPersons.getTypicalAddressBook(), new UserPrefs());
        expectedModel.setAddressBook(new AddressBook());

        CommandTestUtil.assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

}
