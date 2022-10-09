package modtrekt.logic.parser;

import static modtrekt.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static modtrekt.logic.parser.CommandParserTestUtil.assertParseFailure;
import static modtrekt.logic.parser.CommandParserTestUtil.assertParseSuccess;
<<<<<<< HEAD:src/test/java/modtrekt/logic/parser/RemoveTaskCommandParserTest.java
import static modtrekt.testutil.TypicalIndexes.INDEX_FIRST_TASK;
=======
import static modtrekt.testutil.TypicalIndexes.INDEX_FIRST_MODULE;
>>>>>>> junhao/HoJunHao2000/week-8/implement-module-commands:src/test/java/modtrekt/logic/parser/DeleteCommandParserTest.java

import org.junit.jupiter.api.Test;

import modtrekt.logic.commands.RemoveTaskCommand;

/**
 * As we are only doing white-box testing, our test cases do not cover path variations
 * outside of the DeleteCommand code. For example, inputs "1" and "1 abc" take the
 * same path through the DeleteCommand, and therefore we test only one of them.
 * The path variation for those two cases occur inside the ParserUtil, and
 * therefore should be covered by the ParserUtilTest.
 */
public class RemoveTaskCommandParserTest {

    private RemoveTaskCommandParser parser = new RemoveTaskCommandParser();

    @Test
    public void parse_validArgs_returnsDeleteCommand() {
<<<<<<< HEAD:src/test/java/modtrekt/logic/parser/RemoveTaskCommandParserTest.java
        assertParseSuccess(parser, "1", new RemoveTaskCommand(INDEX_FIRST_TASK));
=======
        assertParseSuccess(parser, "1", new DeleteCommand(INDEX_FIRST_MODULE));
>>>>>>> junhao/HoJunHao2000/week-8/implement-module-commands:src/test/java/modtrekt/logic/parser/DeleteCommandParserTest.java
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                RemoveTaskCommand.MESSAGE_USAGE));
    }
}
