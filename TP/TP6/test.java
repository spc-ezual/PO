public class test {
    public static void main(String[] args) {
        DicListe d=new DicListe();
        d.associe("null", "null");
        d.associe("a", "a");
        System.out.println(d);
        d.associe("b", "c");
        d.associe("a", "b");
        System.out.println(d);
        d.supprime("null");
        System.out.println(d);

        DicoArbre tree = new DicoArbre();
        tree.associe("5", "timoté");
        System.out.println(tree);
        tree.associe("4", "gitan");
        System.out.println(tree);
        tree.associe("9", "toro");
        tree.associe("1", "rttrt");
        tree.associe("44", "rttrt");

        tree.associe("5", "rttrt");
        System.out.println(tree.get("4"));
    }
}
