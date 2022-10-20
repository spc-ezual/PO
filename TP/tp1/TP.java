import java.awt.Color;

public class TP{
    public static void main(String[] args) {
        System.out.println(estPresentEn(0, "bon", "bonjour"));
        //StdDraw.circle(0.5, 0.2, 0.2);
        //exo5(0.1, 0.05, 0.05, 10);
        piramide(64);
    }

    public static boolean estPresentEn( int i ,String s1, String s) {
        return (s.length()-i-s1.length()>=0)&&(s.substring(i,i+s1.length()).equals(s1));
    }

    public static int indiceDe(String s1,String s) {
        for(int i=0;i<s.length();i++){if(estPresentEn(i, s1, s))return i;}return -1;
    }

    public static void piramide(int k) {
        exo5((double)1/k, (double)1/(k*2) , (double)1/(k*2) ,k);
        //exo5(0.1, 0.05, 0.05, 10);
    }
    public static void exo5(double longeur,double hauteur,double largeur,int k) {
        if(k==0)return;

        
        exo5(longeur, hauteur+largeur*2, largeur, k-1);
        
        for(double i=0; i<k;i++){
        
            StdDraw.circle(largeur+i*longeur,1-hauteur, largeur);
        
        }
        
    }
}