package Analizador;

import java.util.regex.Pattern;

public class Lexer {
    private int state;
    private int currentPos;
    private int lineCounter;
    private int colCounter;
    private char[] source;
    private int size;

    public Lexer( ){

    }


    public void Analyze(String sourceText){
        sourceText = sourceText + "  ";
        source = sourceText.toCharArray();
        size = source.length;
        currentPos = 0;
        lineCounter = 1;
        colCounter = 0;
        state = 0;
    }

    public Token getNextToken(){
        Token token = processNextToken();
        while(token != null){
            if (token.getType() == Type.COMMENT){
                token = processNextToken();
                continue;
            }
            return token;
        }
        return null;
    }


    public Token processNextToken(){
        String lexeme = "";
        String lex = "";
        char c;
        int  a;
        Type type = Type.UNDEFINED;
        int initColCounter = 0;


        while ( currentPos < size){

            c = source[currentPos];

            a =  c;
            //System.out.println("Current char at line " + lineCounter + ". Col counter " + colCounter + ". C: " + c + ". A:" + a + ". State = " + state +  ". Counter: " + currentPos);
            switch (state){
                case 0:
                    lexeme= "";
                    initColCounter = colCounter;
                    if(c=='/'){
                        type = Type.COMMENT;
                        lexeme += c;
                        state = 1;
                        colCounter++;currentPos++;
                    }
                    else if(c=='<'){
                        type = Type.COMMENT;
                        lexeme += c;
                        state = 2;
                        colCounter++;currentPos++;
                    }
                    else if(Character.isLetter(c)){
                        type = Type.ID;
                        lexeme += c;
                        state = 3;
                        colCounter++;currentPos++;
                    }
                    else if(Character.isDigit(c)){
                        type = Type.ID;
                        lexeme += c;
                        state = 3;
                        colCounter++;currentPos++;
                    }

                    else if(c=='\"' || c == '“' || c == '”' ){
                        type = Type.STRING;
                        lexeme += c;
                        state = 4;
                        colCounter++;currentPos++;
                    }
                    else if(c=='%'){
                        type = Type.DOUBLE_PERCENTAGE;
                        lexeme += c;
                        state = 5;
                        colCounter++;currentPos++;
                    }
                    else if(c=='-'){
                        type = Type.ARROW;
                        lexeme += c;
                        state = 6;
                        colCounter++;currentPos++;
                    }
                    else if(c == ','){
                        type = Type.COMMA;
                        lexeme += c;
                        state = 13;
                        colCounter++;currentPos++;
                    }
                    else if(c == '.'){
                        type = Type.DOT;
                        lexeme += c;
                        state = 13;
                        colCounter++;currentPos++;
                    }
                    else if(c == '|'){
                        type = Type.OR;
                        lexeme += c;
                        state = 13;
                        colCounter++;currentPos++;
                    }
                    else if(c == '?'){
                        type = Type.QUESTION_MARK;
                        lexeme += c;
                        state = 13;
                        colCounter++;currentPos++;
                    }
                    else if(c == '*'){
                        type = Type.TIMES;
                        lexeme += c;
                        state = 13;
                        colCounter++;currentPos++;
                    }
                    else if(c == '+'){
                        type = Type.PLUS;
                        lexeme += c;
                        state = 13;
                        colCounter++;currentPos++;
                    }
                    else if(c == ':'){
                        type = Type.COLON;
                        lexeme += c;
                        state = 13;
                        colCounter++;currentPos++;
                    }
                    else if(c == ';'){
                        type = Type.SEMI_COLON;
                        lexeme += c;
                        state = 13;
                        colCounter++;currentPos++;
                    }
                    else if(c == '{'){
                        type = Type.OPEN_CURLY_BRACES;
                        lexeme += c;
                        state = 13;
                        colCounter++;currentPos++;
                    }
                    else if(c == '}'){
                        type = Type.CLOSE_CURLY_BRACES;
                        lexeme += c;
                        state = 13;
                        colCounter++;currentPos++;
                    }
                    else if(a >= 33 && a <= 125){
                        type = Type.MACRO;
                        lexeme += c;
                        state = 7;
                        colCounter++;currentPos++;
                    }else if(c == ' '){
                        type = Type.MACRO;
                        lexeme += c;
                        state = 11;
                        colCounter++;currentPos++;
                    }else if(c == '\n'){
                        lineCounter++;
                        colCounter = 0;
                        currentPos++;
                    }
                    else if(isDelimiter(c)){/*If is delimiter just ignore*/colCounter++;currentPos++;}
                    else {
                        state = 15;
                    }
                    initColCounter++;
                    break;
                case 1:
                    if(c == '/'){
                        lexeme += c;
                        state = 8;
                        colCounter++;currentPos++;
                    }else if(c == '~'){
                        lexeme += c;
                        state = 12;
                        colCounter++;currentPos++;
                    }else{

                        state = 15;
                    }
                    break;
                case 2:
                    if(c == '!'){
                        lexeme += c;
                        state = 9;
                        colCounter++;currentPos++;
                    }else if(c == '~'){
                        lexeme += c;
                        state = 12;
                        colCounter++;currentPos++;
                    }else{
                        state = 15;
                    }
                    break;
                case 3:
                    if(Character.isLetter(c)){
                        lexeme += c;
                        state = 3;
                        colCounter++;currentPos++;
                    }
                    else if(Character.isDigit(c)){
                        lexeme += c;
                        state = 3;
                        colCounter++;currentPos++;
                    }else if(c == '~'){
                        lexeme += c;
                        state = 12;
                        colCounter++;currentPos++;
                    }
                    else if(c=='_'){
                        lexeme += c;
                        state = 3;
                        colCounter++;currentPos++;
                    }else{
                        state = 0;
                        return new Token(lexeme, initColCounter, lineCounter, Type.ID);
                    }
                    break;
                case 4:
                    if(c=='\"' || c == '“' ||  c== '”'){
                        lexeme += c;
                        state = 7;
                        colCounter++;currentPos++;
                    }else if(c == '\n'){
                        state = 15;
                        //TODO: Add error message unclosed striing
                    }else {
                        lexeme += c;
                        state = 4;
                        colCounter++;currentPos++;
                    }
                    break;
                case 5:
                    if(c=='%'){
                        lexeme += c;
                        state = 7;
                        colCounter++;currentPos++;
                    }else if(c == '~'){
                        lexeme += c;
                        state =12;
                        colCounter++;currentPos++;
                    }else  {
                        state = 15;
                    }
                    break;
                case 6:
                    if(c=='>'){
                        lexeme += c;
                        state = 7;
                        colCounter++;currentPos++;
                    }else if(c == '~'){
                        lexeme += c;
                        state = 12;
                        colCounter++;currentPos++;
                    }else{
                        state = 15;
                    }
                    break;
                case 7:
                    state = 0;
                    return new Token(lexeme, initColCounter, lineCounter, type);
                case 8:
                    if(c!='\n'){
                        lexeme += c;
                        state = 8;
                        colCounter++;currentPos++;
                    }else {
                        state = 0;
                        return new Token(lexeme, initColCounter, lineCounter, type);
                    }
                    break;
                case 9:
                    if(c!='!'){
                        lexeme += c;
                        state = 9;
                        colCounter++;currentPos++;
                    }else {
                        lexeme += c;
                        state = 10;
                        colCounter++;currentPos++;
                    }
                    break;
                case 10:
                    if(c!='>'){
                        lexeme += c;
                        state = 9;
                        colCounter++;currentPos++;
                    }else {
                        lexeme += c;
                        state = 7;
                        colCounter++;currentPos++;
                    }
                    break;
                case 11:
                    if(c == '~'){
                        lexeme += c;
                        state = 12;
                        colCounter++;currentPos++;
                    }else {
                        lexeme = "";
                        state = 0;
                    }
                    break;
                case 12:
                    if(a >= 32 && a <= 125){
                        type = Type.MACRO;
                        lexeme += c;
                        state = 7;
                        colCounter++;currentPos++;
                    }else{
                        state = 15;
                    }
                    break;
                case 13:
                    if(c == '~'){
                        lexeme += c;
                        state = 12;
                        colCounter++;currentPos++;
                    }else{
                        state = 7;

                    }
                    break;
                case 14:

                    break;
                case 15: //CASE ERROR

                    if(c == '\n'){
                        state = 0;
                        lineCounter++;
                        //TODO dont return anythguing but let user know there is an error
                        //return new Token("ERROR: "+ lexeme, colCounter, lineCounter, Type.ERROR);
                    }
                    lexeme += c;
                    currentPos++;

                default:
            }

        }
        if (currentPos == size){
            return new Token("", 0, 0, Type.EOF);
        }
        //TODO verificar en que estado se estaba
        return null;

    }

    private boolean isDelimiter(char c){
        String s = "" + c;
        if(Pattern.matches("\\s", s)){
            return true;
        }
        return false;
    }




}
