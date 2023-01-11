package ressources.Unite;

import java.util.ArrayList;
import java.util.Collections;

import ressources.Chemins;

public final class Bombardier extends unite {

    public Bombardier(int appartient) {
        super(7, deplacement.Aerien, 20000, null, appartient);
        ArrayList<armes> ar = new ArrayList<armes>();
        ar.add(armes.Bombes);
        this.Arme = ar;
    }

    @Override
    public boolean peutAttaque(unite def) {
        return def.getClass()!=Helicoptere.class||def.getClass()!=Bombardier.class;
    }

    @Override
    public int maxCoef(ArrayList<armes> arme) {
        ArrayList<Double> coef = new ArrayList<Double>();
        for (armes ar : arme) {
            switch (ar) {
                case MitraLourde -> coef.add(0.7);
                default -> coef.add(null);
            }
        }
        Double maxi = Collections.max(coef);
        if(maxi!=null)return (int)maxi.doubleValue();
        return 0;
    }
    @Override
    public String getChemin() {
        return Chemins.getCheminUnite(app, dispo, Chemins.FICHIER_BOMBARDIER);
    }
}
