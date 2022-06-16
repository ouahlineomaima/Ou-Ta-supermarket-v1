package application;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;

import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;

public class AjouterGestionnaireController implements Initializable{
	@FXML
	private TextField istxtfield = new TextField();
	@FXML
	private TextField nametxtfield = new TextField();
	@FXML
	private TextField teletxtfield = new TextField();
	@FXML
	private TextField adresstxtfield = new TextField();
	@FXML
	private Button btnvalider;
	@FXML
	private Button btnretour;
	
	
	Admin admin = TableGestionnaireController.admin;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		istxtfield.setText("");
		nametxtfield.setText("");
		teletxtfield.setText("");
		adresstxtfield.setText("");
	}

	// Event Listener on Button[#btnvalider].onAction
	@FXML
	public void validationHandler(ActionEvent event) {
		if(istxtfield.getText() != null) {
			Gestionnaire gestionnaire = new Gestionnaire(istxtfield.getText(), nametxtfield.getText(), teletxtfield.getText(), adresstxtfield.getText());
			int test = admin.ajouterGestionnaire(gestionnaire);
			if (test == 1) {
				TableGestionnaireController.items.add(gestionnaire);
				Alert information = new Alert(Alert.AlertType.INFORMATION);
				information.setTitle("Opération réussie");
				information.setContentText("Le gestionnaire a été ajouté avec succès");
				information.setHeaderText(null);
				information.show();
				TableGestionnaireController t = new TableGestionnaireController();
				t.initialize(null, null);
			}
			else {
				if(test == -1) {
					Alert error = new Alert(Alert.AlertType.ERROR);
					error.setTitle("Opération échouée");
					error.setContentText("Le gestionnaire correspondant à l'id saisi figure déjà dans la base de données");
					error.setHeaderText(null);
					error.show();
				}
				else {
					Alert error = new Alert(Alert.AlertType.ERROR);
					error.setTitle("Opération échouée");
					error.setContentText("Erreur de la connexion avec la base de données. Veuillez réessayer plus tard.");
					error.setHeaderText(null);
					error.show();
				}
			}
		}
		else {
			Alert error = new Alert(Alert.AlertType.ERROR);
			error.setTitle("Opération échouée");
			error.setContentText("Veuillez saisir un id pour le gestionnaire.");
			error.setHeaderText(null);
			error.show();
		}
	}
	
	// Event Listener on Button[#btnretour].onAction
	@FXML
	public void returnHandler(ActionEvent event) {
		TableGestionnaireController.s.close(SceneAjouterGestionnaire.principaleWindow);
	}
	
}
