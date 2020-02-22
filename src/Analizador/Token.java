package Analizador;

public class Token {
    private String lexeme;
    private int row;
    private int column;
    private Type type;
    private int state;

    public Token(String lexeme, int row, int column, Type type) {
        this.setRow(row);
        this.setColumn(column);
        this.setType(type);
        this.setLexeme(lexeme);
    }

    public String getLexeme() {
        return lexeme;
    }

    public void setLexeme(String lexeme) {
        if(lexeme.compareToIgnoreCase("conj") == 0){
            setType(Type.CONJ);
        }
        this.lexeme = lexeme;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}
