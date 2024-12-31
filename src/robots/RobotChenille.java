package robots;

import classes.*;
/**
 * Classe représentant un robot à chenilles. Ce type de robot est conçu pour se déplacer
 * efficacement sur certains types de terrains et possède un réservoir d'eau pour lutter
 * contre les incendies.
 * 
 * Restrictions :
 * - Ne peut pas se déplacer sur des terrains de type EAU ou ROCHE.
 * - Sa vitesse est réduite de 50 % lorsqu'il se déplace sur un terrain de type FORET.
 */
public class RobotChenille extends Robot {
     /**
     * Constructeur de la classe RobotChenille.
     *
     * @param position La position initiale du robot.
     * @param vitesse La vitesse initiale du robot (en km/h).
     * @throws IllegalArgumentException Si la position initiale est sur un terrain de type EAU ou ROCHE.
     */
    public RobotChenille(Case position, int vitesse) {
        super(position, vitesse);
        this.capacite = 2000;
        this.volumeEau = 2000;
        this.tempsRemplissageParLitre = 300.0 / (double) capacite; // 5 min pour remplissage complet
        this.tempsDeversementParLitre = 8.0 / 100.0;
        if (position.getNatureTerrain() == NatureTerrain.EAU || position.getNatureTerrain() == NatureTerrain.ROCHE) {
            throw new IllegalArgumentException(
                    "Le robot à chenille ne peut pas se rendre sur de l’eau ou du rocher..\n");
        }
    }
    /**
     * Renvoie la vitesse du robot en fonction du terrain actuel.
     *
     * @param nature Le type de terrain.
     * @return La vitesse adaptée au terrain (en km/h).
     *         Retourne 0 si le terrain est de type EAU ou ROCHE.
     */
    @Override
    public int getVitesseNature(NatureTerrain nature) {
        if (nature == NatureTerrain.FORET) {
            return this.getVitesse() / 2; // Réduction de 50% en forêt
        } else if (nature == NatureTerrain.EAU || nature == NatureTerrain.ROCHE) {
            return 0; // Ne peut pas se déplacer sur l'eau ou les rochers
        } else {
            return this.getVitesse();
        }
    }
    /**
     * Renvoie la vitesse par défaut pour un type de terrain donné.
     *
     * @param terrain Le type de terrain.
     * @return La vitesse par défaut (en km/h).
     * @throws IllegalArgumentException Si le terrain est de type EAU ou ROCHE.
     */
    public int defaultvitesse(NatureTerrain terrain) {
        int res;
        if (terrain == NatureTerrain.TERRAIN_LIBRE || terrain == NatureTerrain.HABITAT
                || terrain == NatureTerrain.FORET) {
            res = 60; // km/h
        } else {
            throw new IllegalArgumentException("Le robot à roues ne peut pas se déplacer sur le terrain " + terrain);
        }
        if (terrain == NatureTerrain.FORET) {
            res /= 2;
        }
        return res;
    }
    /**
     * Modifie la position actuelle du robot.
     *
     * @param position La nouvelle position.
     * @throws IllegalArgumentException Si la nouvelle position est sur un terrain de type EAU ou ROCHE.
     */
    public void setPosition(Case position) {

        try {
            if (position == null) {
                throw new IllegalArgumentException("La nouvelle position est invalide.");
            }

            if (position.getNatureTerrain() == NatureTerrain.EAU
                    || position.getNatureTerrain() == NatureTerrain.ROCHE) {
                throw new IllegalArgumentException(
                        "Le robot à chenilles ne peut pas se rendre sur de l’eau ou du rocher.");
            }
            this.position = position;

        } catch (IllegalArgumentException e) {
            System.err.println("Erreur : " + e.getMessage());
        }
    }
    /**
     * Remplit le réservoir d'eau du robot.
     *
     * @param tab La carte représentant l'environnement.
     * @throws IllegalArgumentException Si le robot n'est pas à côté d'une case contenant de l'eau.
     */
    public void remplirReservoir(Carte tab) {
        if (this.getPosition().possedeVoisindeType(NatureTerrain.EAU, tab)) {
            this.setVolumeEau(this.capacite);
        } else {
            throw new IllegalArgumentException(
                    "Remplissage échoué! Nécéssité de présente à côté d'une case contenant de l'eau.\n");
        }
    }
}
