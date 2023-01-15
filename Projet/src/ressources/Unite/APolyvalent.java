package ressources.Unite;

import java.util.ArrayList;
import java.util.Collections;

import ressources.Chemins;

public final class APolyvalent extends unite {

    public APolyvalent(int app) {
        super(5, deplacement.Aerien, 15000,new ArrayList<armes>(), app, 1, 1);
        this.Arme.add(armes.MitraLeg);
        this.Arme.add(armes.Canon);
        // n'ayans pas fais de distinction entre les canon et les lance rockette( bazooka equipe de canon ) 
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
                case MitraLeg -> coef.add(0.3);
                case MitraLourde -> coef.add(0.8);
                case MissileAirAir -> coef.add(1.2);
                default -> coef.add(0.);
            }
        }
        Double maxi = Collections.max(coef);
        return maxi;
    }

    @Override
    public String getChemin() {
        return Chemins.getCheminUnite(app, dispo, Chemins.FICHIER_APOLIVALENT);
    }

}
