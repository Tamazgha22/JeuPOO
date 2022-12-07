package modele.deplacements;

import modele.plateau.EntiteDynamique;

import java.util.Random;

public class IA extends RealisateurDeDeplacement {
    private Direction directionCourante;

    public void setDirectionCourante(Direction _directionCourante) {
        directionCourante = _directionCourante;
    }

    public static Direction randomDirection(){
        Random r = new Random();
        if(r.nextInt(2) == 1) return Direction.droite;
        return Direction.gauche;
    }

    public boolean realiserDeplacement() {
        boolean ret = false;
        for (EntiteDynamique e : lstEntitesDynamiques) {
            if (directionCourante != null)
                switch (directionCourante) {
                    case droite :
                        if (e.avancerDirectionChoisie(directionCourante))
                            ret = true;
                        else setDirectionCourante(Direction.gauche);
                        break;
                    case gauche :
                        if (e.avancerDirectionChoisie(directionCourante))
                            ret = true;
                        else setDirectionCourante(Direction.droite);
                        break;
                }
            else randomDirection();
        }
        return ret;
    }
}
