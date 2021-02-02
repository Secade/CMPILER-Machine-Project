package commands;

import antlr.ClypsParser;
import com.udojava.evalex.Expression;
import controller.ClypsCustomVisitor;
import controller.ClypsValue;
import controller.SymbolTableManager;
import controller.editor;

import java.util.List;

public class NewVarCommand implements ICommand{

    private ClypsParser.LocalVariableDeclarationStatementContext ctx;

    public NewVarCommand(ClypsParser.LocalVariableDeclarationStatementContext ctx){
        this.ctx=ctx;
    }

    @Override
    public void execute() {
        System.out.println("NEW VAR");


        System.out.println(ctx.getText());

        String type = ctx.localVariableDeclaration().unannType(0).getText();
        if (ctx.localVariableDeclaration().unannType().size() > 1)
            return ;
        String name = ctx.localVariableDeclaration().variableDeclaratorList().variableDeclarator().get(0).variableDeclaratorId().getText();
        String value1 = ctx.localVariableDeclaration().variableDeclaratorList().variableDeclarator().get(0).variableInitializer().getText();
//        System.out.println(ctx.localVariableDeclaration().unannType(0).getText());
//        System.out.println(ctx.localVariableDeclaration().variableDeclaratorList().variableDeclarator().get(0).variableDeclaratorId().getText());
//        System.out.println(ctx.localVariableDeclaration().variableDeclaratorList().variableDeclarator().get(0).variableInitializer().getText());

        System.out.println("----");
        List<Integer> dummy = null;
        String value = ClypsCustomVisitor.testingExpression(value1, dummy, ctx.start.getLine());

        if (SymbolTableManager.searchVariableInLocalIterative(name, SymbolTableManager.getInstance().getActiveLocalScope()) == null &&
                SymbolTableManager.searchVariableInLocalIterative(name, SymbolTableManager.getInstance().getActiveLocalScope().getParent()) == null) {
            System.out.println("VAR NOT FOUND");
            boolean test1 = value.contains("\"");
            boolean test2 = value.contains("'");

            System.out.println(test2);
            System.out.println(type);
            System.out.println(ClypsValue.translateType(type));
            if (ClypsValue.translateType(type) != ClypsValue.PrimitiveType.STRING && !test1 && ClypsValue.translateType(type) != ClypsValue.PrimitiveType.CHAR && !test2) {
                System.out.println(value);
                value = ClypsCustomVisitor.testingExpression(value, dummy, ctx.start.getLine());
                System.out.println(value);
                if (value.contains("f") && !value.contains("false"))
                    value = value.replaceAll("f", "");
                if (value.contains("!")) {
                    value = value.replaceAll("!", "not");
                }
                boolean test = false;
                try {
                    test = new Expression(value).isBoolean();
                } catch (Expression.ExpressionException e) {
                    editor.addCustomError("TYPE MISMATCH", ctx.start.getLine());
                }

                if (!test && value.contains("true") || value.contains("false"))
                    test = true;
                System.out.println("====");
                System.out.println(test);
                if (ClypsValue.translateType(type) == ClypsValue.PrimitiveType.BOOLEAN && test) {
                    System.out.println("IS BOOLEAN");
                    SymbolTableManager.getInstance().getActiveLocalScope().addInitializedVariableFromKeywords(type, name, value);

                } else if (ClypsValue.translateType(type) != ClypsValue.PrimitiveType.BOOLEAN && (test || value.contains("true") || value.contains("false"))) {
                    System.out.println("NOT BOOLEAN");
                    editor.addCustomError("TYPE MISMATCH", ctx.start.getLine());
                } else if (ClypsValue.translateType(type) != ClypsValue.PrimitiveType.BOOLEAN && !test) {
                    System.out.println("IS DECIMAL");
                    SymbolTableManager.getInstance().getActiveLocalScope().addInitializedVariableFromKeywords(type, name, value);

                } else {
                    editor.addCustomError("TYPE MISMATCH", ctx.start.getLine());
                }
                if (!ctx.localVariableDeclaration().variableModifier().isEmpty()) {
                    if (ctx.localVariableDeclaration().variableModifier().get(0).getText().contains("final")) {
                        SymbolTableManager.getInstance().getActiveLocalScope().searchVariableIncludingLocal(name).markFinal();
                    }
                }
            } else if ((ClypsValue.translateType(type) == ClypsValue.PrimitiveType.STRING && test1) ||
                    (ClypsValue.translateType(type) == ClypsValue.PrimitiveType.CHAR && test2)) {
                SymbolTableManager.getInstance().getActiveLocalScope().addEmptyVariableFromKeywords(type, name);
                SymbolTableManager.getInstance().getActiveLocalScope().searchVariableIncludingLocal(name).setSCValue(value);
                if (!ctx.localVariableDeclaration().variableModifier().isEmpty()) {
                    if (ctx.localVariableDeclaration().variableModifier().get(0).getText().contains("final")) {
                        SymbolTableManager.getInstance().getActiveLocalScope().searchVariableIncludingLocal(name).markFinal();
                    }
                }

            } else {
                System.out.println("This?");
                editor.addCustomError("TYPE MISMATCH", ctx.start.getLine());
            }

        } else {
            editor.addCustomError("DUPLICATE VAR DETECTED", ctx.start.getLine());
        }

        System.out.println("PRINT ALL VARS");
        SymbolTableManager.getInstance().getActiveLocalScope().printAllVars();
        System.out.println("PRINT ALL VARS");
    }
}
