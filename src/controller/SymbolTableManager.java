package controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;

public class SymbolTableManager {
    private HashMap<String, ClypsFunction> functionMap;
    private Scope activeScope = null;

    private static SymbolTableManager sharedInstance = null;

    private SymbolTableManager() {
        this.functionMap = new HashMap<>();
    }

    public static SymbolTableManager getInstance() {
        return sharedInstance;
    }

    static void initialize() {
        sharedInstance = new SymbolTableManager();
    }

    static void destroy() {
        sharedInstance = null;
    }

    public Scope openLocalScope() {
        if(SymbolTableManager.getInstance().activeScope == null) {
            //System.out.println("INIT PARENT SCOPE");
            SymbolTableManager.getInstance().activeScope = new Scope();
        }
        else {
            //System.out.println("ADDING SCOPE CHILD");
            Scope childLocalScope = new Scope();
            childLocalScope.setParent(SymbolTableManager.getInstance().activeScope);
            SymbolTableManager.getInstance().activeScope.addChild(childLocalScope);
            SymbolTableManager.getInstance().activeScope = childLocalScope;
        }

        return this.activeScope;
    }

    public Scope getActiveLocalScope() {
        return this.activeScope;
    }

    public void setActiveScope(Scope scope){
        SymbolTableManager.getInstance().activeScope=scope;
    }

    public void closeLocalScope() {
        if(this.activeScope.getParent() != null && this.activeScope.getParent() instanceof Scope) {

            this.activeScope = (Scope) this.activeScope.getParent();
            this.activeScope.reset();
        }
        else if(this.activeScope.getParent() == null) {
            //System.out.println(": " + "Cannot change parent. Current active local scope no longer has a parent.");
        }
        else {
            //System.out.println(": " + "Cannot change parent. Current active local scope's parent is now a class scope.");
        }
    }

    public static ClypsValue searchVariableInLocalIterative(String identifier, Scope localScope) {

        if(localScope == null) {
            return null;
        }

        Stack<Scope> stack = new Stack<Scope>();

        stack.push(localScope);

        List<Scope> discoveredScopes = new ArrayList<Scope>();
        Scope scope;

        while(!stack.isEmpty()) {
            scope = stack.pop();

            if(scope.containsVariable(identifier)) {
                return scope.searchVariableIncludingLocal(identifier);
            }

            if(!discoveredScopes.contains(scope)) {
                discoveredScopes.add(scope);

                for(int i = 0; i < scope.getChildCount(); i++) {
                    Scope childScope = scope.getChildAt(i);
                    stack.push(childScope);
                }
            }
        }

        //System.out.println(": " + identifier + " not found in any local scope!");
        return null;
    }

    public ClypsFunction functionLookup(String functionName) {
        if (this.functionMap.get(functionName)!=null) {
            return this.functionMap.get(functionName);
        } else {
            //editor.addCustomError("Function does not exist.", 0);
            return null;
        }

    }

//    public void printAllVars() {
//        this.symbolTable.entrySet().forEach(entry -> {
//            System.out.println(entry.getKey() + " " + entry.getValue().getValue() + " " + entry.getValue().getPrimitiveType());
//        });
//    }

    public static void reset() {
        sharedInstance.functionMap.clear();
        sharedInstance.activeScope=null;
    }

    public void addFunction(String name, ClypsFunction function) {
        this.functionMap.put(name, function);
    }

    public HashMap<String, ClypsFunction> getFunctions() {
        return functionMap;
    }

//    public void printAllFunctions() {
//        functionMap.entrySet().forEach(entry -> {
//            System.out.println(entry.getKey() + " " + entry.getValue().toString());
//        });
//    }

}
