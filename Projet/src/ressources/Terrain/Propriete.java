package ressources.Terrain;

import ressources.Unite.unite;

public  abstract class Propriete extends Terrain {
    public int appartient ;
    public int Pv=20;
        public Propriete(int a){
            appartient=a;
        }
        @Override
        public boolean capture(unite u){
            Pv-=Math.floor(u.pv);
            enCapture=true;
            if(Pv<1){
                Pv=20;
                appartient=u.app;
            }
            return false;
        }
        @Override
        public int appart() {
            return appartient;
        }
        @Override
        public void RemiseAzero() {
            Pv=20;
        }
        @Override
        public String toString() {
            return super.toString()+ " a " +Pv+" PV";
        }
}
