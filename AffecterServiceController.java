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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class AffecterServiceController implements Initializable {

    @FXML
    private TableView<Service> tableservice = new TableView<Service>();
    @FXML
    private TableColumn<Service, String> clidservice = new TableColumn<Service, String>();
    @FXML
    private TableColumn<Service, String> clnom = new TableColumn<Service, String>();
    @FXML
    private TableColumn<Service, Gestionnaire> clidgestionnqire = new TableColumn<Service, Gestionnaire>();
    @FXML
    private Button btnretour;
    @FXML
    private Button btnvalider;

    static ObservableList<Service> listservice=Data.getService();
    TableGestionnaireController t = new TableGestionnaireController();
    TableServiceController ts = new TableServiceController();
    Gestionnaire gestionnaire = t.getSelectedGestionnaire();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    	listservice = Data.getService();
        clidservice.setCellValueFactory(new PropertyValueFactory<Service, String>("idService"));
        clnom.setCellValueFactory(new PropertyValueFactory<Service, String>("nom"));
        clidgestionnqire.setCellValueFactory(new PropertyValueFactory<Service, Gestionnaire>("gestionnaire"));
        tableservice.setItems(listservice);
    }    

    @FXML
    private void retouraffecterservicebutton(ActionEvent event) throws IOException {
    	TableGestionnaireController.s1.close(SceneAffecterService.principaleWindow);
    }

    @FXML
    private void validerserviceButton(ActionEvent event) {
    	Service service = tableservice.getSelectionModel().getSelectedItem();
    	if(service == null) {
    		Alert error = new Alert(Alert.AlertType.ERROR);
			error.setTitle("Op�ration �chou�e");
			error.setContentText("Aucun service s�lectionn�. Veuillez s�lectionner un service.");
			error.setHeaderText(null);
			error.show();
    	}
    	else {
    		if(service.getIdService().equals("0")) {
    			Alert error = new Alert(Alert.AlertType.ERROR);
    			error.setTitle("Op�ration �chou�e");
    			error.setContentText("Impossible d'affecter le service par d�faut.");
    			error.setHeaderText(null);
    			error.show();
    		}
    		else {
    			if(service.getGestionnaire().getIdGestionnaire().equals(gestionnaire.getIdGestionnaire())) {
            	    Alert information = new Alert(Alert.AlertType.INFORMATION);
        			information.setTitle("Op�ration �chou�e");
        			information.setContentText("Le service s�l�ctionn� est d�j� affect� � ce gestionnaire");
        			information.setHeaderText(null);
        			information.show();
        		}
            	else {
            		int test = TableGestionnaireController.admin.affecterService(gestionnaire, service);
            	    if (test == 1) {
            	    	Alert information = new Alert(Alert.AlertType.INFORMATION);
            	    	information.setTitle("Op�ration r�ussie");
            	    	information.setContentText("Le service a �t� affect� avec succ�s");
            	    	information.setHeaderText(null);
            	    	information.show();
            	    	initialize(null, null);
            	    	ts.initialize(null, null);
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
    }
}



