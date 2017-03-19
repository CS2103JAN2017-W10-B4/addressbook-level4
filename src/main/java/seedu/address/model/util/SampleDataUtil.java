package seedu.address.model.util;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.ReadOnlyWhatsLeft;
import seedu.address.model.WhatsLeft;
import seedu.address.model.person.Activity;
import seedu.address.model.person.Description;
import seedu.address.model.person.Location;
import seedu.address.model.person.Priority;
import seedu.address.model.person.UniqueToDoList.DuplicateToDoException;
import seedu.address.model.tag.UniqueTagList;

public class SampleDataUtil {
    public static Activity[] getSampleActivities() {
        try {
            return new Activity[] {
                new Activity(new Description("CS2010 Written Quiz 1"), new Priority("high"),
                    new Location("SR1"),
                    new UniqueTagList("friends")),
                new Activity(new Description("CS2103 Tutorial 6"), new Priority("medium"),
                    new Location("COM-1-B1"),
                    new UniqueTagList("colleagues", "friends")),
                new Activity(new Description("Buy fruits"), new Priority("low"),
                    new Location("FairPrice"),
                    new UniqueTagList("neighbours")),
                new Activity(new Description("Home Assignment 2"), new Priority("high"),
                    new Location("CLB"),
                    new UniqueTagList("family")),
                new Activity(new Description("CS2102 Consultation"), new Priority("medium"),
                    new Location("I-Cube"),
                    new UniqueTagList("classmates")),
                new Activity(new Description("IVLE Survey"), new Priority("low"),
                    new Location("anywhere"),
                    new UniqueTagList("colleagues"))
            };
        } catch (IllegalValueException e) {
            throw new AssertionError("sample data cannot be invalid", e);
        }
    }

    public static ReadOnlyWhatsLeft getSampleWhatsLeft() {
        try {
            WhatsLeft sampleAB = new WhatsLeft();
            for (Activity sampleActivity : getSampleActivities()) {
                sampleAB.addToDo(sampleActivity);
            }
            return sampleAB;
        } catch (DuplicateToDoException e) {
            throw new AssertionError("sample data cannot contain duplicate activities", e);
        }
    }
}
