package ressources.Unite;

import java.util.ArrayList;

public sealed abstract class unite permits Infanterie, Bazooka, Artillerie, Convoi, Bombardier, DCA, Tank, Helicoptere {
  public int nbPas;
  public deplacement typeDeplacement;
  public int prix;
  public ArrayList<armes> Arme;
  public int pv;
  public int app;
  public boolean dispo;

  public unite(int nbP, deplacement td, int p, ArrayList<armes> a, int appartient) {
    typeDeplacement = td;
    prix = p;
    Arme = a;
    app = appartient;
    pv = 10;
    nbPas = nbP;
    dispo = false;
  }

  public boolean enVie() {
    return pv > 0;
  }

  public abstract boolean peutAttaque(unite def);

  public void attaquer(unite def, boolean estRiposte){
    int degat = def.maxCoef(Arme) * (int) Math.floor(this.pv);
    def.newPv(degat);
    if (!estRiposte && def.enVie())
        attaquer(def, true);
      }

  public void newPv(int n) {
    pv = n;
  }

  public abstract int maxCoef(ArrayList<armes> arme);

    public abstract String getChemin();
    public void NouveauTours(){
      dispo=true;
    }
    public void FinDeTour(){
      dispo=false;
    }
}