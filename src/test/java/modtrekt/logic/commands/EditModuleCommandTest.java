package modtrekt.logic.commands;

import static modtrekt.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

import modtrekt.logic.parser.ModtrektParser;
import modtrekt.logic.parser.exceptions.ParseException;
import modtrekt.model.module.ModCode;
import modtrekt.model.module.ModCredit;
import modtrekt.model.module.ModName;

class EditModuleCommandTest {

    // === === ===
    // Positive test cases

    @Test
    public void testCommand_sameObjectReferenceEquals_returnsTrue() {
        EditModuleCommand cmd =
                new EditModuleCommand(new ModCode("CS2100"), new ModCode("CS2109S"),
                        new ModName("Intro to AI and machine learning"), new ModCredit("4"));
        assertEquals(cmd, cmd);
    }

    @Test
    public void testCommand_differentObjectValuesEquals_returnsFalse() {
        EditModuleCommand a =
                new EditModuleCommand(new ModCode("CS2100"), new ModCode("CS2109S"),
                        new ModName("Intro to AI and machine learning"), new ModCredit("4"));
        EditModuleCommand b =
                new EditModuleCommand(new ModCode("CS2103T"), new ModCode("CS2109S"),
                        new ModName("Intro to AI and machine learning"), new ModCredit("4"));
        EditModuleCommand c =
                new EditModuleCommand(new ModCode("CS2100"), new ModCode("CS2103T"),
                        new ModName("Intro to AI and machine learning"), new ModCredit("4"));
        EditModuleCommand d =
                new EditModuleCommand(new ModCode("CS2100"), new ModCode("CS2109S"),
                        new ModName("AI and ML"), new ModCredit("4"));
        EditModuleCommand e =
                new EditModuleCommand(new ModCode("CS2100"), new ModCode("CS2109S"),
                        new ModName("Intro to AI and machine learning"), new ModCredit("2"));
        assertNotEquals(a, b);
        assertNotEquals(a, c);
        assertNotEquals(a, d);
        assertNotEquals(a, e);
    }

    @Test
    public void testCommand_sameObjectValuesEquals_returnsTrue() {
        EditModuleCommand cmd1 =
                new EditModuleCommand(new ModCode("CS2100"), new ModCode("CS2109S"),
                        new ModName("Intro to AI and machine learning"), new ModCredit("4"));
        EditModuleCommand cmd2 =
                new EditModuleCommand(new ModCode("CS2100"), new ModCode("CS2109S"),
                        new ModName("Intro to AI and machine learning"), new ModCredit("4"));
        assertEquals(cmd1, cmd2);
    }

    @Test
    public void testModtRektParser_validCommandWord_returnsValidCommand() throws ParseException {
        EditModuleCommand expected =
                new EditModuleCommand(new ModCode("CS2100"), new ModCode("CS2109S"),
                        new ModName("Intro to AI and machine learning"), new ModCredit("4"));
        Command actual =
                new ModtrektParser()
                        .parseCommand("edit module CS2100 -c CS2109S -n \"Intro to AI and machine learning\" -cr 4");
        assertEquals(expected, actual);
    }

    @Test
    public void testParser_validModCode_returnsValidCommand() throws ParseException {
        EditModuleCommand ai =
                new EditModuleCommand(new ModCode("CS2100"), new ModCode("CS2109S"), null, null);
        EditModuleCommand swe =
                new EditModuleCommand(new ModCode("CS2100"), new ModCode("CS2103T"), null, null);

        assertEquals(ai, new ModtrektParser().parseCommand("edit module CS2100 -c CS2109S"));
        assertEquals(swe, new ModtrektParser().parseCommand("edit module CS2100 -c CS2103T"));
    }

    @Test
    public void testParser_validName_returnsValidCommand() throws ParseException {
        EditModuleCommand name1 =
                new EditModuleCommand(new ModCode("CS2100"), null,
                        new ModName("Intro to AI and machine learning"), null);
        EditModuleCommand name2 =
                new EditModuleCommand(new ModCode("CS2100"), null,
                        new ModName("Software Engineering"), null);
        assertEquals(name1,
                new ModtrektParser().parseCommand("edit module CS2100 -n \"Intro to AI and machine learning\""));
        assertEquals(name2, new ModtrektParser().parseCommand("edit module CS2100 -n \"Software Engineering\""));
    }

    @Test
    public void testParser_validCredit_returnsValidCommand() throws ParseException {
        EditModuleCommand credit1 =
                new EditModuleCommand(new ModCode("CS2100"), null, null, new ModCredit("1"));
        EditModuleCommand credit2 =
                new EditModuleCommand(new ModCode("CS2100"), null, null, new ModCredit("2"));
        assertEquals(credit1, new ModtrektParser().parseCommand("edit module CS2100 -cr 1"));
        assertEquals(credit2, new ModtrektParser().parseCommand("edit module CS2100 -cr 2"));
    }


    // === === ===
    // Negative test cases

    // NULL

    @Test
    public void testParser_nullInput_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new ModtrektParser().parseCommand(null));
    }

    // FLAGS

    @Test
    public void testParser_invalidFlags_throwsParseException() {
        assertThrows(ParseException.class, () -> new ModtrektParser().parseCommand("edit module CS2100 -p high"));
        assertThrows(ParseException.class, () -> new ModtrektParser().parseCommand("edit -b -c -p high"));
    }

    @Test
    public void testParser_missingFlags_throwsParseException() {
        assertThrows(ParseException.class, () -> new ModtrektParser().parseCommand("edit module"));
    }

    // MODULES

    @Test
    public void testParser_invalidModule_throwsParseException() {
        assertThrows(ParseException.class, () -> new ModtrektParser().parseCommand("edit module ST2334 -c CS@0"));
    }

    @Test
    public void testParser_multipleModules_throwsParseException() {
        assertThrows(ParseException.class, () -> new ModtrektParser()
                .parseCommand("edit module ST2334 -c CS2100 CS2103T"));
    }

    @Test
    public void testParser_missingModule_throwsParseException() {
        assertThrows(ParseException.class, () -> new ModtrektParser().parseCommand("edit module ST2334 -c"));
    }

    // CREDIT

    @Test
    public void testParser_invalidCredit_throwsParseException() {
        assertThrows(ParseException.class, () -> new ModtrektParser().parseCommand("edit module ST2334 -cr a"));
    }

    @Test
    public void testParser_multipleCredit_throwsParseException() {
        assertThrows(ParseException.class, () -> new ModtrektParser()
                .parseCommand("edit module ST2334 -cr 2 -cr 3"));
    }

    @Test
    public void testParser_missingCredit_throwsParseException() {
        assertThrows(ParseException.class, () -> new ModtrektParser().parseCommand("edit module ST2334 -cr"));
    }

    // NAME

    @Test
    public void testParser_missingName_throwsParseException() {
        assertThrows(ParseException.class, () -> new ModtrektParser().parseCommand("edit module ST2334 -n"));
    }

    // TARGET

    @Test
    public void testParser_doesNotExist_throwsParseException() {
        assertThrows(ParseException.class, () -> new ModtrektParser().parseCommand("edit module abc s -c CS2103T"));
    }

    @Test
    public void testParser_missingIndex_throwsParseException() {
        assertThrows(ParseException.class, () -> new ModtrektParser().parseCommand("edit module -c CS2103T"));
        assertThrows(ParseException.class, () -> new ModtrektParser().parseCommand("edit -c CS2103T"));
    }
}
