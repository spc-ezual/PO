package TD7;

public class ArbreBinaire {
    
    public class Noeud {
        int element;
        Noeud filsDroit;
        Noeud filsGauche;
        
        public Noeud(int element){
            this.element=element;
            filsDroit=null;
            filsGauche=null;
        }
        public Noeud(int element,Noeud filsGauche,Noeud filsDroit){
            this.element=element;
            this.filsDroit=filsDroit;
            this.filsGauche=filsGauche;
        }
        
    }
    
    private Noeud racine;

    public int size(){
    return sizerc(racine);
    }
    public int sizerc(Noeud act){
        if(act==null)return 0;
        return 1+sizerc(act.filsDroit)+sizerc(act.filsGauche);
    }

    public int leaves(){
        return leavesrc(racine);
    }
    public int leavesrc(Noeud act){
        if(act==null)return 0;
        if(act.filsDroit==null&&act.filsGauche==null)return 1;
        return leavesrc(act.filsDroit)+leavesrc(act.filsDroit);
    }
    public int height(){
        return heightrc(racine);
    }
    public int heightrc(Noeud act){
        if(act==null)return 0;
        return 1+Math.max(heightrc(act.filsDroit),heightrc(act.filsGauche));
    }
    public boolean balanced (){
        return balancedrc(racine);
    }
    public boolean balancedrc(Noeud act){
        if(act==null)return true;
        return balancedrc(act.filsGauche)&&balancedrc(act.filsDroit)&&(Math.abs(heightrc(act.filsDroit))-Math.abs(heightrc(act.filsGauche))<=1);
    }
    public int Max(){
        Noeud act=racine;
        return 1;
    }
}
