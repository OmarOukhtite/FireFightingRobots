package dessin;

import classes.*;
import gui.GUISimulator;
import gui.ImageElement;
import helper.Helper;
/**
 * La classe DessineCase permet de dessiner une case individuelle de la carte.
 * Chaque case est représentée par une image spécifique en fonction de son type de terrain.
 */
public class DessineCase {
    /**
     * Dessine une case sur le simulateur graphique.
     * Utilise une image différente pour chaque type de terrain.
     * 
     * @param ccase La case à dessiner.
     * @param gui Le simulateur graphique utilisé pour afficher l'image de la case.
     * @param carte La carte qui contient la case à dessiner.
     */
    public static void drawCase(Case ccase, GUISimulator gui, Carte carte) {
        int cote = Helper.transform(carte.getNbColonnes());
        int centre_x = ccase.getColonne() * cote;
        int centre_y = ccase.getLigne() * cote;
        String imagePath = "";
        switch (ccase.getNatureTerrain()) {
            case EAU:
                imagePath = "photos/eau.png";
                break;
            case FORET:
                imagePath = "photos/foret.png";
                break;
            case ROCHE:
                imagePath = "photos/roche.png";
                break;
            case TERRAIN_LIBRE:
                imagePath = "photos/terrainlibre.png";
                break;
            case HABITAT:
                imagePath = "photos/habitat.png";
                break;
        }
        gui.addGraphicalElement(
                new ImageElement(
                        centre_x, // position x
                        centre_y, // position y
                        imagePath, // chemin de l'image
                        cote, // largeur
                        cote, // hauteur
                        gui // ImageObserver
                ));
    }
}