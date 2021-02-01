package controller;

import ErrorCheckers.TypeChecking;
import antlr.ClypsBaseVisitor;
import antlr.ClypsParser;
import com.udojava.evalex.Expression;
import commands.ForCommand;
import commands.IFCommand;
import commands.IncDecCommand;
import commands.PrintCommand;
import execution.ExecutionManager;
import items.ClypsValue;
import sun.awt.Symbol;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ClypsCustomVisitor extends ClypsBaseVisitor<ClypsValue> {

    @Override
    public ClypsValue visitLocalVariableDeclarationStatement(ClypsParser.LocalVariableDeclarationStatementContext ctx) {
        System.out.println("NEW VAR");


        System.out.println(ctx.getText());

        String type = ctx.localVariableDeclaration().unannType(0).getText();
        if (ctx.localVariableDeclaration().unannType().size() > 1)
            return visitChildren(ctx);
        String name = ctx.localVariableDeclaration().variableDeclaratorList().variableDeclarator().get(0).variableDeclaratorId().getText();
        String value1 = ctx.localVariableDeclaration().variableDeclaratorList().variableDeclarator().get(0).variableInitializer().getText();
        System.out.println(ctx.localVariableDeclaration().unannType(0).getText());
        System.out.println(ctx.localVariableDeclaration().variableDeclaratorList().variableDeclarator().get(0).variableDeclaratorId().getText());
        System.out.println(ctx.localVariableDeclaration().variableDeclaratorList().variableDeclarator().get(0).variableInitializer().getText());

        System.out.println("----");
        List<Integer> dummy = null;
        String value = testingExpression(value1, dummy, ctx.start.getLine());


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
                value = testingExpression(value, dummy, ctx.start.getLine());
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
        return visitChildren(ctx);
    }

    @Override
    public ClypsValue visitVariableDeclarationStatement(ClypsParser.VariableDeclarationStatementContext ctx) {
        //System.out.println(ctx.variableDeclarator().variableDeclaratorId().Identifier());
        System.out.println("REASSINING PART");
        if (ctx.variableDeclarator().variableDeclaratorId().getText().contains("[")) {
            List<Integer> dummy = null;
            System.out.println(ctx.variableDeclarator().variableDeclaratorId().Identifier().getText());
            System.out.println(ctx.variableDeclarator().variableInitializer().getText());
            int index = Integer.parseInt(testingExpression(ctx.variableDeclarator().variableDeclaratorId().expression().getText(), dummy, ctx.start.getLine()));
            String value = testingExpression(ctx.variableDeclarator().variableInitializer().getText(), dummy, ctx.start.getLine());
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
                    SymbolTableManager.getInstance().getActiveLocalScope().searchArray(ctx.variableDeclarator().variableDeclaratorId().Identifier().getText()).updateValueAt(temp, index);

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
            if (SymbolTableManager.getInstance().getActiveLocalScope().searchVariableIncludingLocal(ctx.variableDeclarator().variableDeclaratorId().Identifier().getText()) != null) {
                if (!SymbolTableManager.getInstance().getActiveLocalScope().searchVariableIncludingLocal(ctx.variableDeclarator().variableDeclaratorId().Identifier().getText()).isFinal()) {
                    if (SymbolTableManager.getInstance().getActiveLocalScope().searchVariableIncludingLocal(ctx.variableDeclarator().variableDeclaratorId().Identifier().getText()) != null) {
                        System.out.println("REASSIGN");
                        String value;
                        System.out.println(ctx.variableDeclarator().variableDeclaratorId().getText());
                        List<Integer> dummy = null;
                        if (!ctx.variableDeclarator().variableInitializer().getText().contains("[")) {
                            System.out.println("not array");
                            value = testingExpression(ctx.variableDeclarator().variableInitializer().getText(), dummy, ctx.start.getLine());
                        } else {
                            System.out.println("array");
                            List<Integer> matchList = new ArrayList<Integer>();
                            Pattern regex = Pattern.compile("\\[(.*?)\\]");
                            System.out.println(ctx.variableDeclarator().variableInitializer().getText());
                            Matcher regexMatcher = regex.matcher(ctx.variableDeclarator().variableInitializer().getText());

                            while (regexMatcher.find()) {//Finds Matching Pattern in String
                                matchList.add(Integer.parseInt(regexMatcher.group(1).trim()));//Fetching Group from String
                            }
                            value = testingExpression(ctx.variableDeclarator().variableInitializer().getText(), matchList, ctx.start.getLine());
                        }
                        SymbolTableManager.getInstance().getActiveLocalScope().setDeclaredVariable(ctx.variableDeclarator().variableDeclaratorId().Identifier().getText(), value);
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


        System.out.println("PRINT ALL VARS");
        SymbolTableManager.getInstance().getActiveLocalScope().printAllVars();
        System.out.println("PRINT ALL VARS");
        return visitChildren(ctx);
    }

    @Override
    public ClypsValue visitVariableNoInit(ClypsParser.VariableNoInitContext ctx) {
        String type = ctx.unannType().getText();
        String name = ctx.variableDeclaratorId().Identifier().getText();

        if (SymbolTableManager.searchVariableInLocalIterative(name, SymbolTableManager.getInstance().getActiveLocalScope()) == null &&
                SymbolTableManager.searchVariableInLocalIterative(name, SymbolTableManager.getInstance().getActiveLocalScope().getParent()) == null) {
            System.out.println("VAR NOT FOUND");

            System.out.println(ClypsValue.translateType(type));
            if (ClypsValue.translateType(type) != ClypsValue.PrimitiveType.STRING) {
                if (ClypsValue.translateType(type) == ClypsValue.PrimitiveType.BOOLEAN) {
                    System.out.println("IS BOOLEAN");
                    SymbolTableManager.getInstance().getActiveLocalScope().addEmptyVariableFromKeywords(type, name);
                } else if (ClypsValue.translateType(type) != ClypsValue.PrimitiveType.BOOLEAN) {
                    System.out.println("IS DECIMAL");
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

        System.out.println("PRINT ALL VARS");
        SymbolTableManager.getInstance().getActiveLocalScope().printAllVars();
        System.out.println("PRINT ALL VARS");

        return visitChildren(ctx);
    }

    @Override
    public ClypsValue visitArrayCreationExpression(ClypsParser.ArrayCreationExpressionContext ctx) {
        System.out.println("INIT ARRAY");
        String firstType = ctx.unannArrayType().getText();
        String name = ctx.Identifier().getText();
        String secondType = ctx.primitiveType().getText();
        List<Integer> dummy = null;
        String size1 = ctx.dimExpr().getText().replaceAll("\\[", "").replaceAll("\\]", "");
        String size2 = testingExpression(size1, dummy, ctx.start.getLine());
        System.out.println("After DUmmy " + size2);
        System.out.println(name);
        System.out.println(SymbolTableManager.getInstance().getActiveLocalScope().searchArray(name));
        if (size2.matches("[^A-Za-z_2]+")) {
            String size = new Expression(size2).eval().toPlainString();
            if (SymbolTableManager.searchVariableInLocalIterative(name, SymbolTableManager.getInstance().getActiveLocalScope()) == null &&
                    SymbolTableManager.searchVariableInLocalIterative(name, SymbolTableManager.getInstance().getActiveLocalScope().getParent()) == null &&
                    SymbolTableManager.getInstance().getActiveLocalScope().searchArray(name) == null) {
                if (Integer.parseInt(size) > 0) {
                    if (firstType.contains(secondType)) {
                        SymbolTableManager.getInstance().getActiveLocalScope().addArray(secondType, name, size);
                    } else {
                        editor.addCustomError("INVALID TYPE INITIALIZATION", ctx.start.getLine());
                    }
                } else {
                    editor.addCustomError("ZERO AND NEGATIVE VALUES ARE NOT ALLOWED", ctx.start.getLine());
                }
            } else {
                editor.addCustomError("ARRAY NAME IN USE", ctx.start.getLine());
            }
        } else {
            editor.addCustomError("INVALID SIZE ALLOTMENT", ctx.start.getLine());
        }


        System.out.println("PRINT ALL ARRAYS");
        SymbolTableManager.getInstance().getActiveLocalScope().printAllArrays();
        System.out.println("PRINT ALL VALUES");
        SymbolTableManager.getInstance().getActiveLocalScope().printArrayValues();
        System.out.println("END PRINT");
        return visitChildren(ctx);
    }

    @Override
    public ClypsValue visitIfThenStatement(ClypsParser.IfThenStatementContext ctx) {


        return visitChildren(ctx);
    }

    @Override
    public ClypsValue visitBlock(ClypsParser.BlockContext ctx) {
        System.out.println("ENTER BLOCK");
        SymbolTableManager.getInstance().openLocalScope();

        visitChildren(ctx);

        SymbolTableManager.getInstance().closeLocalScope();
        System.out.println("EXIT BLOCK");
        return null;
    }

    @Override
    public ClypsValue visitMethodDeclaration(ClypsParser.MethodDeclarationContext ctx) {
        System.out.println("ENTER FUNCTION");
        System.out.println(ctx.getText());
        System.out.println(ctx.methodHeader().result().getText());
        System.out.println(ctx.methodHeader().methodDeclarator().Identifier().getText());

        if (SymbolTableManager.getInstance().functionLookup(ctx.methodHeader().methodDeclarator().Identifier().getText()) == null) {
            ClypsFunction function = new ClypsFunction();
            SymbolTableManager.getInstance().addFunction(ctx.methodHeader().methodDeclarator().Identifier().getText(), function);
        } else {
            editor.addCustomError("DUPLICATE FUNCTION DETECTED", ctx.start.getLine());
        }

        System.out.println("PRINT ALL FUNCTION");
        SymbolTableManager.getInstance().printAllFunctions();

        return visitChildren(ctx);
    }

    @Override
    public ClypsValue visitPrintStatement(ClypsParser.PrintStatementContext ctx) {
        System.out.println("Added Print Command");

        PrintCommand printCommand = new PrintCommand(ctx);

        StatementController statementControl = StatementController.getInstance();

        //System.out.println(statementControl.getActiveControlledCommand());

        if(statementControl.isInConditionalCommand()) {
            System.out.println("PRINT IN CONDITIONAL");
            IConditionalCommand conditionalCommand = (IConditionalCommand) statementControl.getActiveControlledCommand();

            if(statementControl.isInPositiveRule()) {
                conditionalCommand.addPositiveCommand(printCommand);
            }
            else {
                conditionalCommand.addNegativeCommand(printCommand);
            }
        } else if(statementControl.isInControlledCommand()) {
            System.out.println("PRINT IN CONTROLLED");
            IControlledCommand controlledCommand = (IControlledCommand) statementControl.getActiveControlledCommand();
            controlledCommand.addCommand(printCommand);
        } else {
            System.out.println("PRINT IN OPEN ");
            ExecutionManager.getInstance().addCommand(printCommand);
        }

        return visitChildren(ctx);
    }

    @Override
    public ClypsValue visitScanStatement(ClypsParser.ScanStatementContext ctx) {

        //PLACEHOLDER ONLY
        System.out.println("INPUT: "+editor.getInput());


        return visitChildren(ctx);
    }

    @Override
    public ClypsValue visitIncDecStatement(ClypsParser.IncDecStatementContext ctx) {
        System.out.println("ENTER INC DEC COMMAND");
        String name = ctx.getText().replaceAll("\\+","").replaceAll("-","").replaceAll(";","");
        name=name.replaceAll("\\[.*\\]", "");
        if (SymbolTableManager.searchVariableInLocalIterative(name, SymbolTableManager.getInstance().getActiveLocalScope()) != null ||
                SymbolTableManager.searchVariableInLocalIterative(name, SymbolTableManager.getInstance().getActiveLocalScope().getParent()) != null||
                SymbolTableManager.getInstance().getActiveLocalScope().searchArray(name.replaceAll("\\[.*\\]", ""))!=null) {
            //FIX NULL

            System.out.println(name);

            String check = "";
            if (ctx.getText().contains("++"))
                check = "pos";
            else if (ctx.getText().contains("--"))
                check = "neg";

            System.out.println("SIII");
            System.out.println(name);

            IncDecCommand incDecCommand = null;

            if (ctx.getText().contains("[")) {
                if (SymbolTableManager.getInstance().getActiveLocalScope().searchArray(name).getPrimitiveType() == ClypsValue.PrimitiveType.INT ||
                        SymbolTableManager.getInstance().getActiveLocalScope().searchArray(name).getPrimitiveType() == ClypsValue.PrimitiveType.DOUBLE ||
                        SymbolTableManager.getInstance().getActiveLocalScope().searchArray(name).getPrimitiveType() == ClypsValue.PrimitiveType.FLOAT) {

                    incDecCommand = new IncDecCommand(ctx, name, check, SymbolTableManager.getInstance().getActiveLocalScope().searchArray(name).getPrimitiveType());
                } else {
                    editor.addCustomError("CAN ONLY INC/DEC A NUMBER", ctx.start.getLine());
                }
                } else {
                    if (SymbolTableManager.searchVariableInLocalIterative(name, SymbolTableManager.getInstance().getActiveLocalScope()).getPrimitiveType() == ClypsValue.PrimitiveType.INT ||
                            SymbolTableManager.searchVariableInLocalIterative(name, SymbolTableManager.getInstance().getActiveLocalScope()).getPrimitiveType() == ClypsValue.PrimitiveType.DOUBLE ||
                            SymbolTableManager.searchVariableInLocalIterative(name, SymbolTableManager.getInstance().getActiveLocalScope()).getPrimitiveType() == ClypsValue.PrimitiveType.FLOAT) {
                        incDecCommand = new IncDecCommand(ctx, name, check, SymbolTableManager.searchVariableInLocalIterative(name, SymbolTableManager.getInstance().getActiveLocalScope()).getPrimitiveType());
                    } else {
                        editor.addCustomError("CAN ONLY INC/DEC A NUMBER", ctx.start.getLine());
                    }
                }
                StatementController statementControl = StatementController.getInstance();

            if (incDecCommand!=null){
                if (statementControl.isInConditionalCommand()) {
                    System.out.println("PRINT IN CONDITIONAL");
                    IConditionalCommand conditionalCommand = (IConditionalCommand) statementControl.getActiveControlledCommand();

                    if (statementControl.isInPositiveRule()) {
                        conditionalCommand.addPositiveCommand(incDecCommand);
                    } else {
                        conditionalCommand.addNegativeCommand(incDecCommand);
                    }
                } else if (statementControl.isInControlledCommand()) {
                    System.out.println("PRINT IN CONTROLLED");
                    IControlledCommand controlledCommand = (IControlledCommand) statementControl.getActiveControlledCommand();
                    controlledCommand.addCommand(incDecCommand);
                } else {
                    System.out.println("PRINT IN OPEN ");
                    ExecutionManager.getInstance().addCommand(incDecCommand);
                }
            }


        }else {
            editor.addCustomError("VARIABLE DOES NOT EXIST", ctx.start.getLine());
        }

        System.out.println("PRINT ALL VARS");
        SymbolTableManager.getInstance().getActiveLocalScope().printAllVars();
        System.out.println("PRINT ALL VARS");

        return visitChildren(ctx);
        }


    @Override
    public ClypsValue visitForStatement(ClypsParser.ForStatementContext ctx) {
        System.out.println("ENTER FOR COMMAND");
        List<Integer> dummy = null;
        String start=ClypsCustomVisitor.testingExpression(ctx.forInit().variableDeclaratorList().variableDeclarator(0).variableInitializer().getText(),dummy,ctx.start.getLine());
        String end=ClypsCustomVisitor.testingExpression(ctx.assignmentExpression().getText(),dummy,ctx.start.getLine());
        int counter=Integer.parseInt(new Expression(start).eval().toPlainString());
        int stop=Integer.parseInt(new Expression(end).eval().toPlainString());

        if ((ctx.forMiddle().getText().contains("up to") && counter > stop) || (ctx.forMiddle().getText().contains("down to") && counter < stop)) {
            editor.addCustomError("VALUE RANGE IS NOT POSSIBLE", ctx.start.getLine());
        } else {
            ForCommand forCommand = new ForCommand(ctx);
            StatementController.getInstance().openControlledCommand(forCommand);
            System.out.println(ctx.block().blockStatements().getChildCount());


            //ExecutionManager.getInstance().addCommand(forCommand);
            visitChildren(ctx);

            StatementController.getInstance().compileControlledCommand();

        }

        System.out.println("EXIT FOR COMMAND");

        return null;
    }

    @Override
    public ClypsValue visitForInit(ClypsParser.ForInitContext ctx) {

        List<Integer> dummy = null;
        String type = ctx.unannType().get(0).getText();
        String name = ctx.variableDeclaratorList().variableDeclarator(0).variableDeclaratorId().getText();
        //FIX VAR NOT FOUND
        String value = testingExpression(ctx.variableDeclaratorList().variableDeclarator(0).variableInitializer().getText(),dummy,ctx.start.getLine());
        System.out.println("ENTER FOR INIT");
        System.out.println(type);
        System.out.println(name);
        System.out.println(value);

        if (type.equals("int")){
            if (SymbolTableManager.searchVariableInLocalIterative(name, SymbolTableManager.getInstance().getActiveLocalScope()) == null &&
                    SymbolTableManager.searchVariableInLocalIterative(name, SymbolTableManager.getInstance().getActiveLocalScope().getParent()) == null) {
                System.out.println("VAR NOT FOUND");
                SymbolTableManager.getInstance().getActiveLocalScope().addInitializedVariableFromKeywords(type, name, value);
            }else {
                editor.addCustomError("VARIABLE ALREADY EXISTS IN FOR LOOP", ctx.start.getLine());
            }
        }else {
            editor.addCustomError("TYPE MISMATCH IN FOR LOOP", ctx.start.getLine());
        }

        System.out.println("PRINT ALL VARS");
        SymbolTableManager.getInstance().getActiveLocalScope().printAllVars();
        System.out.println("PRINT ALL VARS");

        return visitChildren(ctx);
    }

    public static String testingExpression(String value, List<Integer> index, int line) {
        System.out.println("START OF TESTING EXPRESSION");
        //String[] test = value.split("[^A-Za-z]+");
        String[] test = value.split("[-+*/\\(\\)&|><=!]+");
        ArrayList<String> vars = new ArrayList<>();
        ArrayList<String> store = new ArrayList<>();
        System.out.println("------");
        for (int i = 0; i < test.length; i++) {
            System.out.println(test[i]);
            if (SymbolTableManager.searchVariableInLocalIterative(test[i], SymbolTableManager.getInstance().getActiveLocalScope()) != null ||
                    SymbolTableManager.searchVariableInLocalIterative(test[i], SymbolTableManager.getInstance().getActiveLocalScope().getParent()) != null ||
                    SymbolTableManager.getInstance().getActiveLocalScope().searchArray(test[i].replaceAll("\\[.*\\]", "")) != null) {
                vars.add(test[i].replaceAll("\\[.*\\]", ""));
                System.out.println(test[i].replaceAll("\\[.*\\]", ""));
            } else if (test[i].matches("[A-Za-z]+") && (!test[i].contains("true") && !test[i].contains("false"))) {
                System.out.println("Hey " + test[i]);
                editor.addCustomError("VARIABLE DOES NOT EXIST", line);
                break;
            }
        }

        System.out.println("NEW LIST");
        for (String print : vars) {
            System.out.println(print);
        }

        System.out.println("START VARS");
        for (int i = 0; i < vars.size(); i++) {
            System.out.println(vars.get(i));
            System.out.println(index);
            if (SymbolTableManager.searchVariableInLocalIterative(vars.get(i), SymbolTableManager.getInstance().getActiveLocalScope()) != null ||
                    SymbolTableManager.searchVariableInLocalIterative(vars.get(i), SymbolTableManager.getInstance().getActiveLocalScope().getParent()) != null ||
                    SymbolTableManager.getInstance().getActiveLocalScope().searchArray(vars.get(i)) != null) {
                System.out.println("VAR FOUND HERE");
                if (index == null) {
                    System.out.println("PROCESS REGULAR");
                    if (SymbolTableManager.getInstance().getActiveLocalScope().searchVariableIncludingLocal(vars.get(i)).getValue() == null) {
                        editor.addCustomError("INCORRECT ASSIGNMENT", line);
                    } else {
                        store.add(SymbolTableManager.getInstance().getActiveLocalScope().searchVariableIncludingLocal(vars.get(i)).getValue().toString());

                    }
                } else {
                    System.out.println("PROCESS ARRAY");
                    store.add(SymbolTableManager.getInstance().getActiveLocalScope().searchArray(vars.get(i))
                            .getValueAt(index.get(i))
                            .getValue().toString());
                }
            }
        }

        System.out.println("SHOW STORED");
        for (String pr : store) {
            System.out.println(pr);
        }

        for (int i = 0; i < store.size(); i++) {
            if (store.size()==1&&!value.contains("\"")){
                value = value.replaceAll(vars.get(i), store.get(i));
            }else
                value = value.replaceAll("(?<=[+])"+vars.get(i), store.get(i));
            if (value.contains("[")) {
                value = value.replaceAll("\\[.*?\\]", "");
            }
        }

        System.out.println("NEW CREATED VALUE");
        System.out.println(value);


        return value;
    }

}
