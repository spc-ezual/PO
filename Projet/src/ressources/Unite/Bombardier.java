package ressources.Unite;

import java.util.ArrayList;
import java.util.Collections;

import ressources.Chemins;

public final class Bombardier extends unite {

    public Bombardier(int appartient) {
        super(7, deplacement.Aerien, 20000, new ArrayList<armes>(), appartient,1,1);
        this.Arme.add(armes.Bombes);
    }

    @Override
    public boolean peutAttaque(unite def) {
        return def.typeDeplacement!=deplacement.Aerien;
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
        return Chemins.getCheminUnite(app, dispo, Chemins.FICHIER_BOMBARDIER);
    }
}
