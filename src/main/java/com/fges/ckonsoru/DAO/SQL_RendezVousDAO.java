package com.fges.ckonsoru.DAO;

import com.fges.ckonsoru.Interfaces.RendezVousDAO;

import java.sql.*;
import java.util.Arrays;
import java.util.List;

public class SQL_RendezVousDAO implements RendezVousDAO {
    //protected BDDRequests bdd;
    protected Connection bdd;

    public SQL_RendezVousDAO(Connection bddReq){
        this.bdd = bddReq;
    }

    public void listerRendezVousClient(String nomClient){
        String requetesql = "SELECT rv_id, rv_debut, vet_nom FROM rendezvous, veterinaire WHERE rv_client = ? AND veterinaire.vet_id = rendezvous.vet_id ORDER BY rv_debut DESC";
        try {
            PreparedStatement req = bdd.prepareStatement(requetesql);
            req.setString(1, nomClient);
            ResultSet res = req.executeQuery();
            int totalRows = 0;
            try {
                res.last();
                totalRows = res.getRow();
                res.beforeFirst();
            } catch(Exception e){
                totalRows = 0;
            }
            System.out.println(totalRows + " RDVs trouvés pour " + nomClient);
            while (res.next()){
                System.out.println(res.getTimestamp(2) + " avec " + res.getString(3));
            }
        } catch(SQLException e){
            System.err.println(e);
        }
    }
    public void prendreRendezVous(String nomClient, String nomVeterinaire, String date){
        String txtReq = "INSERT INTO rendezvous (vet_id, rv_debut, rv_client) VALUES((SELECT vet_id FROM veterinaire WHERE vet_nom = ?), ?, ?)";
        try {
            Timestamp ts = Timestamp.valueOf(date);
            PreparedStatement req = bdd.prepareStatement(txtReq);
            req.setString(1, nomVeterinaire);
            req.setTimestamp(2, ts);
            req.setString(3, nomClient);
            req.executeUpdate();
            System.out.println("Un RDV pour " + nomClient + " avec " + nomVeterinaire + " le " + date + " a été ajouté.");

        } catch(Exception e){
            System.err.println(e);
        }
    }
    public void supprimerRendezVous(String nomClient, String date){
        String sqlReq = "DELETE FROM rendezvous WHERE rv_client = ? AND rv_debut = ?";
        try {
            PreparedStatement req = bdd.prepareStatement(sqlReq);
            req.setString(1, nomClient);
            Timestamp ts = Timestamp.valueOf(date);
            req.setTimestamp(2,ts);
            req.executeUpdate();
            System.out.println("RDV de " + nomClient + " le " + date + " a été supprimé.");
        } catch(Exception e){
            System.err.println(e);
        }
    }
    public void afficherDisponibilitesDate(String date){
        Timestamp ts = Timestamp.valueOf(date);
        String sqlRequest = "WITH creneauxDisponibles AS (SELECT vet_nom, generate_series(?::date+dis_debut, ?::date+dis_fin-'00:20:00'::time, '20 minutes'::interval) debut FROM disponibilite INNER JOIN veterinaire ON veterinaire.vet_id = disponibilite.vet_id WHERE dis_jour = EXTRACT('DOW' FROM ?::date) ORDER BY vet_nom, dis_id), creneauxReserves AS (SELECT vet_nom, rv_debut debut FROM rendezvous INNER JOIN veterinaire ON veterinaire.vet_id = rendezvous.vet_id WHERE rv_debut BETWEEN ?::date AND ?::date +'23:59:59'::time), creneauxRestants AS (SELECT * FROM creneauxDisponibles EXCEPT SELECT * FROM creneauxReserves) SELECT * FROM creneauxRestants ORDER BY vet_nom, debut";
        try {
            PreparedStatement req = bdd.prepareStatement(sqlRequest);
            req.setString(1, date);
            req.setString(2, date);
            req.setString(3, date);
            req.setString(4, date);
            req.setString(5, date);
            ResultSet res = req.executeQuery();
            while(res.next()){
                System.out.println(res.getString(1) + " - Dispo le " + res.getTimestamp(2));
            }
        } catch(Exception e){
            System.err.println(e);
        }
    }
}
