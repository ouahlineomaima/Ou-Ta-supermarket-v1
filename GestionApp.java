package application;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;


public class GestionApp extends Application {
	static AnchorPane root = new AnchorPane();
	static Scene scene = new Scene(root);
	static Stage principaleWindow = new Stage();
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);

		
	}
	
	@Override
	public void start(Stage window) throws Exception {
		// TODO Auto-generated method stub
		AnchorPane root = FXMLLoader.load(getClass().getResource("Login2.fxml"));
		scene.setRoot(root);
		window = principaleWindow;
		window.setScene(scene);
		window.setTitle("Ou&Ta Supermarket");
		window.show();
		
	}
	
}
