package lecteur;

import classes.*;
import java.io.*;
import java.util.*;
import java.util.zip.DataFormatException;
import robots.*;


/**
 * Classe responsable du chargement des données de simulation depuis un fichier dans un objet DonneesSimulation.
 */

public class StockageDonnees {


    /**
     * Lit les données de simulation à partir d'un fichier et les stocke dans l'objet DonneesSimulation donné.
     *
     * @param fichierDonnees Le fichier contenant les données de simulation.
     * @param donnees L'objet DonneesSimulation où les données seront stockées.
     * @throws FileNotFoundException Si le fichier n'est pas trouvé.
     * @throws DataFormatException Si le format des données dans le fichier est incorrect.
     */
    public static void stock(String fichierDonnees, DonneesSimulation donnees) throws FileNotFoundException, DataFormatException {
        StockageDonnees lecteur = new StockageDonnees(fichierDonnees);
        List<Case> casesEau = new ArrayList<>();
        donnees.setCarte(lecteur.stockCarte(donnees.getCasesEau()));
        donnees.setCasesEau(casesEau);
        donnees.setIncendies(lecteur.stockIncendies());
        Carte carte = donnees.getCarte();
        donnees.setRobots(lecteur.stockRobots(carte));
        scanner.close();
    }


    // Tout le reste de la classe est prive!

    private static Scanner scanner;
    private List<Case> casesEau = new ArrayList<>();  // Liste pour stocker les cases de type EAU


    /**
     * Constructeur pour initialiser le scanner afin de lire les données depuis le fichier.
     *
     * @param fichierDonnees Le fichier contenant les données de simulation.
     * @throws FileNotFoundException Si le fichier n'est pas trouvé.
     */
    private StockageDonnees(String fichierDonnees) throws FileNotFoundException {
        scanner = new Scanner(new File(fichierDonnees));
        scanner.useLocale(Locale.US);
    }
    /**
     * Lit et traite la disposition de la carte (cases) depuis le fichier de données.
     *
     * @param casesEau Une liste pour stocker les cases de type EAU (eau).
     * @return Un objet Carte représentant la carte avec ses cases.
     * @throws DataFormatException Si le format des données est invalide.
     */
    private Carte stockCarte(List<Case> casesEau) throws DataFormatException {
        ignorerCommentaires();
        try {
            int nbLignes = scanner.nextInt();
            int nbColonnes = scanner.nextInt();
            int tailleCases = scanner.nextInt();

            Case[][] matriceCases = new Case[nbLignes][nbColonnes];
            Carte carte = new Carte(matriceCases, tailleCases);

            for (int lig = 0; lig < nbLignes; lig++) {
                for (int col = 0; col < nbColonnes; col++) {
                    Case currentCase = stockCase(lig, col);
                    carte.setCase(lig, col, currentCase);
                    if (currentCase.getNatureTerrain() == NatureTerrain.EAU) {
                        casesEau.add(currentCase);  // Ajouter la case à la liste si elle est de type EAU
                    }
                }
            }
            return carte;

        } catch (NoSuchElementException e) {
            throw new DataFormatException("Format invalide. Attendu: nbLignes nbColonnes tailleCases");
        }
    }
    /**
     * Lit et traite les données pour une seule case sur la carte (type de terrain et propriétés associées).
     *
     * @param lig L'index de la ligne de la case.
     * @param col L'index de la colonne de la case.
     * @return Un objet Case représentant le terrain pour les coordonnées spécifiées.
     * @throws DataFormatException Si les données de la case sont invalides.
     */
    private Case stockCase(int lig, int col) throws DataFormatException {
    ignorerCommentaires();
    try {
        String chaineNature = scanner.next();
        
        NatureTerrain nature = NatureTerrain.valueOf(chaineNature);

        verifieLigneTerminee();
        return new Case(lig, col, nature); // Création de l'objet Case

    } catch (NoSuchElementException e) {
        throw new DataFormatException("format de case invalide. "
                + "Attendu: nature altitude [valeur_specifique]");
    }
}


