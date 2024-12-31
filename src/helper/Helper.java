package helper;

import evenements.*;
import java.util.List;
/**
 * Classe utilitaire fournissant des méthodes d'aide pour effectuer des calculs liés aux événements et transformations.
 * 
 * Cette classe contient des méthodes pour calculer la durée totale des événements et transformer une valeur donnée.
 */
public class Helper {
    /**
     * Calcule la durée totale d'une liste d'événements.
     * 
     * La durée totale est la somme des durées de chaque événement de la liste. Chaque événement doit implémenter
     * la méthode `getDuree()` pour fournir sa durée respective.
     * 
     * @param events La liste des événements dont il faut calculer la durée totale.
     * @return La durée totale des événements en millisecondes.
     */
    public static long CalculDureeEvents(List<Evenement> events) {
        long res = 0;
        for (Evenement event : events) {
            res += event.getDuree();
        }
        return res;
    }
    /**
     * Applique une transformation mathématique à une valeur d'entrée.
     * 
     * Cette méthode prend une entrée `input` et applique une transformation pour la réduire.
     * La transformation retourne un nombre basé sur l'inverse de `(input + 2)`, multiplié par 1000.
     * 
     * @param input La valeur d'entrée à transformer.
     * @return La valeur transformée selon la formule `1000 / (input + 2)`.
     */
    public static int transform(int input) {
        return 1000/(input+2);
    }

}
