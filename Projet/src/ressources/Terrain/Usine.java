package ressources.Terrain;
import ressources.Chemins;
public class Usine extends Propriete {

    public Usine(int a) {
        super(a);
        //TODO Auto-generated constructor stub
    }
    @Override
    public String getChemin() {
        return Chemins.getCheminPropriete(Chemins.FICHIER_USINE,appartient);
    }
}
