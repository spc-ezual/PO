package TD5;

class ListeDouble{
    private class Element{
        int Value;
        Element Suivant;
        Element Precedent;

        public Element(int v,Element s,Element p){
            Value=v;
            Suivant=s;
            Precedent=p;
        }

        public int getValue() {
            return this.Value;
        }

        public Element getSuivant() {
            return this.Suivant;
        }

        public void setSuivant(Element Suivant) {
            this.Suivant = Suivant;
        }

        public Element getPrecedent() {
            return this.Precedent;
        }

        public void setPrecedent(Element Precedent) {
            this.Precedent = Precedent;
        }

    }
    Element TeteDeListe;
    Element FinDeListe;

    public ListeDouble(){
        TeteDeListe=null;
        FinDeListe=null;
    }

    public boolean estVide(){
        return TeteDeListe ==null;

    }
    public void inserEnTete(int x){
        Element temp=new Element(x, TeteDeListe,null );
        if(estVide()){
            FinDeListe=temp;
            TeteDeListe=temp;
        }
        else{
            TeteDeListe.setPrecedent(temp);
            TeteDeListe=temp;
        }
    }
    public void inserEnFin(int x){
        Element temp= new Element(x, null, FinDeListe);
        if(estVide()){
            FinDeListe=temp;
            TeteDeListe=temp;
        }
        else{
            FinDeListe.setSuivant(temp);
            FinDeListe=temp;
        }
    }
    public void inserApres(int v, int x){
        if(!estVide()){
            if(FinDeListe.getValue()==v){
                inserEnFin(x);
            }
            else{
            Element courant=TeteDeListe;
            
            
            do{
                if(courant.getValue()==v){
                    Element temp = new Element(x, courant.getSuivant(), courant);
                    courant.getSuivant().setPrecedent(temp);
                    courant.setSuivant(temp);
                    break;
                }else{
                    courant=courant.getSuivant();
                }
                


            }while(courant!=null);
            }
        }
        
    }
    public void inserAvant(int v,int x){
        if(!estVide()){
            if(TeteDeListe.getValue()==v){
                inserEnTete(x);
            }
            else{
            Element courant=TeteDeListe;
            
            
            do{
                if(courant.getValue()==v){
                    Element temp = new Element(x, courant, courant.getPrecedent());
                    courant.getPrecedent().setSuivant(temp);
                    courant.setPrecedent(temp);
                    break;
                }else{
                    courant=courant.getSuivant();
                }
                


            }while(courant!=null);
            }
        }
    }
    public void supprime(int x){
        if(!estVide()){
            Element courant = TeteDeListe;
            while(courant!=null){
                    if(courant.getValue()==x){
                        if(courant.getPrecedent()!=null){
                            courant.getPrecedent().setSuivant(courant.getSuivant());

                        }
                        else{
                            TeteDeListe=courant.getSuivant();
                        }
                        if(courant.getSuivant()!=null){
                            courant.getSuivant().setPrecedent(courant.getPrecedent());

                        }
                        else{
                            FinDeListe=courant.getPrecedent();
                        }
                    }



                courant= courant.getSuivant();
            }

        }

    }
    @Override
    public String toString() {
        String rep ="";
        if(!estVide()){
            Element courant= TeteDeListe;
            while(courant!=null){
                rep += courant.getValue()+" ";
                courant=courant.getSuivant();
            }
        }
        return rep;
    }
    public void insertionTriee(int x){
        if(!estVide()){
            if(FinDeListe.getValue()<x){
                inserEnFin(x);
            return;
            }
            Element courant=TeteDeListe;
            while(courant!=null){
                if(x<courant.getValue()){
                    if(TeteDeListe==courant){
                        inserEnTete(x);
                    }else{
                    Element temp = new Element(x, courant, courant.getPrecedent());
                    courant.getPrecedent().setSuivant(temp);
                    courant.setPrecedent(temp);
                    }
                    break;
                    
                }
                else{
                    courant=courant.getSuivant();
                }
            }

        }
        else{
            inserEnTete(x);
        }
    }
}