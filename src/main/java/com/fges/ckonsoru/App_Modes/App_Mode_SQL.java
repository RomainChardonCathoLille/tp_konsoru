package com.fges.ckonsoru.App_Modes;

import com.fges.ckonsoru.BDDRequests;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class App_Mode_SQL {
    public void lancerProgramme(){
        Scanner sc = new Scanner(System.in); // Pour la saisie clavier
        System.out.println("Bienvenue sur Clinique Konsoru !");

        while(true){
            String actionARealiser = ""; // On le redéfinit chaque fois pour éviter une boucle infinie

            System.out.println("Actions disponibles :");
            System.out.println("1. Afficher les créneaux disponibles pour une date donnée");
            System.out.println("2. Lister les rendez-vous passés, présent et à venir d'un client");
            System.out.println("3. Prendre un rendez-vous");
            System.out.println("4. Supprimer un rendez-vous");
            System.out.println("9. Quitter");

            // Boucle qui empêche l'utilisateur de rentrer une action non valide
            while(actionARealiser.equals("1") == false && actionARealiser.equals("2") == false && actionARealiser.equals("3") == false && actionARealiser.equals("4") == false && actionARealiser.equals("9") == false){
                if(actionARealiser.equals("") == false){
                    System.out.println("Veuillez choisir une action valide !");
                }
                System.out.println("Entrez l'action que vous souhaitez réaliser :");
                actionARealiser = sc.nextLine();
            }

            // Une fois que l'utilisateur a choisi une action convenable, on l'exécute
            if(actionARealiser.equals("1")){
                System.out.println("De quel jour souhaitez-vous afficher les créneaux (de forme jj/mm/aaaa) ?");
                String datecherchee = sc.nextLine();
                String partiesdates[] = datecherchee.split("/");
                BDDRequests test = new BDDRequests();
                test.bdd_creneaux(Integer.parseInt(partiesdates[2]), Integer.parseInt(partiesdates[1]), Integer.parseInt(partiesdates[0]));
            }else if(actionARealiser.equals("2")){
                System.out.println("Liste de rendez-vous d'un client.");
                System.out.println("Indiquer le nom du client :");
                String nomcli = sc.nextLine();
                BDDRequests test = new BDDRequests();
                test.listerdv(nomcli);
            }else if(actionARealiser.equals("3")){
                System.out.println("Prise de rendez-vous.");
                System.out.println("Indiquer une date et heure de début au format JJ/MM/AAAA HH:MM (ex: 18/03/2021 15:00) :");
                String daterdv = sc.nextLine();
                System.out.println("Indiquer le nom du vétérinaire :");
                String nomveto = sc.nextLine();
                System.out.println("Indiquer le nom du client :");
                String nomcli = sc.nextLine();
                BDDRequests test = new BDDRequests();
                try{
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm");
                    Date parsedDate = dateFormat.parse(daterdv);
                    Timestamp datetimestamp = new java.sql.Timestamp(parsedDate.getTime());
                    test.prendrerdv(datetimestamp, nomveto, nomcli);
                }catch(Exception e){
                    System.out.println(e);
                }
            }else if(actionARealiser.equals("4")){
                System.out.println("Suppression d'un rendez-vous.");
                System.out.println("Indiquer une date et heure de début au format JJ/MM/AAAA HH:MM (ex: 18/03/2021 15:00) :");
                String daterdv = sc.nextLine();
                System.out.println("Indiquer le nom du client :");
                String nomcli = sc.nextLine();
                BDDRequests test = new BDDRequests();
                try{
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm");
                    Date parsedDate = dateFormat.parse(daterdv);
                    Timestamp datetimestamp = new java.sql.Timestamp(parsedDate.getTime());
                    test.supprrdv(nomcli, datetimestamp);
                }catch(Exception e){
                    System.out.println(e);
                }
            }else if(actionARealiser.equals("9")){
                System.exit(0);
            }
        }
    }
}
