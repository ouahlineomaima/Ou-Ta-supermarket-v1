package application;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.collections.ObservableList;

public class Admin extends Gestionnaire {

	public Admin(){
		
	}
	
	public Admin(String idGestionnaire, String nomComplet, String telephone, String adresse) {
		super(idGestionnaire, nomComplet, telephone, adresse);
	}
	
	public int ajouterService(Service service){
		Service service1 = Data.rechercherService(service.getIdService());
	    if (service1 != null) {
	            return -1;
	    }
	    String request= "insert into service values (?, ?, ?)";
        Connection conn = Data.getConnection();
	        try{
	        	PreparedStatement pstatement = conn.prepareStatement(request);
	        	pstatement.setString(1, service.getIdService());
				pstatement.setString(2, service.getNom());
				pstatement.setString(3, service.getGestionnaire().getIdGestionnaire());
				return pstatement.executeUpdate();
	        }
	        catch(Exception e){
	            return 0;
	            
	        }
	}  
	public int ajouterGestionnaire(Gestionnaire gestionnaire){
		Gestionnaire gestionnaire2 = Data.rechercherGestionnaire(gestionnaire.getIdGestionnaire());
		if(gestionnaire2 == null) {
			String request= "INSERT INTO gestionnaire VALUES (?, ?, ?, ?)";
	        Connection conn = Data.getConnection();
	        PreparedStatement st;
	        try{
	            st=conn.prepareStatement(request);
	            st.setString(1, gestionnaire.getIdGestionnaire());
	            st.setString(2, gestionnaire.getNomComplet());
	            st.setString(3, gestionnaire.getTelephone());
	            st.setString(4, gestionnaire.getAddress());
	            return st.executeUpdate();
	        }
	        catch(Exception e){
	            return 0;
	        }
		}
		return -1;      
	}
	
	public int affecterService(Gestionnaire gestionnaire , Service service){
	    if((Data.rechercherGestionnaire(gestionnaire.getIdGestionnaire())==null) ||(Data.rechercherService(service.getIdService())==null)){
	        return -1;
	    }
	    else{
	    	Connection connexion = Data.getConnection();
	    	String requete = "update service set idgestionnaire = ? where idservice = ?";
	    	service.setGestionnaire(gestionnaire);
	    	try {
	    		PreparedStatement pstatement = connexion.prepareStatement(requete);
	    		pstatement.setString(1, gestionnaire.getIdGestionnaire());
	    		pstatement.setString(2, service.getIdService());
	    		return pstatement.executeUpdate();
			} catch (SQLException e) {
				return 0;
			}
	    }  
	}
	
	
	public int supprimerGestionnaire(Gestionnaire gestionnaire){
		for (int i =0; i<Data.getService().size(); i++) {
			if(Data.getService().get(i).getGestionnaire().getIdGestionnaire().equals(gestionnaire.getIdGestionnaire())) {
				Data.getService().get(i).setGestionnaire(this);
			}
		}
		String request= "DELETE FROM gestionnaire WHERE idgestionnaire= " + gestionnaire.getIdGestionnaire();
        Connection conn = Data.getConnection();
        Statement st;
        try{
            st=conn.createStatement();
            return st.executeUpdate(request);
        }
        catch(Exception e){
        	e.printStackTrace();
            return 0; 
        }   
	}
	
	public int supprimerService(Service service){
	    ObservableList<Produit> listProduit = Data.getProduit(service);
	    for(int i =0; i<listProduit.size(); i++) {
	    	listProduit.get(i).setService(TableGestionnaireController.service);
	    }
	    String request= "DELETE FROM service WHERE idservice= " + service.getIdService();
	    Connection conn = Data.getConnection();
	    Statement st;
	    try{
            st=conn.createStatement();
            return st.executeUpdate(request);     
	    }
	    catch(Exception e){
	            return 0;
	            
	    }
	}

}
