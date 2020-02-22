package Analizador;

import java.util.ArrayList;
import java.util.Iterator;

public class SymbolTable {
    private ArrayList<RegularExpresion> definitions = new ArrayList<>();
    private ArrayList<Input> implementation = new ArrayList<>();
    private ArrayList<Set> sets = new ArrayList<>();
    private  int state;

    public SymbolTable(){
        definitions = new ArrayList<>();
        implementation = new ArrayList<>();
        sets = new ArrayList<>();

        /*
        va guardar conjuntos

        declaraciones e implementaciones
        verificar que no esten repetidos

        verificar que conjuntos existan en las definiciones regulares

        */



    }
    public void print(){
        Iterator<Set>  it =sets.iterator();
        while (it.hasNext()){
            Set set = it.next();
            System.out.println("Set: Name: " + set.getName() + ". Expresion: " + set.getExpression() );
        }
        Iterator<RegularExpresion> itr = definitions.iterator();
        while (itr.hasNext()){
            RegularExpresion re = itr.next();

            System.out.println("RS: Name: " + re.getName() + ". Expresion: " + re.getDefinitionToString() );
        }
        Iterator<Input> iti = implementation.iterator();
        while (iti.hasNext()){
            Input in = iti.next();
            System.out.println("RD: Name: " + in.getRegularMatch() + ". Expresion: " + in.getContent() );
        }

    }
    public boolean addSet(Set set){
        return getSets().add(set);
    }
    public boolean addDefinition(RegularExpresion rs){
        return getDefinitions().add(rs);
    }
    public boolean addImplementation(Input input){
        return getImplementation().add(input);
    }

    public boolean setExists(String setName){
        Iterator<Set> it = sets.iterator();
        boolean setExists = false;
        while (it.hasNext()){
            if(it.next().getName().compareToIgnoreCase(setName) == 0){
                setExists = true;
            }
        }
        return setExists;
    }

    public boolean definitionExists(String definitionName){
        Iterator<RegularExpresion> it = definitions.iterator();
        boolean setExists = false;
        while (it.hasNext()){
            if(it.next().getName().compareToIgnoreCase(definitionName) == 0){
                setExists = true;
            }
        }
        return setExists;
    }

    public boolean implementationExists(String implementationName){
        Iterator<Input> it = implementation.iterator();
        boolean setExists = false;
        while (it.hasNext()){
            if(it.next().getRegularMatch().compareToIgnoreCase(implementationName) == 0){
                setExists = true;
            }
        }
        return setExists;
    }
    public void setState(int state){
        this.state = state;
    }

    public ArrayList<RegularExpresion> getDefinitions() {
        return definitions;
    }

    public ArrayList<Input> getImplementation() {
        return implementation;
    }

    public ArrayList<Set> getSets() {
        return sets;
    }

    public int getState() {
        return state;
    }
    public Set getSetDefinition(String setName){
        Iterator<Set> it = sets.iterator();
        while (it.hasNext()){
            Set set = it.next();
            if(set.getName().compareToIgnoreCase(setName) == 0){
                return set;
            }
        }
        return null;
    }
}
