package dessin;

import gui.GUISimulator;

import classes.*;
import robots.*;
/**
 * La classe DessineDonnees permet de dessiner l'ensemble des éléments de la simulation sur une interface graphique.
 * Cela inclut la carte, les incendies et les robots.
 */
public class DessineDonnees {
    /**
     * Dessine l'ensemble des données sur l'interface graphique.
     * 
     * @param donnees Les données de la simulation contenant la carte, les incendies et les robots.
     * @param gui Le simulateur graphique utilisé pour afficher les éléments de la simulation.
     */
    public static void drawDonnees(DonneesSimulation donnees, GUISimulator gui) {
        // affichage de la carte
        DessineCarte.drawCarte(donnees.getCarte(), gui);

        // Afichage des incendies 
        for (Incendie incendie : donnees.getIncendies()) {
            DessineIncendie.drawIncendie(incendie, gui, donnees.getCarte());
        }

        // Afichage des robots
        for (Robot robot : donnees.getRobots()) {
            DessineRobot.drawRobot(robot, gui, donnees.getCarte());
        }
    }
}