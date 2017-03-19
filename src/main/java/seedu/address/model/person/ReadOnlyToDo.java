package seedu.address.model.person;

import seedu.address.model.tag.UniqueTagList;

public interface ReadOnlyToDo {

    Description getDescription();
    Location getLocation();
    UniqueTagList getTags();
}
