package ressources.Terrain;
import ressources.Chemins;
public class Usine extends Propriete {

    public Usine(int a) {
        super(a);
    }
    @Override
    public String getChemin(boolean enAttaque) {
        return Chemins.getCheminPropriete(Chemins.FICHIER_USINE,appartient,enAttaque);
    }
}
