package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Data {
	public static Connection getConnection() {
		String url = "jdbc:mysql://localhost:3306/gestionstock";
		Connection connexion;
		try {
			connexion = DriverManager.getConnection(url, "root", "root");
			return connexion;
		} catch (SQLException e) {
			return null;
		}
	}

	public static ObservableList<Gestionnaire> getGestionnaire() {
		ObservableList<Gestionnaire> gestionnaireliste = FXCollections.observableArrayList();
		Connection connexion = getConnection();
		String request = "SELECT * FROM gestionnaire";
		Statement stat;
		ResultSet res;
		try {
			stat = connexion.createStatement();
			res = stat.executeQuery(request);

			while (res.next()) {
				if (res.getString("idgestionnaire").equals("0000")) {
					Admin admin = new Admin("0000", res.getString("nomComplet"), res.getString("telephone"),
							res.getString("address"));
					gestionnaireliste.add(admin);
				} else {
					Gestionnaire gestionnaire = new Gestionnaire(res.getString("idgestionnaire"),
							res.getString("nomComplet"), res.getString("telephone"), res.getString("address"));
					gestionnaireliste.add(gestionnaire);
				}

			}
		} catch (Exception ex) {
			Gestionnaire gestionnaire = new Gestionnaire("problème de connexion", null, null, null);
			gestionnaireliste.add(gestionnaire);
			return gestionnaireliste;
		}
		return gestionnaireliste;
	}

	public static ObservableList<Service> getService() {
		ObservableList<Service> serviceliste = FXCollections.observableArrayList();
		Connection connexion = getConnection();
		String request = "SELECT * FROM service";
		Statement stat;
		ResultSet res;
		try {
			stat = connexion.createStatement();
			res = stat.executeQuery(request);
			while (res.next()) {
				// recherche du gestionnaire correspondant au service
				for (int i = 0; i < getGestionnaire().size(); i++) {
					if (getGestionnaire().get(i).getIdGestionnaire().equals(res.getString("idgestionnaire"))) {
						Service service = new Service(res.getString("idservice"), res.getString("nom"),
								getGestionnaire().get(i));
								serviceliste.add(service);
					}
				}
			}
		} catch (Exception ex) {
			Service service = new Service("problème de connexion", null, null);
			serviceliste.add(service);
			return serviceliste;
		}
		return serviceliste;
	}

	public static ObservableList<Produit> getProduit(Service service){
    	ObservableList<Produit> listProduit = FXCollections.observableArrayList();
    	Connection connexion = getConnection();
    	String requete = "select * from produit where service ="+ service.getIdService();
    	try {
			Statement statement = connexion.createStatement();
			ResultSet resultat = statement.executeQuery(requete);
			while(resultat.next()) {
				int q = Integer.parseInt(resultat.getString("quantite"));
				Produit produit = new Produit(resultat.getString("idproduit"), resultat.getString("nom"), q, service, resultat.getInt("prix"));
				listProduit.add(produit);
			}
			return listProduit;
		} catch (SQLException e) {
			Produit produit = new Produit("problème de connexion", null, 0, null, 0);
			listProduit.add(produit);
			return listProduit;
		}
    }

	public static Gestionnaire rechercherGestionnaire(String idGestionnaire) {
		ObservableList<Gestionnaire> listeGestionnaire = getGestionnaire();
		Gestionnaire g = null;
		for (int i = 0; i < listeGestionnaire.size(); i++) {
			if (listeGestionnaire.get(i).getIdGestionnaire().equals(idGestionnaire)) {
				g = listeGestionnaire.get(i);
			}
		}
		return g;
	}

	public static Service rechercherService(String idService) {
		ObservableList<Service> listeService = getService();
		Service s = null;
		for (int i = 0; i < listeService.size(); i++) {
			if (listeService.get(i).getIdService().equals(idService)) {
				s = listeService.get(i);
			}
		}
		return s;
	}
	
	public static Produit rechercherProduit(String idProduit , Service service){
        ObservableList<Produit> listeProduit = getProduit(service);
         Produit p=null;
        for(int i=0;i<listeProduit.size();i++) {
           if(listeProduit.get(i).getIdProduit().equals(idProduit)) {
               p=listeProduit.get(i);
           }
       }
       return p;
	}
	
	public static int ajouterProduit(Produit produit,Service service) {
		int x = 0;
		for (int i=0; i< Data.getService().size(); i++) {
			if(rechercherProduit(produit.getIdProduit(), Data.getService().get(i)) != null) {
				x = 1;
				break;
			}
		}
		if (x == 0) {
			Connection conn = getConnection();
			String requete = "insert into produit values (?, ?, ?, ?, ?)";
			try {
				PreparedStatement pst = conn.prepareStatement(requete);
				pst.setString(1, produit.getIdProduit());
				pst.setString(2, produit.getNom());
				pst.setString(3, String.valueOf(produit.getQuantite()));
				pst.setString(4, service.getIdService());
				pst.setInt(5, produit.getPrix());
				return pst.executeUpdate();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				return 0;
			}
		}
		else {
			return -1;
		}
	}
	
	public static int supprimerProduit(String idproduit) {
		Connection con = getConnection();
		String requete = "delete from produit where idproduit = "+idproduit;
		try {
			Statement st = con.createStatement();
			return st.executeUpdate(requete);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			return 0;
		}
		
	}
	
	public static void modifierProduit(Produit sp, Produit tp) {
		supprimerProduit(sp.getIdProduit());
		ajouterProduit(tp, tp.getService());
	}

}
