package ressources.Unite;

import java.util.ArrayList;
import java.util.Collections;

import ressources.Chemins;

public final class DCA extends unite {

    public DCA(int appartient) {
        super(6, deplacement.Chenille, 6000, null, appartient);
        ArrayList<armes> ar = new ArrayList<armes>();
        ar.add(armes.MitraLourde);
        this.Arme = ar;
    }

    @Override
    public boolean peutAttaque(unite def) {
        return true;
    }

    @Override
    public int maxCoef(ArrayList<armes> arme) {
        ArrayList<Double> coef = new ArrayList<Double>();
        for (armes ar : arme) {
            switch (ar) {
                case MitraLeg -> coef.add(0.1);
                case Canon -> coef.add(0.60);
                case MitraLourde -> coef.add(0.30);
                case Missile -> coef.add(0.40);
                case Bombes -> coef.add(0.7);
                case Mortier -> coef.add(0.7);
                default -> coef.add(null);
            }
        }
        Double maxi = Collections.max(coef);
        if(maxi!=null)return (int)maxi.doubleValue();
        return 0;
    }
    @Override
    public String getChemin() {
        return Chemins.getCheminUnite(app, dispo, Chemins.FICHIER_ANTIAIR);
    }
}
