public class Paire<a1,a2>{
    a1 Key;
    a2 Value;

    public Paire(a1 k,a2 j){
        Key=k;
        Value =j;
    }

    public a1 getKey(){return Key;}
    public a2 getValue(){return Value;}
    public void setKey(a1 c){Key=c;}
    public void setValue(a2 v){Value=v;}
    @Override
    public String toString() {
        return "{"+Key+","+Value+"}";
    }
}
