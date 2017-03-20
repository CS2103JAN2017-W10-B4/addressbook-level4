package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.person.ReadOnlyDeadline;

/**
 * Represents a selection change in the Person List Panel
 */
public class DeadlinePanelSelectionChangedEvent extends BaseEvent {


    private final ReadOnlyDeadline newSelection;

    public DeadlinePanelSelectionChangedEvent(ReadOnlyDeadline newSelection) {
        this.newSelection = newSelection;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public ReadOnlyDeadline getNewSelection() {
        return newSelection;
    }
}
