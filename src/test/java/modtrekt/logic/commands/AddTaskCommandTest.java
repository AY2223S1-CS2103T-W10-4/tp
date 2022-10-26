package modtrekt.logic.commands;

import static modtrekt.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Path;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import modtrekt.commons.core.GuiSettings;
import modtrekt.logic.commands.exceptions.CommandException;
import modtrekt.model.Model;
import modtrekt.model.ReadOnlyModuleList;
import modtrekt.model.ReadOnlyTaskBook;
import modtrekt.model.ReadOnlyUserPrefs;
import modtrekt.model.module.ModCode;
import modtrekt.model.module.Module;
import modtrekt.model.task.Task;
import modtrekt.testutil.AddTaskCommandBuilder;

public class AddTaskCommandTest {

    @Test
    public void testCommand_sameObjectReferenceEquals_returnsTrue() {
        AddTaskCommand cmd = AddTaskCommandBuilder.build("desc", null, null);
        assertEquals(cmd, cmd);
    }

    @Test
    public void testCommand_differentObjectValuesEquals_returnsFalse() {
        AddTaskCommand cmd1 = AddTaskCommandBuilder.build("desc", "2022-01-01", "CS2103T");
        AddTaskCommand cmd2 = AddTaskCommandBuilder.build("des", "2022-01-01", "CS2103T");
        AddTaskCommand cmd3 = AddTaskCommandBuilder.build("desc", "2022-01-02", "CS2103T");
        AddTaskCommand cmd4 = AddTaskCommandBuilder.build("desc", "2022-01-01", "CS2103");
        assertNotEquals(cmd1, cmd2);
        assertNotEquals(cmd1, cmd3);
        assertNotEquals(cmd1, cmd4);
        assertNotEquals(cmd2, cmd3);
        assertNotEquals(cmd3, cmd4);
    }

    @Test
    public void testCommand_sameObjectValuesEquals_returnsTrue() {
        AddTaskCommand cmd1 = AddTaskCommandBuilder.build("desc", "2022-01-01", "CS2103T");
        AddTaskCommand cmd2 = AddTaskCommandBuilder.build("desc", "2022-01-01", "CS2103T");
        assertEquals(cmd1, cmd2);
    }

    @Test
    public void testCommand_sameObjectValuesEqualsWithNulls_returnsTrue() {
        AddTaskCommand cmd1 = AddTaskCommandBuilder.build("desc", null, "CS2103T");
        AddTaskCommand cmd2 = AddTaskCommandBuilder.build("desc", null, "CS2103T");
        AddTaskCommand cmd3 = AddTaskCommandBuilder.build("desc", "2022-01-01", null);
        AddTaskCommand cmd4 = AddTaskCommandBuilder.build("desc", "2022-01-01", null);
        assertEquals(cmd1, cmd2);
        assertEquals(cmd3, cmd4);
    }

    @Test
    public void testCommandNoCurrentModuleAndNoModuleCode_throws() {
        AddTaskCommand cmd = AddTaskCommandBuilder.build("desc", null, null);
        assertThrows(CommandException.class, () -> cmd.execute(new ModelStub()));
    }

    @Test
    public void testCommandNoCurrentModuleWithModuleCode_returnsTrue() throws Exception {
        AddTaskCommand cmd = AddTaskCommandBuilder.build("desc", null, "CS2102");
        CommandResult result = cmd.execute(new ModelHasModuleWithModCode());
        assertTrue(result.getFeedbackToUser().contains("CS2102"));
        assertTrue(result.getFeedbackToUser().contains("desc"));
    }

    @Test
    public void testCommandDeadlineNoCurrentModuleWithModuleCode_returnsTrue() throws Exception {
        AddTaskCommand cmd = AddTaskCommandBuilder.build("desc", "2022-04-15", "CS2102");
        CommandResult result = cmd.execute(new ModelHasModuleWithModCode());
        assertTrue(result.getFeedbackToUser().contains("CS2102"));
        assertTrue(result.getFeedbackToUser().contains("desc"));
        assertTrue(result.getFeedbackToUser().contains("due 2022-04-15"));
    }

    @Test
    public void testCommandCurrentModuleWithoutModuleCode_returnsTrue() throws Exception {
        AddTaskCommand cmd = AddTaskCommandBuilder.build("desc", null, null);
        ModelStub model = new ModelStub();
        model.setCurrentModule(new ModCode("CS2106"));
        CommandResult result = cmd.execute(model);
        assertTrue(result.getFeedbackToUser().contains("CS2106"));
        assertTrue(result.getFeedbackToUser().contains("desc"));
    }

