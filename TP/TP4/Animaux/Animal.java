package TP4.Animaux;

public abstract class Animal {
    protected int age;
    protected String name;

    public Animal(int a, String n){
        age=a;
        name=n;
    }
    
    public void setName(String n){name=n;
    }
    public void setAge(int a){
        age=a;
    }
    public int getAge(){return age;}
    public String getName(){return name;}
    @Override
    public String toString() {
        return name+" a "+age+" ans.";
    }
    public boolean equals(Animal obj) {
        return (getClass()==obj.getClass())&&obj.age==age&&obj.name==name;
    }
    public abstract int getLongevite();
}
