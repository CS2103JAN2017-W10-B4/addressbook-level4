package seedu.address.model.person;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.commons.exceptions.IllegalValueException;


/**
 * Represents an Event's ToDate in WhatsLeft.
 * Guarantees: immutable; is valid as declared in {@link #isValidToDate(String)}
 */
public class ToDate {

    public static final String MESSAGE_TODATE_CONSTRAINTS =
            "Event ToDate can take only 6 digits, and it should be in DDMMYY format (Day-Month-Year)";

    /*
     * must be in digits only
     *
     */
    public static final String TODATE_VALIDATION_REGEX = "([0123][\\d])([01][\\d])([\\d][\\d])";

    public final String value;

    /**
     * Validates given ToDate.
     *
     * @throws IllegalValueException if given ToDate string is invalid.
     */
    public ToDate(String todate) throws IllegalValueException {
        assert todate != null;
        if (!isValidToDate(todate)) {
            throw new IllegalValueException(MESSAGE_TODATE_CONSTRAINTS);
        }
        this.value = todate;
    }

    /**
     * Returns true if a given string is a valid event ToDate.
     */
    public static boolean isValidToDate(String test) {
        Pattern pattern = Pattern.compile(TODATE_VALIDATION_REGEX);
        Matcher matcher = pattern.matcher(test);
        boolean day = Integer.parseInt(matcher.group(1)) < 32;
        boolean month = Integer.parseInt(matcher.group(2)) < 13;
        return (test.matches(TODATE_VALIDATION_REGEX) && day && month);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ToDate // instanceof handles nulls
                && this.value.equals(((ToDate) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}