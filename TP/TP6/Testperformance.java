public class Testperformance {
    public static void main(String[] args) {
        for(int i =1; i<=20;i++){
            //System.out.println(timeSetListe(5000*i));
            //System.out.println(timeSetArbre(5000*i));
            //System.out.println(timeGetListe(i*5000));
            System.out.println(timeGetArbre(5000*i));
        } 
        
    }
    public static long timeSetListe(double iter){
        DicListe lis1=new DicListe();
        long start=System.nanoTime();
        for(int i =0; i<iter;i++) {
			int a = (int)( Math.random() * 99999 -1);
			int b = (int)( Math.random() * 99999 -1);
			String c = Integer.toString(a);
			String d = Integer.toString(b);
			lis1.associe(c, d);
		}
        return System.nanoTime()-start;
    }
    public static long timeSetArbre(double iter){
        DicoArbre lis1=new DicoArbre();
        long start=System.nanoTime();
        for(int i =0; i<iter;i++) {
			int a = (int)( Math.random() * 99999 -1);
			int b = (int)( Math.random() * 99999 -1);
			String c = Integer.toString(a);
			String d = Integer.toString(b);
			lis1.associe(c, d);
		}
        return System.nanoTime()-start;
    }
    public static long timeGetListe(double iter){
        DicListe lis1=new DicListe();
        for(int i =0; i<iter;i++) {
			int a = (int)( Math.random() * 99999 -1);
			int b = (int)( Math.random() * 99999 -1);
			String c = Integer.toString(a);
			String d = Integer.toString(b);
			lis1.associe(c, d);
		}
        long start=System.nanoTime();
        for(int i =0; i<iter;i++) {
			int a = (int)( Math.random() * 99999 -1);
			String c = Integer.toString(a);
			lis1.get(c);
		}
        return System.nanoTime()-start;
    }
    public static long timeGetArbre(double iter){
        DicoArbre lis1=new DicoArbre();
        for(int i =0; i<iter;i++) {
			int a = (int)( Math.random() * 99999 -1);
			int b = (int)( Math.random() * 99999 -1);
			String c = Integer.toString(a);
			String d = Integer.toString(b);
			lis1.associe(c, d);
		}
        long start=System.nanoTime();
        for(int i =0; i<iter;i++) {
			int a = (int)( Math.random() * 99999 -1);
			String c = Integer.toString(a);
			lis1.get(c);
		}
        return System.nanoTime()-start;
    }

}
