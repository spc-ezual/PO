package ressources.Terrain;
import ressources.Chemins;

public class Foret extends Terrain{
    @Override
    public String getChemin() {
        return Chemins.getCheminTerrain(Chemins.FICHIER_FORET);
    }
}
