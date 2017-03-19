package seedu.address.model.person;

import seedu.address.model.tag.UniqueTagList;

public abstract class ToDo implements ReadOnlyToDo {

    public abstract void setDescription(Description description);
    public abstract Description getDescription();
    public abstract void setLocation(Location location);
    public abstract Location getLocation();
    public abstract UniqueTagList getTags();

    public abstract void setTags(UniqueTagList replacement);
    public abstract void resetData(ReadOnlyToDo editedToDo);
}
