package TP4;

import TP4.Animaux.*;

class teste{
    public static void main(String[] args) {
        Zoo fleche=new Zoo("La Flèche", 1600);
        fleche.ajouterAnimal(new Aigle(8,"a"));
        fleche.ajouterAnimal(new Cerf(3,"b"));
        fleche.ajouterAnimal(new Faucon(12,"c"));
        fleche.ajouterAnimal(new Elan(40,"d"));
        fleche.ajouterAnimal(new Panthere(8,"e"));
        fleche.ajouterAnimal(new Perroquet(7,"f"));
        fleche.ajouterAnimal(new Daim(0,"g"));
        fleche.ajouterAnimal(new Lion(8,"h"));
        fleche.ajouterAnimal(new Tigre(1,"i"));
        System.out.println(fleche);
        fleche.supprimerAnimal(new Lion(1, "i"));
        fleche.ajouterAnimal(new Faucon(0, "nul"));
        System.out.println(fleche);
        System.out.println(fleche.enfants());
    }
}