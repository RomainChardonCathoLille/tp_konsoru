package com.fges.ckonsoru.App_Modes;

import com.fges.ckonsoru.Disponibilites;
import com.fges.ckonsoru.Interfaces.RendezVousDAO;
import com.fges.ckonsoru.RendezVous;
import com.fges.ckonsoru.XMLRequests;

import java.util.List;

public class XML_RendezVousDAO implements RendezVousDAO {
    protected XMLRequests xmldb;

    public XML_RendezVousDAO(XMLRequests xmlConnexion){
        this.xmldb = xmlConnexion;
    }

    public List<RendezVous> listerRendezVousClient(String nomClient){
        return null;
    }
    public void prendreRendezVous(String nomClient, String nomVeterinaire, String date){

    }
    public void supprimerRendezVous(String nomClient, String date){

    }
    public Disponibilites afficherDisponibilitesDate(String date){
        return null;
    }
}
