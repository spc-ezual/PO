package TP4.Animaux;

public abstract class  Oiseaux extends Animal{
    protected int spc=0;


	public int getSpc() {
		return this.spc;
	}

	public void setSpc(int spc) {
		this.spc=spc;
	}

    public Oiseaux(int a, String n) {
        super(a, n);
    }
    @Override
    public String toString() {
        return super.toString()+" C'est un oiseau, il a participé à "+spc+" spectacle";
    }
    public abstract int getLongevite();
}
