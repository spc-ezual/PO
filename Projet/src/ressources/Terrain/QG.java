package ressources.Terrain;
import ressources.Chemins;

public class QG extends Propriete{

    public QG(int a) {
        super(a);
        //TODO Auto-generated constructor stub
    }
    @Override
    public String getChemin() {
        return Chemins.getCheminPropriete(Chemins.FICHIER_QG,appartient);
    }
    
}
