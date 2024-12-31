package dessin;

import gui.GUISimulator;
import classes.*;
/**
 * La classe DessineCarte permet de dessiner une carte sur un simulateur graphique.
 * Chaque case de la carte est dessinée à l'aide de la classe DessineCase.
 */
public class DessineCarte {
    /**
     * Dessine la carte sur le simulateur graphique.
     * Parcourt chaque case de la carte et utilise DessineCase pour la dessiner.
     * 
     * @param carte La carte à dessiner.
     * @param gui Le simulateur graphique utilisé pour afficher la carte.
     */
    public static void drawCarte(Carte carte, GUISimulator gui) {
        for (int i =0; i<carte.getNbLignes(); i++) {
            for (int j=0; j<carte.getNbColonnes(); j++) {
                DessineCase.drawCase(carte.getCase(i, j), gui, carte);
            }
        }
    }
}