package controller;

import java.util.ArrayList;
import java.util.HashMap;

public class Scope {
    private Scope parentScope;
    private HashMap<String, ClypsValue> localVariables=null;
    private HashMap<String, ClypsArray> localArrays=null;
    private ArrayList<Scope> scopeList=null;

    public Scope(){
        this.parentScope=null;
        this.localVariables = new HashMap<String, ClypsValue>();
        this.localArrays = new HashMap<String, ClypsArray>();
        this.scopeList = new ArrayList<Scope>();
    }

    public Scope(Scope scope){
        this.parentScope=scope;
        this.localVariables = new HashMap<String, ClypsValue>();
        this.localArrays = new HashMap<String, ClypsArray>();
        this.scopeList = new ArrayList<Scope>();
    }

    public void setParent(Scope parentScope) {
        this.parentScope = parentScope;
    }

    public void addChild(Scope scope) {
        this.scopeList.add(scope);
    }

    public boolean isParent() {
        return (this.parentScope == null);
    }

    public Scope getParent() {
        return this.parentScope;
    }

    public int getChildCount() {
        if(this.scopeList != null)
            return this.scopeList.size();
        else
            return 0;
    }

    public Scope getChildAt(int index) {
        if(this.scopeList != null)
            return this.scopeList.get(index);
        else
            return null;
    }
    
    public ClypsValue searchVariableIncludingLocal(String identifier) {
        if(this.containsVariable(identifier)) {
            return this.localVariables.get(identifier);
        }
        else {
            return null;
        }
    }

    public ClypsArray searchArray(String identifier){
        if(this.containsVariable(identifier)) {
            //System.out.println("FOUND NAME");
            return this.localArrays.get(identifier);
        }
        else {
            //System.out.println("NO NAME FOUND");
            return null;
        }
    }

    public boolean containsVariable(String identifier) {
        if((this.localVariables!= null && this.localVariables.containsKey(identifier))||(this.localArrays!=null&&this.localArrays.containsKey(identifier))) {
            return true;
        }
        else {
            return false;
        }
    }

    public void setDeclaredVariable(String identifierString, String valueString){
        if (containsVariable(identifierString)) {
            this.localVariables.get(identifierString).setValue(valueString);
        }
    }

    public void addEmptyVariableFromKeywords(String primitiveTypeString, String identifierString) {
        ClypsValue.PrimitiveType type=ClypsValue.translateType(primitiveTypeString);
        ClypsValue clypsValue = ClypsValue.createEmptyVariableFromKeywords(primitiveTypeString);
        clypsValue.setType(type);
        this.localVariables.put(identifierString, clypsValue);
    }

    public void addInitializedVariableFromKeywords(String primitiveTypeString, String identifierString, String valueString) {
        ClypsValue.PrimitiveType type=ClypsValue.translateType(primitiveTypeString);
        this.addEmptyVariableFromKeywords(primitiveTypeString, identifierString);
        ClypsValue clypsValue = this.localVariables.get(identifierString);
        clypsValue.setValue(valueString);
        clypsValue.setType(type);
    }

    public void addParamsToScope(String primitiveTypeString, String identifierString, String valueString) {
        ClypsValue.PrimitiveType type=ClypsValue.translateType(primitiveTypeString);
        this.addEmptyVariableFromKeywords(primitiveTypeString, identifierString);
        ClypsValue clypsValue = this.localVariables.get(identifierString);
        clypsValue.setValue(valueString);
        clypsValue.setType(type);
    }

    public void addArray(String primitiveTypeString, String identifierString, String size) {
        ClypsValue.PrimitiveType type=ClypsValue.translateType(primitiveTypeString);
        ClypsArray array = new ClypsArray(type);
        array.initializeSize(Integer.parseInt(size));
        this.localArrays.put(identifierString,array);
    }

    public void addClypsValue(String identifier, ClypsValue clypsValue) {
        this.localVariables.put(identifier, clypsValue);
    }

//    public void printAllVars() {
//        if (parentScope!=null){
//            parentScope.localVariables.entrySet().forEach(entry -> {
//                if (entry.getValue().isFinal())
//                    System.out.println(entry.getKey() + " " + entry.getValue().getValue() + " " + entry.getValue().getPrimitiveType()+" FINAL");
//                else
//                    System.out.println(entry.getKey() + " " + entry.getValue().getValue() + " " + entry.getValue().getPrimitiveType());
//            });
//        }
//
//        this.localVariables.entrySet().forEach(entry -> {
//            if (entry.getValue().isFinal())
//                System.out.println(entry.getKey() + " " + entry.getValue().getValue() + " " + entry.getValue().getPrimitiveType()+" FINAL");
//            else
//                System.out.println(entry.getKey() + " " + entry.getValue().getValue() + " " + entry.getValue().getPrimitiveType());
//        });
//    }
//
//    public void printAllArrays() {
//        if (parentScope!=null){
//            parentScope.localArrays.entrySet().forEach(entry -> {
//                if (entry.getValue().isFinal())
//                    System.out.println(entry.getKey() + " " + entry.getValue().getSize() + " " + entry.getValue().getPrimitiveType()+" FINAL");
//                else
//                    System.out.println(entry.getKey() + " " + entry.getValue().getSize() + " " + entry.getValue().getPrimitiveType());
//            });
//        }
//
//        this.localArrays.entrySet().forEach(entry -> {
//            if (entry.getValue().isFinal())
//                System.out.println(entry.getKey() + " " + entry.getValue().getSize() + " " + entry.getValue().getPrimitiveType()+" FINAL");
//            else
//                System.out.println(entry.getKey() + " " + entry.getValue().getSize() + " " + entry.getValue().getPrimitiveType());
//        });
//    }
//
//    public void printArrayValues(){
//        if (parentScope!=null){
//            parentScope.localArrays.entrySet().forEach(entry -> {
//                for (int i = 0;i<entry.getValue().getSize();i++){
//                    System.out.println(entry.getKey() + " " +entry.getValue().getValueAt(i).getValue().toString());
//                }
//
//            });
//        }
//
//        this.localArrays.entrySet().forEach(entry -> {
//            for (int i = 0;i<entry.getValue().getSize();i++){
//                if (entry.getValue().getValueAt(i)!=null)
//                    System.out.println(entry.getKey() + " " +entry.getValue().getValueAt(i).getValue());
//                else
//                    System.out.println(entry.getKey() + " " +entry.getValue().getValueAt(i));
//            }
//        });
//    }

    public void reset(){
        this.localVariables.clear();
        this.localArrays.clear();
    }

}
