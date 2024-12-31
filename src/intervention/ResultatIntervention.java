package intervention;

import classes.*;
import evenements.*;
import java.util.List;
/**
 * Classe représentant le résultat d'une intervention effectuée par un robot dans la simulation.
 * 
 * Cette classe encapsule les événements générés lors de l'intervention (comme le déplacement du robot, le déversement d'eau, etc.) 
 * ainsi que la position finale du robot après avoir terminé l'intervention.
 */
public class ResultatIntervention {
    private List<Evenement> events;
    private Case positionFinale;
    /**
     * Constructeur de la classe `ResultatIntervention`.
     * 
     * @param events La liste des événements générés lors de l'intervention.
     * @param positionFinale La position finale du robot après l'intervention.
     */
    public ResultatIntervention(List<Evenement> events, Case positionFinale) {
        this.events = events;
        this.positionFinale = positionFinale;
    }
    /**
     * Obtient la liste des événements générés par l'intervention.
     * 
     * @return La liste des événements associés à l'intervention.
     */
    public List<Evenement> getEvents() {
        return events;
    }
    /**
     * Obtient la position finale du robot après l'intervention.
     * 
     * @return La position finale du robot.
     */
    public Case getPositionFinale() {
        return positionFinale;
    }
}
