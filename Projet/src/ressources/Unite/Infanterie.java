package ressources.Unite;

import java.util.ArrayList;
import java.util.Collections;

import ressources.Chemins;

public final class Infanterie extends unite {

    public Infanterie(int appartient) {
        super(3, deplacement.Pied, 1500, null, appartient,1,1);
        // super(...) doit etre en 1er dans le constructeur :
        // Constructor call must be the first statement in a constructorJava(1207959691)
        ArrayList<armes> ar = new ArrayList<armes>();
        ar.add(armes.MitraLeg);
        this.Arme = ar;
    }

    @Override
    public boolean peutAttaque(unite def) {
        return def.getClass() != Bombardier.class;
    }

    @Override
    public double maxCoef(ArrayList<armes> arme) {
        ArrayList<Double> coef = new ArrayList<Double>();
        for (armes ar : arme) {
            switch (ar) {
                case MitraLeg -> coef.add(0.6);
                case MitraLourde, Bombes -> coef.add(1.0);
                case Mortier -> coef.add(0.4);
                default -> coef.add(0.);
            }
        }
        Double maxi = Collections.max(coef);
        return maxi;
    }
    
    @Override
    public String getChemin() {
        return Chemins.getCheminUnite(app, dispo, Chemins.FICHIER_INFANTERIE);
    }
}
