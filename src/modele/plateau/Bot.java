/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele.plateau;

import modele.deplacements.Direction;
import modele.deplacements.Gravite;
import modele.deplacements.IA;
import modele.deplacements.RealisateurDeDeplacement;

import java.util.Random;

/**
 * Ennemis (Smicks)
 */
public class Bot extends EntiteDynamique {
    private Random r = new Random();
    private Gravite g = new Gravite();

    private IA entiteIA = new IA();
    public Direction randomDirection(){
        Direction direction = Direction.droite;
        // r entre 0 et 2 : la valeur de direction gauche ou droite s'alterene alors a chaque nouveau r genere aleatoirement
        if(r.nextInt(2) == 1) direction = Direction.gauche;
        entiteIA.setDirectionCourante(direction);
        return  direction;
    }
//    public static Direction randomDirection(){
//        Random r = new Random();
//        // r entre 0 et 2 : la valeur de direction gauche ou droite s'alterene alors a chaque nouveau r genere aleatoirement
//        if(r.nextInt(2) == 1) return Direction.droite;
//        return Direction.gauche;
//    }
    public Bot(Jeu _jeu) {
        super(_jeu);
        g.addEntiteDynamique(this);
        entiteIA.addEntiteDynamique(this);
        randomDirection();
    }

    public RealisateurDeDeplacement getGravite(){
        return g;
    }

    public RealisateurDeDeplacement getIA(){
        return entiteIA;
    }
    public boolean peutEtreEcrase() { return true; }
    public boolean peutServirDeSupport() { return true; }
    public boolean peutPermettreDeMonterDescendre() { return false; };
}
