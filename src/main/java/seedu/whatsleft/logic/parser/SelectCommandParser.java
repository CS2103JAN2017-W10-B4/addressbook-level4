package seedu.whatsleft.logic.parser;

import static seedu.whatsleft.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Optional;

import seedu.whatsleft.logic.commands.Command;
import seedu.whatsleft.logic.commands.IncorrectCommand;
import seedu.whatsleft.logic.commands.SelectCommand;

/**
 * Parses input arguments and creates a new SelectCommand object
 */
public class SelectCommandParser {
    //@@author A0110491U
    /**
     * Parses the given {@code String} of arguments in the context of the SelectCommand
     * and returns an SelectCommand object for execution.
     */
    public Command parse(String args) {
        Optional<Integer> index = ParserUtil.parseIndex(args);
        if (!index.isPresent()) {
            return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectCommand.MESSAGE_USAGE));
        }

        Optional<String> type = ParserUtil.parseType(args);
        if (!type.isPresent()) {
            return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectCommand.MESSAGE_USAGE));
        }

        return new SelectCommand(index.get(), type.get().toLowerCase());
    }

}
