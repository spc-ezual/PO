package ressources.Unite;

import java.util.ArrayList;

public sealed abstract class unite permits Infanterie, Bazooka, Artillerie, Convoi, Bombardier, DCA, Tank, Helicoptere,Achasse, APolyvalent,Drone {
  /**
   * Nb de pas maximum de l'unite
   */
  public int nbPas;
  /**
   * Type de deplacement de l'unite
   */
  public deplacement typeDeplacement;
  /**
   * Prix de l'unite
   */
  public int prix;
  /**
   * Enemble des armes de l'unite
   */
  public ArrayList<armes> Arme;
  /**
   * Point de Vie actuel de l'unite default 10
   */
  public double pv;
  /**
   * Index du joueur a qui elle appartient
   */
  public int app;
  /**
   * Vrais si l'unite des disponible
   */
  public boolean dispo;
  /**
   * Porte minimal de l'unite
   */
  public int porteMin;
  /**
   * Porte maximal de l'unite
   */
  public int porteMax;

  /**
   * Constructeur
   * @param nbP Nb de pas maximum de l'unite
   * @param td Type de deplacement de l'unite
   * @param p Prix de l'unite
   * @param a Enemble des armes de l'unite
   * @param appartient Index du joueur a qui elle appartient
   * @param pmn Porte minimal de l'unite
   * @param pmx Porte maximal de l'unite
   */
  public unite(int nbP, deplacement td, int p, ArrayList<armes> a, int appartient,int pmn,int pmx) {
    typeDeplacement = td;
    prix = p;
    Arme = a;
    app = appartient;
    pv = 10;
    nbPas = nbP;
    dispo = false;
    porteMin=pmn;
    porteMax=pmx;
    
  }

  /**
   * @return true si l'unite est en vie (Pv>0)
   */
  public boolean enVie() {
    return pv > 0;
  }

  /**
   * @param def unite a attaque
   * @return true si l'unite actuel peut attaque def
   */
  public abstract boolean peutAttaque(unite def);

  /**
   * @param def unite a attaque
   * @param estRiposte true si l'attaque est une riposte
   */
  public void attaquer(unite def, boolean estRiposte){
    double degat = def.maxCoef(Arme) * Math.floor(this.pv);
    def.newPv(degat);
    if (!estRiposte && def.enVie()&&def.peutAttaque(this)){
        def.attaquer(this, true);}
      }

  /**
   * @param n Pv a retire
   */
  public void newPv(double n) {
    pv -= n;
  }

  /**
   * @param arme ensemble des arme a teste 
   * @return plus grand coef. de degat que l'unite subi dans arme
   */
  public abstract double maxCoef(ArrayList<armes> arme);

    /**
     * @return chemin vers l'image de l'unite
     */
    public abstract String getChemin();
    /**
     * Rend l'unite disponible
     */
    public void NouveauTours(){
      dispo=true;
    }
    /**
     * Rend l'unite indisponible
     */
    public void FinDeTour(){
      dispo=false;
    }
    @Override
    public String toString() {
        return this.getClass().getSimpleName()+" à "+this.pv+" PV";
    }

    /**
     * @return Prix de l'unite actuel
     */
    public int getPrix() {
    return this.prix;
}

  }

