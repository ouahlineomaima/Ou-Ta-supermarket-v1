package application;

import java.io.Serializable;

public class Gestionnaire implements Serializable{
	private static final long serialVersionUID = 5275465409705788526L;
	private String idGestionnaire;
    private String nomComplet;
    private String telephone;
    private String address ;
    
    public Gestionnaire() {
     	  
    }
    
    public Gestionnaire(String idGestionnaire, String nomComplet, String telephone, String address ){
        this.idGestionnaire=idGestionnaire;
        this.nomComplet=nomComplet;
        this.telephone=telephone;
        this.address=address;
    }
    
    public String getIdGestionnaire() {
        return idGestionnaire;
    }
    
    public String getNomComplet() {
        return nomComplet;
    }
    
    public String getTelephone() {
        return telephone;
    }
    
    public String getAddress() {
        return address;
    }
    
    public void setIdGestionnaire(String idGestionnaire) {
        this.idGestionnaire = idGestionnaire;
    }
    
    public void setNomComplet(String nomComplet) {
        this.nomComplet = nomComplet;
    }
    
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
    
    public void setAddress(String address) {
        this.address = address;
    }
    
    
    @Override
    public String toString() {
        return nomComplet ;
    }
   
}
