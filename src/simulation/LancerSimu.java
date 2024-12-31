package simulation;

import java.io.FileNotFoundException;
import java.util.zip.DataFormatException;
import java.awt.Color;

import chefPompier.*;
import classes.Carte;
import classes.Case;
import classes.DonneesSimulation;
import dessin.DessineDonnees;
import evenements.Scenario;
import gui.GUISimulator;
import lecteur.StockageDonnees;
/**
 * Classe principale pour lancer la simulation de gestion des incendies.
 * Cette classe lit les données depuis un fichier, initialise les données de simulation,
 * configure l'interface graphique, et démarre la simulation selon une stratégie spécifiée.
 */
public class LancerSimu {
        /**
     * Méthode statique principale pour initialiser et lancer la simulation.
     *
     * @param fichier Le chemin du fichier contenant les données de simulation.
     * @param gui Une instance de GUISimulator pour l'affichage graphique (peut être null pour en créer une nouvelle).
     * @param StratEvoluee Un booléen indiquant si la stratégie évoluée (true) ou élémentaire (false) doit être utilisée.
     */
    public static void lancerSimu(String fichier, GUISimulator gui, Boolean StratEvoluee) {
        // Nom du fichier de données
        String fichierDonnees = fichier;
        Case[][] matrice = new Case[1][1];
        Carte carte1 = new Carte(matrice, 1000);

        // Crée une instance de DonneesSimulation où les données seront stockées
        DonneesSimulation donnees = new DonneesSimulation(carte1);

        try {
            // Appel de la méthode de stockage pour lire le fichier et remplir les données
            StockageDonnees.stock(fichierDonnees, donnees);

            Color BackGroundColor = Color.BLACK;

            System.out.println("Calcul des chemins et évènements en cours. Veuillez patienter ...");

            if (gui == null) {
                gui = new GUISimulator(1000,1000, BackGroundColor);
            }
            Simulateur simulateur = new Simulateur(fichier, null, gui, donnees, StratEvoluee);

            DessineDonnees.drawDonnees(donnees, gui);

            if (StratEvoluee) {
                ChefPompierEvolue chef = new ChefPompierEvolue(donnees, simulateur);
                Scenario scenario = chef.AffecterRobotsEvolue();
                simulateur = new Simulateur(fichier, scenario, gui,donnees, StratEvoluee);
            } else {
                ChefPompierElementaire chef = new ChefPompierElementaire(donnees, simulateur);
                Scenario scenario = chef.AffecterRobotsElementaire();
                simulateur = new Simulateur(fichier, scenario, gui, donnees, StratEvoluee);
            }
            System.out.println("Chemins et évènements determinés. Vous pouvez commencer la simulation.");

            
        } catch (FileNotFoundException e) {
            System.out.println("Le fichier " + fichierDonnees + " est introuvable.");
        } catch (DataFormatException e) {
            System.out.println("Erreur de format des données : " + e.getMessage());
        }
    }
}
