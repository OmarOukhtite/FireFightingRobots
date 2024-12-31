package chemins;

import classes.*;
import java.util.Objects;
import robots.*;
/**
 * La classe RobotIncendieKey sert de clé pour des associations entre un robot
 * et la position d'un incendie dans des structures de données comme des HashMaps.
 */
public class RobotIncendieKey {
    private Robot robot;
    private Case positionIncendie;
    /**
     * Constructeur pour créer une instance de RobotIncendieKey.
     * 
     * @param robot            Le robot associé à la clé.
     * @param positionIncendie La position de l'incendie associée au robot.
     */
    public RobotIncendieKey(Robot robot, Case positionIncendie) {
        this.robot = robot;
        this.positionIncendie = positionIncendie;
    }

    public Robot getRobot() {
        return robot;
    }

    public Case getPositionIncendie() {
        return positionIncendie;
    }
    /**
     * Vérifie si cette instance est égale à un autre objet.
     * Deux instances de RobotIncendieKey sont égales si elles ont le même robot et la même position d'incendie.
     * 
     * @param o L'objet à comparer avec cette instance.
     * @return true si les objets sont égaux, sinon false.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RobotIncendieKey that = (RobotIncendieKey) o;
        return Objects.equals(robot, that.robot) &&
               Objects.equals(positionIncendie, that.positionIncendie);
    }
    /**
     * Calcule un code de hachage pour cette instance.
     * 
     * @return Le code de hachage basé sur le robot et la position d'incendie.
     */
    @Override
    public int hashCode() {
        return Objects.hash(robot, positionIncendie);
    }

    @Override
    public String toString() {
        return "RobotIncendieKey{" +
               "robot=" + robot +
               ", positionIncendie=" + positionIncendie +
               '}';
    }
}
