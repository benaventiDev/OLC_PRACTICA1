package grammar;

import Analizador.Set;
import Analizador.SymbolTable;
import Analizador.Token;
import Analizador.Type;

import java.util.ArrayList;
import java.util.Iterator;

public class Leaf extends Grammar {
    private Token token;
    private ArrayList<String> validStrings;
    private SymbolTable sTable;

    public Leaf(Token token, SymbolTable sTable, String name) {
        super(name);
        validStrings = new ArrayList<>();
        this.token = token;
        if(token.getType() == Type.STRING){
            validStrings.add(token.getLexeme().replaceAll("\"",""));
        }else if(token.getType() == Type.ID){
            Set set = sTable.getSetDefinition(token.getLexeme());
            if(set.getType() == Type.MACRO){
                int start = set.getExpression().charAt(0);
                int end = set.getExpression().charAt(2);
                for(int i = start; i <= end; i++){
                    char c = (char) i;
                    if(Character.isDigit(i) || Character.isLetter(i)){continue;}
                    validStrings.add("" + c);
                }
            }else if(set.getType() == Type.ID){
                    String[] arr =set.getExpression().split(",");
                   for (int i = 0; i < arr.length; i++){
                       validStrings.add(arr[i]);
                   }
            }else{
                System.out.println("Error in set.");
            }
        }

    }


    @Override
    public String check(String str, int pos) {
        if(str.length() == 0){return null;}
        char c = str.charAt(0);
        String check = "" + c;
        Iterator<String> it = validStrings.iterator();
        while (it.hasNext()){
            if(it.next().compareTo(check) == 0){
                StringBuilder sb =  new StringBuilder(str);
                sb.deleteCharAt(0);
                return sb.toString();
            }
        }
        return null;
    }
}
