package simulation;

import gui.GUISimulator;
import gui.Simulable;
import dessin.*;
import classes.*;
import robots.*;
import evenements.*;
/**
 * Classe Simulateur pour gérer et exécuter une simulation d'intervention de robots pompiers.
 * Cette classe orchestre l'exécution des événements, gère le temps de simulation,
 * et met à jour l'interface graphique.
 */
public class Simulateur implements Simulable {
    private long dateSimulation;
    private Scenario scenario;
    private GUISimulator gui;
    private DonneesSimulation donnees;
    private String fichier;
    private Boolean StratEvoluee;
    /**
     * Constructeur de la classe Simulateur.
     *
     * @param fichier       Le chemin du fichier contenant les données de simulation.
     * @param scenario      Le scénario avec les événements à exécuter (peut être null au départ).
     * @param gui           L'interface graphique pour l'affichage de la simulation.
     * @param donnees       Les données de simulation (carte, robots, incendies).
     * @param StratEvoluee  Un booléen indiquant si une stratégie avancée est utilisée.
     */
    public Simulateur(String fichier, Scenario scenario, GUISimulator gui, DonneesSimulation donnees, Boolean StratEvoluee) {
        this.dateSimulation = 0;
        this.scenario = scenario;
        this.gui = gui;
        gui.setSimulable(this);
        this.donnees = donnees;
        this.fichier = fichier;
        this.StratEvoluee = StratEvoluee;
    }
    /**
     * Définit un nouveau scénario pour la simulation.
     *
     * @param scenario Le scénario à définir.
     */
    public void setScenario(Scenario scenario) {
        this.scenario = scenario;
    }

    private void incrementeDate() {
        this.dateSimulation++;
    }
    /**
     * Passe à la prochaine étape de la simulation.
     * Exécute les événements prévus pour la date actuelle et met à jour l'affichage.
     */
    @Override
    public void next() {
        incrementeDate();
        if (simulationTerminee()) {
            System.out.println("Simulation terminée");
            return;
        }
        
        while (!this.scenario.getEvenements().isEmpty() && scenario.getEvenements().peek().getDate() <= dateSimulation) {
            Evenement e = scenario.getEvenements().poll();
            e.execute();
            
            // Si l'événement est de type Deplacer, afficher la position actuelle du robot
            if (e instanceof Deplacer) {
                ((Deplacer) e).getRobot();
            }
            // Mise à jour de l'affichage
            this.gui.reset();
            DessineDonnees.drawDonnees(donnees, this.gui);
        }
        
        
    }
    /**
     * Redémarre la simulation depuis le début.
     */
    @Override
    public void restart() {
        LancerSimu.lancerSimu(fichier, this.gui, this.StratEvoluee);
        System.out.println("Restart");
    }

    private boolean simulationTerminee() {
        return this.scenario.getEvenements().isEmpty();
    }

    public long getDateSimulation() {
        return this.dateSimulation;
    }
} 