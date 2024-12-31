package robots;

import classes.*;
/**
 * Classe représentant un robot à pattes. Ce type de robot est adapté pour se déplacer
 * sur divers terrains, à l'exception de l'eau, avec des performances spécifiques pour chaque type.
 * Le robot à pattes dispose d'une capacité infinie d'eau et ne nécessite pas de remplissage.
 */
public class RobotPattes extends Robot {
    /**
     * Constructeur de la classe RobotPattes.
     *
     * @param position La position initiale du robot.
     * @param vitesse La vitesse initiale du robot (en km/h).
     * @throws IllegalArgumentException Si la position initiale est de type EAU.
     */
    public RobotPattes(Case position, int vitesse) {
        super(position, vitesse);
        this.capacite = Integer.MAX_VALUE; // capacité infinie
        this.volumeEau = Integer.MAX_VALUE;
        this.tempsRemplissageParLitre = 0.0; // n'effectue jamais le remplissage
        this.tempsDeversementParLitre = 1.0 / 10.0; // vide 10l en une seconde.
        if (position.getNatureTerrain() == NatureTerrain.EAU) {
            throw new IllegalArgumentException("Le robot à Pattes ne peut pas se rendre sur de l'eau.\n");
        }
    }
    /**
     * Renvoie la vitesse du robot en fonction de la nature du terrain.
     *
     * @param nature Le type de terrain.
     * @return La vitesse du robot (en km/h). Retourne 0 pour l'eau, 10 pour les rochers,
     *         ou la vitesse normale pour les autres terrains.
     */
    @Override
    public int getVitesseNature(NatureTerrain nature) {
        if (nature == NatureTerrain.EAU) {
            return 0; // Ne peut pas se déplacer sur l'eau
        } else if (nature == NatureTerrain.ROCHE) {
            return 10; // Réduction de la vitesse sur les rochers
        } else {
            return this.getVitesse();
        }
    }
    /**
     * Renvoie la vitesse par défaut du robot en fonction du type de terrain.
     *
     * @param terrain Le type de terrain.
     * @return La vitesse par défaut (en km/h). 0 pour l'eau, 10 pour les rochers,
     *         ou 60 pour les autres terrains.
     */
    public int defaultvitesse(NatureTerrain terrain) {
        if (terrain == NatureTerrain.EAU) {
            return 0; // Ne peut pas de déplacer sur l'eau
        } else if (terrain == NatureTerrain.ROCHE) {
            return 10; // km/h
        }
        // Pour les autres natures de terrain
        return 60; // km/h
    }
    /**
     * Modifie la position actuelle du robot.
     * Le robot à pattes ne peut pas se déplacer sur l'eau.
     *
     * @param position La nouvelle position.
     * @throws IllegalArgumentException Si la position est invalide (null ou de type EAU).
     */
    public void setPosition(Case position) {
        try {
            if (position == null) {
                throw new IllegalArgumentException("La nouvelle position est invalide.");
            }
            if (position.getNatureTerrain() == NatureTerrain.EAU) {
                throw new IllegalArgumentException("Le robot à pattes ne peut pas se rendre sur de l'eau.");
            }

            // Si les conditions sont satisfaites, mise à jour de la position
            this.position = position;

        } catch (IllegalArgumentException e) {
            System.err.println("Erreur : " + e.getMessage());
        }
    }
    /**
     * Implémente la méthode de remplissage du réservoir.
     * Ce robot n'effectue jamais de remplissage en raison de sa capacité infinie.
     *
     * @param tab La carte représentant l'environnement (non utilisée ici).
     */
    public void remplirReservoir(Carte tab) {
        // Le robot à pattes ne se remplit jamais.
    }
}
