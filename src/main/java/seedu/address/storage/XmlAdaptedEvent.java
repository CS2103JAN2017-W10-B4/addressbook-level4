package seedu.address.storage;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Event;
import seedu.address.model.person.Description;
import seedu.address.model.person.EndDate;
import seedu.address.model.person.EndTime;
import seedu.address.model.person.Location;
import seedu.address.model.person.ReadOnlyEvent;
import seedu.address.model.person.StartDate;
import seedu.address.model.person.StartTime;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;

/**
 * JAXB-friendly version of the Event.
 */
public class XmlAdaptedEvent {

    @XmlElement(required = true)
    private String description;
    @XmlElement(required = true) 
    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    private LocalDate startdate;
    @XmlElement(required = true)
    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    private LocalDate enddate;
    @XmlElement(required = true)
    @XmlJavaTypeAdapter(LocalTimeAdapter.class)
    private LocalTime starttime;
    @XmlElement(required = true)
    @XmlJavaTypeAdapter(LocalTimeAdapter.class)
    private LocalTime endtime;
    @XmlElement(required = true)
    private String location;
    @XmlElement
    private List<XmlAdaptedTag> tagged = new ArrayList<>();

    /**
     * Constructs an XmlAdaptedEvent.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedEvent() {}


    /**
     * Converts a given Event into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedEvent
     */
    public XmlAdaptedEvent(ReadOnlyEvent source) {
        description = source.getDescription().toString();
        startdate = source.getStartDate().getValue();
        enddate = source.getEndDate().getValue();
        starttime = source.getStartTime().getValue();
        endtime = source.getEndTime().getValue();
        location = source.getLocation().value;
        tagged = new ArrayList<>();
        for (Tag tag : source.getTags()) {
            tagged.add(new XmlAdaptedTag(tag));
        }
    }

    /**
     * Converts this jaxb-friendly adapted person object into the model's Event object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person
     */
    public Event toModelType() throws IllegalValueException {
        final List<Tag> personTags = new ArrayList<>();
        for (XmlAdaptedTag tag : tagged) {
            personTags.add(tag.toModelType());
        }
        final Description description = new Description(this.description);
        final StartDate startdate = new StartDate(this.startdate);
        final EndDate enddate = new EndDate(this.startdate);
        final StartTime starttime = new StartTime(this.starttime);
        final EndTime endtime = new EndTime(this.endtime);
        final Location location = new Location(this.location);
        final UniqueTagList tags = new UniqueTagList(personTags);
        return new Event(description, startdate, enddate, starttime, endtime, location, tags);
    }
}