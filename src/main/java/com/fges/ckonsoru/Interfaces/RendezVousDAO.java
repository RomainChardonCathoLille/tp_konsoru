package com.fges.ckonsoru.Interfaces;

import com.fges.ckonsoru.Disponibilites;
import com.fges.ckonsoru.RendezVous;

import java.util.Date;
import java.util.List;

public interface RendezVousDAO {
    public List<RendezVous> listerRendezVousClient(String nomClient);
    public void prendreRendezVous(String nomClient, String nomVeterinaire, String date);
    public void supprimerRendezVous(String nomClient, String date);
    public Disponibilites afficherDisponibilitesDate(String date);
}
