package evenements;

import classes.*;
import robots.*;
/**
 * Représente un événement de remplissage du réservoir d'eau d'un robot dans la simulation.
 * 
 * Cet événement permet de remplir le réservoir d'un robot en fonction de sa capacité maximale et de son
 * volume d'eau actuel. L'événement est exécuté sur le robot en appelant la méthode `remplirReservoir`.
 */
public class RemplirEau extends Evenement {
    private Robot robot;
    private int volumeEauARemplir;
    /**
     * Constructeur de l'événement RemplirEau.
     * 
     * Initialise l'événement avec la date de l'événement, le robot concerné et le volume d'eau à remplir.
     * Le volume d'eau à remplir est calculé en fonction de la capacité du robot et de l'eau déjà présente dans son réservoir.
     * 
     * @param date La date de l'événement (en millisecondes).
     * @param robot Le robot qui doit remplir son réservoir d'eau.
     * @param donnees Les données de simulation associées à cet événement.
     */
    public RemplirEau(long date, Robot robot, DonneesSimulation donnees) {
        super(date, donnees);
        this.robot = robot;
        this.volumeEauARemplir = robot.getCapacite() - robot.getVolumeEau();
    }

    public Robot getRobot() {
        return robot;
    }

    public int getVolumeEauARemplir() {
        return volumeEauARemplir;
    }

    @Override
    public void execute() {
        robot.remplirReservoir(this.donnees.getCarte());
    }

    @Override
    public long getDuree() {
        return this.robot.getTempsRemplissage(volumeEauARemplir);
    }
}
