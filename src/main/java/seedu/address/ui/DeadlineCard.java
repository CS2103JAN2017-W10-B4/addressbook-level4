package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.person.ReadOnlyDeadline;

public class DeadlineCard extends UiPart<Region> {

    private static final String FXML = "DeadlineListCard.fxml";

    @FXML
    private HBox cardPane;
    @FXML
    private Label description;
    @FXML
    private Label id;
    @FXML
    private Label bytime;
    @FXML
    private Label bydate;
    @FXML
    private Label locations;
    @FXML
    private FlowPane tags;

    public DeadlineCard(ReadOnlyDeadline deadline, int displayedIndex) {
        super(FXML);
        description.setText(deadline.getDescription().description);
        id.setText(displayedIndex + ". ");
        bytime.setText(deadline.getEndTime().value);
        bydate.setText(deadline.getByDate().value);
        locations.setText(deadline.getLocation().value);
        initTags(deadline);
    }

    private void initTags(ReadOnlyDeadline deadline) {
    	deadline.getTags().forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
    }
}
