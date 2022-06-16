package application;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

import javafx.scene.control.TableView;

import javafx.scene.control.TableColumn;

public class TableServiceController implements Initializable{
	@FXML
	private TableView<Service> tableservice = new TableView<Service>();
	@FXML
	private TableColumn<Service, String> clidservice = new TableColumn<Service, String>();
	@FXML
	private TableColumn<Service, String> clnom = new TableColumn<Service, String>();
	@FXML
	private TableColumn<Service, Gestionnaire> clnomgestionnaire = new TableColumn<Service, Gestionnaire>();
	@FXML
	private Button btnservices;
	@FXML
	private Button btngestionnaire;
	@FXML
	private Button btnajouterservice;
	@FXML
	private Button btnsupprimerservice;
	@FXML
	private Button btnconsulteradmin;
	@FXML
    private Button btndisconnection;
	@FXML
	private Button btnactualiser;
	
	
	static SceneAjouterService s = new SceneAjouterService();
	static Stage window = new Stage();
	
	static ObservableList<Service> items = Data.getService();
	static Admin admin = (Admin) Data.rechercherGestionnaire("0000");
	static Service service = new Service();
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		items = Data.getService();
		clidservice.setCellValueFactory(new PropertyValueFactory<Service, String>("idService"));
		clnom.setCellValueFactory(new PropertyValueFactory<Service, String>("nom"));
		clnomgestionnaire.setCellValueFactory(new PropertyValueFactory<Service, Gestionnaire>("gestionnaire"));
		tableservice.setItems(items);
	}

	// Event Listener on Button[#btnservices].onAction
	@FXML
	public void affichergestionnairbutton(ActionEvent event) throws IOException {
		AnchorPane root = FXMLLoader.load(getClass().getResource("TableGestionnaire.fxml"));
		GestionApp.scene.setRoot(root);
		GestionApp.principaleWindow.setWidth(720);
		
	}
	
	// Event Listener on Button[#btnajouterservice].onAction
	@FXML
	public void ajouterserviceHandler(ActionEvent event) {
		try {
			s.start(window);
		} catch (Exception e) {
		}
		
	}
	
	// Event Listener on Button[#btnsupprimerservice].onAction
	@FXML
	public void supprimerservicebutton(ActionEvent event) {
		Service service = tableservice.getSelectionModel().getSelectedItem();
		if(service != null) {
			if(service.getIdService().equals("0")) {
				Alert error = new Alert(Alert.AlertType.ERROR);
				error.setTitle("Op�ration �chou�e");
				error.setContentText("Impossible de supprimer le service par d�faut.");
				error.setHeaderText(null);
				error.show();
			}
			else {
				Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
				confirm.setTitle("Confirmation");
				confirm.setHeaderText(null);
				confirm.setContentText("�tes vous sur de vouloir supprimer ce service ?");
				confirm.getButtonTypes().removeAll(ButtonType.OK, ButtonType.CANCEL);
				ButtonType btnOui = new ButtonType("Oui");
				ButtonType btnNon = new ButtonType("Annuler");
				confirm.getButtonTypes().addAll(btnOui, btnNon);
				Optional<ButtonType> result = confirm.showAndWait();
				if(result.get() == btnOui) {
					int test =admin.supprimerService(service);
					if(test == 1) {
						items.remove(service);
						initialize(null, null);
						Alert information = new Alert(Alert.AlertType.INFORMATION);
						information.setTitle("Op�ration r�ussie");
						information.setContentText("Le service a �t� supprim� avec succ�s");
						information.setHeaderText(null);
						information.show();
					}
					else {
						Alert error = new Alert(Alert.AlertType.ERROR);
						error.setTitle("Op�ration �chou�e");
						error.setContentText("Probl�me de connexion.");
						error.setHeaderText(null);
						error.show();
					}
					
				}
			}
		}
		else {
			Alert error = new Alert(Alert.AlertType.ERROR);
			error.setTitle("Op�ration �chou�e");
			error.setContentText("Aucun service s�lectionn�. Veuillez s�lectionner un service.");
			error.setHeaderText(null);
			error.show();
		}
		
	}
	
	// Event Listener on Button[#btnconsulteradmin].onAction
	@FXML
	public Service consulteradminbutton(ActionEvent event) throws IOException {
		// TODO Autogenerated
		service = tableservice.getSelectionModel().getSelectedItem();
		if(service != null) {
			AnchorPane root = FXMLLoader.load(getClass().getResource("ProduitAdmin.fxml"));
			GestionApp.scene.setRoot(root);
			GestionApp.principaleWindow.setWidth(720);
			return service;
		}
		else {
			Alert error = new Alert(Alert.AlertType.ERROR);
			error.setTitle("Op�ration �chou�e");
			error.setContentText("Aucun service s�lectionn�. Veuillez s�lectionner un service.");
			error.setHeaderText(null);
			error.show();
			return null;
		}
		
	}
	
	// Event Listener on Button[#btndisconnection].onAction
	@FXML
    private void disconnectionHandler(ActionEvent event) {
    	Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
    	confirm.setTitle("Confirmer la d�connexion");
    	confirm.setContentText("�tes vous s�r de vouloir se d�connecter?");
    	confirm.setHeaderText(null);
    	confirm.getButtonTypes().removeAll(ButtonType.OK, ButtonType.CANCEL);
		ButtonType btnOui = new ButtonType("Oui");
		ButtonType btnNon = new ButtonType("Annuler");
		confirm.getButtonTypes().addAll(btnOui, btnNon);
		Optional<ButtonType> result = confirm.showAndWait();
		if(result.get()==btnOui) {
			AnchorPane root = null;
			try {
				root = FXMLLoader.load(getClass().getResource("Login2.fxml"));
				GestionApp.scene.setRoot(root);
				GestionApp.principaleWindow.setWidth(624);
				GestionApp.principaleWindow.setTitle("Ou&Ta Supermarket");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
    }
	
	// Event Listener on Button[#btnactualiser].onAction
		@FXML
		public void actualiserHandler(ActionEvent event) {
			items.clear();
			tableservice.setItems(items);
			initialize(null, null);
			items.clear();
			tableservice.setItems(items);
			initialize(null, null);
		}
	
	public static Admin connectedAdmin() {
		return admin;
	}
	
	public Service getSelectedService() {
		return tableservice.getSelectionModel().getSelectedItem();
	}
	
}
