package Analizador;

public class Set {

    private String name;
    private String expression;
    private char[] charsIncluded;
    private Type type;
    public Set(String name, String expression, Type type) {
        this.setName(name);
        this.setExpression(expression);
        this.setType(type); // This will help to know if is macro or list of values
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    public char[] getCharsIncluded() {
        return charsIncluded;
    }

    public void setCharsIncluded(char[] charsIncluded) {
        this.charsIncluded = charsIncluded;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }
}
