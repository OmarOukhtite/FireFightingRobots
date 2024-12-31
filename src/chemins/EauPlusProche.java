package chemins;

import classes.*;
import evenements.*;
import helper.*;
import java.util.HashMap;
import java.util.List;
import robots.*;
/**
 * La classe EauPlusProche fournit des outils pour trouver les sources d'eau les plus proches
 * d'une position donnée, en tenant compte des contraintes du robot.
 */
public class EauPlusProche {
    /**
     * Trouve la source d'eau la plus proche d'une position d'incendie pour un robot donné.
     * 
     * @param robot             Le robot pour lequel on cherche une source d'eau.
     * @param positionIncendie  La position de l'incendie nécessitant une intervention.
     * @param casesEau          La liste des cases contenant de l'eau.
     * @param donnees           Les données de simulation, incluant la carte et les terrains.
     * @return La case la plus proche où le robot peut se remplir en eau.
     */
    public static Case eauPlusProche(Robot robot, Case positionIncendie, List<Case> casesEau, DonneesSimulation donnees) {
        Case positionRemplissageEau = donnees.getCasesEau().get(0);  
        long tempsMin = Long.MAX_VALUE;

        for (Case positionEau : casesEau) {
            if (robot instanceof RobotDrone) {
                List<Evenement> eauPlusProche = DeplacerRobotMin.DeplacementMin(positionIncendie, positionEau, robot, donnees, 0);
                if (!eauPlusProche.isEmpty()) {
                    long temps = Helper.CalculDureeEvents(eauPlusProche);
                    if (temps < tempsMin) {
                        tempsMin = temps;
                        positionRemplissageEau = positionEau;
                    }
                }
            } else { 
                for (Direction dir : Direction.values()) {
                    Case voisinEau = donnees.getCarte().getVoisin(positionEau, dir);
                    if (voisinEau != null && voisinEau.getNatureTerrain() != NatureTerrain.EAU) {
                        List<Evenement> eauPlusProche = DeplacerRobotMin.DeplacementMin(positionIncendie, voisinEau, robot, donnees, 0);
                        if (!eauPlusProche.isEmpty()) {
                            long temps = Helper.CalculDureeEvents(eauPlusProche);
                            if (temps < tempsMin) {
                                tempsMin = temps;
                                positionRemplissageEau = voisinEau;
                            }
                        }
                    }
                }
            }
        }

        return positionRemplissageEau;
    }

    /**
     * Crée une map associant chaque incendie à la source d'eau la plus proche pour un robot donné.
     * 
     * @param Incendies   La liste des incendies présents dans la simulation.
     * @param casesEau    La liste des cases contenant de l'eau.
     * @param robot       Le robot pour lequel la map est générée.
     * @param donnees     Les données de simulation, incluant la carte et les terrains.
     * @return Une HashMap associant une clé (robot + position d'incendie) à la case d'eau la plus proche.
     */
    public static HashMap<RobotIncendieKey, Case> creerMapIncendieEau(List<Incendie> Incendies, List<Case> casesEau, Robot robot, DonneesSimulation donnees) {
        HashMap<RobotIncendieKey, Case> incendieVersEauMap = new HashMap<>();

        for (Incendie Incendie : Incendies) {
            Case caseEauProche;
            Case positionIncendie = Incendie.getPosition();

            caseEauProche = EauPlusProche.eauPlusProche(robot, positionIncendie, casesEau, donnees);
            if (caseEauProche != null) {
                RobotIncendieKey key = new RobotIncendieKey(robot, positionIncendie);
                incendieVersEauMap.put(key, caseEauProche);
            }
        }

        return incendieVersEauMap;
    }
}

    
