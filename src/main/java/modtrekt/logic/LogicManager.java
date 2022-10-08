package modtrekt.logic;

import java.io.IOException;
import java.nio.file.Path;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import modtrekt.commons.core.GuiSettings;
import modtrekt.commons.core.LogsCenter;
import modtrekt.logic.commands.Command;
import modtrekt.logic.commands.CommandResult;
import modtrekt.logic.commands.exceptions.CommandException;
import modtrekt.logic.parser.TaskBookParser;
import modtrekt.logic.parser.exceptions.ParseException;
import modtrekt.model.Model;
import modtrekt.model.ReadOnlyTaskBook;
import modtrekt.model.task.Task;
import modtrekt.storage.Storage;

/**
 * The main LogicManager of the app.
 */
public class LogicManager implements Logic {
    public static final String FILE_OPS_ERROR_MESSAGE = "Could not save data to file: ";
    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model model;
    private final Storage storage;
    private final TaskBookParser taskBookParser;

    /**
     * Constructs a {@code LogicManager} with the given {@code Model} and {@code Storage}.
     */
    public LogicManager(Model model, Storage storage) {
        this.model = model;
        this.storage = storage;
        taskBookParser = new TaskBookParser();
    }

    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");

        CommandResult commandResult;
        Command command = taskBookParser.parseCommand(commandText);
        commandResult = command.execute(model);

        try {
            System.out.println("called");
            storage.saveTaskBook(model.getTaskBook());
        } catch (IOException ioe) {
            throw new CommandException(FILE_OPS_ERROR_MESSAGE + ioe, ioe);
        }

        return commandResult;
    }

    @Override
    public ReadOnlyTaskBook getAddressBook() {
        return model.getTaskBook();
    }

    @Override
    public ObservableList<Task> getFilteredPersonList() {
        return model.getFilteredTaskList();
    }

    @Override
    public Path getAddressBookFilePath() {
        return model.getTaskBookFilePath();
    }

    @Override
    public GuiSettings getGuiSettings() {
        return model.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        model.setGuiSettings(guiSettings);
    }
}
