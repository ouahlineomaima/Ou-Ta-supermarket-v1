package application;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javafx.scene.control.Alert;

public class Service implements Serializable {
	private String idService;
    private String nom;
    private Gestionnaire gestionnaire;
    
    
    public Service(String idService,String nom,Gestionnaire gestionnaire){
        this.idService=idService;
        this.nom=nom;
        this.gestionnaire=gestionnaire;
       
    }
    public Service() {
		// TODO Auto-generated constructor stub
	}
    
    public String getIdService() {
        return idService;
    }
    
    public String getNom() {
        return nom;
    }
    
    public Gestionnaire getGestionnaire() {
        return gestionnaire;
    }
    
    public void setIdService(String idService) {
        this.idService = idService;
    }
    
    public void setNom(String nom) {
        this.nom = nom;
    }
    
    public void setGestionnaire(Gestionnaire gestionnaire) {
        this.gestionnaire = gestionnaire;
        Connection connexion = Data.getConnection();
        String requete = "update service set idgestionnaire = ? where idservice = ?";
        try {
 		PreparedStatement pst = connexion.prepareStatement(requete);
 		pst.setString(1, gestionnaire.getIdGestionnaire());
 		pst.setString(2, this.idService);
 		pst.executeUpdate();
 		} 
        catch (SQLException e) {
        	Alert error = new Alert(Alert.AlertType.ERROR);
			error.setTitle("Opération échouée");
			error.setContentText("Erreur de connexion avec la base de données.");
			error.setHeaderText(null);
			error.show();
 		} 
    }
    
    @Override
    public String toString() {
        return nom;
    }
}
