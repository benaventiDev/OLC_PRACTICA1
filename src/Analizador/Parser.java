package Analizador;

import grammar.GrammarChecker;

import java.util.ArrayList;
import java.util.Iterator;

public class Parser {

    private Token token;
    private Lexer lex;
    private Type lookAhead;
    private SymbolTable sTable;
    public static final int DECLARATIONS = 0;
    public static final int IMPLEMENTATIONS = 1;
    int state;
    boolean parseCompleted;
    private String currentLex = "";
    private ArrayList<String> semanticErrors;

    public Parser(){

    }


    public void parse(String source){
        semanticErrors = new ArrayList<>();
        sTable = new SymbolTable();
        parseCompleted = false;
        state = DECLARATIONS;
        lex = new Lexer();
        lex.Analyze(source);
        token = lex.getNextToken();
        if(token != null){
            lookAhead = token.getType();
            ROOT();
        }else{
            //TODO in case file is empty or there are not tokens.
        }

        sTable.print();

        System.out.println("Semantic Erros:");
        Iterator<String> it = semanticErrors.iterator();
        while (it.hasNext()){
            System.out.println(it.next());
        }


        GrammarChecker checker = new GrammarChecker(sTable);

        Iterator<Input> im = sTable.getImplementation().iterator();
        while (im.hasNext()){
            Input in = im.next();
            checker.check(in.getRegularMatch(), in.getContent());
        }



    }

    public void match(Type terminal){
        if(parseCompleted){return;}
            currentLex = token.getLexeme();


        if(lookAhead == terminal){
            if (terminal == Type.EOF){parseCompleted = true;}
            System.out.println("Match: " + token.getLexeme());
            token = lex.getNextToken();
            if(token != null){
                lookAhead = token.getType();
            }else {
                //TODO Syntax  error unexpected end of file
            }
        }else{
            System.out.println("Syntax error in match. Expected " + terminal + " but received " + currentLex);
            Syncs();



            //TODO Syntax error unexpected token.
        }
    }

    private void Syncs(){
        if (state == DECLARATIONS){
            if (token != null && token.getType() != Type.ID && token.getType() != Type.CONJ && token.getType() != Type.EOF) {
                while (token != null && token.getType() != Type.ID && token.getType() != Type.CONJ && token.getType() != Type.EOF) {
                    token = lex.getNextToken();
                }
            }
            if (token == null){
                System.out.println("Syntax error, Could not recover");
                return;
            }

            lookAhead = token.getType();
            switch (token.getType()){
                case EOF:
                    parseCompleted = true;
                    System.out.println("Syntax error, EOF reached not possible to recover");
                    break;
                case ID:
                case CONJ:
                    System.out.println(token.getLexeme());
                    DEFS();if(state != IMPLEMENTATIONS){match(Type.DOUBLE_PERCENTAGE);}  if(state != IMPLEMENTATIONS){match(Type.DOUBLE_PERCENTAGE); state = IMPLEMENTATIONS; sTable.setState(IMPLEMENTATIONS);} EXP(); match(Type.CLOSE_CURLY_BRACES); match(Type.EOF);
                    break;
                default:
                    System.out.println("Unknown error 1");
            }

        }else if (state == IMPLEMENTATIONS){

            if(token != null && token.getType() != Type.ID && token.getType() != Type.EOF) {
                while (token != null && token.getType() != Type.ID && token.getType() != Type.EOF) {
                    token = lex.getNextToken();
                }
            }
            if (token == null){
                System.out.println("Syntax error, Could not recover");
                return;
            }
            lookAhead = token.getType();
            switch (token.getType()){
                case EOF:
                    parseCompleted = true;
                    System.out.println("Syntax error, EOF reached not possible to recover");
                    break;

                case ID:
                    EXP();  match(Type.CLOSE_CURLY_BRACES); match(Type.EOF);
                    break;
                default:
                    System.out.println("Unknown error 2");
            }
        }





    }
    private void ROOT(){
        System.out.println("ROOT");
        switch (lookAhead) {
            case OPEN_CURLY_BRACES:
                match(Type.OPEN_CURLY_BRACES); DEFS(); if(state != IMPLEMENTATIONS){match(Type.DOUBLE_PERCENTAGE);} if(state != IMPLEMENTATIONS){ match(Type.DOUBLE_PERCENTAGE); state = IMPLEMENTATIONS; sTable.setState(IMPLEMENTATIONS);}  EXP(); match(Type.CLOSE_CURLY_BRACES); match(Type.EOF);
                break;
            case EOF:
                parseCompleted = true;
                match(Type.EOF);
                break;
                default:
                    Syncs();
                System.out.println("Syntax error in ROOT");
            //TODO: ERROR missing opening curly brackets

        }
    }

