package grammar;

public class PLUS extends Grammar{
    public PLUS(String name) {
        super(name);
    }

    @Override
    public String check(String str, int pos) {
        String result = node.check(str, pos);
        String backup = null;
        if(result == null){return null;}

        while (result != null){
            backup = result;
            result = node.check(str, pos);
        }
        return backup;
    }

    private Grammar node;


    public Grammar getNode() {
        return node;
    }

    public void setNode(Grammar node) {
        this.node = node;
    }
}
