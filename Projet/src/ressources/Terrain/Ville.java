package ressources.Terrain;

import ressources.Chemins;

public class Ville extends Propriete {

    public Ville(int a) {
        super(a);
        //TODO Auto-generated constructor stub
    }
    @Override
    public String getChemin() {
        return Chemins.getCheminPropriete(Chemins.FICHIER_VILLE,appartient);
    }
    
}
