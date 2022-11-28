
public class Paire<T1, T2> {
    protected T1 Key;
    protected T2 Value;

    public T1 getKey() {
        return this.Key;
    }

    public void setKey(T1 Key) {
        this.Key = Key;
    }

    public T2 getValue() {
        return this.Value;
    }

    public void setValue(T2 Value) {
        this.Value = Value;
    }
    public Paire(T1 k,T2 V){
        Key=k;
        Value=V;
    }

    @Override
    public String toString() {
        return "{"+Key+","+Value+"}";
    }

}
