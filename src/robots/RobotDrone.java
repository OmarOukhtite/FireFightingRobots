package robots;

import classes.*;
/**
 * Classe représentant un robot drone. Ce type de robot a la capacité de se déplacer 
 * sur tous types de terrains sans restriction et dispose d'une grande capacité d'eau 
 * pour lutter contre les incendies.
 */
public class RobotDrone extends Robot {

    /**
     * Constructeur de la classe RobotDrone.
     *
     * @param position La position initiale du robot.
     * @param vitesse La vitesse initiale du robot (en km/h).
     */
    public RobotDrone(Case position, int vitesse) {
        super(position, vitesse);
        this.capacite = 10000;
        this.volumeEau = 10000;
        this.tempsRemplissageParLitre = 1800.0 / (double) capacite; // 30 min pour remplissage complet
        this.tempsDeversementParLitre = 30.0 / (double) capacite; // vide le reservoir en 30 secondes
    }
    /**
     * Renvoie la vitesse du drone pour tout type de terrain.
     * Le drone n'est pas affecté par la nature du terrain.
     *
     * @param nature Le type de terrain.
     * @return La vitesse du drone (en km/h).
     */
    @Override
    public int getVitesseNature(NatureTerrain nature) {
        return this.getVitesse();
    }
    /**
     * Renvoie la vitesse par défaut du drone, indépendante du terrain.
     *
     * @param terrain Le type de terrain.
     * @return La vitesse par défaut du drone (en km/h).
     */
    public int defaultvitesse(NatureTerrain terrain) {
        return 100;
    }
    /**
     * Modifie la position actuelle du drone.
     * Le drone peut se déplacer sur tous les types de terrains sans restriction.
     *
     * @param position La nouvelle position.
     * @throws IllegalArgumentException Si la nouvelle position est invalide (null).
     */
    public void setPosition(Case position) {
        try {
            if (position == null) {
                throw new IllegalArgumentException("La nouvelle position est invalide.");
            }
            this.position = position;
        } catch (IllegalArgumentException e) {
            System.out.println("Erreur : " + e.getMessage());
        }
    }
    /**
     * Remplit le réservoir d'eau du drone.
     *
     * @param carte La carte représentant l'environnement.
     * @throws IllegalArgumentException Si le drone n'est pas sur une case contenant de l'eau.
     */
    public void remplirReservoir(Carte carte) {
        if (this.getPosition().getNatureTerrain() == NatureTerrain.EAU) {
            this.setVolumeEau(10000);
        } else {
            throw new IllegalArgumentException(
                    "Remplissage échoué! Nécéssité de présence sur une case contenant de l'eau.\n");
        }
    }
}
