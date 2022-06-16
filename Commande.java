package application;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;

public class Commande implements Serializable {
	private String idCommande;
    private HashMap<Produit,Integer> map;
    static int nbCommande;
    
    public Commande(String idCommande, HashMap<Produit,Integer> map){
        this.idCommande=idCommande;
        this.map=map;
        nbCommande++;
    }

    public Commande() {
		// TODO Auto-generated constructor stub
    	nbCommande++;
	}

	public String getIdCommande() {
        return idCommande;
    }

    public static int getNbCommande() {
		return nbCommande;
	}

	public static void setNbCommande(int nbCommande) {
		Commande.nbCommande = nbCommande;
	}

	public HashMap<Produit, Integer> getMap() {
        return map;
    }

    public void setIdCommande(String idCommande) {
        this.idCommande = idCommande;
    }

    public void setMap(HashMap<Produit, Integer> map) {
        this.map = map;
    }

    public void achatProduit(Produit produit, int quan){
        produit.achatproduit(quan);
    }
    
    public void sauveCommande() {
    	File f = new File("Commandestock.tmp");
    	try {
			ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(f));
			out.writeObject(this);
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
		}
    }

}
