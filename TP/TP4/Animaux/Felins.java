package TP4.Animaux;

public abstract class Felins extends Animal{
    public Felins(int a, String n) {
        super(a, n);
    }
    @Override
    public String toString() {
        return super.toString()+" C'est un félin.";
    }
    public abstract int getLongevite();
}
