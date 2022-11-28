package TP4.Animaux;

public class Panthere extends Felins{

    public Panthere(int a, String n) {
        super(a, n);
    }

@Override
public String toString() {
    return super.toString()+" C'est une panthere.";
}

@Override
public int getLongevite() {
    return 15;
}


    
}
