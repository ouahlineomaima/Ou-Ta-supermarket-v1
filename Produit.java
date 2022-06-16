package application;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Objects;

import javafx.scene.control.Alert;

public class Produit implements Serializable {
	private String idProduit;
    private String nom;
    private int quantite;
    private Service service;
    private int prix;
    
    public Produit() {
		// TODO Auto-generated constructor stub
	}
    
    public Produit(String idProduit,String nom, int quantite,Service service, int prix){
        this.idProduit=idProduit;
        this.nom=nom;
        this.quantite=quantite;
        this.service=service;
        this.prix = prix;
    }

    public int getPrix() {
		return prix;
	}

	public void setPrix(int prix) {
		this.prix = prix;
	}


	public String getIdProduit() {
        return idProduit;
    }

    public String getNom() {
        return nom;
    }

    public int getQuantite() {
        return quantite;
    }

    public Service getService() {
        return service;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setService(Service service) {
        this.service = service;
        Connection connexion = Data.getConnection();
        String requete = "update produit set service = ? where idproduit = ?";
        try {
 		PreparedStatement pst = connexion.prepareStatement(requete);
 		pst.setString(1, service.getIdService());
 		pst.setString(2, this.idProduit);
 		pst.executeUpdate();
 		} 
        catch (SQLException e) {
        	Alert error = new Alert(Alert.AlertType.ERROR);
			error.setTitle("Opération échouée");
			error.setContentText("problème de connexion");
			error.setHeaderText(null);
			error.show();
		}
    }

    public void setIdProduit(String idProduit) {
        this.idProduit = idProduit;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    @Override
    public String toString() {
        return nom + quantite;
    }

    public void achatproduit(int quan){
    		Produit p = Data.rechercherProduit(this.idProduit.substring(6), this.service);
    		p.setQuantite(p.quantite -quan);
            Connection conn = Data.getConnection();
            String requete = "update produit set quantite = ? where idproduit = ?";
            try {
				PreparedStatement pst = conn.prepareStatement(requete);
				pst.setString(1, String.valueOf(p.getQuantite()));
				pst.setString(2, p.getIdProduit());
				pst.executeUpdate();
				this.setQuantite(this.getQuantite() - quan);
			} catch (SQLException e) {
				Alert error = new Alert(Alert.AlertType.ERROR);
				error.setTitle("Opération échouée");
				error.setContentText("problème de connexion");
				error.setHeaderText(null);
				error.show();
			}
            
    }

	@Override
	public int hashCode() {
		return Objects.hash(idProduit);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Produit other = (Produit) obj;
		return Objects.equals(idProduit, other.idProduit);
	}

}
