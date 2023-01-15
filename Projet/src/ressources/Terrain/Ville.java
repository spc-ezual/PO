package ressources.Terrain;

import ressources.Chemins;

public class Ville extends Propriete {

    public Ville(int a) {
        super(a);
    }
    @Override
    public String getChemin(boolean enAttaque) {
        return Chemins.getCheminPropriete(Chemins.FICHIER_VILLE,appartient,enAttaque);
    }
    
}
