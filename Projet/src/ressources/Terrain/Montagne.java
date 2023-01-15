package ressources.Terrain;
import ressources.Chemins;
public  class Montagne extends Terrain{
    @Override
    public String getChemin(boolean enAttaque) {
        return Chemins.getCheminTerrain(Chemins.FICHIER_MONTAGNE,enAttaque);
    }
}
