package application;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.Iterator;
import java.util.ResourceBundle;
import java.util.Set;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class ExporterCommandeController implements Initializable {
	@FXML
	private TextField pathtxtfield;
	@FXML
	private Button btnvalider;
	@FXML
	private Button btndefault;
	
	
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		pathtxtfield.setText("");
	}

	// Event Listener on Button[#btnvalider].onAction
	@FXML
	public void validationHandler(ActionEvent event) {
		String path = pathtxtfield.getText();
		if (path.equals("")) {
			Alert error = new Alert(Alert.AlertType.ERROR);
			error.setTitle("Opération échouée");
			error.setContentText("Aucun chemin inséré. Veuillez inséré un chemin.");
			error.setHeaderText(null);
			error.show();
		}
		else {
			try {
				FileWriter writer = new FileWriter(path+"\\Commande"+PanierController.cd.getIdCommande()+".txt");
				Set<Produit> productSet = PanierController.cd.getMap().keySet();
				Iterator<Produit> it = productSet.iterator();
				String ligne = "Commande numéro"+PanierController.cd.getIdCommande()+":";
				String ligne2 = "\n";
				writer.write(ligne);
				writer.write(ligne2);
				writer.write(ligne2);
				int i =1;
				while(it.hasNext()) {
					Produit produit = it.next();
					String ligne1 = "Produit numéro "+i+":  " +"Nom produit: "+produit.getNom() +"  Service: "+produit.getService()+"  Quantité: " +PanierController.cd.getMap().get(produit)+"  Prix unitaire: " +produit.getPrix()+ "  Pirx total: "+String.valueOf(produit.getPrix()*PanierController.cd.getMap().get(produit));
					writer.write(ligne1);
					writer.write(ligne2);
					i++;
				}
				writer.close();
				Alert information = new Alert(Alert.AlertType.INFORMATION);
				information.setTitle("Opération réussie");
				information.setContentText("La commande a été exportée avec succès");
				information.setHeaderText(null);
				information.show();
				initialize(null, null);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				Alert error = new Alert(Alert.AlertType.ERROR);
				error.setTitle("Opération échouée");
				error.setContentText("Chemin invalide.");
				error.setHeaderText(null);
				error.show();
			}
		}
		
	}
	
	// Event Listener on Button[#btndefault].onAction
	@FXML
	public void defaultHandler(ActionEvent event) {
		try {
			FileWriter writer = new FileWriter("Commande"+PanierController.cd.getIdCommande()+".txt");
			Set<Produit> productSet = PanierController.cd.getMap().keySet();
			Iterator<Produit> it = productSet.iterator();
			String ligne = "Commande numéro"+PanierController.cd.getIdCommande()+":";
			String ligne2 = "\n";
			writer.write(ligne);
			writer.write(ligne2);
			writer.write(ligne2);
			int i =1;
			while(it.hasNext()) {
				Produit produit = it.next();
				String ligne1 = "Produit numéro "+i+": " +"  Nom produit: "+produit.getNom() +"  Service: "+produit.getService()+"  Quantité: " +PanierController.cd.getMap().get(produit)+"  Prix unitaire: " +produit.getPrix()+ "  Pirx total: "+String.valueOf(produit.getPrix()*PanierController.cd.getMap().get(produit));
				writer.write(ligne1);
				writer.write(ligne2);
				i++;
			}
			writer.close();
			Alert information = new Alert(Alert.AlertType.INFORMATION);
			information.setTitle("Opération réussie");
			information.setContentText("La commande a été exportée avec succès dans le dossier courant");
			information.setHeaderText(null);
			information.show();
			initialize(null, null);
		} catch (IOException e) {
			// TODO Auto-generated catch block	
		}
	}
}
