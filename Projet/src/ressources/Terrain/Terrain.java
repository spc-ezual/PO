package ressources.Terrain;
import ressources.Unite.unite;

public  abstract class Terrain  {
    /**
     * unite sur la case 
     */
    public unite act=null;
    /**
     * Si la case est en capture actuelement
     */
    public boolean enCapture=false;
    /**
     * @param u unite a place sur la case
     */
    public void setUnit(unite u){
        act=u;
        
    }
    /**
     * @param enAttaque si la case peut etre attaque
     * @return chemin de l'image 
     */
    public abstract String getChemin(boolean enAttaque);
    /**
     * @param u unite qui capture
     * @return true si le qg a ete capture
     */
    public boolean capture(unite u){return false;}
    /**
     * @return iindex du joeur proprietaire de la case -1 si ce n'es pas une propriete
     */
    public int appart(){return -1;}
    /**
     * remise a 20 des pv uniquement pour les propriete
     */
    public void RemiseAzero(){}
    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
