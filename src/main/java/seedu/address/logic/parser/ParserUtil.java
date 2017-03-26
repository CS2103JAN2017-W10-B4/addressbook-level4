package seedu.address.logic.parser;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.StringUtil;
import seedu.address.model.person.ByDate;
import seedu.address.model.person.ByTime;
import seedu.address.model.person.Description;
import seedu.address.model.person.EndTime;
import seedu.address.model.person.StartDate;
import seedu.address.model.person.Location;
import seedu.address.model.person.Priority;
import seedu.address.model.person.StartTime;
import seedu.address.model.person.EndDate;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes
 */
public class ParserUtil {

    private static final Pattern INDEX_ARGS_FORMAT = Pattern.compile("(?<targetType>[et][vs])\\s(?<targetIndex>.+)");
    private static final Pattern INDEX_ALONE_ARG_FORMAT = Pattern.compile("(?<index>.+)");

    /**
     * Returns the specified index in the {@code command} if it is a positive unsigned integer
     * Returns an {@code Optional.empty()} otherwise.
     */
    public static Optional<Integer> parseIndex(String command) {
        final Matcher matcher = INDEX_ARGS_FORMAT.matcher(command.trim());
        if (!matcher.matches()) {
            return Optional.empty();
        }

        String index = matcher.group("targetIndex");
        if (!StringUtil.isUnsignedInteger(index)) {
            return Optional.empty();
        }
        return Optional.of(Integer.parseInt(index));

    }
    //@@author A0110491U
    public static Optional<Integer> parseIndexAlone(String command) {
        final Matcher matcher = INDEX_ALONE_ARG_FORMAT.matcher(command.trim());
        if (!matcher.matches()) {
            return Optional.empty();
        }

        String index = matcher.group("index");
        if (!StringUtil.isUnsignedInteger(index)) {
            return Optional.empty();
        }
        return Optional.of(Integer.parseInt(index));

    }

    public static Optional<String> parseType(String command) {
        final Matcher matcher = INDEX_ARGS_FORMAT.matcher(command.trim());
        if (!matcher.matches()) {
            return Optional.empty();
        }

        String type = matcher.group("targetType");
        return Optional.of(type);
    }
    //@@author

    /**
     * Returns a new Set populated by all elements in the given list of strings
     * Returns an empty set if the given {@code Optional} is empty,
     * or if the list contained in the {@code Optional} is empty
     */
    public static Set<String> toSet(Optional<List<String>> list) {
        List<String> elements = list.orElse(Collections.emptyList());
        return new HashSet<>(elements);
    }

    /**
    * Splits a preamble string into ordered fields.
    * @return A list of size {@code numFields} where the ith element is the ith field value if specified in
    *         the input, {@code Optional.empty()} otherwise.
    */
    public static List<Optional<String>> splitPreamble(String preamble, int numFields) {
        return Arrays.stream(Arrays.copyOf(preamble.split("\\s+", numFields), numFields))
                .map(Optional::ofNullable)
                .collect(Collectors.toList());
    }

    /**
     * Parses a {@code Optional<String> description} into an {@code Optional<Description>}
     * if {@code description} is present.
     */
    public static Optional<Description> parseDescription(Optional<String> description) throws IllegalValueException {
        assert description != null;
        return description.isPresent() ? Optional.of(new Description(description.get())) : Optional.empty();
    }

    /**
     * Parses a {@code Optional<String> priority} into an {@code Optional<Priority>} if {@code priority} is present.
     */
    public static Optional<Priority> parsePriority(Optional<String> priority) throws IllegalValueException {
        assert priority != null;
        return priority.isPresent() ? Optional.of(new Priority(priority.get())) : Optional.empty();
    }
    
    /**
     * Parses a {@code Optional<String> startTime} into an {@code Optional<StartTime>} if {@code startTime} is present.
     */
    public static Optional<StartTime> parseStartTime(Optional<String> startTime) throws IllegalValueException {
        assert startTime != null;
        return startTime.isPresent() ? Optional.of(new StartTime(startTime.get())) : Optional.empty();
    }
    
    /**
     * Parses a {@code Optional<String> startDate} into an {@code Optional<StartDate>} if {@code startDate} is present.
     */
    public static Optional<StartDate> parseStartDate(Optional<String> startDate) throws IllegalValueException {
        assert startDate != null;
        return startDate.isPresent() ? Optional.of(new StartDate(startDate.get())) : Optional.empty();
    }
    
    /**
     * Parses a {@code Optional<String> startTime} into an {@code Optional<StartTime>} if {@code startTime} is present.
     */
    public static Optional<EndTime> parseEndTime(Optional<String> endTime) throws IllegalValueException {
        assert endTime != null;
        return endTime.isPresent() ? Optional.of(new EndTime(endTime.get())) : Optional.empty();
    }

    /**
     * Parses a {@code Optional<String> endDate} into an {@code Optional<EndDate>} if {@code endDate} is present.
     */
    public static Optional<EndDate> parseEndDate(Optional<String> endDate) throws IllegalValueException {
        assert endDate != null;
        return endDate.isPresent() ? Optional.of(new EndDate(endDate.get())) : Optional.empty();
    }

    /**
     * Parses a {@code Optional<String> byTime} into an {@code Optional<ByTime>} if {@code email} is present.
     */
    public static Optional<ByTime> parseByTime(Optional<String> byTime) throws IllegalValueException {
        assert byTime != null;
        return byTime.isPresent() ? Optional.of(new ByTime(byTime.get())) : Optional.empty();
    }
    
    /**
     * Parses a {@code Optional<String> byDate} into an {@code Optional<byDate>} if {@code ByDate} is present.
     */
    public static Optional<ByDate> parseByDate(Optional<String> byDate) throws IllegalValueException {
        assert byDate != null;
        return byDate.isPresent() ? Optional.of(new ByDate(byDate.get())) : Optional.empty();
    }
    
    /**
     * Parses a {@code Optional<String> location} into an {@code Optional<Location>} if {@code location} is present.
     */
    public static Optional<Location> parseLocation(Optional<String> location) throws IllegalValueException {
        assert location != null;
        return location.isPresent() ? Optional.of(new Location(location.get())) : Optional.empty();
    }

    /**
     * Parses {@code Collection<String> tags} into an {@code UniqueTagList}.
     */
    public static UniqueTagList parseTags(Collection<String> tags) throws IllegalValueException {
        assert tags != null;
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(new Tag(tagName));
        }
        return new UniqueTagList(tagSet);
    }
    
    //@@author A0121668A
    public static String parseStatus(String command) {
        String trimmedCommand = command.trim();
        return trimmedCommand;
    }
}
