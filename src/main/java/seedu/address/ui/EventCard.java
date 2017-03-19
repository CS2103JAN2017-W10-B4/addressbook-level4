package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.person.ReadOnlyEvent;

public class EventCard extends UiPart<Region> {

    private static final String FXML = "EventListCard.fxml";

    @FXML
    private HBox cardPane;
    @FXML
    private Label description;
    @FXML
    private Label id;
    @FXML
    private Label locations;
    @FXML
    private Label fromdate;
    @FXML
    private Label todate;
    @FXML
    private Label starttime;    
    @FXML
    private Label endtime;        
    @FXML
    private FlowPane tags;
    
 
    public EventCard(ReadOnlyEvent event, int displayedIndex) {
        super(FXML);
        description.setText(event.getDescription().description);
        id.setText(displayedIndex + ". ");
        locations.setText(event.getLocation().value);
        fromdate.setText(event.getFromDate().value);
        todate.setText(event.getToDate().value);
        starttime.setText(event.getStartTime().value);
        endtime.setText(event.getEndTime().value);
        initTags(event);
    }

    private void initTags(ReadOnlyEvent event) {
        event.getTags().forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
    }
}
