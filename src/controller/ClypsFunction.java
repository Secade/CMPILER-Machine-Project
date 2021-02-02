package controller;

import commands.ICommand;
import commands.IFCommand;
import commands.ReturnCommand;
import execution.ExecutionManager;
import execution.ExecutionThread;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Locale;

public class ClypsFunction {
    public enum FunctionType{
        BOOL_TYPE,
        INT_TYPE,
        DECIMAL_TYPE,
        STRING_TYPE,
        CHAR_TYPE,
        VOID_TYPE
    }

    private Scope parentScope;
    private String methodName;
    private String functionName;
    private ArrayList<ICommand> commandList;
    private LinkedHashMap<String,ClypsValue> parameters;
    private ClypsValue returnValue;
    private FunctionType functionType = FunctionType.VOID_TYPE;

    private boolean hasValidReturns = true;
    public boolean isReturned=false;

    public ClypsFunction(){
        this.commandList = new ArrayList<>();
        this.parameters = new LinkedHashMap<>();


    }

    public void setParentScope(Scope scope){
        this.parentScope=scope;
    }

    public Scope getParentScope(){
        return this.parentScope;
    }

    public void setReturnValue(FunctionType functionType){
        this.functionType=functionType;

        switch(this.functionType) {
            case BOOL_TYPE: this.returnValue = new ClypsValue(true, ClypsValue.PrimitiveType.BOOLEAN); setValidReturns(false); break;
            case INT_TYPE: this.returnValue = new ClypsValue(0, ClypsValue.PrimitiveType.INT); setValidReturns(false); break;
            case DECIMAL_TYPE: this.returnValue = new ClypsValue(0.0, ClypsValue.PrimitiveType.DOUBLE); setValidReturns(false); break;
            case STRING_TYPE: this.returnValue = new ClypsValue("", ClypsValue.PrimitiveType.STRING); setValidReturns(false); break;
            case CHAR_TYPE: this.returnValue = new ClypsValue(0, ClypsValue.PrimitiveType.CHAR); setValidReturns(false); break;
            default:
                break;
        }
    }

    public void changeReturnValue(String value){
        this.returnValue.setValue(value);
    }

    public boolean hasValidReturns(){
        return this.hasValidReturns;
    }

    public void setValidReturns(boolean b) {
        hasValidReturns = b;
    }

    public FunctionType getReturnType() {
        return this.functionType;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getMethodName() {
        return this.methodName;
    }

    public void addParameter(String identifierString, ClypsValue baracoValue) {
        this.parameters.put(identifierString, baracoValue);
        System.out.println(this.methodName + " added an empty parameter " +identifierString+ " type " + baracoValue.getPrimitiveType());
    }

    public ClypsValue getReturnValue() {
        if(this.functionType == FunctionType.VOID_TYPE) {
            System.out.println(this.methodName + " is a void function. Null mobi value is returned");
            return null;
        }
        else {
            return this.returnValue;
        }
    }

    public void addCommand(ICommand command) {
        this.commandList.add(command);
        //Console.log("Command added to " +this.functionName);
    }

    public void mapParameterByValueAt(String value, int index) {
        if(index >= this.parameters.size()) {
            return;
        }

        ClypsValue baracoValue = this.getParameterAt(index);
        baracoValue.setValue(value);
    }

    public ClypsValue getParameterAt(int index) {
        int i = 0;

        for(ClypsValue mobiValue : this.parameters.values()) {
            if(i == index) {
                return mobiValue;
            }

            i++;
        }

        return null;
    }

    public void execute() {
        Scope scope = SymbolTableManager.getInstance().getActiveLocalScope();

        Scope funcScope = new Scope();
        SymbolTableManager.getInstance().setActiveScope(funcScope);
        ExecutionThread executionThread = ExecutionManager.getInstance().getExecutionThread();

        try {
            for(ICommand command : this.commandList) {
                executionThread.tryExecution();
                command.execute();

                if (command instanceof ReturnCommand) {
                    break;
                } else if (command instanceof IFCommand) {
                    if (((IFCommand) command).isReturned()) {
                        ((IFCommand) command).resetReturnFlag();
                        break;
                    }
                }


            }
        } catch(InterruptedException e) {
        }

        SymbolTableManager.getInstance().setActiveScope(scope);

        //LocalVarTracker.resetLocalVars(localVars);
    }

    public void printParams() {
        this.parameters.entrySet().forEach(entry -> {
            System.out.println(entry.getKey() + " " + entry.getValue().getValue().toString()+" "+entry.getValue().getPrimitiveType().toString());
        });
    }

    public FunctionType identifyFunctionType(String primitiveTypeString) {

        if(primitiveTypeString.contains(ClypsValue.PrimitiveType.BOOLEAN.toString().toLowerCase(Locale.ROOT))) {
            return FunctionType.BOOL_TYPE;
        }
        else if(primitiveTypeString.contains(ClypsValue.PrimitiveType.CHAR.toString().toLowerCase(Locale.ROOT))) {
            return FunctionType.CHAR_TYPE;
        }
        else if(primitiveTypeString.contains(ClypsValue.PrimitiveType.DOUBLE.toString().toLowerCase(Locale.ROOT))) {
            return FunctionType.DECIMAL_TYPE;
        }
        else if(primitiveTypeString.contains(ClypsValue.PrimitiveType.FLOAT.toString().toLowerCase(Locale.ROOT))) {
            return FunctionType.DECIMAL_TYPE;
        }
        else if(primitiveTypeString.contains(ClypsValue.PrimitiveType.INT.toString().toLowerCase(Locale.ROOT))) {
            return FunctionType.INT_TYPE;
        }
        else if(primitiveTypeString.toLowerCase(Locale.ROOT).contains(ClypsValue.PrimitiveType.STRING.toString().toLowerCase(Locale.ROOT))) {
            return FunctionType.STRING_TYPE;
        }else {
            return FunctionType.VOID_TYPE;
        }
    }


}
