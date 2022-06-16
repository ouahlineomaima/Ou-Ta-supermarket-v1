package application;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;


public class AjouterProduitController implements Initializable {

    @FXML
    private TextField tfidproduit;
    @FXML
    private TextField tfnom;
    @FXML    
    private TextField tfquantite;
    @FXML
    private TextField prixtxtfield;
    @FXML
    private Button btnvaliderproduit;
    @FXML
    private Button btnajouterproduit1;
    
    
    static Produit produit=ProduitController.getProduit();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tfidproduit.setText("");
        tfnom.setText("");
        tfquantite.setText(""); 
        prixtxtfield.setText("");
    }    

    @FXML
    private void validerproduitbutton(ActionEvent event) {
      try {
          if(tfnom.getText().equals("")|| tfidproduit.getText().equals("")){
        	  Alert error = new Alert(Alert.AlertType.ERROR);
              error.setTitle("Op�ration �chou�e");
              error.setContentText("Veuillez ins�rer un nom et un id");
              error.setHeaderText(null);
              error.show();                                
          }
          else{
        	  Produit produit = new Produit(tfidproduit.getText(), tfnom.getText(), Integer.parseInt(tfquantite.getText()), AffichageGestionnaireController.service, Integer.parseInt(prixtxtfield.getText()));
        	  int test = Data.ajouterProduit(produit, AffichageGestionnaireController.service);
        	  if (test == 1) {
        		  initialize(null, null); 
        		  Alert information = new Alert(Alert.AlertType.INFORMATION);
        		  information.setTitle("Op�ration r�ussie");
        		  information.setContentText("Le produit a �t� ajout� avec succ�s");
        		  information.setHeaderText(null);
        		  information.show();
        		  ProduitController p = new ProduitController();
        		  p.initialize(null, null);	
        	  }
        	  if (test == -1) {
        		  Alert error = new Alert(Alert.AlertType.ERROR);
  				  error.setTitle("Op�ration �chou�e");
  				  error.setContentText("L'id saisi figure d�j� dans la base de donn�es");
  				  error.setHeaderText(null);
  				  error.show();	
			  }
        	  if(test == 0) {
        		  Alert error = new Alert(Alert.AlertType.ERROR);
  				  error.setTitle("Op�ration �chou�e");
  				  error.setContentText("Probl�me de connexion");
  				  error.setHeaderText(null);
  				  error.show();
    			}
          }
		}
		catch(NumberFormatException e) {
			Alert error = new Alert(Alert.AlertType.ERROR);
			error.setTitle("Op�ration �chou�e");
			error.setContentText("Quantit� ou prix invalide");
			error.setHeaderText(null);
			error.show();
		}
    }

    @FXML
    private void retourproduitbutton(ActionEvent event) throws IOException {
             ProduitController.sceneajouter.close(SceneAjouterProduit.principaleWindow);
    }
    
}
