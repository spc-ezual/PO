package ressources.Unite;

import java.util.ArrayList;
import java.util.Collections;

import ressources.Chemins;

public final class Convoi extends unite {

    public Convoi(int appartient) {
        super(6, deplacement.Chenille, 5000, new ArrayList<armes>(), appartient,0,0);
    }

    @Override
    public boolean peutAttaque(unite def) {
        return false;
    }

    @Override
    public double maxCoef(ArrayList<armes> arme) {
        ArrayList<Double> coef = new ArrayList<Double>();
        for (armes ar : arme) {
            switch (ar) {
                case MitraLeg -> coef.add(0.40);
                case Canon -> coef.add(0.7);
                case MitraLourde -> coef.add(0.5);
                case MissileAirSol -> coef.add(0.70);
                case Bombes -> coef.add(1.0);
                case Mortier -> coef.add(0.7);
                default -> coef.add(0.);
            }
        }
        Double maxi = Collections.max(coef);
        return maxi;
    }
    @Override
    public String getChemin() {
        return Chemins.getCheminUnite(app, dispo, Chemins.FICHIER_GENIE);
    }
}
