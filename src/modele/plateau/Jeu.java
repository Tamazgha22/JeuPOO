/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele.plateau;

import modele.deplacements.*;

import java.awt.Point;
import java.util.HashMap;

/** Actuellement, cette classe gère les postions
 * (ajouter conditions de victoire, chargement du plateau, etc.)
 */
public class Jeu {

    public static final int SIZE_X = 20;
    public static final int SIZE_Y = 10;

    // compteur de déplacements horizontal et vertical (1 max par défaut, à chaque pas de temps)
    private HashMap<Entite, Integer> cmptDeplH = new HashMap<Entite, Integer>();
    private HashMap<Entite, Integer> cmptDeplV = new HashMap<Entite, Integer>();

    private Heros hector;
    private Colonne colonne;
    private  Bot smick;

    private HashMap<Entite, Point> map = new  HashMap<Entite, Point>(); // permet de récupérer la position d'une entité à partir de sa référence
    private Entite[][] grilleEntites = new Entite[SIZE_X][SIZE_Y]; // permet de récupérer une entité à partir de ses coordonnées

    private Ordonnanceur ordonnanceur = new Ordonnanceur(this);

    public Jeu() {
        initialisationDesEntites();
    }

    public void resetCmptDepl() {
        cmptDeplH.clear();
        cmptDeplV.clear();
    }

    public void start(long _pause) {
        ordonnanceur.start(_pause);
    }
    
    public Entite[][] getGrille() {
        return grilleEntites;
    }
    
    public Heros getHector() {
        return hector;
    }
    public Bot getSmick() {
        return smick;
    }
    public Colonne getColonne() {
        return colonne;
    }
    
    private void initialisationDesEntites() {
        hector = new Heros(this);
        addEntite(hector, 2, 1);
        Gravite g = new Gravite();
        g.addEntiteDynamique(hector);
        ordonnanceur.add(g);
        Controle4Directions.getInstance().addEntiteDynamique(hector);
        ordonnanceur.add(Controle4Directions.getInstance());


        Bot smick = new Bot(this);
        addEntite(smick, 1, 8);
        ordonnanceur.add(smick.getIA());
        ordonnanceur.add(smick.getGravite());

        Bot smick2 = new Bot(this);
        addEntite(smick2, 15, 8);
        ordonnanceur.add(smick2.getIA());
        ordonnanceur.add(smick2.getGravite());

       /* Bot smick3 = new Bot(this);
        addEntite(smick3, 8, 2);
        ordonnanceur.add(smick3.getIA());
        ordonnanceur.add(smick3.getGravite());*/



        colonne = new Colonne(this);
        addEntite(colonne, 14, 6);
        ColonneDeplacement.getInstance().addEntiteDynamique(colonne);
        ordonnanceur.add(ColonneDeplacement.getInstance());

        colonne = new Colonne(this);
        addEntite(colonne, 14, 5);
        ColonneDeplacement.getInstance().addEntiteDynamique(colonne);
        ordonnanceur.add(ColonneDeplacement.getInstance());

        colonne = new Colonne(this);
        addEntite(colonne, 14, 4);
        ColonneDeplacement.getInstance().addEntiteDynamique(colonne);
        ordonnanceur.add(ColonneDeplacement.getInstance());


        Bombe bombe = new Bombe(this);
        addEntite(bombe,3,5);
        addEntite(bombe,5,5);
        addEntite(bombe,12,6);


        // murs extérieurs horizontaux
        for (int x = 0; x < 20; x++) {
            addEntite(new Mur(this), x, 0);
            addEntite(new Mur(this), x, 9);
        }

        // murs extérieurs verticaux
        for (int y = 1; y < 9; y++) {
            addEntite(new Mur(this), 0, y);
            addEntite(new Mur(this), 19, y);
        }

        addEntite(new Mur(this), 1, 6);
        addEntite(new Mur(this), 2, 6);
        addEntite(new Mur(this), 3, 6);
        addEntite(new Mur(this), 4, 6);
        addEntite(new Mur(this), 5, 6);
        addEntite(new Mur(this), 6, 6);
        addEntite(new Mur(this), 7, 6);
        addEntite(new Mur(this), 7, 5);
        addEntite(new Mur(this), 7, 4);
        addEntite(new Mur(this), 7, 3);
        addEntite(new Mur(this), 8, 3);
        addEntite(new Mur(this), 10, 4);
        addEntite(new Mur(this), 10, 7);
        addEntite(new Mur(this), 11, 7);
        addEntite(new Mur(this), 12, 7);
        addEntite(new Mur(this), 13, 7);
        addEntite(new Mur(this), 14, 7);
        addEntite(new Mur(this), 15, 7);
        addEntite(new Mur(this), 16, 7);
        addEntite(new Mur(this), 17, 7);
        addEntite(new Mur(this), 18, 7);
        addEntite(new Mur(this), 11, 4);
        addEntite(new Mur(this), 12, 4);
        addEntite(new Mur(this), 13, 4);

        addEntite(new Corde(this), 6, 5);
        addEntite(new Corde(this), 6, 4);
        addEntite(new Corde(this), 6, 3);
        addEntite(new Corde(this), 6, 2);
        addEntite(new Corde(this), 6, 1);
        addEntite(new Corde(this), 9, 3);
        addEntite(new Corde(this), 9, 4);
        addEntite(new Corde(this), 9, 5);
        addEntite(new Corde(this), 9, 6);
        addEntite(new Corde(this), 9, 7);
        addEntite(new Corde(this), 9, 8);

    }

