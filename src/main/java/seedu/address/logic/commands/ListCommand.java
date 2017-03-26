package seedu.address.logic.commands;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.JumpToEventListRequestEvent;
import seedu.address.commons.events.ui.JumpToTaskListRequestEvent;

/**
 * Lists all activities in WhatsLeft to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_SUCCESS = "Listed all activities";


    @Override
    public CommandResult execute() {
        model.updateFilteredListToShowAll();
        model.storePreviousCommand("");
        EventsCenter.getInstance().post(new JumpToEventListRequestEvent(0));
        EventsCenter.getInstance().post(new JumpToTaskListRequestEvent(0));
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
