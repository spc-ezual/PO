package TP4.Animaux;

public class Lion extends Felins{

    public Lion(int a, String n) {
        super(a, n);
    }
    @Override
    public String toString() {
        return super.toString()+"C'est un lion.";
    }
    @Override
    public int getLongevite() {
        return 12;
    }
    
}
