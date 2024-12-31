package dessin;

import java.awt.*;
import gui.GUISimulator;
import gui.Text;
import helper.Helper;
import gui.ImageElement;

import classes.*;

public class DessineIncendie {
/**
 * Dessine un incendie sur l'interface graphique en affichant l'image du feu ainsi que l'intensité 
 * sous forme de texte, si l'intensité de l'incendie est différente de zéro.
 * 
 * L'incendie est dessiné à la position correspondant à la case de la carte où il se trouve. 
 * L'image du feu est ajoutée à la position calculée, et l'intensité de l'incendie est affichée 
 * au centre de la case en texte noir.
 * 
 * @param incendie L'objet Incendie représentant l'incendie à dessiner.
 * @param gui Le simulateur graphique utilisé pour afficher les éléments graphiques de la simulation.
 * @param carte La carte de la simulation, utilisée pour obtenir la taille des cases et la position de l'incendie.
 */
    public static void drawIncendie(Incendie incendie, GUISimulator gui, Carte carte) {
        if (incendie.getIntensite() != 0) {
            // Taille de la case pour représenter l'incendie
            int intensitySize = Helper.transform(carte.getNbColonnes());

            // Coordonnées de la case où se trouve l'incendie
            int centre_x = incendie.getPosition().getColonne() * intensitySize;
            int centre_y = incendie.getPosition().getLigne() * intensitySize;

            // chemin vers l'image utilisée pour modéliser une incendie
            String imagePath = "photos/fire.png";

            // Ajoute l'image du feu en utilisant ImageElement
            gui.addGraphicalElement(
                    new ImageElement(
                            centre_x, // position x
                            centre_y, // position y
                            imagePath, // chemin de l'image
                            intensitySize, // largeur
                            intensitySize, // hauteur
                            gui // ImageObserver
                    ));

            // Affiche l'intensité sous forme de texte
            gui.addGraphicalElement(
                    new Text(
                            centre_x + intensitySize / 2,
                            centre_y + intensitySize / 2,
                            Color.BLACK,
                            "" + incendie.getIntensite()));
        }
    }
}