    private void DEFS(){
        if(parseCompleted){return;}
        System.out.println("DEFS " + token.getLexeme());
        switch (lookAhead) {
            case CONJ:
                CONJ(); DEFS();
                break;
            case ID:
                DEF(); DEFS();
                break;
            default:
        }
    }

    private void CONJ(){
        if(parseCompleted){return;}
        System.out.println("CONJ "  + token.getLexeme());
        switch (lookAhead) {
            case CONJ:
                String name = "";
                match(Type.CONJ); match(Type.COLON); name = token.getLexeme(); match(Type.ID);  match(Type.ARROW); CONJ_EXP(name); match(Type.SEMI_COLON);
                break;
            default:
                Syncs();
                System.out.println("Syntax error in CONJ");
                //TODO ERROR
        }
    }

    private void DEF(){
        if(parseCompleted){return;}
        System.out.println("DEF");
        switch (lookAhead) {
            case ID:
                String name = token.getLexeme();
                RegularExpresion re = new RegularExpresion(name);
                match(Type.ID); match(Type.ARROW); ERI(re); match(Type.SEMI_COLON);
                if(sTable.definitionExists(name)){
                    semanticErrors.add("Regular expression "  + re.getName() +" already defined. Please choose another name.");
                }else {
                    sTable.addDefinition(re);
                }

                break;
            default:
                Syncs();
                //TODO ERROR
        }
    }

    private void ERI(RegularExpresion re){
        if(parseCompleted){return;}

        switch (lookAhead) {
            case OPEN_CURLY_BRACES:
            case STRING:
                re.addDefinition(token);

                LEAF(re);
                break;
            case DOT:
                re.addDefinition(token);
                match(Type.DOT); ERI(re); ERI(re);
                break;
            case OR:
                re.addDefinition(token);
                match(Type.OR); ERI(re); ERI(re);
                break;
            case TIMES:
                re.addDefinition(token);
                match(Type.TIMES); ERI(re);
                break;
            case PLUS:
                re.addDefinition(token);
                match(Type.PLUS); ERI(re);
                break;
            case QUESTION_MARK:
                re.addDefinition(token);
                match(Type.QUESTION_MARK); ERI(re);
                break;
            default:
                System.out.println("Syntax error in ERI");
                Syncs();

                //TODO ERROR
        }
    }

    private void LEAF(RegularExpresion re){
        if(parseCompleted){return;}
        switch (lookAhead) {
            case OPEN_CURLY_BRACES:
                match(Type.OPEN_CURLY_BRACES); re.addDefinition(token); match(Type.ID); re.addDefinition(token); match(Type.CLOSE_CURLY_BRACES);
                break;

            case STRING:
                re.addDefinition(token);
                match(Type.STRING);
                break;
            default:
                Syncs();
                System.out.println("Syntax error in LEAF");
                //TODO ERROR
        }
    }
    private void EXP(){
        if(parseCompleted){return;}
        System.out.println("EXP");
        switch (lookAhead) {
            case ID:
                String regularMatch = token.getLexeme();
                match(Type.ID); match(Type.COLON);
                String s;
                if (token.getType() == Type.STRING){ s = token.getLexeme();}else {s = "\"\"";}

                match(Type.STRING); Input input = new Input(regularMatch,  s);
                if (sTable.definitionExists(regularMatch)){
                    sTable.addImplementation(input);
                }else {
                    if(s.compareTo("\"\"") == 0){
                        semanticErrors.add("Cant process input " + s + " with regular expression " + regularMatch + " as the input is empty.");
                    }else {
                        semanticErrors.add("Cant process input " + s + " as there is no regular expression defined with name: " + regularMatch);
                    }
                }
                EXP();
                break;
            default:
        }
    }

    private void CONJ_EXP(String setName){
        if(parseCompleted){return;}
        String setContent = "";
        Set set;
        switch (lookAhead) {
            case ID:
                setContent = token.getLexeme();
                match(Type.ID); LIST(setContent)   ; set = new Set(setName, setContent, Type.ID);

                if(sTable.setExists(setName)){
                    semanticErrors.add("Set: " + setName + " already declared. Please use a different name.");
                }else{
                    sTable.addSet(set);
                }
                break;
            case MACRO:
                setContent = token.getLexeme(); set = new Set(setName, setContent, Type.MACRO);
                match(Type.MACRO);

                if(sTable.setExists(setName)){
                    semanticErrors.add("Set: " + setName + " already declared. Please use a different name.");
                }else{
                    sTable.addSet(set);
                }
                break;
            default:
                Syncs();
                //TODO ERROR
        }
    }

    private void LIST(String setContent){
        if( parseCompleted){return;}
        switch (lookAhead) {
            case COMMA:
                setContent += token.getLexeme();
                match(Type.COMMA); match(Type.ID); LIST(setContent);
                break;
            default:
        }
    }
}
