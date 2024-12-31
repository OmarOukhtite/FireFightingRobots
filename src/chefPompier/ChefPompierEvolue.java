package chefPompier;

import chemins.*;
import classes.Case;
import classes.DonneesSimulation;
import classes.Incendie;
import evenements.Evenement;
import evenements.Scenario;
import intervention.EteindreIncendie;
import intervention.ResultatIntervention;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import robots.Robot;
import simulation.Simulateur;
/**
 * La classe ChefPompierEvolue représente un chef pompier utilisant une stratégie évoluée
 * pour attribuer des robots aux incendies. Cette classe prend en compte des critères
 * avancés, comme le temps d'intervention minimal, pour optimiser la gestion des ressources.
 */
public class ChefPompierEvolue {
    DonneesSimulation donnees;
    Simulateur simulateur;
    /**
     * Constructeur pour initialiser un chef pompier évolué.
     *
     * @param donnees    Les données de simulation contenant les robots, incendies, cases d'eau, etc.
     * @param simulateur Le simulateur utilisé pour exécuter les événements générés.
     */
    public ChefPompierEvolue(DonneesSimulation donnees, Simulateur simulateur) {
        this.donnees = donnees;
        this.simulateur = simulateur;
    }
    /**
     * Génère un scénario optimisé pour affecter des robots aux incendies.
     *
     * Cette méthode attribue un robot à chaque incendie en fonction du temps
     * d'intervention total (temps de déplacement + temps d'extinction) minimal.
     * Elle utilise une pré-computation des chemins entre les robots, les incendies,
     * et les sources d'eau pour minimiser les temps de calcul pendant la simulation.
     *
     * Fonctionnalités principales :
     * - Calcul d'une correspondance entre les robots, les incendies et les sources d'eau.
     * - Choix du robot optimal pour chaque incendie basé sur le temps de fin d'intervention.
     * - Gestion du remplissage d'eau pour les robots nécessitant une recharge.
     * - Mise à jour des positions et des dates des robots après chaque intervention.
     *
     * @return Un scénario contenant tous les événements nécessaires pour éteindre les incendies.
     */
    public Scenario AffecterRobotsEvolue() {
        List<Evenement> events = new ArrayList<>();
        List<Robot> robots = donnees.getRobots();
        List<Incendie> incendies = donnees.getIncendies();

        HashMap<Robot, HashMap<RobotIncendieKey, Case>> robotToIncendieVersEauMap = new HashMap<>();
        for (Robot robot : robots) {
            // Création de la HashMap pour ce robot
            HashMap<RobotIncendieKey, Case> incendieVersEauMap = EauPlusProche.creerMapIncendieEau(
                donnees.getIncendies(),
                donnees.getCasesEau(),
                robot,
                donnees
            );
        
            // Stocker la HashMap dans la map globale associée à ce robot
            robotToIncendieVersEauMap.put(robot, incendieVersEauMap);
        
            
        }


        // On récupère les positions initiales des robots
        Map<Robot, Case> positionsInitales = new HashMap<>();
        for (Robot robot: robots) {
            positionsInitales.put(robot, robot.getPosition());
        }

        for (Incendie incendie : incendies) {
            if (incendie.getIntensite() <= 0) {
                continue;  // Passer l'incendie si déjà éteint
            }
    
            // Trouver un robot disponible qui peut atteindre l'incendie
            Robot robotChoisi = null;
            ResultatIntervention resultat = null;

            EteindreIncendie pompier = new EteindreIncendie();
            Case positionRobot = null;

            // On recherche le robot avec le temps d'intervention minimal
            long tempsMin = Long.MAX_VALUE;
            for (Robot robot : robots) {
                HashMap<RobotIncendieKey, Case> incendieVersEauMap = robotToIncendieVersEauMap.get(robot);
                RobotIncendieKey key = new RobotIncendieKey(robot, incendie.getPosition());
                Case positionRemplissageEau = incendieVersEauMap.get(key);

                pompier = new EteindreIncendie();
                positionRobot = robot.getPosition();
                ResultatIntervention resultatCourant = pompier.intervenir(robot, positionRobot, incendie.getPosition(), positionRemplissageEau,donnees, robot.getDate());

                if (resultatCourant==null) {
                    continue; // on fait rien si le robot ne peut pas intervenir
                }

                // On choisi le robot qui terminera l'intervention le premier
                long dureeIntervention = pompier.getDureeEteindreIncendie();
                long tempsFinIntervention = robot.getDate() + dureeIntervention;
                if (tempsFinIntervention < tempsMin) {
                    tempsMin = tempsFinIntervention;
                    robotChoisi = robot;
                    resultat = resultatCourant;
                }
            }
            
            // Si aucun robot n'est trouvé pour éteindre cet incendie, passer au prochain incendie
            if (robotChoisi == null) {
                continue;
            }
            events.addAll(resultat.getEvents());
            robotChoisi.setPosition(resultat.getPositionFinale());
            robotChoisi.setDate(tempsMin + 1); // Ajouter 1 pour éviter chevauchements
        }

        // On rétablit le volume d'eau et la position de chaque robot
        for (Robot robot : robots) {
            robot.setVolumeEau(robot.getCapacite());
            robot.setPosition(positionsInitales.get(robot));
        }
    
        // Création du scénario et ajout des événements générés
        Scenario scenario = new Scenario();
        scenario.ajouteEvenements(events);
        return scenario;
    }
    
}


