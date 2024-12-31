package dessin;

import gui.GUISimulator;
import gui.Text;
import classes.*;
import robots.*;
import helper.Helper;
import java.awt.Color;
/**
 * Classe utilitaire pour dessiner les robots sur l'interface graphique de la simulation.
 * 
 * La méthode `drawRobot` est utilisée pour dessiner un robot de type spécifique à une position donnée 
 * sur la carte. Le robot est représenté par un cercle de couleur correspondant à son type, 
 * avec un symbole textuel qui indique le type du robot.
 */
public class DessineRobot {

    /**
     * Dessine un robot sur l'interface graphique en utilisant une couleur spécifique en fonction du type 
     * de robot et en ajoutant un symbole textuel représentant son type.
     * 
     * Le robot est dessiné à la position correspondant à la case de la carte où il se trouve. 
     * La couleur et le symbole du robot dépendent du type spécifique du robot (Drone, Chenille, Pattes, Roues).
     * 
     * @param robot L'objet Robot représentant le robot à dessiner.
     * @param gui Le simulateur graphique utilisé pour afficher les éléments graphiques de la simulation.
     * @param carte La carte de la simulation, utilisée pour obtenir la taille des cases et la position du robot.
     */
    public static void drawRobot(Robot robot, GUISimulator gui, Carte carte) {
        Color robotColor = null;
        // Définir la couleur en fonction du type de robot
        if (robot instanceof RobotDrone) {
            robotColor = new Color(0, 191, 255); // Bleu ciel pour le drone
        } else if (robot instanceof RobotChenille) {
            robotColor = new Color(139, 69, 19); // Marron pour les chenilles
        } else if (robot instanceof RobotPattes) {
            robotColor = new Color(34, 139, 34); // Vert pour les pattes
        } else if (robot instanceof RobotRoues) {
            robotColor = new Color(255, 69, 0); // Rouge-orange pour les roues
        } else {
            robotColor = Color.GRAY; // Couleur par défaut
        }

        int caseSize = Helper.transform(carte.getNbColonnes());
        int centre_x = robot.getPosition().getColonne() * caseSize + caseSize / 2;
        int centre_y = robot.getPosition().getLigne() * caseSize + caseSize / 2;

        // Crée un cercle pour représenter le robot
        gui.addGraphicalElement(new gui.Oval(
                centre_x,
                centre_y,
                robotColor,
                robotColor,
                caseSize / 2));

        // Ajoute le symbole du type de robot
        Text symbolText = new Text(
                centre_x,
                centre_y,
                Color.WHITE, // Texte blanc pour contraster avec le fond coloré
                getRobotSymbol(robot));
        gui.addGraphicalElement(symbolText);
    }
    /**
     * Retourne le symbole textuel représentant le type du robot.
     * 
     * @param robot L'objet Robot dont le symbole textuel doit être retourné.
     * @return Une chaîne de caractères représentant le symbole du robot (par exemple "D" pour Drone).
     */
    private static String getRobotSymbol(Robot robot) {
        if (robot instanceof RobotDrone) {
            return "D";
        } else if (robot instanceof RobotChenille) {
            return "Ch";
        } else if (robot instanceof RobotPattes) {
            return "P";
        } else if (robot instanceof RobotRoues) {
            return "R";
        }
        return "?";
    }

}
