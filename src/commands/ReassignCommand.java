package commands;

import antlr.ClypsParser;
import controller.*;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ReassignCommand implements ICommand {

    private ClypsParser.VariableDeclarationStatementContext ctx;

    public ReassignCommand(ClypsParser.VariableDeclarationStatementContext ctx) {
        this.ctx = ctx;
    }

    @Override
    public void execute() {
        System.out.println("DUPLICATE DETECTED ");

        System.out.println("REASSINING PART");
        if (ctx.variableDeclarator().variableDeclaratorId().getText().contains("[")) {
            List<Integer> dummy = null;
            System.out.println(ctx.variableDeclarator().variableDeclaratorId().Identifier().getText());
            System.out.println(ctx.variableDeclarator().variableInitializer().getText());
            int index = Integer.parseInt(ClypsCustomVisitor.testingExpression(ctx.variableDeclarator().variableDeclaratorId().expression().getText(), dummy, ctx.start.getLine()));
            String value = ClypsCustomVisitor.testingExpression(ctx.variableDeclarator().variableInitializer().getText(), dummy, ctx.start.getLine());
            if (SymbolTableManager.getInstance().getActiveLocalScope().searchArray(ctx.variableDeclarator().variableDeclaratorId().Identifier().getText()) != null) {
                System.out.println("WE IN");
                ClypsArray te = SymbolTableManager.getInstance().getActiveLocalScope().searchArray(ctx.variableDeclarator().variableDeclaratorId().Identifier().getText());
                ClypsValue temp = new ClypsValue();
                temp.setType(te.getPrimitiveType());
                temp.setValue(value);
                System.out.println("FOR CHECKING");
                System.out.println(temp.getValue());
                System.out.println(temp.getPrimitiveType());
                if (index >= te.getSize() || index <= -1) {
                    editor.addCustomError("ARRAY OUT OF BOUNDS", ctx.start.getLine());
                } else {
                    if (ClypsValue.attemptTypeCast(value,SymbolTableManager.getInstance().getActiveLocalScope().searchArray(ctx.variableDeclarator().variableDeclaratorId().Identifier().getText()).getPrimitiveType())!=null)
                        SymbolTableManager.getInstance().getActiveLocalScope().searchArray(ctx.variableDeclarator().variableDeclaratorId().Identifier().getText()).updateValueAt(temp, index);
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
            System.out.println(ctx.variableDeclarator().variableDeclaratorId().Identifier().getText());
            //System.out.println(SymbolTableManager.getInstance().getActiveLocalScope().searchVariableIncludingLocal(ctx.variableDeclarator().variableDeclaratorId().Identifier().getText()).getValue().toString());
            if (SymbolTableManager.searchVariableInLocalIterative(ctx.variableDeclarator().variableDeclaratorId().Identifier().getText(), SymbolTableManager.getInstance().getActiveLocalScope())!= null) {
                if (!SymbolTableManager.searchVariableInLocalIterative(ctx.variableDeclarator().variableDeclaratorId().Identifier().getText(), SymbolTableManager.getInstance().getActiveLocalScope()).isFinal()) {
                    if (SymbolTableManager.searchVariableInLocalIterative(ctx.variableDeclarator().variableDeclaratorId().Identifier().getText(), SymbolTableManager.getInstance().getActiveLocalScope())!= null) {
                        System.out.println("REASSIGN");
                        System.out.println(ctx.variableDeclarator().variableDeclaratorId().getText());
                        String value;
                        List<Integer> dummy = null;
                        if (!ctx.variableDeclarator().variableInitializer().getText().contains("[")) {
                            System.out.println("not array");
                            value = ClypsCustomVisitor.testingExpression(ctx.variableDeclarator().variableInitializer().getText(), dummy, ctx.start.getLine());
                        } else {
                            System.out.println("array");
                            List<Integer> matchList = new ArrayList<Integer>();
                            Pattern regex = Pattern.compile("\\[(.*?)\\]");
                            System.out.println(ctx.variableDeclarator().variableInitializer().getText());
                            Matcher regexMatcher = regex.matcher(ctx.variableDeclarator().variableInitializer().getText());

                            while (regexMatcher.find()) {//Finds Matching Pattern in String
                                matchList.add(Integer.parseInt(regexMatcher.group(1).trim()));//Fetching Group from String
                            }
                            value = ClypsCustomVisitor.testingExpression(ctx.variableDeclarator().variableInitializer().getText(), matchList, ctx.start.getLine());
                        }
                        System.out.println("CHECK THE TYPE ======?");
                        System.out.println(value);
                        //System.out.println(ClypsValue.attemptTypeCast(value,SymbolTableManager.getInstance().getActiveLocalScope().searchVariableIncludingLocal(ctx.variableDeclarator().variableDeclaratorId().Identifier().getText()).getPrimitiveType()));
                        //System.out.println(SymbolTableManager.getInstance().getActiveLocalScope().searchVariableIncludingLocal(ctx.variableDeclarator().variableDeclaratorId().Identifier().getText()).getPrimitiveType());
                        System.out.println(SymbolTableManager.searchVariableInLocalIterative(ctx.variableDeclarator().variableDeclaratorId().Identifier().getText(), SymbolTableManager.getInstance().getActiveLocalScope()).getPrimitiveType());
                        System.out.println("CHECK THE TYPE ======?");
                        if (ClypsValue.attemptTypeCast(value, SymbolTableManager.searchVariableInLocalIterative(ctx.variableDeclarator().variableDeclaratorId().Identifier().getText(), SymbolTableManager.getInstance().getActiveLocalScope()).getPrimitiveType()) != null){
                            System.out.println("In???");
                            System.out.println(ctx.variableDeclarator().variableDeclaratorId().Identifier().getText());
                            SymbolTableManager.searchVariableInLocalIterative(ctx.variableDeclarator().variableDeclaratorId().Identifier().getText(), SymbolTableManager.getInstance().getActiveLocalScope()).setValue(value);
                            //SymbolTableManager.getInstance().getActiveLocalScope().setDeclaredVariable(ctx.variableDeclarator().variableDeclaratorId().Identifier().getText(), value);
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
}
