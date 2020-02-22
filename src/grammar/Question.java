package grammar;

public class Question extends Grammar {
    private Grammar node;
    public Question(String name){
        super(name);
    }

    @Override
    public String  check(String str, int pos) {
        String result = node.check(str, pos);
        if(result == null){
            return str;
        }else {
            return result;
        }
    }

    public Grammar getNode() {
        return node;
    }

    public void setNode(Grammar node) {
        this.node = node;
    }
}
