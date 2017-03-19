package seedu.address.model.person;

import java.util.Objects;

import seedu.address.commons.util.CollectionUtil;
import seedu.address.model.tag.UniqueTagList;

/**
 * Represents an Activity in WhatsLeft.
 * Guarantees: details are present and not null, field values are validated.
 */
public class Activity extends ToDo implements ReadOnlyActivity {

    private Description description;
    private Priority priority;
    private Location location;

    private UniqueTagList tags;

    /**
     * Every field must be present and not null.
     */
    public Activity(Description description, Priority priority, Location location, UniqueTagList tags) {
        assert !CollectionUtil.isAnyNull(description, tags);
        this.description = description;
        this.priority = priority;
        this.location = location;
        this.tags = new UniqueTagList(tags); // protect internal tags from changes in the arg list
    }

    /**
     * Creates a copy of the given ReadOnlyActivity.
     */
    public Activity(ReadOnlyActivity source) {
        this(source.getDescription(), source.getPriority(), source.getLocation(), source.getTags());
    }

    public void setDescription(Description description) {
        assert description != null;
        this.description = description;
    }

    @Override
    public Description getDescription() {
        return description;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    @Override
    public Priority getPriority() {
        return priority;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    @Override
    public Location getLocation() {
        return location;
    }

    @Override
    public UniqueTagList getTags() {
        return new UniqueTagList(tags);
    }

    /**
     * Replaces this activity's tags with the tags in the argument tag list.
     */
    public void setTags(UniqueTagList replacement) {
        tags.setTags(replacement);
    }

    /**
     * Updates this activity with the details of {@code replacement}.
     */
    public void resetData(ReadOnlyActivity replacement) {
        assert replacement != null;

        this.setDescription(replacement.getDescription());
        this.setPriority(replacement.getPriority());
        this.setLocation(replacement.getLocation());
        this.setTags(replacement.getTags());
    }

    @Override
    public void resetData(ReadOnlyToDo editedToDo) {
        if (editedToDo instanceof ReadOnlyActivity) {
            ReadOnlyActivity edited = (ReadOnlyActivity) editedToDo;
            this.setDescription(edited.getDescription());
            this.setPriority(edited.getPriority());
            this.setLocation(edited.getLocation());
            this.setTags(edited.getTags());
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ReadOnlyActivity // instanceof handles nulls
                && this.isSameStateAs((ReadOnlyActivity) other));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(description, priority, location, tags);
    }

    @Override
    public String toString() {
        return getAsText();
    }


}
