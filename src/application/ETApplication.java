package application;
	
import controller.ETController;
import gui.mainview.MainViewController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ETApplication extends Application {
	
	@Override
	public void start(Stage primaryStage) {
		try {
			ETController etController = new ETController();
			etController.initDiary();
			
			MainViewController mvc = new MainViewController(etController);
			mvc.setMinWidth(1280);
			mvc.setMinHeight(800);
			
			Scene scene = new Scene(mvc);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			
			primaryStage.setMinWidth(1280);
			primaryStage.setMinHeight(800);
			
			primaryStage.setScene(scene);
			primaryStage.setTitle("E.T.-Das clevere Ernährungstagebuch");
			primaryStage.show();
			//testILVC(primaryStage,et);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
}