package intervention;

import chemins.*;
import classes.*;
import evenements.*;
import helper.*;
import java.util.ArrayList;
import java.util.List;
import robots.*;
/**
 * Classe représentant l'intervention d'un robot pour éteindre un incendie dans une simulation.
 * 
 * Cette classe gère le processus de déplacement du robot, de déversement d'eau sur l'incendie, et de gestion de l'approvisionnement en eau. 
 * Elle prend en compte les différents événements associés à l'intervention et retourne un résultat de l'intervention sous forme d'événements.
 */
public class EteindreIncendie {
    private long dureeEteindreIncendie;
    /**
     * Obtient la durée nécessaire pour éteindre l'incendie.
     * 
     * @return La durée de l'intervention pour éteindre l'incendie en millisecondes.
     */
    public long getDureeEteindreIncendie() {
        return dureeEteindreIncendie;
    }

    public void setDureeEteindreIncendie(long duree) {
        this.dureeEteindreIncendie = duree;
    }
    /**
     * Effectue l'intervention d'un robot pour éteindre un incendie.
     * 
     * Cette méthode gère le processus de déplacement du robot vers l'incendie, 
     * le déversement d'eau pour réduire l'intensité de l'incendie, et le remplissage du réservoir d'eau si nécessaire.
     * Elle crée et ajoute les événements associés à ces actions à une liste d'événements.
     * 
     * @param robot Le robot qui intervient pour éteindre l'incendie.
     * @param positionRobot La position initiale du robot dans la simulation.
     * @param positionIncendie La position de l'incendie dans la simulation.
     * @param positionRemplissageEau La position de la source d'eau pour remplir le réservoir du robot.
     * @param donnees Les données de la simulation contenant la carte, les incendies et les robots.
     * @param dateDebut La date de début de l'intervention.
     * @return Un objet `ResultatIntervention` contenant les événements générés par l'intervention et la position finale du robot.
     */
    public ResultatIntervention intervenir(Robot robot, Case positionRobot, Case positionIncendie, Case positionRemplissageEau, DonneesSimulation donnees, long dateDebut) {
        List<Evenement> events = new ArrayList<>();
        List<Case> casesEau = donnees.getCasesEau();
        long date1 = dateDebut;
        long date3 = 0;
        long date2, date4;
        int ligne = positionIncendie.getLigne();
        int colonne = positionIncendie.getColonne();
        Incendie incendie = donnees.getIncendie(donnees.getCase(ligne, colonne));
        int intensite = incendie.getIntensite();



        while (true) {


            if (positionRemplissageEau == null) {
                break;
            }

            // Le robot se déplace vers l'incendie
            List<Evenement> events1 = DeplacerRobotMin.DeplacementMin(positionRobot, donnees.getCase(ligne, colonne), robot, donnees, date1);
            if (events1.isEmpty()) {
                return null;  // Aucun chemin vers l'incendie
            }
            events.addAll(events1);
            date2 = date1 + Helper.CalculDureeEvents(events1) + 1;

            // Le robot déverse de l'eau
            int VolumeADeverser = Math.min(intensite, robot.getVolumeEau());
            Evenement event2 = new DeverserEau(date2, robot, VolumeADeverser, donnees);
            intensite -= VolumeADeverser;
            robot.setVolumeEau(robot.getVolumeEau() - VolumeADeverser);
            events.add(event2);

            date3 = date2 + event2.getDuree() + 1;
            // On diminue l'intensité de l'incendie
            Evenement diminuer = new DiminuerIntensite(date3, donnees, incendie, intensite);
            events.add(diminuer);

            // Si le réservoir est vide ou l'incendie n'est pas éteint
            if (intensite > 0 || robot.getVolumeEau() == 0) {
                boolean foundWaterSource = false;
                List<Evenement> events3 = new ArrayList<>();

                if (positionRemplissageEau != null) {
                    events3 = DeplacerRobotMin.DeplacementMin(positionIncendie, positionRemplissageEau, robot, donnees, date3);
                    if (!events3.isEmpty()) {
                        events.addAll(events3);
                        date4 = date3 + Helper.CalculDureeEvents(events3) + 1;

                        // Remplissage du réservoir
                        Evenement event4 = new RemplirEau(date4, robot, donnees);
                        events.add(event4);
                        robot.setVolumeEau(robot.getCapacite());

                        date1 = date4 + event4.getDuree() + 1;
                        positionRobot = positionRemplissageEau;
                        foundWaterSource = true;
                    }
                }
                
                if (!foundWaterSource) {
                    // Pas de source d'eau accessible pour remplir le réservoir
                    break;
                }

                if (intensite <= 0 && robot.getVolumeEau() > 0) {
                    break;
                }
            } else {
                positionRobot = donnees.getCase(ligne, colonne);
                break;
            }
        }

        this.setDureeEteindreIncendie(Math.max(date1, date3) - dateDebut);
        return new ResultatIntervention(events, positionRobot);
    }
}