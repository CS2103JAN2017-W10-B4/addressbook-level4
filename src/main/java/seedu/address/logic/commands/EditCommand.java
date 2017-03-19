package seedu.address.logic.commands;

import java.util.List;
import java.util.Optional;

import seedu.address.commons.core.Messages;
import seedu.address.commons.util.CollectionUtil;
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
import seedu.address.model.person.ReadOnlyActivity;
import seedu.address.model.person.ReadOnlyDeadline;
import seedu.address.model.person.ReadOnlyEvent;
import seedu.address.model.person.ReadOnlyToDo;
import seedu.address.model.person.StartTime;
import seedu.address.model.person.ToDate;
import seedu.address.model.person.ToDo;
import seedu.address.model.person.UniqueToDoList.DuplicateToDoException;
import seedu.address.model.tag.UniqueTagList;

/**
 * Edits the details of an existing activity in WhatsLeft.
 */
public class EditCommand extends Command {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the activity identified "
            + "by the index number used in the last activity listing. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[DESCRIPTION] [p/PRIORITY] [l/LOCATION ] [t/TAG]...\n"
            + "Example: " + COMMAND_WORD + " 1 p/high e/johndoe@yahoo.com";

    public static final String MESSAGE_EDIT_ACTIVITY_SUCCESS = "Edited Activity: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_ACTIVITY = "This activity already exists in WhatsLeft.";

    private final int filteredActivityListIndex;
    private final EditActivityDescriptor editActivityDescriptor;
    private final EditEventDescriptor editEventDescriptor;
    private final EditDeadlineDescriptor editDeadlineDescriptor;

    /**
     * @param filteredActivityListIndex the index of the activity in the filtered activity list to edit
     * @param editActivityDescriptor details to edit the activity with
     */
    public EditCommand(int filteredActivityListIndex, EditActivityDescriptor editActivityDescriptor,
            EditEventDescriptor editEventDescriptor, EditDeadlineDescriptor editDeadlineDescriptor) {
        assert filteredActivityListIndex > 0;
        assert editActivityDescriptor != null;

        // converts filteredActivityListIndex from one-based to zero-based.
        this.filteredActivityListIndex = filteredActivityListIndex - 1;

        this.editActivityDescriptor = new EditActivityDescriptor(editActivityDescriptor);
        this.editEventDescriptor = new EditEventDescriptor(editEventDescriptor);
        this.editDeadlineDescriptor = new EditDeadlineDescriptor(editDeadlineDescriptor);
    }

    @Override
    public CommandResult execute() throws CommandException {
        List<ReadOnlyToDo> lastShownList = model.getFilteredToDoList();

        if (filteredActivityListIndex >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_ACTIVITY_DISPLAYED_INDEX);
        }

        ReadOnlyToDo activityToEdit = lastShownList.get(filteredActivityListIndex);
        ToDo editedToDo = createEditedToDo(activityToEdit, editActivityDescriptor, editEventDescriptor,
                editDeadlineDescriptor);

