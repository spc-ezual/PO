package TP4.Animaux;

public class Aigle extends Oiseaux{

    public Aigle(int a, String n) {
        super(a, n);
    }

    @Override
    public String toString() {
        return super.toString()+" C'est un aigle.";
    }
    @Override 
    public int getLongevite(){
        return 14;
    }
}
