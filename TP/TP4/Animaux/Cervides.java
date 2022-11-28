package TP4.Animaux;

public class Cervides extends Animal{

    public Cervides(int a, String n) {
        super(a, n);
    }
    @Override
    public String toString() {
        return super.toString()+" C'est un cervidé.";
    }
    @Override
    public int getLongevite(){
        return  20;
    }
}
