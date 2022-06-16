package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class SceneAjouterProduitAdmin extends Application {
	static AnchorPane root = new AnchorPane();
	static Scene scene = new Scene(root);
	static Stage principaleWindow = new Stage();

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch();

	}

	@Override
	public void start(Stage window) throws Exception {
		// TODO Auto-generated method stub
		AnchorPane root = FXMLLoader.load(getClass().getResource("AjouterProduitAdmin.fxml"));
		scene.setRoot(root);
		window = principaleWindow;
		window.setScene(scene);
		window.setTitle("Ajouter un Produit");
		window.show();
	}
	
	public void close(Stage window) {
		window.close();
	}

}
