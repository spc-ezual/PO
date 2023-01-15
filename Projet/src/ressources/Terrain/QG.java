package ressources.Terrain;
import ressources.Chemins;
import ressources.Unite.unite;

public class QG extends Propriete{

    public QG(int a) {
        super(a);
    }
    @Override
    public String getChemin(boolean enAttaque) {
        return Chemins.getCheminPropriete(Chemins.FICHIER_QG,appartient,enAttaque);
    }
    @Override
    public boolean capture(unite u) {
        Pv-=Math.floor(u.pv);
        enCapture=true;
        if(Pv<1){
            Pv=20;
            appartient=u.app;
            return true;
        }
        return false;
    }
}
