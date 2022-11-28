package TP4;

import TP4.Animaux.Animal;
import TP4.Animaux.Oiseaux;
public class Zoo {
    private String Name;
    private int Max;
    private int Actuel;
    private Animal[] listeAnimaux;

    public String getName() {
        return this.Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public int getMax() {
        return this.Max;
    }

    public void setMax(int Max) {
        this.Max = Max;
    }

    public int getActuel() {
        return this.Actuel;
    }

    public void setActuel(int Actuel) {
        this.Actuel = Actuel;
    }

    public Animal[] getListeAnimaux() {
        return this.listeAnimaux;
    }

    public void setListeAnimaux(Animal[] listeAnimaux) {
        this.listeAnimaux = listeAnimaux;
    }

    public Zoo(String n , int max){
        Name=n;
        Max=max;
        Actuel=0;
        listeAnimaux = new Animal[max];
    }
    
    public void ajouterAnimal(Animal a){
        if(Actuel<Max){
            listeAnimaux[Actuel++]=a;
        }
        else{
            System.out.println(Name+" est complet!!");
        }
    }
    public void supprimerAnimal(Animal a) {
        for(int i =0;i<Actuel;i++){
            if(listeAnimaux[i].equals(a)){
                if(i<Actuel-1){
                    listeAnimaux[i]=listeAnimaux[--Actuel];
                    listeAnimaux[Actuel]=null;
                }
                else{
                    listeAnimaux[Actuel--]=null;
                }
            }

        }
        
    }
    @Override
    public String toString() {
        String rep="";
        for (int i =0;i<Actuel;i++)rep+=listeAnimaux[i]+"\n";
        return rep;

    }
    
    public Animal doyen(){
        Animal rep =listeAnimaux[0] ;
        for(int i =1;i<Actuel;i++){
            if(rep.getAge()<listeAnimaux[i].getAge())rep=listeAnimaux[i];
        }
        return rep;
    }

    public double ageMoyen(){
        double rep=0;
        for(int i =0;i<Actuel;i++)rep+=listeAnimaux[Actuel].getAge();
        return rep/Actuel;
    }

    public Animal[] enfants() {
        Animal[] rep = new Animal[Max];
        int va=0;
        for (int i =0;i<Actuel;i++){
            if(listeAnimaux[i].getAge()<listeAnimaux[i].getLongevite())rep[va++]=listeAnimaux[i];
            
        }
        return rep;

    }
    public void creerSpectacle(Oiseaux o){
        o.setSpc(o.getSpc()+1);
        System.out.println(o.getName()+" participa à un spectacle.");
    }
    public Oiseaux trouverOiseauLeMoinsFatigue(){
        Oiseaux rep=null;
        for (Animal animal : listeAnimaux) {
            if(animal instanceof Oiseaux){
                Oiseaux animau=(Oiseaux) animal;
                if(rep==null){
                    rep=animau;
                }
                else if(rep.getSpc()< animau.getSpc()){
                    rep=animau;
                }
            }
        }
        return rep;
    }

}
