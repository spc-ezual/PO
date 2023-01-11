package ressources.Unite;

import java.util.ArrayList;
import java.util.Collections;

import ressources.Chemins;

public final class Artillerie extends unite {

    public Artillerie(int appartient) {
        super(6, deplacement.Chenille, 6000, new ArrayList<armes>(), appartient);
        this.Arme.add(armes.Mortier);
    }

    @Override
    public boolean peutAttaque(unite def) {
        return def.typeDeplacement!=deplacement.Aerien;
    }

    @Override
    public int maxCoef(ArrayList<armes> arme) {
        ArrayList<Double> coef = new ArrayList<Double>();
        for (armes ar : arme) {
            switch (ar) {
                case MitraLeg -> coef.add(0.40);
                case Canon -> coef.add(0.7);
                case MitraLourde -> coef.add(0.5);
                case Missile -> coef.add(0.70);
                case Bombes -> coef.add(1.0);
                case Mortier -> coef.add(0.7);
                default -> coef.add(null);
            }
        }
        Double maxi = Collections.max(coef);
        if(maxi!=null)return (int)maxi.doubleValue();
        return 0;
    }@Override
    public String getChemin() {
        return Chemins.getCheminUnite(app, dispo, Chemins.FICHIER_ARTILLERIE);
    }
}
