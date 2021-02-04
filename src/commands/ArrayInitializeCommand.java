package commands;

import antlr.ClypsParser;
import com.udojava.evalex.Expression;
import controller.ClypsCustomVisitor;
import controller.SymbolTableManager;
import controller.editor;

import java.util.List;

public class ArrayInitializeCommand implements ICommand{

    private ClypsParser.ArrayCreationExpressionContext ctx;

    public ArrayInitializeCommand(ClypsParser.ArrayCreationExpressionContext ctx){
        this.ctx=ctx;
    }


    @Override
    public void execute() {
        //System.out.println("INIT ARRAY");
        String firstType = ctx.unannArrayType().getText();
        String name = ctx.Identifier().getText();
        String secondType = ctx.unannType().getText();
        List<Integer> dummy = null;
        String size1 = ctx.dimExpr().getText().replaceAll("\\[", "").replaceAll("\\]", "");
        String size2 = ClypsCustomVisitor.testingExpression(size1, dummy, ctx.start.getLine());
        //System.out.println("After DUmmy " + size2);
        //System.out.println(size1);
        //System.out.println(name);
        //System.out.println(SymbolTableManager.getInstance().getActiveLocalScope().searchArray(name));
        if (size2.matches("[^A-Za-z]+")) {
            String size = new Expression(size2).eval().toPlainString();
            if (SymbolTableManager.searchVariableInLocalIterative(name, SymbolTableManager.getInstance().getActiveLocalScope()) == null &&
                    SymbolTableManager.searchVariableInLocalIterative(name, SymbolTableManager.getInstance().getActiveLocalScope().getParent()) == null &&
                    SymbolTableManager.getInstance().getActiveLocalScope().searchArray(name) == null) {
                if (Integer.parseInt(size) > 0) {
                    if (firstType.contains(secondType)) {
                        //System.out.println("ADDED ARRAY");
                        SymbolTableManager.getInstance().getActiveLocalScope().addArray(secondType, name, size);
                    } else {
                        editor.addCustomError("INVALID TYPE INITIALIZATION", ctx.start.getLine());
                    }
                } else  if(size==null){

                }else if (Integer.parseInt(size) <= 0) {
                    editor.addCustomError("ZERO AND NEGATIVE VALUES ARE NOT ALLOWED", ctx.start.getLine());
                }
            } else {
                editor.addCustomError("ARRAY NAME IN USE", ctx.start.getLine());
            }
        } else {
            editor.addCustomError("INVALID SIZE ALLOTMENT", ctx.start.getLine());
        }

        //System.out.println("PRINT ALL ARRAYS");
        //SymbolTableManager.getInstance().getActiveLocalScope().printAllArrays();
        //System.out.println("PRINT ALL VALUES");
        //SymbolTableManager.getInstance().getActiveLocalScope().printArrayValues();
        //System.out.println("END PRINT");
    }
}
