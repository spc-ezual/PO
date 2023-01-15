
package ressources.Unite;

import java.util.ArrayList;
import java.util.Collections;

import ressources.Chemins;

public final class Drone extends unite {

    public Drone(int appartient) {
        super(8, deplacement.Aerien, 25000, new ArrayList<armes>(), appartient, 1, 1);
        this.Arme.add(armes.MissileAirAir);
        this.Arme.add(armes.MitraLourde);
    }

    @Override
    public boolean peutAttaque(unite def) {
        return true;
    }

    @Override
    public double maxCoef(ArrayList<armes> arme) {
        ArrayList<Double> coef = new ArrayList<Double>();
        for (armes ar : arme) {
            switch (ar) {
                case MitraLourde -> coef.add(0.95);
                case MissileAirAir -> coef.add(1.3);
                default -> coef.add(0.);
            }
        }
        Double maxi = Collections.max(coef);
        return maxi;
    }

    @Override
    public String getChemin() {
        return Chemins.getCheminUnite(app, dispo, Chemins.FICHIER_DRONE);
    }

}
