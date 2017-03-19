package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BYDATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ENDTIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FROMDATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LOCATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRIORITY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STARTTIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TODATE;

import java.util.NoSuchElementException;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.IncorrectCommand;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddCommandParser {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     */
    public Command parse(String args) {
        ArgumentTokenizer argsTokenizer =
                new ArgumentTokenizer(PREFIX_PRIORITY, PREFIX_LOCATION, PREFIX_TAG, PREFIX_FROMDATE,
                        PREFIX_TODATE, PREFIX_BYDATE, PREFIX_STARTTIME, PREFIX_ENDTIME);
        argsTokenizer.tokenize(args);
        try {
            boolean pExists = argsTokenizer.getValue(PREFIX_PRIORITY).isPresent();
            boolean lExists = argsTokenizer.getValue(PREFIX_LOCATION).isPresent();
            boolean fExists = argsTokenizer.getValue(PREFIX_FROMDATE).isPresent();
            boolean uExists = argsTokenizer.getValue(PREFIX_TODATE).isPresent();
            boolean bExists = argsTokenizer.getValue(PREFIX_BYDATE).isPresent();
            boolean sExists = argsTokenizer.getValue(PREFIX_STARTTIME).isPresent();
            boolean eExists = argsTokenizer.getValue(PREFIX_ENDTIME).isPresent();

            if (fExists) {
                //means it is an event
                return new AddCommand(argsTokenizer.getPreamble().get(),
                        argsTokenizer.getValue(PREFIX_FROMDATE).get(),
                        argsTokenizer.getValue(PREFIX_TODATE).orElse(null),
                        argsTokenizer.getValue(PREFIX_STARTTIME).orElse(null),
                        argsTokenizer.getValue(PREFIX_ENDTIME).orElse(null),
                        argsTokenizer.getValue(PREFIX_LOCATION).orElse(null),
                        ParserUtil.toSet(argsTokenizer.getAllValues(PREFIX_TAG)));

            } else if (bExists) {
                return new AddCommand(
                        argsTokenizer.getPreamble().get(),
                        argsTokenizer.getValue(PREFIX_BYDATE).get(),
                        argsTokenizer.getValue(PREFIX_ENDTIME).orElse(null),
                        argsTokenizer.getValue(PREFIX_LOCATION).orElse(null),
                        ParserUtil.toSet(argsTokenizer.getAllValues(PREFIX_TAG)));
            } else {
                return new AddCommand(
                        argsTokenizer.getPreamble().get(),
                        argsTokenizer.getValue(PREFIX_PRIORITY).get(),
                        argsTokenizer.getValue(PREFIX_LOCATION).get(),
                        ParserUtil.toSet(argsTokenizer.getAllValues(PREFIX_TAG)));
            }

        } catch (NoSuchElementException nsee) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        } catch (IllegalValueException ive) {
            return new IncorrectCommand(ive.getMessage());
        }
    }

}
