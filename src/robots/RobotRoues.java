package robots;

import classes.*;
/**
 * Classe représentant un robot à roues. Ce type de robot est conçu pour se déplacer
 * uniquement sur des terrains libres ou des habitats. Il possède une capacité limitée
 * et peut remplir ou vider son réservoir selon des temps spécifiques.
 */
public class RobotRoues extends Robot {
    /**
     * Constructeur de la classe RobotRoues.
     *
     * @param position La position initiale du robot.
     * @param vitesse La vitesse initiale du robot (en km/h).
     * @throws IllegalArgumentException Si la position initiale n'est pas un terrain libre ou un habitat.
     */
    public RobotRoues(Case position, int vitesse) {
        super(position, vitesse);
        this.capacite = 5000;
        this.volumeEau = 5000;
        this.tempsRemplissageParLitre = 600.0 / (double) capacite; // 10 min pour remplissage complet
        this.tempsDeversementParLitre = 5.0 / 100.0; // vide 100L en 5 secondes
        if (position.getNatureTerrain() != NatureTerrain.TERRAIN_LIBRE
                && position.getNatureTerrain() != NatureTerrain.HABITAT) {
            throw new IllegalArgumentException(
                    "Le robot à roues ne peut se déplacer que sur du terrain libre ou habitat.\n");
        }
    }
    /**
     * Renvoie la vitesse du robot en fonction de la nature du terrain.
     *
     * @param nature Le type de terrain.
     * @return La vitesse du robot (en km/h) sur les terrains libres ou les habitats,
     *         ou 0 pour les autres terrains.
     */
    @Override
    public int getVitesseNature(NatureTerrain nature) {
        if (nature == NatureTerrain.TERRAIN_LIBRE || nature == NatureTerrain.HABITAT) {
            return this.getVitesse();
        } else {
            return 0; // Ne peut pas se déplacer sur les autres terrains
        }
    }
    /**
     * Renvoie la vitesse par défaut du robot sur un type de terrain donné.
     *
     * @param terrain Le type de terrain.
     * @return La vitesse par défaut (en km/h) sur les terrains libres ou habitats.
     * @throws IllegalArgumentException Si le terrain n'est pas libre ou habitat.
     */
    public int defaultvitesse(NatureTerrain terrain) {
        if (terrain == NatureTerrain.TERRAIN_LIBRE || terrain == NatureTerrain.HABITAT) {
            return 80; // km/h
        } else {
            throw new IllegalArgumentException("Le robot à roues ne peut pas se déplacer sur le terrain " + terrain);
        }
    }
    /**
     * Modifie la position actuelle du robot.
     * Le robot à roues ne peut se déplacer que sur des terrains libres ou des habitats.
     *
     * @param position La nouvelle position.
     * @throws IllegalArgumentException Si la position est invalide ou inaccessible pour le robot.
     */
    public void setPosition(Case position) {
        try {
            if (position == null) {
                throw new IllegalArgumentException("La nouvelle position est invalide.");
            }

            if (position.getNatureTerrain() != NatureTerrain.TERRAIN_LIBRE
                    && position.getNatureTerrain() != NatureTerrain.HABITAT) {
                throw new IllegalArgumentException(
                        "Le robot à roues ne peut se déplacer que sur du terrain libre ou habitat.");
            }

            // Si toutes les conditions sont remplies, on peut mettre à jour la position
            this.position = position;

        } catch (IllegalArgumentException e) {
            System.out.println("Erreur : " + e.getMessage());
        }
    }
    /**
     * Remplit le réservoir du robot si celui-ci est à proximité d'une case contenant de l'eau.
     *
     * @param tab La carte représentant l'environnement.
     * @throws IllegalArgumentException Si le robot n'est pas adjacent à une case contenant de l'eau.
     */
    public void remplirReservoir(Carte tab) {
        if (this.getPosition().possedeVoisindeType(NatureTerrain.EAU, tab)) {
            this.setVolumeEau(5000);
        } else {
            throw new IllegalArgumentException(
                    "Remplissage échoué! Nécéssité de présente à côté d'une case contenant de l'eau.\n");
        }
    }
    /**
     * Calcule le temps nécessaire pour remplir le réservoir avec un nombre donné de litres.
     *
     * @param nbLitres Le nombre de litres à remplir.
     * @return Le temps de remplissage en secondes.
     */
    public int getTempsRemplissage(int nbLitres) {
        return nbLitres * 600 / 5000;
    }
    /**
     * Calcule le temps nécessaire pour vider un nombre donné de litres d'eau.
     *
     * @param nbLitres Le nombre de litres à vider.
     * @return Le temps de déversement en secondes.
     */
    public int getTempsDeversement(int nbLitres) {
        return nbLitres * 5 / 100;
    }
}
