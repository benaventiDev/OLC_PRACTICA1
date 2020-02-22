package Analizador;

import grammar.Grammar;

import java.util.ArrayList;
import java.util.Iterator;

public class RegularExpresion {
    private String name;
    private ArrayList<Token> definition;
    private Grammar grammar;

    public RegularExpresion(String name) {
        this.setName(name);
        definition = new ArrayList<Token>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Token> getDefinition() {
        return definition;
    }

    public void addDefinition(Token token) {
        this.definition.add(token);
    }
    public String getDefinitionToString(){
        Iterator<Token> it = definition.iterator();
        String summary = "";
        while (it.hasNext()){
            summary += it.next().getLexeme();
        }
        return summary;
    }
    public void setGrammar(){
        this.grammar = grammar;
    }

}
