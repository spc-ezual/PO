package ressources.Unite;

import java.util.ArrayList;
import java.util.Collections;

import ressources.Chemins;

public final class Achasse extends unite {

    public Achasse(int appartient) {
        super(8, deplacement.Aerien, 19000, new ArrayList<armes>(), appartient, 1, 2);
        this.Arme.add(armes.MissileAirAir);
    }

    @Override
    public boolean peutAttaque(unite def) {
        return def.typeDeplacement==deplacement.Aerien;
    }

    @Override
    public double maxCoef(ArrayList<armes> arme) {
        ArrayList<Double> coef = new ArrayList<Double>();
        for (armes ar : arme) {
            switch (ar) {
                case MitraLourde -> coef.add(0.7);
                case MissileAirAir -> coef.add(1.1);
                default -> coef.add(0.);
            }
        }
        Double maxi = Collections.max(coef);
        return maxi;
    }

    @Override
    public String getChemin() {
        return Chemins.getCheminUnite(app, dispo, Chemins.FICHIER_ACHASSE);
    }
    
}