    /**
     * Lit et traite les incidents d'incendie depuis le fichier de données.
     *
     * @return Une liste d'objets Incendie représentant les incidents d'incendie.
     * @throws DataFormatException Si les données d'incendie ne sont pas correctement formatées.
     */
    private List<Incendie> stockIncendies() throws DataFormatException {
    ignorerCommentaires();
    List<Incendie> incendies = new ArrayList<>();
    try {
        int nbIncendies = scanner.nextInt();
        for (int i = 0; i < nbIncendies; i++) {
            incendies.add(stockIncendie(i)); // Ajouter chaque incendie à la liste
        }
    } catch (NoSuchElementException e) {
        throw new DataFormatException("Format invalide. "
                + "Attendu: nbIncendies");
    }
    return incendies;
}
    /**
     * Lit et traite les données pour un seul incident d'incendie.
     *
     * @param i L'index de l'incendie.
     * @return Un objet Incendie représentant l'incendie.
     * @throws DataFormatException Si les données de l'incendie sont invalides.
     */
    private Incendie stockIncendie(int i) throws DataFormatException {
    ignorerCommentaires();
    try {
        int lig = scanner.nextInt();
        int col = scanner.nextInt();
        Case case_incendie = new Case(lig, col, NatureTerrain.TERRAIN_LIBRE);
        int intensite = scanner.nextInt();
        if (intensite <= 0) {
            throw new DataFormatException("incendie " + i + " : nb litres pour eteindre doit être > 0");
        }
        verifieLigneTerminee();

        return new Incendie(case_incendie, intensite); // Création de l'objet Incendie

    } catch (NoSuchElementException e) {
        throw new DataFormatException("format d'incendie invalide. Attendu: ligne colonne intensite");
    }
}
    /**
     * Lit et traite les données des robots depuis le fichier et crée une liste d'objets Robot.
     *
     * @param carte La carte (Carte) sur laquelle les robots sont positionnés.
     * @return Une liste d'objets Robot.
     * @throws DataFormatException Si les données des robots sont invalides.
     */
    private List<Robot> stockRobots(Carte carte) throws DataFormatException {
    ignorerCommentaires();
    List<Robot> robots = new ArrayList<>();
    try {
        int nbRobots = scanner.nextInt();
        for (int i = 0; i < nbRobots; i++) {
            robots.add(stockRobot(i, carte)); // Ajouter chaque robot à la liste
        }
    } catch (NoSuchElementException e) {
        throw new DataFormatException("Format invalide. Attendu: nbRobots");
    }
    return robots;
}
    /**
     * Lit et traite les données pour un seul robot et crée l'objet Robot correspondant.
     *
     * @param i L'index du robot.
     * @param carte La carte (Carte) sur laquelle le robot est positionné.
     * @return Un objet Robot correspondant aux données du fichier.
     * @throws DataFormatException Si les données du robot sont invalides.
     */
    private Robot stockRobot(int i, Carte carte) throws DataFormatException {
    ignorerCommentaires();
    int lig = scanner.nextInt();
    int col = scanner.nextInt();
    Case case_robot = carte.getCase(lig, col);
    NatureTerrain nature = case_robot.getNatureTerrain();
    Case caseRobot = new Case(lig, col, nature);
    String type = scanner.next();
    String s = scanner.findInLine("(\\d+)");	
    int vitesse;
    if (s == null) {
        vitesse = 0;
    } else {
        vitesse = Integer.parseInt(s) ; // km/h
    }

    switch (type){
        case "DRONE":
            Robot robotadrone = new RobotDrone(caseRobot, vitesse);
            if (s == null){
                robotadrone.setVitesse(100);
            }
            return robotadrone;
        case "ROUES":
            Robot robotaroues = new RobotRoues(caseRobot, vitesse);
            if (s==null){
                robotaroues.setVitesse(80);
            }
            return robotaroues;
        case "PATTES":
            Robot robotapattes = new RobotPattes(caseRobot, vitesse);
            if (s == null || nature == NatureTerrain.ROCHE){
                robotapattes.setVitesse(10);
            }
            if (s == null|| nature != NatureTerrain.ROCHE){
                robotapattes.setVitesse(30);
            }
            return robotapattes;
        case "CHENILLES":
            Robot robotachenilles = new RobotChenille(caseRobot, vitesse);
            if (s == null){
                robotachenilles.setVitesse(60);
            }
            if (nature == NatureTerrain.FORET ) {
                int nv_vitesse = robotachenilles.getVitesse();
                robotachenilles.setVitesse(nv_vitesse);
            }
            return robotachenilles;
        default:
            throw new DataFormatException("Type de robot inconnu : " + type);
    }
}

    /** Ignore toute (fin de) ligne commencant par '#' */
    private void ignorerCommentaires() {
        while(scanner.hasNext("#.*")) {
            scanner.nextLine();
        }
    }

    /**
     * //verifie qu'il n'y a plus rien a stock sur cette ligne (int ou float).
     * @throws ExceptionFormatDonnees
     */
    private void verifieLigneTerminee() throws DataFormatException {
        if (scanner.findInLine("(\\d+)") != null) {
            throw new DataFormatException("format invalide, donnees en trop.");
        }
    }
}
