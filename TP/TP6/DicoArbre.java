
public class DicoArbre extends Dico {
    Noeud racine;

    class Noeud{
        Noeud Droit;
        Noeud Gauche;
        Paire<String,String> pair;

        public Noeud(Noeud D,Noeud G,String Key,String Value){
            Droit=D;
            Gauche=G;
            pair=new Paire<String,String>(Key,Value);
        }
        public Noeud(String Key,String Value){
            pair=new Paire<String,String>(Key,Value);
        }
        public String getKey(){
            return pair.getKey();
        }
        
        public String getValue(){
            return pair.getValue();
        }
        public void setKey(String c){
            pair.setKey(c);
        }
        
        public void setValue(String v){
            pair.setValue(v);
        }
        public void setPair(Paire<String,String> n){
            pair=n;
        }
        public Paire<String,String> getPair(){
            return pair;
        }
        public void resetAll(Noeud news){
            Droit=news.Droit;
            Gauche=news.Gauche;
            pair=news.pair;
        }
        @Override
        public String toString() {
            return " ("+Gauche+") "+pair+" ("+Droit+")";
        }
    }

    @Override
    public void associe(String cle, String valeur) {
        if(racine==null){
            racine=new Noeud( cle, valeur);
            return;
        }
        else inser(cle, valeur, racine);
    }
    
    /**
     * @param cle Clé de l'association, supposée non nulle
     * @param value Valeur de l'association, supposée non nulle
     * @param courant Noeud apartir du quel ajouter le nouveau noeud
     */
    public void inser(String cle,String value,Noeud courant){
        if(courant.getKey()==cle){
            courant.setValue(value);
        }
        else if(courant.getKey().compareTo(cle)>0){
            if(courant.Gauche==null){
                courant.Gauche=new Noeud(cle, value);
            }
            else{
                inser(cle, value, courant.Gauche);
            }
        }
        else{
            if(courant.Droit==null){
                courant.Droit=new Noeud(cle, value);
            }
            else{
                inser(cle, value, courant.Droit);
            }
        }
    }


    
    @Override
    public void supprime(String cle) {
        supprimerRc(cle, racine);
        
    }

    public void supprimerRc(String cle,Noeud courant){
        Noeud aSupp=getRc(cle, courant);
        // Cas avec 1 Feuille
        if(aSupp.Gauche!=null&&aSupp.Droit==null){
            aSupp.resetAll(aSupp.Gauche);
        }
        else if(aSupp.Gauche==null&&aSupp.Droit!=null){
            aSupp.resetAll(aSupp.Droit);
        }

        else{
            Noeud pere=getPere(aSupp);

            // cas feuille
            if(aSupp.Gauche==null&&aSupp.Droit==null){
                if(pere.Gauche==aSupp){
                    pere.Gauche=null;
                }
                else {
                    pere.Droit=null;
                }
            }
            //cas 2 feuilles
            else{
                Noeud rempNoeud=plusDroit(aSupp.Gauche);
                Noeud pereRemp=getPere(rempNoeud);
                
                if(pereRemp==aSupp){
                    aSupp.Gauche=rempNoeud.Gauche;
                }
                else{
                    pereRemp.Droit=rempNoeud.Gauche;
                }

                aSupp.setPair(rempNoeud.getPair());

            }
        }
        
    }
    private Noeud getPere(Noeud courant){
        Noeud pere= new Noeud(null, null);
        Noeud actuel=racine;
        while(actuel!=courant){
            pere=actuel;
            if(actuel.getKey().compareTo(courant.getKey())>0){
                actuel=actuel.Gauche;
            }
            else {
                actuel=actuel.Droit;
            }
        }
        return pere;        
    }
    public Noeud plusDroit(Noeud courant){
        if(courant.Droit==null){
            return courant;
        }
        else return plusDroit(courant.Droit);
    }

    @Override
    public String get(String cle) {
        Noeud rep=getRc(cle, racine);
        if(rep!=null)
        return rep.getValue();
        else return null;
    }

    /**
     * @param cle Clé à rechercher
     * @param courant Noeud à patrir du quel chercher
     * @return Noeud associé à la clef
     */
    public Noeud getRc(String cle,Noeud courant){
        if(courant==null) return null;
        if(courant.getKey().compareTo(cle)>0){
            return getRc(cle, courant.Gauche);
        }
        else if(courant.getKey().compareTo(cle)<0){
            return getRc(cle, courant.Droit);
        }
        return courant;
    }

    
	private String toStringRec(Noeud courant, String prefixe) {
		String resultat = "";
		if(courant!=null) {
			resultat +=	prefixe + "("+ courant.getKey() + ", " + courant.getValue() + ")" + "\n"+ toStringRec(courant.Gauche, prefixe + "  ") + toStringRec(courant.Droit, prefixe + "  ") ;			
			return resultat;
		}
		return "";
		
	}
	
	public String toString() {
		if(racine==null) {
			return "l'arbre est vide!!!!!";
		}
		else return toStringRec(racine,"");
	}
}

