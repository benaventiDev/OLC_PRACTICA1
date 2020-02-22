package grammar;

public class Concatenation extends Grammar {
    private Grammar nodeRight;
    private Grammar nodeLeft;

    public Concatenation(String name){
        super(name);
    }

    public Grammar getNodeRight() {
        return nodeRight;
    }

    public void setNodeRight(Grammar nodeRight) {

        this.nodeRight = nodeRight;

    }

    public Grammar getNodeLeft() {
        return nodeLeft;
    }

    public void setNodeLeft(Grammar nodeLeft) {
        this.nodeLeft = nodeLeft;
    }

    @Override
    public  String check(String str, int pos){
        String result = nodeRight.check(str, pos);
        if(result == null){
            return null;
        }
        return  nodeLeft.check(result,pos);
    }
}