    @Test
    public void testCommandDeadlineCurrentModuleWithoutModuleCode_returnsTrue() throws Exception {
        AddTaskCommand cmd = AddTaskCommandBuilder.build("desc", "2022-04-15", null);
        ModelStub model = new ModelStub();
        model.setCurrentModule(new ModCode("CS2106"));
        CommandResult result = cmd.execute(model);
        assertTrue(result.getFeedbackToUser().contains("CS2106"));
        assertTrue(result.getFeedbackToUser().contains("desc"));
        assertTrue(result.getFeedbackToUser().contains("due 2022-04-15"));
    }

    @Test
    public void testCommandModuleCodeInvalid_throws() {
        AddTaskCommand cmd = AddTaskCommandBuilder.build("desc", null, "CS2000");
        assertThrows(CommandException.class, "Module code CS2000 does not exist", ()
                -> cmd.execute(new ModelStub()));
    }

    @Test
    public void testCommandCurrentModuleWithModuleCode_returnsCodeSpecified() throws Exception {
        AddTaskCommand cmd = AddTaskCommandBuilder.build("desc", null, "CS2102");
        ModelStub model = new ModelHasModuleWithModCode();
        model.setCurrentModule(new ModCode("CS2106"));
        CommandResult result = cmd.execute(model);
        assertTrue(result.getFeedbackToUser().contains("CS2102"));
    }

    @Test
    public void testCommandDeadlineCurrentModuleWithModuleCode_returnsCodeSpecified() throws Exception {
        AddTaskCommand cmd = AddTaskCommandBuilder.build("desc", "2022-04-15", "CS2102");
        ModelStub model = new ModelHasModuleWithModCode();
        model.setCurrentModule(new ModCode("CS2106"));
        CommandResult result = cmd.execute(model);
        assertTrue(result.getFeedbackToUser().contains("CS2102"));
        assertTrue(result.getFeedbackToUser().contains("due 2022-04-15"));
    }

    /**
     * Model stub that implements getCurrentModule() and setCurrentModule() methods for testing.
     */
    private class ModelStub implements Model {
        private ModCode currentModule = null;

        @Override
        public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {

        }

        @Override
        public ReadOnlyUserPrefs getUserPrefs() {
            return null;
        }

        @Override
        public GuiSettings getGuiSettings() {
            return null;
        }

        @Override
        public void setGuiSettings(GuiSettings guiSettings) {

        }

        @Override
        public Path getTaskBookFilePath() {
            return null;
        }

        @Override
        public Path getModuleListFilePath() {
            return null;
        }

        @Override
        public void setTaskBookFilePath(Path taskBookFilePath) {

        }

        @Override
        public void setModuleListFilePath(Path moduleListFilePath) {

        }

        @Override
        public void setTaskBook(ReadOnlyTaskBook taskBook) {

        }

        @Override
        public ReadOnlyTaskBook getTaskBook() {
            return null;
        }

        @Override
        public void setModuleList(ReadOnlyModuleList moduleList) {

        }

        @Override
        public ReadOnlyModuleList getModuleList() {
            return null;
        }

        @Override
        public void deleteTask(Task target) {

        }

        @Override
        public boolean hasModule(Module module) {
            return false;
        }

        @Override
        public boolean hasModuleWithModCode(ModCode code) {
            return false;
        }

        @Override
        public void updateModuleTaskCount(Task t) {

        }

        @Override
        public void deleteTasksOfModule(Module target) {

        }

        @Override
        public void addTask(Task t) {

        }

        @Override
        public void deleteModule(Module target) {

        }

        @Override
        public Module parseModuleFromCode(ModCode code) {
            return null;
        }

        @Override
        public ModCode getCurrentModule() {
            return currentModule;
        }

        @Override
        public void setCurrentModule(ModCode code) {
            currentModule = code;
        }

        @Override
        public void updateTaskModule(ModCode oldCode, ModCode newCode) {

        }

        @Override
        public void setTask(Task target, Task editedTask) {

        }

        @Override
        public void archiveDoneModuleTasks(ModCode code) {

        }

        @Override
        public ObservableList<Task> getFilteredTaskList() {
            return null;
        }

        @Override
        public void updateFilteredTaskList(Predicate<Task> predicate) {

        }

        @Override
        public void addModule(Module module) {

        }

        @Override
        public void setModule(Module target, Module editedModule) {

        }

        @Override
        public ObservableList<Module> getFilteredModuleList() {
            return null;
        }

        @Override
        public void updateFilteredModuleList(Predicate<Module> predicate) {

        }
    }

    /**
     * A model stub that returns true for the hasModuleWithModCode method, to mimic specified behaviour.
     */
    private class ModelHasModuleWithModCode extends ModelStub {
        @Override
        public boolean hasModuleWithModCode(ModCode code) {
            return true;
        }
    }
}
