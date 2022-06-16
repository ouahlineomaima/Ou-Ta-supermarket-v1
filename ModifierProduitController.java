package application;

import java.io.IOException;
import java.net.URL;

import java.util.ResourceBundle;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.fxml.Initializable;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;


public class ModifierProduitController implements Initializable {

    @FXML
    private TextField NewNameTextField;
    @FXML
    private TextField NewQuantityTextField;
    @FXML
    private TextField prixtxtfield;
    @FXML
    private Button ValidationButton;
    @FXML
    private Button ReturnButton;
    
    static Produit produit=ProduitController.getProduit();
    static Service service =ProduitController.getService();
    static ObservableList<Produit> items = Data.getProduit(service);
          
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
         NewNameTextField.setText("");
         NewQuantityTextField.setText("");
         prixtxtfield.setText("");
    }    

    @FXML
    private void ValidationHandler(ActionEvent event) {
       String idproduit=produit.getIdProduit();
       if(NewNameTextField.getText().equals("") && NewQuantityTextField.getText().equals("") && prixtxtfield.getText().equals("")){
    	   Alert error = new Alert(Alert.AlertType.ERROR);
           error.setTitle("Opération échouée");
           error.setContentText("Veuillez saisir des information");
           error.setHeaderText(null);
           error.show();       
       }
       
       else{
    	   String name = NewNameTextField.getText();
    	   String quant = NewQuantityTextField.getText();
    	   String prix = prixtxtfield.getText();
    	   
    	   String name2 = name.equals("")?produit.getNom():name;
    	   String quant2 = quant.equals("")?String.valueOf(produit.getQuantite()):quant;
    	   String prix2 = prix.equals("")?String.valueOf(produit.getPrix()):prix;
    	   try {
				Produit p = new Produit(idproduit, name2, Integer.parseInt(quant2), service, Integer.parseInt(prix2));
				Data.modifierProduit(produit, p);
				ProduitController.items.add(p);
				ProduitController.items.remove(ProduitController.produit);
				Alert information = new Alert(Alert.AlertType.INFORMATION);
				information.setTitle("Opération réussie");
				information.setContentText("Le produit a été modifié avec succès");
				information.setHeaderText(null);
				information.show();
				initialize(null, null);
				ProduitController pa = new ProduitController();
				pa.initialize(null, null);
				
			}
			catch(NumberFormatException e) {
				Alert error = new Alert(Alert.AlertType.ERROR);
				error.setTitle("Opération échouée");
				error.setContentText("Quantité ou prix invalide");
				error.setHeaderText(null);
				error.show();
			}
    	   
	   }
    }

    @FXML
    private void ReturnHandler(ActionEvent event) throws IOException {
    	initialize(null, null);
		ProduitController p = new ProduitController();
		p.initialize(null, null);
        ProduitController.scenemodifier.close(SceneModifierProduit.principaleWindow);
    }
    
}
