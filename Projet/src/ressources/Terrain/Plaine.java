package ressources.Terrain;

import ressources.Chemins;

public class Plaine extends Terrain{
    @Override
    public String getChemin() {
        return Chemins.getCheminTerrain(Chemins.FICHIER_PLAINE);
    }
}
