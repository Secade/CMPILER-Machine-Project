package controller;

import antlr.ClypsParser;
import commands.ICommand;
import items.ClypsValue;

import java.util.ArrayList;

public class ClypsFunction {
    public enum FunctionType{
        BOOL_TYPE,
        INT_TYPE,
        DECIMAL_TYPE,
        STRING_TYPE,
        CHAR_TYPE,
        VOID_TYPE
    }

    private String functionName;
    private ArrayList<ICommand> commandList;
    private int level;
    private ArrayList<String> parameters;
    private FunctionType functionType;

    public void addCommand(ICommand command) {
        this.commandList.add(command);
        //Console.log("Command added to " +this.functionName);
    }

//    public ClypsValue invoke(ArrayList<ClypsParser.ExpressionContext> params, int level){
//        SymbolTableManager.getInstance().addLevel();
//        ClypsCustomVisitor visitor = new ClypsCustomVisitor(level);
//        for (int i =0;i<params.size();i++){
//            ClypsValue value = visitor.visit(params.get(i));
//            SymbolTableManager.getInstance().registerVar(params.get(i).getText(),value);
//        }
//        ClypsCustomVisitor visitorNext = new ClypsCustomVisitor(level);
//
//        //ClypsValue ret = new ClypsValue();
//
//        //temp
//        return null;
//
//    }


}
