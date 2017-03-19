package seedu.address.model.person;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.core.UnmodifiableObservableList;
import seedu.address.commons.exceptions.DuplicateDataException;

public class UniqueToDoList implements Iterable<ToDo> {

    private final ObservableList<ToDo> internalList = FXCollections.observableArrayList();

    public boolean contains(ReadOnlyToDo toCheck) {
        assert toCheck != null;
        return internalList.contains(toCheck);
    }

    public void add(ToDo toAdd) throws DuplicateToDoException {
        assert toAdd != null;
        if (contains(toAdd)) {
            throw new DuplicateToDoException();
        }
        internalList.add(toAdd);
    }

    /**
     * Updates the ToDo in the list at position {@code index} with {@code editedToDo}.
     *
     * @throws DuplicateToDoException if updating the activity's details causes the ToDo to be equivalent to
     *      another existing activity in the list.
     * @throws IndexOutOfBoundsException if {@code index} < 0 or >= the size of the list.
     */
    public void updateToDo(int index, ReadOnlyToDo editedToDo) throws DuplicateToDoException {
        assert editedToDo != null;

        ToDo ToDoToUpdate = internalList.get(index);
        if (!ToDoToUpdate.equals(editedToDo) && internalList.contains(editedToDo)) {
            throw new DuplicateToDoException();
        }

        ToDoToUpdate.resetData(editedToDo);
        // TODO: The code below is just a workaround to notify observers of the updated activity.
        // The right way is to implement observable properties in the Activity class.
        // Then, ActivityCard should then bind its text labels to those observable properties.
        internalList.set(index, ToDoToUpdate);
    }

    public boolean remove(ReadOnlyToDo toRemove) throws ToDoNotFoundException {
        assert toRemove != null;

        final boolean ToDoFoundAndDeleted = internalList.remove(toRemove);
        if (!ToDoFoundAndDeleted) {
            throw new ToDoNotFoundException();
        }
        return ToDoFoundAndDeleted;
    }

    public void setToDo(UniqueToDoList replacement) {
        this.internalList.setAll(replacement.internalList);
    }

    public void setToDo(List<? extends ReadOnlyToDo> todo) throws DuplicateToDoException {
        final UniqueToDoList replacement = new UniqueToDoList();
        for (final ReadOnlyToDo each : todo) {
            if (each instanceof ReadOnlyActivity) {
                replacement.add(new Activity((ReadOnlyActivity) each));
            } else if (each instanceof ReadOnlyEvent) {
                replacement.add(new Event((ReadOnlyEvent) each));
            } else if (each instanceof ReadOnlyDeadline) {
                replacement.add(new Deadline((ReadOnlyDeadline) each));
            }
        }
        setToDo(replacement);
    }

    public UnmodifiableObservableList<ToDo> asObservableList() {
        return new UnmodifiableObservableList<>(internalList);
    }

    @Override
    public Iterator<ToDo> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueToDoList // instanceof handles nulls
                && this.internalList.equals(
                ((UniqueToDoList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    public static class ToDoNotFoundException extends Exception{}

    /**
     * Signals that an operation would have violated the 'no duplicates' property of the list.
     */
    public static class DuplicateToDoException extends DuplicateDataException {
        protected DuplicateToDoException() {
            super("Operation would result in duplicate ToDo");
        }
    }

}
