package seedu.address.logic.commands;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.UnmodifiableObservableList;
import seedu.address.commons.events.ui.JumpToEventListRequestEvent;
import seedu.address.commons.events.ui.JumpToTaskListRequestEvent;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.ModelManager;
import seedu.address.model.ReadOnlyWhatsLeft;
import seedu.address.model.person.ReadOnlyEvent;
import seedu.address.model.person.ReadOnlyTask;
import seedu.address.model.person.UniqueEventList.EventNotFoundException;
import seedu.address.model.person.UniqueTaskList.TaskNotFoundException;

/**
 * Deletes an activity identified using it's last displayed index from WhatsLeft.
 */
public class DeleteCommand extends Command {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the activity identified by the type and index number used in the last respective listing.\n"
            + "Parameters: TYPE (ev represents event or ts represents task) and INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " ev" + " 1";

    public static final String MESSAGE_DELETE_ACTIVITY_SUCCESS = "Deleted Activity: %1$s";

    public final int targetIndex;
    public final String targetType;
    //@@author A0110491U
    public DeleteCommand(int targetIndex, String targetType) {
        this.targetIndex = targetIndex;
        this.targetType = targetType;
    }
    //@@author A0124377A
    @Override
    public CommandResult execute() throws CommandException {

        UnmodifiableObservableList<ReadOnlyEvent> lastShownEventList = model.getFilteredEventList();
        UnmodifiableObservableList<ReadOnlyTask> lastShownTaskList = model.getFilteredTaskList();

        if (targetType.equals("ev")) {
            if (lastShownEventList.size() < targetIndex) {
                throw new CommandException(Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX);
            }
            ReadOnlyEvent eventToDelete = lastShownEventList.get(targetIndex - 1);
            try {
                //store for undo operation
                ReadOnlyWhatsLeft currState = model.getWhatsLeft();
                ModelManager.setPreviousState(currState);
                EventsCenter.getInstance().post(new JumpToEventListRequestEvent(targetIndex - 1));
                model.deleteEvent(eventToDelete);
                model.storePreviousCommand("delete");
            } catch (EventNotFoundException pnfe) {
                assert false : "The target event cannot be missing";
            }
            return new CommandResult(String.format(MESSAGE_DELETE_ACTIVITY_SUCCESS, eventToDelete));
        }

        if (targetType.equals("ts")) {
            if (lastShownTaskList.size() < targetIndex) {
                throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
            }
            ReadOnlyTask taskToDelete = lastShownTaskList.get(targetIndex - 1);
            try {
                //store for undo operation
                ReadOnlyWhatsLeft currState = model.getWhatsLeft();
                ModelManager.setPreviousState(currState);
                EventsCenter.getInstance().post(new JumpToTaskListRequestEvent(targetIndex - 1));
                model.deleteTask(taskToDelete);
                model.storePreviousCommand("delete");
            } catch (TaskNotFoundException pnfe) {
                assert false : "The target task cannot be missing";
            }
            return new CommandResult(String.format(MESSAGE_DELETE_ACTIVITY_SUCCESS, taskToDelete));
        }
        return new CommandResult(String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));

    }

}
