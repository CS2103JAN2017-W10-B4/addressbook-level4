package seedu.address.model;

import java.util.Set;

import seedu.address.commons.core.UnmodifiableObservableList;
import seedu.address.model.person.ReadOnlyToDo;
import seedu.address.model.person.ToDo;
import seedu.address.model.person.UniqueActivityList.DuplicateActivityException;
import seedu.address.model.person.UniqueToDoList.DuplicateToDoException;
import seedu.address.model.person.UniqueToDoList.ToDoNotFoundException;

/**
 * The API of the Model component.
 */
public interface Model {
    /** Clears existing backing model and replaces with the provided new data. */
    void resetData(ReadOnlyWhatsLeft newData);

    /** Returns the WhatsLeft */
    ReadOnlyWhatsLeft getWhatsLeft();

    /** Deletes the given activity.
     * @throws ToDoNotFoundException */
    void deleteToDo(ReadOnlyToDo target) throws ToDoNotFoundException;

    /** Adds the given activity
     * @throws DuplicateToDoException */
    void addToDo(ToDo todo) throws DuplicateToDoException;

    /**
     * Updates the activity located at {@code filteredActivityListIndex} with {@code editedActivity}.
     *
     * @throws DuplicateActivityException if updating the activity's details causes the activity to be equivalent to
     *      another existing activity in the list.
     * @throws DuplicateToDoException
     * @throws IndexOutOfBoundsException if {@code filteredActivityListIndex} < 0 or >= the size of the filtered list.
     */
    void updateToDo(int filteredActivityListIndex, ReadOnlyToDo editedToDo)
            throws DuplicateToDoException;

    /** Returns the filtered activity list as an {@code UnmodifiableObservableList<ReadOnlyActivity>} */
    UnmodifiableObservableList<ReadOnlyToDo> getFilteredToDoList();

    /** Updates the filter of the filtered activity list to show all activities */
    void updateFilteredListToShowAll();

    /** Updates the filter of the filtered activity list to filter by the given keywords*/
    void updateFilteredToDoList(Set<String> keywords);

}
