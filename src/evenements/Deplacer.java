package evenements;

import classes.*;
import robots.*;
/**
 * Représente un événement de déplacement d'un robot dans la simulation.
 * 
 * Cet événement modifie la position d'un robot en le déplaçant vers une case cible spécifique.
 * Il calcule également la durée nécessaire pour effectuer ce déplacement en fonction de la vitesse du robot
 * et de la nature du terrain de la case destination.
 */
public class Deplacer extends Evenement {
    private Robot robot;
    private Case destination;
    private DonneesSimulation donnees;
    /**
     * Constructeur de l'événement Deplacer.
     * 
     * Initialise l'événement avec la date de l'événement, le robot à déplacer, 
     * la destination du robot, et les données de simulation.
     * 
     * @param date La date de l'événement (en millisecondes).
     * @param robot Le robot à déplacer.
     * @param destination La case destination vers laquelle le robot se déplace.
     * @param donnees Les données de simulation associées à cet événement.
     */
    public Deplacer(long date, Robot robot, Case destination, DonneesSimulation donnees) {
        super(date, donnees);
        this.robot = robot;
        this.destination = destination;
        this.donnees = donnees;
    }

    public Robot getRobot() {
        return this.robot;
    }
    /**
     * Exécute l'événement de déplacement en mettant à jour la position du robot et en ajustant sa vitesse 
     * en fonction du type de terrain de la destination.
     * 
     * Cette méthode modifie la position du robot et réévalue sa vitesse selon la nature du terrain.
     * Si la destination est invalide, une exception est capturée et un message d'erreur est affiché.
     */
    @Override
    public void execute() {
    try {
        robot.setPosition(this.destination);
        robot.setVitesse(robot.getVitesseNature(this.destination.getNatureTerrain()));
    } catch (NullPointerException e) {
        System.out.println("Destination invalide.");
    }
    }
        
    /**
     * Calcule la durée nécessaire pour effectuer le déplacement du robot vers la destination.
     * 
     * La durée est calculée en fonction de la vitesse moyenne du robot sur le terrain, 
     * tenant compte de la vitesse du robot et de la nature du terrain.
     * La durée est exprimée en millisecondes.
     * 
     * @return La durée du déplacement en millisecondes.
     */
    @Override
    public long getDuree() {
        Carte carte = this.donnees.getCarte();
        int vitesse1 = robot.getVitesse();
        int vitesse2 = robot.getVitesseNature(this.destination.getNatureTerrain());
        double vitesseMoyenne = ((double)(vitesse1 + vitesse2)) / (3.6*2.0); 
        // On divise par 3.6 pour convertir la vitesse de km/h à m/s
        return (long)Math.ceil(((double)carte.getTailleCases()) /vitesseMoyenne);
    }

    @Override
    public String toString() {
        return("Deplacement de " + robot + "vers " + " a la date : " + this.getDate());
    }
}

