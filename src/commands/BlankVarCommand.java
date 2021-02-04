package commands;

import antlr.ClypsParser;
import controller.ClypsValue;
import controller.SymbolTableManager;
import controller.editor;

public class BlankVarCommand implements ICommand{

    private ClypsParser.VariableNoInitContext ctx;

    public BlankVarCommand(ClypsParser.VariableNoInitContext ctx){
        this.ctx=ctx;
    }

    @Override
    public void execute() {
        String type = ctx.unannType().getText();
        String name = ctx.variableDeclaratorId().Identifier().getText();

        if (SymbolTableManager.searchVariableInLocalIterative(name, SymbolTableManager.getInstance().getActiveLocalScope()) == null &&
                SymbolTableManager.searchVariableInLocalIterative(name, SymbolTableManager.getInstance().getActiveLocalScope().getParent()) == null) {
            //System.out.println("VAR NOT FOUND");

            //System.out.println(ClypsValue.translateType(type));
            if (ClypsValue.translateType(type) != ClypsValue.PrimitiveType.STRING) {
                if (ClypsValue.translateType(type) == ClypsValue.PrimitiveType.BOOLEAN) {
                    //System.out.println("IS BOOLEAN");
                    SymbolTableManager.getInstance().getActiveLocalScope().addEmptyVariableFromKeywords(type, name);
                } else if (ClypsValue.translateType(type) != ClypsValue.PrimitiveType.BOOLEAN) {
                    //System.out.println("IS DECIMAL");
                    SymbolTableManager.getInstance().getActiveLocalScope().addEmptyVariableFromKeywords(type, name);
                }
                if (!ctx.variableModifier().isEmpty()) {
                    if (ctx.variableModifier().get(0).getText().contains("final")) {
                        editor.addCustomError("CONSTANT VARIABLE NEEDS TO BE INITIALIZED", ctx.start.getLine());
                    }
                }
            } else if ((ClypsValue.translateType(type) == ClypsValue.PrimitiveType.STRING) ||
                    (ClypsValue.translateType(type) == ClypsValue.PrimitiveType.CHAR)) {
                SymbolTableManager.getInstance().getActiveLocalScope().addEmptyVariableFromKeywords(type, name);
                if (!ctx.variableModifier().isEmpty()) {
                    if (ctx.variableModifier().get(0).getText().contains("final")) {
                        editor.addCustomError("CONSTANT VARIABLE NEEDS TO BE INITIALIZED", ctx.start.getLine());
                    }
                }
            } else {
                editor.addCustomError("TYPE MISMATCH", ctx.start.getLine());
            }

        } else {
            editor.addCustomError("DUPLICATE VAR DETECTED", ctx.start.getLine());
        }



        //System.out.println("PRINT ALL VARS");
        //SymbolTableManager.getInstance().getActiveLocalScope().printAllVars();
        //System.out.println("PRINT ALL VARS");
    }
}
