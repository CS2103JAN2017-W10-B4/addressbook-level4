package seedu.address.ui;

import java.util.logging.Logger;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.DeadlinePanelSelectionChangedEvent;
import seedu.address.commons.util.FxViewUtil;
import seedu.address.model.person.ReadOnlyDeadline;

/**
 * Panel containing the list of activities.
 */
public class DeadlineListPanel extends UiPart<Region> {
    private final Logger logger = LogsCenter.getLogger(DeadlineListPanel.class);
    private static final String FXML = "DeadlineListPanel.fxml";

    @FXML
    private ListView<ReadOnlyDeadline> deadlineListView;

    public DeadlineListPanel(AnchorPane deadlineListPlaceholder, ObservableList<ReadOnlyDeadline> deadlineList) {
        super(FXML);
        setConnections(deadlineList);
        addToPlaceholder(deadlineListPlaceholder);
    }

    private void setConnections(ObservableList<ReadOnlyDeadline> deadlineList) {
        deadlineListView.setItems(deadlineList);
        deadlineListView.setCellFactory(listView -> new DeadlineListViewCell());
        setEventHandlerForSelectionChangeEvent();
    }

    private void addToPlaceholder(AnchorPane placeHolderPane) {
        SplitPane.setResizableWithParent(placeHolderPane, false);
        FxViewUtil.applyAnchorBoundaryParameters(getRoot(), 0.0, 0.0, 0.0, 0.0);
        placeHolderPane.getChildren().add(getRoot());
    }

    private void setEventHandlerForSelectionChangeEvent() {
        deadlineListView.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        logger.fine("Selection in deadline list panel changed to : '" + newValue + "'");
                        raise(new DeadlinePanelSelectionChangedEvent(newValue));
                    }
                });
    }

    public void scrollTo(int index) {
        Platform.runLater(() -> {
            deadlineListView.scrollTo(index);
            deadlineListView.getSelectionModel().clearAndSelect(index);
        });
    }

    class DeadlineListViewCell extends ListCell<ReadOnlyDeadline> {

        @Override
        protected void updateItem(ReadOnlyDeadline deadline, boolean empty) {
            super.updateItem(deadline, empty);

            if (empty || deadline == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new DeadlineCard(deadline, getIndex() + 1).getRoot());
            }
        }
    }

}
