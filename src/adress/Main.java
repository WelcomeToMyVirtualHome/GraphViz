package adress;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Main extends Application{
	private Stage primaryStage;
	private BorderPane rootLayout;
	private FXMLLoader layoutLoader;
	private FXMLLoader rootLoader;	

	private AnchorPane layout;

	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("Graph Visualization");
		initRootLayout();
		showLayout();
		
	}

	public void initRootLayout() {
		try {
			rootLoader = new FXMLLoader();
			rootLoader.setLocation(Main.class.getResource("view/RootLayout.fxml"));
			rootLayout = (BorderPane) rootLoader.load();
			Scene scene = new Scene(rootLayout);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void showLayout() {
		try {
			layoutLoader = new FXMLLoader();
			layoutLoader.setLocation(Main.class.getResource("view/Layout.fxml"));
			layout = (AnchorPane) layoutLoader.load();
			rootLayout.setCenter(layout);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Stage getPrimaryStage() {
		return primaryStage;
	}

	public static void main(String[] args) {
		launch(args);
	}
	public AnchorPane getLayout() {
		return layout;
	}
}