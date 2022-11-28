package TP4.Animaux;

public class Tigre extends Felins{

    public Tigre(int a, String n) {
        super(a, n);
    }
    @Override
    public String toString() {
        return super.toString()+" C'est un tigre.";
    }
    @Override
    public int getLongevite() {
        return 23;
    }
    
}
