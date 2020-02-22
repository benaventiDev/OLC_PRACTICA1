package analyzer;

import Analizador.Parser;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {


        Scanner sc = new Scanner(System.in);
        System.out.println("Ingrese nombre del file");
        String fileName = sc.nextLine();

        File fileDir = new File(fileName);//"C:\\Users\\benav\\Documents\\Projects\\OLC\\src\\analyzer\\test.txt");

        BufferedReader buf = null;
        try {
            buf = new BufferedReader(new InputStreamReader(new FileInputStream(fileDir), StandardCharsets.UTF_8));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        String text = "";
        String str = "";
        try {
            while ((str = buf.readLine()) != null) {
                //System.out.println(str);
                text += str + '\n';
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        Parser parser =  new Parser();
        parser.parse(text);

/*
        Lexer lexer = new Lexer();
        lexer.Analyze(text);
        Token token = lexer.getNextToken();
        String lex = token.getLexeme();
        int pos = token.getColumn();
        int line =  token.getRow();
        while (token != null) {

            System.out.println(token.getLexeme() + "  //  " + token.getType());
            token = lexer.getNextToken();
            if(token == null){
                lex = null;
            }else{
                line = token.getColumn();
                pos =  token.getRow();
                lex = token.getLexeme();
            }

        }

*/


    }
}
