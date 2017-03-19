package seedu.address.model;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import javafx.collections.ObservableList;
import seedu.address.commons.core.UnmodifiableObservableList;
import seedu.address.model.person.Activity;
import seedu.address.model.person.Deadline;
import seedu.address.model.person.Event;
import seedu.address.model.person.ReadOnlyActivity;
import seedu.address.model.person.ReadOnlyDeadline;
import seedu.address.model.person.ReadOnlyEvent;
import seedu.address.model.person.ReadOnlyToDo;
import seedu.address.model.person.ToDo;
import seedu.address.model.person.UniqueToDoList;
import seedu.address.model.person.UniqueToDoList.DuplicateToDoException;
import seedu.address.model.person.UniqueToDoList.ToDoNotFoundException;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;

/**
 * Wraps all data at the activity-book level
 * Duplicates are not allowed (by .equals comparison)
 */
public class WhatsLeft implements ReadOnlyWhatsLeft {

    private final UniqueToDoList todo;
    private final UniqueTagList tags;

    /*
     * The 'unusual' code block below is an non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        todo = new UniqueToDoList();
        tags = new UniqueTagList();
    }

    public WhatsLeft() {}

    /**
     * Creates an WhatsLeft using the Activities and Tags in the {@code toBeCopied}
     */
    public WhatsLeft(ReadOnlyWhatsLeft toBeCopied) {
        this();
        resetData(toBeCopied);
    }

//// list overwrite operations

    public void setToDo(List<? extends ReadOnlyToDo> todo)
            throws DuplicateToDoException {
        this.todo.setToDo(todo);
    }

    public void setTags(Collection<Tag> tags) throws UniqueTagList.DuplicateTagException {
        this.tags.setTags(tags);
    }

    public void resetData(ReadOnlyWhatsLeft newData) {
        assert newData != null;
        try {
            setToDo(newData.getToDoList());
        } catch (UniqueToDoList.DuplicateToDoException e) {
            assert false : "WhatsLeft should not have duplicate todo";
        }
        try {
            setTags(newData.getTagList());
        } catch (UniqueTagList.DuplicateTagException e) {
            assert false : "WhatsLeft should not have duplicate tags";
        }
        syncMasterTagListWith(todo);
    }

//// activity-level operations

    /**
     * Adds a activity to WhatsLeft.
     * Also checks the new activity's tags and updates {@link #tags} with any new tags found,
     * and updates the Tag objects in the activity to point to those in {@link #tags}.
     *
     * @throws UniqueActivityList.DuplicateActivityException if an equivalent activity already exists.
     * @throws DuplicateToDoException
     */
    public void addToDo(ToDo a) throws DuplicateToDoException {
        syncMasterTagListWith(a);
        todo.add(a);
    }

    /**
     * Updates the activity in the list at position {@code index} with {@code editedReadOnlyActivity}.
     * {@code WhatsLeft}'s tag list will be updated with the tags of {@code editedReadOnlyActivity}.
     * @see #syncMasterTagListWith(ToDo)
     *
     * @throws DuplicateToDoException if updating the activity's details causes the activity to be equivalent to
     *      another existing todo in the list.
     * @throws DuplicateToDoException
     * @throws IndexOutOfBoundsException if {@code index} < 0 or >= the size of the list.
     */
    public void updateToDo(int index, ReadOnlyToDo editedReadOnlyToDo)
            throws UniqueToDoList.DuplicateToDoException {
        assert editedReadOnlyToDo != null;
        ToDo editedToDo = null;
        if (editedReadOnlyToDo instanceof ReadOnlyActivity) {
            editedToDo = new Activity((ReadOnlyActivity) editedReadOnlyToDo);
        } else if (editedReadOnlyToDo instanceof ReadOnlyEvent) {
            editedToDo = new Event((ReadOnlyEvent) editedReadOnlyToDo);
        } else if (editedReadOnlyToDo instanceof ReadOnlyDeadline) {
            editedToDo = new Deadline((ReadOnlyDeadline) editedReadOnlyToDo);
        }
        syncMasterTagListWith(editedToDo);
        // TODO: the tags master list will be updated even though the below line fails.
        // This can cause the tags master list to have additional tags that are not tagged to any activity
        // in the activity list.
        todo.updateToDo(index, editedToDo);
    }

    /**
     * Ensures that every tag in this activity:
     *  - exists in the master list {@link #tags}
     *  - points to a Tag object in the master list
     */
    private void syncMasterTagListWith(ToDo a) {
        final UniqueTagList activityTags = a.getTags();
        tags.mergeFrom(activityTags);

        // Create map with values = tag object references in the master list
        // used for checking activity tag references
        final Map<Tag, Tag> masterTagObjects = new HashMap<>();
        tags.forEach(tag -> masterTagObjects.put(tag, tag));

        // Rebuild the list of activity tags to point to the relevant tags in the master tag list.
        final Set<Tag> correctTagReferences = new HashSet<>();
        activityTags.forEach(tag -> correctTagReferences.add(masterTagObjects.get(tag)));
        a.setTags(new UniqueTagList(correctTagReferences));
    }

    /**
     * Ensures that every tag in these activities:
     *  - exists in the master list {@link #tags}
     *  - points to a Tag object in the master list
     *  @see #syncMasterTagListWith(Activity)
     */
    private void syncMasterTagListWith(UniqueToDoList todo) {
        todo.forEach(this::syncMasterTagListWith);
    }

    public boolean removeToDo(ReadOnlyToDo key) throws ToDoNotFoundException {
        if (todo.remove(key)) {
            return true;
        } else {
            throw new UniqueToDoList.ToDoNotFoundException();
        }
    }

//// tag-level operations

    public void addTag(Tag t) throws UniqueTagList.DuplicateTagException {
        tags.add(t);
    }

//// util methods

    @Override
    public String toString() {
        return todo.asObservableList().size() + " todo, " + tags.asObservableList().size() +  " tags";
        // TODO: refine later
    }

    @Override
    public ObservableList<ReadOnlyToDo> getToDoList() {
        return new UnmodifiableObservableList<>(todo.asObservableList());
    }

    @Override
    public ObservableList<Tag> getTagList() {
        return new UnmodifiableObservableList<>(tags.asObservableList());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof WhatsLeft // instanceof handles nulls
                && this.todo.equals(((WhatsLeft) other).todo)
                && this.tags.equalsOrderInsensitive(((WhatsLeft) other).tags));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(todo, tags);
    }
}
