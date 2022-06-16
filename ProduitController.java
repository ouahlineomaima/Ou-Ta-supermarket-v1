package application;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;


public class ProduitController implements Initializable {

    @FXML
    private TableView<Produit> tableproduit= new TableView<Produit>();
    @FXML
    private TableColumn<Produit, String> clidproduit=new TableColumn<Produit,String>();
    @FXML
    private TableColumn<Produit, String> clnom=new TableColumn<Produit,String>();
    @FXML
    private TableColumn<Produit, Integer> clquantite=new TableColumn<Produit,Integer>();
    @FXML
    private TableColumn<Produit, Integer> clprix = new TableColumn<Produit, Integer>();
    @FXML
    private Button btnajouterproduit;
    @FXML
    private Button btnsupprimerproduit;
    @FXML
    private Button btnmodifier;
    @FXML
    private Button btnretour;
    @FXML
    private Button btnactualiser;
    @FXML
    private Button btndisconnection;
    
    static Produit produit=new Produit();
    static ActionEvent e = new ActionEvent();
    static SceneAjouterProduit sceneajouter = new SceneAjouterProduit();
    static SceneModifierProduit scenemodifier = new SceneModifierProduit();
    static Stage window = new Stage();
    static AffichageGestionnaireController t = new AffichageGestionnaireController();
    static ObservableList<Produit> items = Data.getProduit(AffichageGestionnaireController.service);
    

   
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ObservableList<Produit> items = Data.getProduit(AffichageGestionnaireController.service);
        clidproduit.setCellValueFactory(new PropertyValueFactory<Produit, String>("idProduit"));
        clnom.setCellValueFactory(new PropertyValueFactory<Produit, String>("nom"));
        clquantite.setCellValueFactory(new PropertyValueFactory<Produit, Integer>("quantite"));
        clprix.setCellValueFactory(new PropertyValueFactory<Produit, Integer>("prix"));
        tableproduit.setItems(items);   
    }
    

    @FXML
    private void ajouterproduitbutton(ActionEvent event){        
		try {
			sceneajouter.start(window);
		} catch (Exception e) {
		}
    }

    @FXML
    private void supprimerproduitbutton(ActionEvent event) {
        if(tableproduit.getSelectionModel().getSelectedItem()==null){
            Alert error = new Alert(Alert.AlertType.ERROR);
			error.setTitle("Opération échouée");
			error.setContentText("Aucun produit sélectionné. Veuillez sélectionner un produit.");
			error.setHeaderText(null);
			error.show();
        }
        else{
            int test = Data.supprimerProduit(tableproduit.getSelectionModel().getSelectedItem().getIdProduit());
            if(test==1){
                Alert information = new Alert(Alert.AlertType.INFORMATION);
                information.setTitle("Opération réussie");
        		information.setContentText("Le produit a été supprimé avec succès");
        		information.setHeaderText(null);
        		information.show();
		
            }
            else{
                if(test==0){
                    Alert error = new Alert(Alert.AlertType.ERROR);
                    error.setTitle("Opération échouée");
    				error.setContentText("problème de connexion");
    				error.setHeaderText(null);
    				error.show();
                }
            }

        }
	}

    @FXML
    private void modifierbutton(ActionEvent event) {
    	e = event;
    	produit = tableproduit.getSelectionModel().getSelectedItem();
		if(produit != null) {
			try {
			scenemodifier.start(window);
		} catch (Exception e) {
		}
		}
		else {
			Alert error = new Alert(Alert.AlertType.ERROR);
			error.setTitle("Opération échouée");
			error.setContentText("Aucun produit sélectionné. Veuillez sélectionner un produit.");
			error.setHeaderText(null);
			error.show();
		}
    
       
    }

    @FXML
    private void retourproduitbutton(ActionEvent event) {
    	AnchorPane root;
		try {
			root = FXMLLoader.load(getClass().getResource("AffichageGestionnaire.fxml"));
			GestionApp.scene.setRoot(root);
			GestionApp.principaleWindow.setWidth(720);
		} catch (IOException e) {
			// TODO Auto-generated catch block
		}
		
    }
    
    @FXML
    private void disconnectionHandler(ActionEvent event) {
    	Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
    	confirm.setTitle("Confirmer la déconnexion");
    	confirm.setContentText("Êtes vous sûr de vouloir se déconnecter?");
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
			} catch (IOException e) {
				// TODO Auto-generated catch block
			}
			
		}
    }
    
    public static Produit getProduit() {
        return produit;
    }

    public static Service getService() {
        return AffichageGestionnaireController.service;
    }
    @FXML
    public void actualiserHandler(ActionEvent event) {
    	/*
    	clidproduit.setCellValueFactory(new PropertyValueFactory<Produit, String>("idProduit"));
        clnom.setCellValueFactory(new PropertyValueFactory<Produit, String>("nom"));
        clquantite.setCellValueFactory(new PropertyValueFactory<Produit, Integer>("quantite"));
        clprix.setCellValueFactory(new PropertyValueFactory<Produit, Integer>("prix"));
        tableproduit.setItems(items); 
        */
    	initialize(null, null);
	}
}
