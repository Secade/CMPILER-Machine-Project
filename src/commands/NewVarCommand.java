package commands;

import antlr.ClypsParser;
import com.udojava.evalex.Expression;
import controller.*;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
        System.out.println(value);

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
                System.out.println("((((((((");
                boolean test = false;
                try {
                    test = new Expression(value).isBoolean();
                } catch (Expression.ExpressionException e) {
                    //editor.addCustomError("DIS ONE?", ctx.start.getLine());
                    //editor.addCustomError("TYPE MISMATCH", ctx.start.getLine());
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
                    editor.addCustomError("DIS TWO?", ctx.start.getLine());
                    editor.addCustomError("TYPE MISMATCH", ctx.start.getLine());
                } else if (ClypsValue.translateType(type) != ClypsValue.PrimitiveType.BOOLEAN && !test) {
                    System.out.println("IS DECIMAL");
                    SymbolTableManager.getInstance().getActiveLocalScope().addInitializedVariableFromKeywords(type, name, value);

                } else {
                    editor.addCustomError("DIS THREE?", ctx.start.getLine());
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
                editor.addCustomError("DIS FOUR?", ctx.start.getLine());
                editor.addCustomError("TYPE MISMATCH", ctx.start.getLine());
            }

        } else {
            System.out.println("DUPLICATE DETECTED ");

            System.out.println("REASSINING PART");
            if (ctx.localVariableDeclaration().variableDeclaratorList().variableDeclarator().get(0).variableDeclaratorId().getText().contains("[")) {
                System.out.println(ctx.localVariableDeclaration().variableDeclaratorList().variableDeclarator().get(0).variableDeclaratorId().Identifier().getText());
                System.out.println(ctx.localVariableDeclaration().variableDeclaratorList().variableDeclarator().get(0).variableInitializer().getText());
                int index = Integer.parseInt(ClypsCustomVisitor.testingExpression(ctx.localVariableDeclaration().variableDeclaratorList().variableDeclarator().get(0).variableDeclaratorId().expression().getText(), dummy, ctx.start.getLine()));
                if (SymbolTableManager.getInstance().getActiveLocalScope().searchArray(ctx.localVariableDeclaration().variableDeclaratorList().variableDeclarator().get(0).variableDeclaratorId().Identifier().getText()) != null) {
                    System.out.println("WE IN");
                    ClypsArray te = SymbolTableManager.getInstance().getActiveLocalScope().searchArray(ctx.localVariableDeclaration().variableDeclaratorList().variableDeclarator().get(0).variableDeclaratorId().Identifier().getText());
                    ClypsValue temp = new ClypsValue();
                    temp.setType(te.getPrimitiveType());
                    temp.setValue(value);
                    System.out.println("FOR CHECKING");
                    System.out.println(temp.getValue());
                    System.out.println(temp.getPrimitiveType());
                    if (index >= te.getSize() || index <= -1) {
                        editor.addCustomError("ARRAY OUT OF BOUNDS", ctx.start.getLine());
                    } else {
                        if (ClypsValue.attemptTypeCast(value,SymbolTableManager.getInstance().getActiveLocalScope().searchArray(ctx.localVariableDeclaration().variableDeclaratorList().variableDeclarator().get(0).variableDeclaratorId().Identifier().getText()).getPrimitiveType())!=null)
                            SymbolTableManager.getInstance().getActiveLocalScope().searchArray(ctx.localVariableDeclaration().variableDeclaratorList().variableDeclarator().get(0).variableDeclaratorId().Identifier().getText()).updateValueAt(temp, index);
                        else
                            editor.addCustomError("TYPE MISMATCH", ctx.start.getLine());
                    }
                } else {
                    //System.out.println("DUPLICATE VAR");
                    editor.addCustomError("VAR DOES NOT EXIST", ctx.start.getLine());
                    //System.out.println(editor.errors.get(editor.errors.size()-1));
                }
                System.out.println("PRINT ALL ARRAYS");
                SymbolTableManager.getInstance().getActiveLocalScope().printAllArrays();
                System.out.println("PRINT ALL VALUES");
                SymbolTableManager.getInstance().getActiveLocalScope().printArrayValues();
                System.out.println("END PRINT");
            } else {
                System.out.println(ctx.localVariableDeclaration().variableDeclaratorList().variableDeclarator().get(0).variableDeclaratorId().Identifier().getText());
                //System.out.println(SymbolTableManager.getInstance().getActiveLocalScope().searchVariableIncludingLocal(ctx.localVariableDeclaration().variableDeclaratorList().variableDeclarator().get(0).variableDeclaratorId().Identifier().getText()).getValue().toString());
                if (SymbolTableManager.searchVariableInLocalIterative(ctx.localVariableDeclaration().variableDeclaratorList().variableDeclarator().get(0).variableDeclaratorId().Identifier().getText(), SymbolTableManager.getInstance().getActiveLocalScope())!= null) {
                    if (!SymbolTableManager.searchVariableInLocalIterative(ctx.localVariableDeclaration().variableDeclaratorList().variableDeclarator().get(0).variableDeclaratorId().Identifier().getText(), SymbolTableManager.getInstance().getActiveLocalScope()).isFinal()) {
                        if (SymbolTableManager.searchVariableInLocalIterative(ctx.localVariableDeclaration().variableDeclaratorList().variableDeclarator().get(0).variableDeclaratorId().Identifier().getText(), SymbolTableManager.getInstance().getActiveLocalScope())!= null) {
                            System.out.println("REASSIGN");
                            System.out.println(ctx.localVariableDeclaration().variableDeclaratorList().variableDeclarator().get(0).variableDeclaratorId().getText());
                            //List<Integer> dummy = null;
                            if (!ctx.localVariableDeclaration().variableDeclaratorList().variableDeclarator().get(0).variableInitializer().getText().contains("[")) {
                                System.out.println("not array");
                                value = ClypsCustomVisitor.testingExpression(ctx.localVariableDeclaration().variableDeclaratorList().variableDeclarator().get(0).variableInitializer().getText(), dummy, ctx.start.getLine());
                            } else {
                                System.out.println("array");
                                List<Integer> matchList = new ArrayList<Integer>();
                                Pattern regex = Pattern.compile("\\[(.*?)\\]");
                                System.out.println(ctx.localVariableDeclaration().variableDeclaratorList().variableDeclarator().get(0).variableInitializer().getText());
                                Matcher regexMatcher = regex.matcher(ctx.localVariableDeclaration().variableDeclaratorList().variableDeclarator().get(0).variableInitializer().getText());

                                while (regexMatcher.find()) {//Finds Matching Pattern in String
                                    matchList.add(Integer.parseInt(regexMatcher.group(1).trim()));//Fetching Group from String
                                }
                                value = ClypsCustomVisitor.testingExpression(ctx.localVariableDeclaration().variableDeclaratorList().variableDeclarator().get(0).variableInitializer().getText(), matchList, ctx.start.getLine());
                            }
                            System.out.println("CHECK THE TYPE ======?");
                            System.out.println(value);
                            //System.out.println(ClypsValue.attemptTypeCast(value,SymbolTableManager.getInstance().getActiveLocalScope().searchVariableIncludingLocal(ctx.localVariableDeclaration().variableDeclaratorList().variableDeclarator().get(0).variableDeclaratorId().Identifier().getText()).getPrimitiveType()));
                            //System.out.println(SymbolTableManager.getInstance().getActiveLocalScope().searchVariableIncludingLocal(ctx.localVariableDeclaration().variableDeclaratorList().variableDeclarator().get(0).variableDeclaratorId().Identifier().getText()).getPrimitiveType());
                            System.out.println(SymbolTableManager.searchVariableInLocalIterative(ctx.localVariableDeclaration().variableDeclaratorList().variableDeclarator().get(0).variableDeclaratorId().Identifier().getText(), SymbolTableManager.getInstance().getActiveLocalScope()).getPrimitiveType());
                            System.out.println("CHECK THE TYPE ======?");
                            if (ClypsValue.attemptTypeCast(value, SymbolTableManager.searchVariableInLocalIterative(ctx.localVariableDeclaration().variableDeclaratorList().variableDeclarator().get(0).variableDeclaratorId().Identifier().getText(), SymbolTableManager.getInstance().getActiveLocalScope()).getPrimitiveType()) != null){
                                System.out.println("In???");
                                System.out.println(ctx.localVariableDeclaration().variableDeclaratorList().variableDeclarator().get(0).variableDeclaratorId().Identifier().getText());
                                SymbolTableManager.searchVariableInLocalIterative(ctx.localVariableDeclaration().variableDeclaratorList().variableDeclarator().get(0).variableDeclaratorId().Identifier().getText(), SymbolTableManager.getInstance().getActiveLocalScope()).setValue(value);
                                //SymbolTableManager.getInstance().getActiveLocalScope().setDeclaredVariable(ctx.localVariableDeclaration().variableDeclaratorList().variableDeclarator().get(0).variableDeclaratorId().Identifier().getText(), value);
                        }else
                                editor.addCustomError("TYPE MISMATCH", ctx.start.getLine());
                        } else {
                            //System.out.println("DUPLICATE VAR");
                            editor.addCustomError("VAR DOES NOT EXIST", ctx.start.getLine());
                            //System.out.println(editor.errors.get(editor.errors.size()-1));
                        }
                    } else {
                        editor.addCustomError("CANNOT CHANGE CONSTANT VARIABLE", ctx.start.getLine());
                    }
                }


            }

            editor.addCustomError("DUPLICATE VAR DETECTED", ctx.start.getLine());
        }

        System.out.println("PRINT ALL VARS");
        SymbolTableManager.getInstance().getActiveLocalScope().printAllVars();
        System.out.println("PRINT ALL VARS");
    }
}
