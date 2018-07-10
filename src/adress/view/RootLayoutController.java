package adress.view;

import javafx.fxml.FXML;
import javafx.geometry.Rectangle2D;
import javafx.scene.layout.BorderPane;
import javafx.stage.Screen;

public class RootLayoutController {

	@FXML
	private BorderPane pane;
	
	@FXML
	public void initialize() {
		Rectangle2D visualBounds = Screen.getPrimary().getVisualBounds();
		double size = 0.9*Math.min(visualBounds.getHeight(), visualBounds.getWidth());
        pane.setPrefHeight(size);
        pane.setPrefWidth(size);
        pane.setMaxHeight(size);
        pane.setMaxWidth(size);
	}

}
