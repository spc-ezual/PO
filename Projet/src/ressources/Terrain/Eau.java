package ressources.Terrain;
import ressources.Chemins;

public class Eau extends Terrain{

    @Override
    public String getChemin() {
        return Chemins.getCheminTerrain(Chemins.FICHIER_EAU);
    }
    
}
