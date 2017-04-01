package seedu.address.logic.commands;

import java.time.LocalDateTime;
import java.util.logging.Logger;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.commons.events.ui.UpdateCalendarEvent;
import seedu.address.logic.commands.CommandResult;

//@@author A0124377A
/**
 * View calendar schedule for next week
 */
public class CalendarViewCommand extends Command {
    private final Logger logger = LogsCenter.getLogger(CalendarViewCommand.class);
    
    public static final String COMMAND_WORD = "next";
    public static final String MESSAGE_USAGE = COMMAND_WORD + "\n" 
        + "Shows calendar schedule for chosen weeks ahead\n"
        + "Example: " + COMMAND_WORD + "1";
    
    private static final String MESSAGE_SUCCESS = "Calendar showing %1$s weeks ahead";
    private LocalDateTime currentDateTime;
    private int weeksAhead;
    
    public CalendarViewCommand(int weeksAhead) {
        currentDateTime = LocalDateTime.now();
        this.weeksAhead= weeksAhead;
    }

    @Override
    public CommandResult execute() throws CommandException {
        logger.info("-------[Executing CalendarViewCommand]" + this.toString());
        EventsCenter.getInstance().post(new UpdateCalendarEvent(currentDateTime,weeksAhead));
        return new CommandResult(String.format(MESSAGE_SUCCESS));

    }
}
