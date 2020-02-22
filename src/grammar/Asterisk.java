package grammar;

public class Asterisk extends Grammar{
    private Grammar node;

    public Asterisk(String name) {
        super(name);
    }

    @Override
    public String check(String str, int pos) {


        String result = node.check(str, pos);
        String backup = null;
        if(result == null){return str;}

        while (result != null){
            backup = result;
            result = node.check(str, pos);
        }
        return backup;
    }

    public Grammar getNode() {
        return node;
    }

    public void setNode(Grammar node) {
        this.node = node;
    }
}
