package ressources.Terrain;
import ressources.Unite.unite;

public abstract class Terrain {
    public unite act=null;
    public void setUnit(unite u){
        act=u;
        
    }
    public abstract String getChemin();
}
