package com.fges.ckonsoru.App_Modes;

import com.fges.ckonsoru.BDDRequests;
import com.fges.ckonsoru.Disponibilites;
import com.fges.ckonsoru.Interfaces.RendezVousDAO;
import com.fges.ckonsoru.RendezVous;

import java.util.Arrays;
import java.util.List;

public class SQL_RendezVousDAO implements RendezVousDAO {
    protected BDDRequests bdd;

    public SQL_RendezVousDAO(BDDRequests bddReq){
        this.bdd = bddReq;
    }

    public List<RendezVous> listerRendezVousClient(String nomClient){
        List<RendezVous> rdv = Arrays.asList(null);
        return rdv;
    }
    public void prendreRendezVous(String nomClient, String nomVeterinaire, String date){

    }
    public void supprimerRendezVous(String nomClient, String date){

    }
    public Disponibilites afficherDisponibilitesDate(String date){
        return null;
    }
}
