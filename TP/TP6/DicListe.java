public class DicListe extends Dico {
    Element TeteDeListe;

    private class Element{
        Element Suivant;
        Paire<String,String> pair;

        public Element(Paire<String,String> v,Element s){
            pair=v;
            Suivant=s;
        }
        public String getKey(){
            return pair.getKey();
        }
        public String getValue(){
            return pair.getValue();
        }
        public Paire<String,String> getPair(){
            return pair;
        }
        public Element getSuivant() {
            return this.Suivant;
        }
        public void setSuivant(Element Suivant) {
            this.Suivant = Suivant;
        }
}

    public DicListe(){
        TeteDeListe=null;
    }

    @Override
    public void associe(String cle, String valeur) {
        Paire<String,String> p=new Paire<String,String>(cle, valeur);
        if(get(cle)!=null){
            supprime(cle);
        }
        inser(p);
    }

    @Override
    public void supprime(String cle) {
            if(!estVide()){
            if(TeteDeListe.getValue()==cle){
                TeteDeListe=TeteDeListe.Suivant;
            }
                Element courant = TeteDeListe;
                Element suit = TeteDeListe.getSuivant();
                while(suit!=null){
                        if(suit.getKey()==cle){
                            courant.setSuivant(suit.Suivant);  
                        }
                        courant=courant.getSuivant();
                        suit=suit.getSuivant();
                    
                    }
                    
                }
    }

    @Override
    public String get(String cle) {
        if(!estVide()){
            Element courant=TeteDeListe;
            while(courant!=null){
                if(courant.getKey()==cle)return courant.getValue();
                courant=courant.getSuivant();
            }
        }
        return null;
    }
    

    public boolean estVide(){
        return TeteDeListe ==null;

    }

    public void inser(Paire<String,String> x){
        Element temp= new Element(x, TeteDeListe);
        TeteDeListe=temp;
        
            
    }
    @Override
    public String toString() {
        String rep ="";
        if(!estVide()){
            Element courant= TeteDeListe;
            while(courant!=null){
                rep += courant.getPair()+" ";
                courant=courant.getSuivant();
            }
        }
        return rep;
    }
}
