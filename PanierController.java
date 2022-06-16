package application;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.TableColumn;

public class PanierController implements Initializable {
	@FXML
	private TableView<Produit> tableproduitpanier = new TableView<Produit>();
	@FXML
	private TableColumn<Produit, String> clidproduit = new TableColumn<Produit, String>();
	@FXML
	private TableColumn<Produit, String> clnom = new TableColumn<Produit, String>();
	@FXML
	private TableColumn<Produit, Integer> clquantite = new TableColumn<Produit, Integer>();
	@FXML
	private TableColumn<Produit, Service> clservice = new TableColumn<Produit, Service>();
	@FXML 
	private TableColumn<Produit, Integer> clprix = new TableColumn<Produit, Integer>();
	@FXML
	private Button btnvaliderpanier;
	@FXML
	private Button btnretirerpanier;
	@FXML
	private Button btnretourpanier;
	@FXML
	private Button btnexporter;
	@FXML
    private Button btndisconnection;
	
	static Commande cd = new Commande();
	
	static ObservableList<Produit> items = ProduitAdmin2Controller.panieritems;
	ProduitAdmin2Controller pa = new ProduitAdmin2Controller();
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		clidproduit.setCellValueFactory(new PropertyValueFactory<Produit, String>("idProduit"));
		clnom.setCellValueFactory(new PropertyValueFactory<Produit, String>("nom"));
		clquantite.setCellValueFactory(new PropertyValueFactory<Produit, Integer>("quantite"));
		clservice.setCellValueFactory(new PropertyValueFactory<Produit, Service>("service"));
		clprix.setCellValueFactory(new PropertyValueFactory<Produit, Integer>("prix"));
		tableproduitpanier.setItems(items);	
	}

	// Event Listener on Button[#btnvaliderpanier].onAction
	@FXML
	public void validerpanierbutton(ActionEvent event) {
		// TODO Autogenerated
		Map<Produit, Integer>produitmap = new HashMap<Produit, Integer>();
		for (int i =0; i<items.size(); i++) {
			produitmap.put(items.get(i), items.get(i).getQuantite());
		}
		File f = new File("Commandestock.tmp");
		try {
			ObjectInputStream in = new ObjectInputStream(new FileInputStream(f));
			Commande cmd = (Commande) in.readObject();
			in.close();
			if(cmd != null) {
				int nb = Integer.parseInt(cmd.getIdCommande());
				cd = new Commande(String.valueOf(nb+1), (HashMap<Produit, Integer>) produitmap);
				for (int i =0; i<produitmap.size(); i++) {
					cd.achatProduit(items.get(i), produitmap.get(items.get(i)));
				}
				cd.sauveCommande();
				items.clear();
				Alert information = new Alert(Alert.AlertType.INFORMATION);
				information.setTitle("Op�ration r�ussie");
				information.setContentText("La commande a �t� effectu�e avec succ�s");
				information.setHeaderText(null);
				information.show();
				initialize(null, null);
				pa.initialize(null, null);
			}
			
		} 
		catch (IOException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			cd = new Commande(String.valueOf(Commande.nbCommande), (HashMap<Produit, Integer>) produitmap);
			for (int i =0; i<produitmap.size(); i++) {
				cd.achatProduit(items.get(i), produitmap.get(items.get(i)));
			}
			cd.sauveCommande();
			items.clear();
			Alert information = new Alert(Alert.AlertType.INFORMATION);
			information.setTitle("Op�ration r�ussie");
			information.setContentText("La commande a �t� effectu�e avec succ�s");
			information.setHeaderText(null);
			information.show();
			initialize(null, null);
			pa.initialize(null, null);		
		}
		
		
	}
	// Event Listener on Button[#btnretirerpanier].onAction
	@FXML
	public void retirerpanierbutton(ActionEvent event) {
		// TODO Autogenerated
		Produit p = tableproduitpanier.getSelectionModel().getSelectedItem();
		if (p != null) {
			items.remove(p);
			Alert information = new Alert(Alert.AlertType.INFORMATION);
			information.setTitle("Op�ration r�ussie");
			information.setContentText("Le produit a �t� retir� avec succ�s");
			information.setHeaderText(null);
			information.show();
			initialize(null, null);
		}
		else {
			Alert error = new Alert(Alert.AlertType.ERROR);
			error.setTitle("Op�ration �chou�e");
			error.setContentText("Aucun produit s�lectionn�. Veuillez s�lectionner un produit.");
			error.setHeaderText(null);
			error.show();
		}
	}
	
	// Event Listener on Button[#btnretourpanier].onAction
	@FXML
	public void retourpanierbutton(ActionEvent event) throws IOException {
		// TODO Autogenerated
		AnchorPane root = FXMLLoader.load(getClass().getResource("ProduitAdmin.fxml"));
		GestionApp.scene.setRoot(root);
	}
	
	// Event Listener on Button[#btnexporter].onAction
	@FXML
	public void exporterHandler(ActionEvent event) {
		if(cd.getIdCommande() != null) {
			try {
				AnchorPane root = FXMLLoader.load(getClass().getResource("ExporterCommande.fxml"));
				SceneExporterCommande.scene.setRoot(root);
				SceneExporterCommande s = new SceneExporterCommande();
				s.start(SceneExporterCommande.principaleWindow);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		else {
			Alert error = new Alert(Alert.AlertType.ERROR);
			error.setTitle("Op�ration �chou�e");
			error.setContentText("Commande non valid�e. Veuillez d'abord valider la commande.");
			error.setHeaderText(null);
			error.show();	
		}
		
	}
	
	//Event Listener on Button[#btndisconnecion].onAction
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
				}
				
			}
	    }
	
	
}