        try {
            model.updateToDo(filteredActivityListIndex, editedToDo);
        } catch (DuplicateToDoException dpe) {
            throw new CommandException(MESSAGE_DUPLICATE_ACTIVITY);
        }
        model.updateFilteredListToShowAll();
        return new CommandResult(String.format(MESSAGE_EDIT_ACTIVITY_SUCCESS, activityToEdit));
    }

    /**
     * Creates and returns a {@code Activity} with the details of {@code activityToEdit}
     * edited with {@code editActivityDescriptor}.
     */
    private static ToDo createEditedToDo(ReadOnlyToDo todoToEdit,
                                             EditActivityDescriptor editActivityDescriptor,
                                             EditEventDescriptor editEventDescriptor,
                                             EditDeadlineDescriptor editDeadlineDescriptor) {
        assert todoToEdit != null;

        if (todoToEdit instanceof ReadOnlyActivity) {
            ReadOnlyActivity toDoToEditTo = (ReadOnlyActivity) todoToEdit;
            Description updatedDescription = editActivityDescriptor.getDescription().orElseGet(
                    toDoToEditTo::getDescription);
            Priority updatedPriority = editActivityDescriptor.getPriority().orElseGet(toDoToEditTo::getPriority);
            Location updatedLocation = editActivityDescriptor.getLocation().orElseGet(toDoToEditTo::getLocation);
            UniqueTagList updatedTags = editActivityDescriptor.getTags().orElseGet(toDoToEditTo::getTags);

            return new Activity(updatedDescription, updatedPriority, updatedLocation, updatedTags);
        } else if (todoToEdit instanceof ReadOnlyEvent) {
            ReadOnlyEvent toDoToEditTo = (ReadOnlyEvent) todoToEdit;
            Description updatedDescription = editEventDescriptor.getDescription().orElseGet(
                    toDoToEditTo::getDescription);
            FromDate updatedFromDate = editEventDescriptor.getFromDate().orElseGet(toDoToEditTo::getFromDate);
            ToDate updatedToDate = editEventDescriptor.getToDate().orElseGet(toDoToEditTo::getToDate);
            StartTime updatedStartTime = editEventDescriptor.getStartTime().orElseGet(toDoToEditTo::getStartTime);
            EndTime updatedEndTime = editEventDescriptor.getEndTime().orElseGet(toDoToEditTo::getEndTime);
            Location updatedLocation = editEventDescriptor.getLocation().orElseGet(toDoToEditTo::getLocation);
            UniqueTagList updatedTags = editEventDescriptor.getTags().orElseGet(toDoToEditTo::getTags);

            return new Event(updatedDescription, updatedFromDate, updatedToDate, updatedStartTime, updatedEndTime,
                    updatedLocation, updatedTags);
        } else if (todoToEdit instanceof ReadOnlyDeadline) {
            ReadOnlyDeadline toDoToEditTo = (ReadOnlyDeadline) todoToEdit;
            Description updatedDescription = editDeadlineDescriptor.getDescription().orElseGet(
                    toDoToEditTo::getDescription);
            ByDate updatedByDate = editDeadlineDescriptor.getByDate().orElseGet(toDoToEditTo::getByDate);
            EndTime updatedEndTime = editDeadlineDescriptor.getEndTime().orElseGet(toDoToEditTo::getEndTime);
            Location updatedLocation = editEventDescriptor.getLocation().orElseGet(toDoToEditTo::getLocation);
            UniqueTagList updatedTags = editEventDescriptor.getTags().orElseGet(toDoToEditTo::getTags);

            return new Deadline(updatedDescription, updatedByDate, updatedEndTime, updatedLocation, updatedTags);
        }
        return null;
    }

    public static class EditDeadlineDescriptor {
        private Optional<Description> description = Optional.empty();
        private Optional<ByDate> bydate = Optional.empty();
        private Optional<EndTime> endtime = Optional.empty();
        private Optional<Location> location = Optional.empty();
        private Optional<UniqueTagList> tags = Optional.empty();

        public EditDeadlineDescriptor() {}

        public EditDeadlineDescriptor(EditDeadlineDescriptor toCopy) {
            this.description = toCopy.getDescription();
            this.bydate = toCopy.getByDate();
            this.endtime = toCopy.getEndTime();
            this.location = toCopy.getLocation();
            this.tags = toCopy.getTags();
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyPresent(this.description, this.bydate,
                    this.endtime, this.location, this.tags);
        }

        public void setDescription(Optional<Description> description) {
            assert description != null;
            this.description = description;
        }

        public Optional<Description> getDescription() {
            return description;
        }

        public void setByDate(Optional<ByDate> bydate) {
            assert bydate != null;
            this.bydate = bydate;
        }

        public Optional<ByDate> getByDate() {
            return bydate;
        }

        public void setEndTime(Optional<EndTime> endtime) {
            assert endtime != null;
            this.endtime = endtime;
        }

        public Optional<EndTime> getEndTime() {
            return endtime;
        }

        public void setLocation(Optional<Location> location) {
            assert location != null;
            this.location = location;
        }

        public Optional<Location> getLocation() {
            return location;
        }

        public void setTags(Optional<UniqueTagList> tags) {
            assert tags != null;
            this.tags = tags;
        }

        public Optional<UniqueTagList> getTags() {
            return tags;
        }
    }

    public static class EditEventDescriptor {
        private Optional<Description> description = Optional.empty();
        private Optional<FromDate> fromdate = Optional.empty();
        private Optional<ToDate> todate = Optional.empty();
        private Optional<StartTime> starttime = Optional.empty();
        private Optional<EndTime> endtime = Optional.empty();
        private Optional<Location> location = Optional.empty();
        private Optional<UniqueTagList> tags = Optional.empty();

        public EditEventDescriptor() {}

        public EditEventDescriptor(EditEventDescriptor toCopy) {
            this.description = toCopy.getDescription();
            this.fromdate = toCopy.getFromDate();
            this.todate = toCopy.getToDate();
            this.starttime = toCopy.getStartTime();
            this.endtime = toCopy.getEndTime();
            this.location = toCopy.getLocation();
            this.tags = toCopy.getTags();
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyPresent(this.description, this.fromdate, this.todate, this.starttime,
                    this.endtime, this.location, this.tags);
        }

        public void setDescription(Optional<Description> description) {
            assert description != null;
            this.description = description;
        }

        public Optional<Description> getDescription() {
            return description;
        }

        public void setFromDate(Optional<FromDate> fromdate) {
            assert fromdate != null;
            this.fromdate = fromdate;
        }

        public Optional<FromDate> getFromDate() {
            return fromdate;
        }

        public void setToDate(Optional<ToDate> todate) {
            assert todate != null;
            this.todate = todate;
        }

        public Optional<ToDate> getToDate() {
            return todate;
        }

        public void setStartTime(Optional<StartTime> starttime) {
            assert starttime != null;
            this.starttime = starttime;
        }

        public Optional<StartTime> getStartTime() {
            return starttime;
        }

        public void setEndTime(Optional<EndTime> endtime) {
            assert endtime != null;
            this.endtime = endtime;
        }

        public Optional<EndTime> getEndTime() {
            return endtime;
        }

        public void setLocation(Optional<Location> location) {
            assert location != null;
            this.location = location;
        }

        public Optional<Location> getLocation() {
            return location;
        }

        public void setTags(Optional<UniqueTagList> tags) {
            assert tags != null;
            this.tags = tags;
        }

        public Optional<UniqueTagList> getTags() {
            return tags;
        }
    }
    /**
     * Stores the details to edit the activity with. Each non-empty field value will replace the
     * corresponding field value of the activity.
     */
    public static class EditActivityDescriptor {
        private Optional<Description> description = Optional.empty();
        private Optional<Priority> priority = Optional.empty();
        private Optional<Location> location = Optional.empty();
        private Optional<UniqueTagList> tags = Optional.empty();

        public EditActivityDescriptor() {}

        public EditActivityDescriptor(EditActivityDescriptor toCopy) {
            this.description = toCopy.getDescription();
            this.priority = toCopy.getPriority();
            this.location = toCopy.getLocation();
            this.tags = toCopy.getTags();
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyPresent(this.description, this.priority, this.location, this.tags);
        }

        public void setDescription(Optional<Description> description) {
            assert description != null;
            this.description = description;
        }

        public Optional<Description> getDescription() {
            return description;
        }

        public void setPriority(Optional<Priority> priority) {
            assert priority != null;
            this.priority = priority;
        }

        public Optional<Priority> getPriority() {
            return priority;
        }

        public void setLocation(Optional<Location> location) {
            assert location != null;
            this.location = location;
        }

        public Optional<Location> getLocation() {
            return location;
        }

        public void setTags(Optional<UniqueTagList> tags) {
            assert tags != null;
            this.tags = tags;
        }

        public Optional<UniqueTagList> getTags() {
            return tags;
        }
    }
}
