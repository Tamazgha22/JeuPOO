package modele.deplacements;

import modele.plateau.Entite;
import modele.plateau.EntiteDynamique;
import modele.plateau.Jeu;

public class IA extends RealisateurDeDeplacement {
    private boolean enemieEstMort = false;
    // Design pattern singleton
    private static IA c3d;

    public static IA getInstance() {
        if (c3d == null) {
            c3d = new IA();
        }
        return c3d;
    }

    public void setDirectionCouranteDroite() {
        for (EntiteDynamique e : lstEntitesDynamiques) {
            Direction directionCouranteDroite = Direction.droite;
            e.avancerDirectionChoisie(directionCouranteDroite);
        }
    }

    public void setDirectionCouranteGauche() {
        for (EntiteDynamique e : lstEntitesDynamiques) {
            Direction directionCouranteGauche = Direction.gauche;
            e.avancerDirectionChoisie(directionCouranteGauche);
        }
    }

    protected boolean realiserDeplacement() {
        boolean ret = false;
        for (EntiteDynamique e : lstEntitesDynamiques) {
            if (!enemieEstMort){
                setDirectionCouranteDroite();
                ret = true;
                setDirectionCouranteGauche();
//                ret = true;
            }
        }
        return ret;
    }
}
