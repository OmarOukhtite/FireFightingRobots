package classes;

import java.util.ArrayList;
import java.util.List;
import robots.*;

/**
 * La classe DonneesSimulation représente les données nécessaires pour la simulation.
 * Elle contient une carte, une liste d'incendies, une liste de robots et les positions des sources d'eau.
 */
public class DonneesSimulation {
    private Carte carte;
    private List<Incendie> incendies;
    private List<Robot> robots;
    private List<Case> casesEau;
    /**
     * Constructeur de la classe DonneesSimulation.
     * 
     * @param carte La carte utilisée pour la simulation.
     */
    public DonneesSimulation(Carte carte) {
        this.carte = carte;
        this.incendies = new ArrayList<>();
        this.robots = new ArrayList<>();
        this.casesEau = new ArrayList<>();
    }

    public Carte getCarte() { 
        return carte; 
    }

    public Case getCase(int ligne, int colonne) {
        return carte.getCase(ligne, colonne);
    }

    public List<Incendie> getIncendies() { 
        return incendies;
    }

    public List<Case> getCasesEau() {
        return casesEau;
    }
    /**
     * Définit les cases contenant de l'eau.
     * 
     * @param cases Liste des cases contenant de l'eau.
     */
    public void setCasesEau(List<Case> cases) {
        for (Case position : cases) {
            this.casesEau.add(position);
        }
    }
    /**
     * Retourne un incendie situé sur une position donnée.
     * 
     * @param position La position à vérifier.
     * @return L'incendie trouvé, ou null s'il n'y a pas d'incendie à cette position.
     */
    public Incendie getIncendie(Case position) {
        for (Incendie incendie : incendies) {
            if (incendie.getPosition().estEgal(position)) {
                return incendie;
            }
        }
        System.out.println("Aucun incendie trouvé à la position : " + position);
        return null; // Retourne null s'il n'y a pas d'incendie dans la position donnée
    }

    public List<Robot> getRobots() { 
        return robots; 
    }

    public void setCarte(Carte carte){
        this.carte = carte;
    }

    public void setIncendies(List<Incendie> incendies){
        this.incendies = incendies;
    }

    public void setRobots(List<Robot> robots){
        this.robots = robots;
    }
    /**
     * Ajoute un incendie à la liste des incendies.
     * 
     * @param incendie L'incendie à ajouter.
     */
    public void ajouteIncendie(Incendie incendie) {
        incendies.add(incendie);
    }
    
    public void ajouteRobot(Robot robot) {
        robots.add(robot);
    }
}