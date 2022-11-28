package TP4.Animaux;

public class Faucon extends Oiseaux{

    public Faucon(int a, String n) {
        super(a, n);
    }
    @Override
    public String toString() {
        return super.toString()+" C'est un faucon.";
    }
    @Override
    public int getLongevite(){return 13;}
}
