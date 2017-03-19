package seedu.address.logic.commands;

import java.util.HashSet;
import java.util.Set;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.Activity;
import seedu.address.model.person.ByDate;
import seedu.address.model.person.Deadline;
import seedu.address.model.person.Description;
import seedu.address.model.person.EndTime;
import seedu.address.model.person.Event;
import seedu.address.model.person.FromDate;
import seedu.address.model.person.Location;
import seedu.address.model.person.Priority;
import seedu.address.model.person.StartTime;
import seedu.address.model.person.ToDate;
import seedu.address.model.person.ToDo;
import seedu.address.model.person.UniqueToDoList.DuplicateToDoException;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;

/**
 * Adds an activity to WhatsLeft.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";


    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds an activity to WhatsLeft. "
            + "Parameters: DESCRIPTION p/PRIORITY l/LOCATION  [t/TAG]...\n"
            + "Example: " + COMMAND_WORD
            + " Project Discussion p/high l/discussion room 4 t/friends t/owesMoney";

    public static final String MESSAGE_SUCCESS = "New activity added: %1$s";
    public static final String MESSAGE_DUPLICATE_ACTIVITY = "This activity already exists in WhatsLeft";

    private final ToDo toAdd;

    /**
     * Creates an AddCommand using raw values.
     *
     * @throws IllegalValueException if any of the raw values are invalid
     */

    public AddCommand(String description, String priority, String location, Set<String> tags)
            throws IllegalValueException {
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(new Tag(tagName));
        }
        this.toAdd = new Activity(
                new Description(description),
                new Priority(priority),
                new Location(location),
                new UniqueTagList(tagSet)
        );
    }

    public AddCommand(String description, String fromdate, String todate, String starttime, String endtime,
            String location, Set<String> tags) throws IllegalValueException {
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(new Tag(tagName));
        }
        this.toAdd = new Event(
                new Description(description),
                new FromDate(fromdate),
                new ToDate(todate),
                new StartTime(starttime),
                new EndTime(endtime),
                new Location(location),
                new UniqueTagList(tagSet));
    }

    public AddCommand(String description, String bydate, String bytime, String location,
            Set<String> tags) throws IllegalValueException {
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(new Tag(tagName));
        }
        this.toAdd = new Deadline(
                new Description(description),
                new ByDate(bydate),
                new EndTime(bytime),
                new Location(location),
                new UniqueTagList(tagSet));
    }

    @Override
    public CommandResult execute() throws CommandException {
        assert model != null;
        try {
            model.addToDo(toAdd);
            return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
        } catch (DuplicateToDoException e) {
            throw new CommandException(MESSAGE_DUPLICATE_ACTIVITY);
        }

    }

}
