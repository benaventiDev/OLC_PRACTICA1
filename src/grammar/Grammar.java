package grammar;

public abstract class Grammar {
    private String name;
    public Grammar(String name){
        this.name = name;
    }
    public abstract String check(String str, int pos);
    public String getName(){return  name;}
}
