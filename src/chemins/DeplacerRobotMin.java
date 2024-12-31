package chemins;

import classes.*;
import evenements.*;
import java.util.ArrayList;
import java.util.List;
import robots.*;
/**
 * La classe DeplacerRobotMin génère les événements nécessaires pour déplacer un robot
 * d'une case de départ à une case d'arrivée en suivant le chemin optimal basé sur le 
 * temps minimal de déplacement. 
 */
public class DeplacerRobotMin {
    /**
     * Calcule et génère une liste d'événements permettant de déplacer un robot d'une case
     * de départ à une case d'arrivée, en utilisant le chemin avec le temps de déplacement minimal.
     *
     * @param depart   La case de départ du robot.
     * @param arrivee  La case de destination du robot.
     * @param robot    Le robot qui effectue le déplacement.
     * @param donnees  Les données de simulation contenant la carte et les informations
     *                 nécessaires pour calculer le chemin.
     * @param date     La date initiale à partir de laquelle le déplacement commence.
     * @return Une liste d'événements représentant les étapes du déplacement du robot.
     *         Si aucun chemin n'existe, retourne une liste vide.
     */
    public static List<Evenement> DeplacementMin(Case depart, Case arrivee, Robot robot, DonneesSimulation donnees, long date) {
        Carte carte = donnees.getCarte();
        CheminMinimum chemin = new CheminMinimum();
        List<Case> chemin_min = chemin.trouverCheminMinimum(carte, depart, arrivee, robot);
        List<Evenement> chemin_parcouru = new ArrayList<>();
        long date_ev = date;

        if (chemin_min == null || chemin_min.isEmpty()) {
            return chemin_parcouru;
        }

        for (int i = 0; i < chemin_min.size() - 1; i++) {
            Case case_depart = chemin_min.get(i);
            Case case_arrivee = chemin_min.get(i + 1);
            
            Evenement ev = new Deplacer(date_ev, robot, case_arrivee, donnees);
            long duree_ev = ev.getDuree();
            
            date_ev += duree_ev;
            chemin_parcouru.add(ev);
        }
        return chemin_parcouru;
    }
}
