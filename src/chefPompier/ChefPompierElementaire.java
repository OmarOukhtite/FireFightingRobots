package chefPompier;

import chemins.*;
import classes.*;
import evenements.*;
import intervention.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import robots.*;
import simulation.*;
/**
 * La classe ChefPompierElementaire représente un chef pompier qui attribue des robots
 * pour éteindre des incendies de manière élémentaire (sans stratégie avancée).
 * Elle utilise les données de simulation et un simulateur pour générer un scénario
 * contenant les événements nécessaires.
 */
public class ChefPompierElementaire {
    DonneesSimulation donnees;
    Simulateur simulateur;
    /**
     * Constructeur pour initialiser un chef pompier élémentaire.
     *
     * @param donnees    Les données de simulation contenant les robots, incendies, carte, etc.
     * @param simulateur Le simulateur utilisé pour exécuter les événements.
     */
    public ChefPompierElementaire(DonneesSimulation donnees, Simulateur simulateur) {
        this.donnees = donnees;
        this.simulateur = simulateur;
    }
    /**
     * Affecte les robots aux incendies de manière élémentaire.
     *
     * Cette méthode génère un scénario d'extinction d'incendies en attribuant des robots
     * disponibles aux incendies restants. Les robots se déplacent pour éteindre les incendies
     * en rechargeant leur eau si nécessaire. Une fois l'intervention terminée, le robot retourne
     * à sa position initiale et retrouve son volume d'eau maximal.
     *
     * La méthode prend en compte les caractéristiques suivantes :
     * - Les robots doivent se déplacer vers la position de l'incendie ou vers une source d'eau.
     * - Les drones peuvent se remplir directement sur les cases d'eau, tandis que les autres robots
     *   doivent être sur une case adjacente.
     * - Les incendies sont éteints selon les capacités et la disponibilité des robots.
     *
     * @return Un scénario contenant les événements nécessaires pour éteindre tous les incendies.
     */
    public Scenario AffecterRobotsElementaire() {
        List<Evenement> events = new ArrayList<>();
        List<Robot> robots = donnees.getRobots();
        List<Incendie> incendies = donnees.getIncendies();

        // On récupère les positions initiales des robots
        Map<Robot, Case> positionsInitiales = new HashMap<>();
        for (Robot robot : robots) {
            positionsInitiales.put(robot, robot.getPosition());
        }

        for (Incendie incendie : incendies) {
            if (incendie.getIntensite() <= 0) {
                continue; // Passer l'incendie si déjà éteint
            }

            // Trouver un robot disponible qui peut atteindre l'incendie
            Robot robotChoisi = null;
            ResultatIntervention resultat = null;

            // Tri des robots par date de disponibilité croissante
            robots.sort((r1, r2) -> Long.compare(r1.getDate(), r2.getDate()));

            EteindreIncendie pompier = new EteindreIncendie();
            Case positionRobot = null;

            for (Robot robot : robots) {
                pompier = new EteindreIncendie();
                positionRobot = robot.getPosition();

                Case positionRemplissageEau = donnees.getCasesEau().get(0);

                for (Case positionEau : donnees.getCasesEau()) {
                    List<Evenement> events3 = new ArrayList<>();

                    if (robot instanceof RobotDrone) {
                        // Si le robot est un drone, il doit se déplacer directement sur une case d'eau
                        events3 = DeplacerRobotMin.DeplacementMin(incendie.getPosition(), positionEau, robot, donnees, 0);
                        positionRemplissageEau = positionEau;
                    } else {
                        // Si le robot n'est pas un drone, il doit aller sur une case adjacente à l'eau
                        for (Direction dir : Direction.values()) {
                            positionRemplissageEau = donnees.getCarte().getVoisin(positionEau, dir);
                            if (positionRemplissageEau != null) {
                                events3 = DeplacerRobotMin.DeplacementMin(incendie.getPosition(), positionRemplissageEau, robot, donnees, 0);
                                if (!events3.isEmpty()) {
                                    break;
                                }
                            }
                        }
                    }
                }
                resultat = pompier.intervenir(robot, positionRobot, incendie.getPosition(), positionRemplissageEau, donnees, robot.getDate());

                // Si un résultat non nul est obtenu, ce robot peut éteindre l'incendie
                if (resultat != null) {
                    robotChoisi = robot;
                    break;
                }
                

                if (robotChoisi != null) {
                    break; // Un robot a été trouvé pour cet incendie
                }
            }

            // Si aucun robot n'est trouvé pour éteindre cet incendie, passer au prochain incendie
            if (robotChoisi == null) {
                continue;
            }

            events.addAll(resultat.getEvents());
            robotChoisi.setPosition(resultat.getPositionFinale());
            robotChoisi.setDate(robotChoisi.getDate() + pompier.getDureeEteindreIncendie() + 1); // Ajouter 1 pour éviter chevauchements
        }

        // On rétablit le volume d'eau et la position de chaque robot
        for (Robot robot : robots) {
            robot.setVolumeEau(robot.getCapacite());
            robot.setPosition(positionsInitiales.get(robot));
        }

        // Création du scénario et ajout des événements générés
        Scenario scenario = new Scenario();
        scenario.ajouteEvenements(events);
        return scenario;
    }
}
