/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fges.ckonsoru;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;
import java.util.Scanner;

import com.fges.ckonsoru.DAO.SQL_RendezVousDAO;
import com.fges.ckonsoru.DAO.XML_RendezVousDAO;
import com.fges.ckonsoru.Interfaces.RendezVousDAO;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;

/**
 * Launch the App
 * @author julie.jacques
 */
public class App {
    public static void main(String args[]){
        // chargement de la configuration de la persistence
        ConfigLoader cf = new ConfigLoader();
        Properties properties = cf.getProperties();

        RendezVousDAO rdvDAO = null;

        if(properties.getProperty("persistence").equals("XML")){
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setNamespaceAware(true);
            DocumentBuilder builder;

            try {
                builder = factory.newDocumentBuilder();
                String filepath = "src/main/resources/ckonsoru.xml";
                Document xmldoc = builder.parse(filepath);
                rdvDAO = new XML_RendezVousDAO(xmldoc, filepath);
            } catch(Exception e) {
                System.err.println(e);
            }
        } else {
            try{
                // Chargement de la configuration de la bdd (le config.properties)

                // Récupération de chaque propriété nécessaire
                String dbConnUrl = properties.getProperty("bdd.url");
                String dbUserName = properties.getProperty("bdd.login");
                String dbPassword = properties.getProperty("bdd.mdp");

                if(!"".equals(dbConnUrl)) {
                    // Le driver jdbc de postgresql
                    Class.forName("org.postgresql.Driver");

                    // Création de la connexion à la base de données
                    Connection dbConn = DriverManager.getConnection(dbConnUrl, dbUserName, dbPassword);
                    rdvDAO = new SQL_RendezVousDAO(dbConn);
                }
            }catch(Exception e){
                System.err.println(e);
            }
        }
        Scanner sc = new Scanner(System.in);
        System.out.println("Bienvenue sur la clinique Konsoru !");

        while(true){
            String actionARealiser = "";
            System.out.println("Actions disponibles :");
            System.out.println("1. Afficher les créneaux disponibles pour une date donnée");
            System.out.println("2. Lister les rendez-vous passés, présent et à venir d'un client");
            System.out.println("3. Prendre un rendez-vous");
            System.out.println("4. Supprimer un rendez-vous");
            System.out.println("9. Quitter");

            while(actionARealiser.equals("1") == false && actionARealiser.equals("2") == false && actionARealiser.equals("3") == false && actionARealiser.equals("4") == false && actionARealiser.equals("9") == false) {
                if (actionARealiser.equals("") == false) {
                    System.out.println("Choisissez une action valide !");
                }
                System.out.println("Entrez l'action a réaliser");
                actionARealiser = sc.nextLine();
            }

            if(actionARealiser.equals("1")){
                System.out.println("De quel jour souhaitez vous afficher les crénaux (aaaa/mm/dd) ?");
                String date = sc.nextLine();
                rdvDAO.afficherDisponibilitesDate(date);
            } else if (actionARealiser.equals("2")) {
                System.out.println("List de RDVs d'un client");
                System.out.println("Indiquer nom du client:");
                String nom = sc.nextLine();
                rdvDAO.listerRendezVousClient(nom);
            } else if (actionARealiser.equals("3")) {
                System.out.println("Prise de RDV");
                System.out.println("Indiquer date et heure de début (aaaa/mm/dd hh:mm):");
                String date = sc.nextLine();
                System.out.println("Indiquer nom du vétérinaire");
                String nomVeto = sc.nextLine();
                System.out.println("Indiquer nom du client");
                String client = sc.nextLine();
                rdvDAO.prendreRendezVous(client, nomVeto, date);
            } else if (actionARealiser.equals("4")) {
                System.out.println("Suppression d'un RDV");
                System.out.println("Indiquer date et heure (aaaa/mm/dd hh:mm):");
                String date = sc.nextLine();
                System.out.println("Indiquer nom du client");
                String nom = sc.nextLine();
                rdvDAO.supprimerRendezVous(nom, date);
            } else if (actionARealiser.equals("9")) {
                System.exit(0);
            }
        }

    }
}
