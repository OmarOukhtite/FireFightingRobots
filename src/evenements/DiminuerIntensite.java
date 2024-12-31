package evenements;

import classes.DonneesSimulation;
import classes.Incendie;
/**
 * Représente un événement qui diminue l'intensité d'un incendie dans la simulation.
 * 
 * Cet événement modifie l'intensité de l'incendie en fonction du nouvel état donné. 
 * Il est exécuté instantanément, modifiant directement l'intensité de l'incendie.
 */
public class DiminuerIntensite extends Evenement {
    private long date;
    private DonneesSimulation donneesSimulation;
    private Incendie incendie;
    private int newIntensite;
    /**
     * Constructeur de l'événement DiminuerIntensite.
     * 
     * Initialise l'événement avec la date de l'événement, les données de simulation,
     * l'incendie à modifier et la nouvelle intensité à appliquer.
     * 
     * @param date La date de l'événement (en millisecondes).
     * @param donnees Les données de simulation associées à cet événement.
     * @param incendie L'incendie dont l'intensité va être diminuée.
     * @param newIntensite La nouvelle intensité de l'incendie.
     */
    public DiminuerIntensite(long date, DonneesSimulation donnees, Incendie incendie, int newIntensite) {
        super(date, donnees);
        this.incendie = incendie;
        this.newIntensite = newIntensite;
    }

    public Incendie getIncendie() {
        return this.incendie;
    }

    @Override
    public void execute() {
        this.incendie.setIntensite(newIntensite);
    }
    /**
     * Retourne la durée de l'événement.
     * 
     * Étant un événement instantané, la durée de cet événement est toujours de 1 pour éviter
     * le chevauchement avec d'autres événements.
     * 
     * @return La durée de l'événement en millisecondes (1 pour cet événement).
     */
    @Override
    public long getDuree() {
        return 1; // c'est un évènement instantanné, on ajoute 1 pour éviter le chevauchement avec d'autres évènements
    }
}
