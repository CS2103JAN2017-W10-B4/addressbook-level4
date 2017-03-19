package seedu.address.model;

import java.util.Set;
import java.util.logging.Logger;

import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.UnmodifiableObservableList;
import seedu.address.commons.events.model.WhatsLeftChangedEvent;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.commons.util.StringUtil;
import seedu.address.model.person.ReadOnlyToDo;
import seedu.address.model.person.ToDo;
import seedu.address.model.person.UniqueToDoList.DuplicateToDoException;
import seedu.address.model.person.UniqueToDoList.ToDoNotFoundException;

/**
 * Represents the in-memory model of the address book data.
 * All changes to any model should be synchronized.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final WhatsLeft whatsLeft;
    private final FilteredList<ReadOnlyToDo> filteredToDo;

    /**
     * Initializes a ModelManager with the given whatsLeft and userPrefs.
     */
    public ModelManager(ReadOnlyWhatsLeft whatsLeft, UserPrefs userPrefs) {
        super();
        assert !CollectionUtil.isAnyNull(whatsLeft, userPrefs);

        logger.fine("Initializing with WhatsLeft: " + whatsLeft + " and user prefs " + userPrefs);

        this.whatsLeft = new WhatsLeft(whatsLeft);
        filteredToDo = new FilteredList<>(this.whatsLeft.getToDoList());
    }

    public ModelManager() {
        this(new WhatsLeft(), new UserPrefs());
    }

    @Override
    public void resetData(ReadOnlyWhatsLeft newData) {
        whatsLeft.resetData(newData);
        indicateWhatsLeftChanged();
    }

    @Override
    public ReadOnlyWhatsLeft getWhatsLeft() {
        return whatsLeft;
    }

    /** Raises an event to indicate the model has changed */
    private void indicateWhatsLeftChanged() {
        raise(new WhatsLeftChangedEvent(whatsLeft));
    }

    @Override
    public synchronized void deleteToDo(ReadOnlyToDo target) throws ToDoNotFoundException {
        whatsLeft.removeToDo(target);
        indicateWhatsLeftChanged();
    }

    @Override
    public synchronized void addToDo(ToDo todo) throws DuplicateToDoException {
        whatsLeft.addToDo(todo);
        updateFilteredListToShowAll();
        indicateWhatsLeftChanged();
    }

    @Override
    public void updateToDo(int filteredToDoListIndex, ReadOnlyToDo editedToDo)
            throws DuplicateToDoException {
        assert editedToDo != null;

        int addressBookIndex = filteredToDo.getSourceIndex(filteredToDoListIndex);
        whatsLeft.updateToDo(addressBookIndex, editedToDo);
        indicateWhatsLeftChanged();
    }

    //=========== Filtered Activity List Accessors =============================================================

    @Override
    public UnmodifiableObservableList<ReadOnlyToDo> getFilteredToDoList() {
        return new UnmodifiableObservableList<>(filteredToDo);
    }

    @Override
    public void updateFilteredListToShowAll() {
        filteredToDo.setPredicate(null);
    }

    @Override
    public void updateFilteredToDoList(Set<String> keywords) {
        updateFilteredToDoList(new PredicateExpression(new NameQualifier(keywords)));
    }

    private void updateFilteredToDoList(Expression expression) {
        filteredToDo.setPredicate(expression::satisfies);
    }

    //========== Inner classes/interfaces used for filtering =================================================

    interface Expression {
        boolean satisfies(ReadOnlyToDo todo);
        String toString();
    }

    private class PredicateExpression implements Expression {

        private final Qualifier qualifier;

        PredicateExpression(Qualifier qualifier) {
            this.qualifier = qualifier;
        }

        @Override
        public boolean satisfies(ReadOnlyToDo todo) {
            return qualifier.run(todo);
        }

        @Override
        public String toString() {
            return qualifier.toString();
        }
    }

    interface Qualifier {
        boolean run(ReadOnlyToDo todo);
        String toString();
    }

    private class NameQualifier implements Qualifier {
        private Set<String> nameKeyWords;

        NameQualifier(Set<String> nameKeyWords) {
            this.nameKeyWords = nameKeyWords;
        }

        @Override
        public boolean run(ReadOnlyToDo todo) {
            return nameKeyWords.stream()
                    .filter(keyword -> StringUtil.containsWordIgnoreCase(todo.
                    getDescription().description, keyword))
                    .findAny()
                    .isPresent();
        }

        @Override
        public String toString() {
            return "name=" + String.join(", ", nameKeyWords);
        }
    }

}