    private void addEntite(Entite e, int x, int y) {
        grilleEntites[x][y] = e;
        map.put(e, new Point(x, y));
    }

    private void supprimerEntite(Entite e, int x, int y){
        grilleEntites[x][y] = null;
        map.remove(e);
    }
    
    /** Permet par exemple a une entité  de percevoir sont environnement proche et de définir sa stratégie de déplacement
     *
     */
    public Entite regarderDansLaDirection(Entite e, Direction d) {
        Point positionEntite = map.get(e);
        return objetALaPosition(calculerPointCible(positionEntite, d));
    }
    
    /** Si le déplacement de l'entité est autorisé (pas de mur ou autre entité), il est réalisé
     * Sinon, rien n'est fait.
     */
    public boolean deplacerEntite(Entite e, Direction d) {
        boolean retour = false;

        Point pCourant = map.get(e);

        Point pCible = calculerPointCible(pCourant, d);

        boolean deplacement = false;
        boolean bombe = false;

        if (contenuDansGrille(pCible)){ // a adapter (collisions murs, etc.)
            // compter le déplacement : 1 deplacement horizontal et vertical max par pas de temps par entité
            if(objetALaPosition(pCible) != null){
                if(objetALaPosition(pCible) instanceof Bombe){
                    if(e instanceof Bot){
                    }
                    else {
                        Entite entiteBombe = objetALaPosition(pCible);
                        deplacement = true;
                        supprimerEntite(entiteBombe, (int) pCible.getX(), (int) pCible.getY());
                        bombe = true;
                    }
                }
                if(objetALaPosition(pCible) instanceof Corde){
                    Entite entiteCorde = objetALaPosition(pCible);
                    deplacement = true;
                    addEntite(entiteCorde, (int) pCible.getX(), (int) pCible.getY());

                }
                if(objetALaPosition(pCible) instanceof Heros && e instanceof Colonne){
                    deplacerEntite(objetALaPosition(pCible), Direction.gauche);
                    deplacement = true;
                }
                if(objetALaPosition(pCible) instanceof Heros && e instanceof Bot){
                    deplacement = false;
                }

                if(objetALaPosition(pCible) instanceof Bot && (e instanceof Colonne || e instanceof Heros)) {
                    Bot cible = (Bot) objetALaPosition(pCible);
                    supprimerEntite(cible, (int) pCible.getX(), (int) pCible.getY());
                    ordonnanceur.remove(cible.getIA());
                    ordonnanceur.remove(cible.getGravite());
                    deplacement = true;
                }
            }
            else deplacement = true;

            if(deplacement)
                switch (d) {
                    case bas, haut:
                        if (cmptDeplV.get(e) == null) {
                            cmptDeplV.put(e, 1);
                            retour = true;
                        }
                        break;
                    case gauche, droite:
                        if (cmptDeplH.get(e) == null) {
                            cmptDeplH.put(e, 1);
                            retour = true;
                        }
                        break;
                }
        }

        if (retour) {
            deplacerEntite(pCourant, pCible, e);
        }

        return retour;
    }

    public boolean interactionEntite(Entite e, Interaction i, Direction d){
        boolean retour = false;
        Point pCourant = map.get(e);
        if(contenuDansGrille(pCourant) && i == Interaction.Entree || i == Interaction.e){
            int x = (int) pCourant.getX();
            int y = (int) pCourant.getY();
            if(d == Direction.droite) x += 1;
            if(d == Direction.gauche) x -= 1;
            Entite entite = objetALaPosition(new Point(x,y));
        }

        return retour;
    }


    private Point calculerPointCible(Point pCourant, Direction d) {
        Point pCible = null;
        
        switch(d) {
            case haut: pCible = new Point(pCourant.x, pCourant.y - 1); break;
            case bas : pCible = new Point(pCourant.x, pCourant.y + 1); break;
            case gauche : pCible = new Point(pCourant.x - 1, pCourant.y); break;
            case droite : pCible = new Point(pCourant.x + 1, pCourant.y); break;     
            
        }
        
        return pCible;
    }
    
    private void deplacerEntite(Point pCourant, Point pCible, Entite e) {
        grilleEntites[pCourant.x][pCourant.y] = null;
        grilleEntites[pCible.x][pCible.y] = e;
        map.put(e, pCible);
    }
    
    /** Indique si p est contenu dans la grille
     */
    private boolean contenuDansGrille(Point p) {
        return p.x >= 0 && p.x < SIZE_X && p.y >= 0 && p.y < SIZE_Y;
    }
    
    private Entite objetALaPosition(Point p) {
        Entite retour = null;
        
        if (contenuDansGrille(p)) {
            retour = grilleEntites[p.x][p.y];
        }
        
        return retour;
    }

    public Ordonnanceur getOrdonnanceur() {
        return ordonnanceur;
    }
}
