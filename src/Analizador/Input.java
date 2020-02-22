package Analizador;

public class Input {
    private String regularMatch;
    private String content;

    public Input(String regularMatch, String content) {
        this.setRegularMatch(regularMatch);
        this.setContent(content);
    }

    public String getRegularMatch() {
        return regularMatch;
    }

    public void setRegularMatch(String regularMatch) {
        this.regularMatch = regularMatch;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
