import TD7.ArbreBinaire.Noeud;

public class DicoArbre extends Dico {
    Element racine;
    private class Element{
        Element Gauche;
        Element Droit;
        Paire<String,String> pair;

        public Element(Paire<String,String> v,Element g,Element d){
            pair=v;
            Gauche=g;
            Droit=d;
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
        public Element getDroit(){
            return Droit;
        }
        public void setDroit(Element d){
            Droit=d;
        }
        public Element getGauche(){
            return Gauche;
        }
        public void setGauche(Element g){
            Gauche=g;
        }
    }
    public boolean estVide(){
        return racine==null;
    }
    @Override
    public void associe(String cle, String valeur) {
        Element temp= new Element(new Paire<String,String>(cle, valeur), null, null);
        if(estVide())racine=temp;
         else inser(temp,racine);
    }

    @Override
    public void supprime(String cle) {
        
    }

    @Override
    public String get(String cle) {
        return getRc(cle, racine);
    }

    public String getRc(String cle , Element x){
        if(x==null) return null;
        if(x.getKey()==cle) return x.getValue();
        if(x.getKey().compareTo(cle)<0){
            return getRc(cle, x.getGauche());
        }else {
            return getRc(cle, x.getDroit());
        }
    }
    public void inser(Element x,Element courant){
        if(x.getKey()==courant.getKey())courant.pair.Value=x.getValue();
            if(courant.getKey().compareTo(x.getKey())<0){
                if(courant.getGauche()==null) {courant.setGauche(x);}
                else {inser(x,courant.getGauche());}
            }else{
                if(courant.getDroit()==null) {courant.setDroit(x);}
                else {inser(x,courant.getDroit());}
                
            }
    }
    @Override
    public String toString(){
        if(estVide())return "L'arbre est vide";
        else return affiche(racine,"");
    }

    public String affiche(Element x,String esp) {
        String resultat = "";
		if(x!=null) {
			resultat +=
					esp + "("+ x.getKey() + ", " + x.getValue() + ")" + "\n"
					+ affiche(x.Gauche, esp + "  ") 
					+ affiche(x.Droit, esp + "  ") ;			
			return resultat;
		}
		return "";
    }


}

