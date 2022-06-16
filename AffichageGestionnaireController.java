package application;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;


public class AffichageGestionnaireController implements Initializable {
    @FXML
    private TableView<Service> serrvicegestionnaire;
    @FXML
    private TableColumn<Service, String> clidservice;
    @FXML
    private TableColumn<Service, String> clnom;
    @FXML
    private Button btnconsultergestionnaire;
    @FXML
    private Button btndisconnection;
    
    static ObservableList<Service> items = Data.getService();
    static Service service = new Service();
    static ActionEvent e = new ActionEvent();
    static Login2Controller l=new Login2Controller();
    static Gestionnaire g =l.getGestionnaire();
    static Stage window = new Stage();
   
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ObservableList<Service> listservice=listeservice(); 
        clidservice.setCellValueFactory(new PropertyValueFactory<Service, String>("idService"));
        clnom.setCellValueFactory(new PropertyValueFactory<Service, String>("nom"));
        serrvicegestionnaire.setItems(listservice);  
    }
    
    
    @FXML
    private void consultergestionnairebutton(ActionEvent event) {
    	e = event;
        service = serrvicegestionnaire.getSelectionModel().getSelectedItem();
        if(service != null) {
        	try {
        		AnchorPane root = FXMLLoader.load(getClass().getResource("Produit.fxml"));
        		GestionApp.scene.setRoot(root);
        		GestionApp.principaleWindow.setTitle("Ou&Ta supermarket");
			} 
        	catch (Exception e) {
        	}   
        }
        else {
        	Alert error = new Alert(Alert.AlertType.ERROR);
			error.setTitle("Opération échouée");
			error.setContentText("Aucun service selectionné. Veuiller séléctionner un service.");
			error.setHeaderText(null);
			error.show();
            service=null;
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
				GestionApp.principaleWindow.setTitle("Ou&Ta Supermarket");
			} catch (IOException e) {
				// TODO Auto-generated catch block
			}
			
		}
    }
        
   
    public ObservableList<Service> listeservice(){
        ObservableList<Service> listservice = FXCollections.observableArrayList();;
        ObservableList<Service> list=Data.getService();
        if(g!=null){
           for(int i=0; i<list.size();i++){
            if(g.getIdGestionnaire().equals(list.get(i).getGestionnaire().getIdGestionnaire())){
                listservice.add(list.get(i));
           }
          }
        }
        return listservice;
    }

    public  Service getServicegestionnaire() {
        return serrvicegestionnaire.getSelectionModel().getSelectedItem();
    }
    
    public String idgestionnaire() throws IOException{
     return l.getIdTextField().getText();
    }
    
    public Gestionnaire gestionnaire(){
        try{
        	Gestionnaire gestionnaire =Data.rechercherGestionnaire(idgestionnaire());
            return gestionnaire; 
        }
        catch(Exception e){
            return null;
        }
    } 
 }  
