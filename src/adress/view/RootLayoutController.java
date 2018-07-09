package adress.view;

import adress.model.GraphVisual;
import javafx.fxml.FXML;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Slider;
import javafx.scene.layout.BorderPane;
import javafx.stage.Screen;

public class RootLayoutController {
	
	@FXML
	private Slider c1;
	@FXML
	private Slider c2;
	@FXML
	private Slider c3;
	@FXML
	private Slider c4;
	@FXML
	private Slider c5;
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
		initSliders();
	}

	public void initSliders() {
		c1.setValue(0.005);
		c2.setValue(0.002);
		c3.setValue(35);
		c4.setValue(0.005);
		c5.setValue(100);
		c1.valueProperty().addListener((observable, oldValue, newValue) -> {
			GraphVisual.setC1((double) newValue);
		});
		c2.valueProperty().addListener((observable, oldValue, newValue) -> {
			GraphVisual.setC2((double) newValue);
		});
		c3.valueProperty().addListener((observable, oldValue, newValue) -> {
			GraphVisual.setC3((double) newValue);
		});
		c4.valueProperty().addListener((observable, oldValue, newValue) -> {
			GraphVisual.setC4((double) newValue);
		});
		c5.valueProperty().addListener((observable, oldValue, newValue) -> {
			GraphVisual.setC5(newValue.intValue());
		});
	}

}
