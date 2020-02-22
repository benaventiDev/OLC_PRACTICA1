package grammar;

import Analizador.RegularExpresion;
import Analizador.SymbolTable;
import Analizador.Token;
import Analizador.Type;

import java.util.ArrayList;
import java.util.Iterator;

public class GrammarChecker {
    private ArrayList<Grammar> grammars;
    private int index;
    private SymbolTable sTable;
    public GrammarChecker(SymbolTable sTable){
        this.sTable = sTable;
        grammars =  new ArrayList<>();
        ArrayList<RegularExpresion> definitions = sTable.getDefinitions();
        Iterator<RegularExpresion> it = definitions.iterator();
        while (it.hasNext()){
            RegularExpresion re = it.next();
            grammars.add(getGrammar(re.getDefinition(), re.getName()));
        }

        index = 0;
    }

    public boolean check(String grammarName, String textToCheck){
        Iterator<Grammar> it = grammars.iterator();
        while (it.hasNext()){
            Grammar gr = it.next();
            if(gr.getName().compareTo(grammarName) == 0){
                return gr.check(textToCheck, 0) != null;
            }
        }
        return false;
    }



    private Grammar getGrammar(ArrayList<Token> definitions, String name){
        index = 0;
        return ERI(definitions, name);
    }




    private Grammar ERI(ArrayList<Token> definitions, String name){

        Token token;
        if (index < definitions.size()){
            return null;
        }
        token = definitions.get(index);
        index++;
        switch (token.getType()) {
            case OPEN_CURLY_BRACES:
                return MACRO(definitions, name);
            case STRING:
                return new Leaf(token, sTable, name);

            case DOT:
                Concatenation concatenation = new Concatenation(name);
                concatenation.setNodeLeft(ERI(definitions, name));
                concatenation.setNodeRight(ERI(definitions, name));
                break;
            case OR:
                OR or = new OR( name);
                or.setNodeLeft(ERI(definitions, name));
                or.setNodeRight(ERI(definitions, name));
                break;
            case TIMES:
                Asterisk asterisk = new Asterisk(name);
                asterisk.setNode(ERI(definitions, name));
                break;
            case PLUS:
                PLUS plus = new PLUS(name);
                plus.setNode(ERI(definitions, name));
                break;
            case QUESTION_MARK:
                Question question = new Question(name);
                question.setNode(ERI(definitions, name));
                break;
            default:
                System.out.println("Syntax error in GrammarChecker");

                //TODO ERROR
        }
        return null;

    }


    private Grammar MACRO(ArrayList<Token> definitions, String name) {
        Token token;
        if (index < definitions.size()) {
            return null;
        }
        token = definitions.get(index);
        index++;
        if (token.getType() == Type.ID) {
            Token set = token;
            if (index < definitions.size()) {
                return null;
            }
            token = definitions.get(index);
            index++;
            if (token.getType() == Type.CLOSE_CURLY_BRACES) {
                return new Leaf(set, sTable, name);
            }
        }
        System.out.println("Error in leaf");
        return null;


    }

}
