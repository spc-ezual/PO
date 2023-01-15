package ressources.Unite;

import java.util.ArrayList;
import java.util.Collections;

import ressources.Chemins;

public final class Tank extends unite {

    public Tank(int appartient) {
        super(6, deplacement.Chenille, 7000, null, appartient,1,1);
        ArrayList<armes> ar = new ArrayList<armes>();
        ar.add(armes.MitraLeg);
        ar.add(armes.Canon);
        this.Arme = ar;
    }

    @Override
    public boolean peutAttaque(unite def) {
        return def.getClass()!=Bombardier.class;
    }

    @Override
    public double maxCoef(ArrayList<armes> arme) {
        ArrayList<Double> coef = new ArrayList<Double>();
        for (armes ar : arme) {
            switch (ar) {
                case MitraLeg -> coef.add(0.15);
                case Canon -> coef.add(0.55);
                case MitraLourde -> coef.add(0.30);
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
        return Chemins.getCheminUnite(app, dispo, Chemins.FICHIER_TANK);
    }
}
