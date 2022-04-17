package com.fges.ckonsoru.DAO;

import com.fges.ckonsoru.Interfaces.RendezVousDAO;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Locale;

public class XML_RendezVousDAO implements RendezVousDAO {
    protected Document xmldb;
    protected String filePath;

    public XML_RendezVousDAO(Document xmlConnexion, String filepath){
        this.xmldb = xmlConnexion;
        this.filePath = filepath;
    }

    public void listerRendezVousClient(String nomClient){
        String reqXPATH = "/ckonsoru/rdvs/rdv[starts-with(client, '"+nomClient+"')]";
        XPath xpath = XPathFactory.newInstance().newXPath();
        try {
            XPathExpression expr = xpath.compile(reqXPATH);
            NodeList nodes = (NodeList) expr.evaluate(xmldb, XPathConstants.NODESET);
            System.out.println(nodes.getLength() + " RDVs trouvés pour " + nomClient);
            for(int i = 0; i < nodes.getLength(); i++){
                Node nNode = nodes.item(i);
                if(nNode.getNodeType() == Node.ELEMENT_NODE){
                    Element eElement = (Element) nNode;
                    String debut = eElement.getElementsByTagName("debut").item(0).getTextContent();
                    String veto = eElement.getElementsByTagName("veterinaire").item(0).getTextContent();
                    System.out.println(debut + " avec " + veto);
                }
            }
        } catch (Exception e){
            System.err.println(e);
        }

    }
    public void prendreRendezVous(String nomClient, String nomVeterinaire, String date){
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        try {
            // cree un rdv avec client & veterinaire
            Element rdv = xmldb.createElement("rdv");

            Element daterdv = xmldb.createElement("debut");
            daterdv.appendChild(xmldb.createTextNode(date));
            rdv.appendChild(daterdv);

            Element client = xmldb.createElement("client");
            client.appendChild(xmldb.createTextNode(nomClient));
            rdv.appendChild(client);

            Element veterinaire = xmldb.createElement("veterinaire");
            veterinaire.appendChild(xmldb.createTextNode(nomVeterinaire));
            rdv.appendChild(veterinaire);

            // ajout au noeud rdvs
            NodeList nodes = xmldb.getElementsByTagName("rdvs");
            System.out.println("RDV pour " + nomClient + " avec " + nomVeterinaire + " a été réservé pour le " + date);

            DOMSource source = new DOMSource(xmldb);
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            StreamResult res = new StreamResult(this.filePath);
            transformer.transform(source, res);

        } catch (Exception e){
            System.err.println(e);
        }
    }
    public void supprimerRendezVous(String nomClient, String date){
        try {
            // créer la requête XPATH
            String requeteXPATH = "/ckonsoru/rdvs/rdv[starts-with(client,'"+nomClient+"') and starts-with(debut, '"+date+"')]";
            XPath xpath = XPathFactory.newInstance().newXPath();
            XPathExpression expr = xpath.compile(requeteXPATH);

            // Supprime le noeud trouvé
            NodeList nodes = (NodeList) expr.evaluate(xmldb, XPathConstants.NODESET);
            //System.out.println(nodes.getLength() + " noeud(s) trouvé(s)");
            for (int i = nodes.getLength() - 1; i >= 0; i--){
                nodes.item(i).getParentNode().removeChild(nodes.item(i));
            }

            System.out.println("Le rendez-vous du " + date + " de " +  nomClient + " a été supprimé");

            // enregistrer le fichier
            DOMSource source = new DOMSource(xmldb);
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            StreamResult result = new StreamResult(this.filePath);
            transformer.transform(source, result);
        } catch (Exception e){
            System.err.println(e);
        }
    }
    public void afficherDisponibilitesDate(String date){
        Timestamp ts = Timestamp.valueOf(date);
        LocalDate dDate = LocalDate.of(ts.getYear(), ts.getMonth(), ts.getDay());
        String nomjour = dDate.format(DateTimeFormatter.ofPattern("EEEE", Locale.FRENCH));
        try {
            // créer la requête XPATH
            String requeteXPATH = "/ckonsoru/disponibilites/disponibilite[starts-with(jour,'"+nomjour+"')]";
            XPath xpath = XPathFactory.newInstance().newXPath();
            XPathExpression expr = xpath.compile(requeteXPATH);
            // evaluer la requête XPATH
            NodeList nodes = (NodeList) expr.evaluate(xmldb, XPathConstants.NODESET);
            for (int i = 0; i < nodes.getLength(); i++)
            {
                Node nNode = nodes.item(i);
                if (nNode.getNodeType() == Node.ELEMENT_NODE)
                {
                    Element eElement = (Element) nNode;
                    String jour = eElement.getElementsByTagName("jour").item(0).getTextContent();
                    String debut = eElement.getElementsByTagName("debut").item(0).getTextContent();
                    String fin = eElement.getElementsByTagName("fin").item(0).getTextContent();
                    String veto = eElement.getElementsByTagName("veterinaire").item(0).getTextContent();
                    // Ici : afficher chaque 20 minutes de début à fin
                    String partiesdebut[] = debut.split(":");
                    String partiesfin[] = fin.split(":");
                    LocalDateTime dateTimedeb = LocalDateTime.of(ts.getYear(), ts.getMonth(), ts.getDay(), Integer.parseInt(partiesdebut[0]), Integer.parseInt(partiesdebut[1]));
                    LocalDateTime dateTimefin = LocalDateTime.of(ts.getYear(), ts.getMonth(), ts.getDay(), Integer.parseInt(partiesfin[0]), Integer.parseInt(partiesfin[1]));
                    while(dateTimedeb.isEqual(dateTimefin) == false){
                        System.out.println(veto + " : " + dateTimedeb);
                        dateTimedeb = dateTimedeb.plus(20, ChronoUnit.MINUTES);
                    }
                }
            }
        } catch (Exception e){
            System.err.println(e);
        }
    }
}
