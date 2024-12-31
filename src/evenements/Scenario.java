package evenements;

import java.util.List;
import java.util.PriorityQueue;
/**
 * Représente un scénario contenant une liste d'événements à exécuter dans la simulation.
 * 
 * Cette classe permet d'ajouter des événements à un scénario, de récupérer les événements
 * dans l'ordre d'exécution (priorisés par date), et de les afficher sous forme de chaîne de caractères.
 */
public class Scenario {
    private PriorityQueue<Evenement> evenements;
    /**
     * Constructeur de la classe Scenario.
     * 
     * Crée un scénario vide avec une priorité basée sur la date des événements.
     * 
     * Les événements sont stockés dans une `PriorityQueue` qui garantit qu'ils sont
     * traités dans l'ordre de leur date (la priorité étant la date de l'événement).
     */
    public Scenario() {
        this.evenements = new PriorityQueue<>();
    }
    /**
     * Retourne la liste des événements du scénario.
     * 
     * @return La `PriorityQueue` contenant les événements du scénario.
     */
    public PriorityQueue<Evenement> getEvenements() {
        return evenements;
    }

    /**
     * Ajoute un événement au scénario.
     * 
     * Si l'événement est non nul, il est ajouté à la `PriorityQueue`. Si l'événement est nul,
     * un message d'erreur est affiché.
     * 
     * @param e L'événement à ajouter au scénario.
     */
    public void ajouteEvenement(Evenement e) {
        if (e != null) {
            this.evenements.add(e);
        }
        else {
            System.out.println("Evenement vide, non ajouté au scénario.");
        }
    }
    /**
     * Ajoute plusieurs événements à la liste du scénario.
     * 
     * Les événements sont ajoutés un par un à la `PriorityQueue` du scénario.
     * 
     * @param events La liste d'événements à ajouter.
     */
    public void ajouteEvenements(List<Evenement> events) {
        for (Evenement e : events) {
            ajouteEvenement(e);
        }
    }

    public String toString() {
        String res = "";
        for (Evenement event : this.evenements) {
            res += event.toString();
            res += "\n";
        }
        return res;
    }
}
