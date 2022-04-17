package com.fges.ckonsoru.Interfaces;

public interface RendezVousDAO {
    public void listerRendezVousClient(String nomClient);
    public void prendreRendezVous(String nomClient, String nomVeterinaire, String date);
    public void supprimerRendezVous(String nomClient, String date);
    public void afficherDisponibilitesDate(String date);

}
