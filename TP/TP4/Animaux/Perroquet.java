package TP4.Animaux;

public class Perroquet extends Oiseaux{

    public Perroquet(int a, String n) {
        super(a, n);
    }

    @Override
    public int getLongevite() {
        return 45;
    }
    
}
