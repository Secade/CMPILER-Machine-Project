package controller;

import antlr.ClypsBaseVisitor;
import antlr.ClypsParser;
import com.udojava.evalex.Expression;
import commands.*;
import execution.ExecutionManager;
import execution.ExecutionThread;

import java.util.ArrayList;
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
//        System.out.println(ctx.localVariableDeclaration().unannType(0).getText());
//        System.out.println(ctx.localVariableDeclaration().variableDeclaratorList().variableDeclarator().get(0).variableDeclaratorId().getText());
//        System.out.println(ctx.localVariableDeclaration().variableDeclaratorList().variableDeclarator().get(0).variableInitializer().getText());

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

        //if (ExecutionManager.getInstance().isInFunctionExecution()){
            NewVarCommand newVarCommand = new NewVarCommand(ctx);

            StatementController statementControl = StatementController.getInstance();

            if(statementControl.isInConditionalCommand()) {
                IConditionalCommand conditionalCommand = (IConditionalCommand) statementControl.getActiveControlledCommand();

                if(statementControl.isInPositiveRule()) {
                    conditionalCommand.addPositiveCommand(newVarCommand);
                }
                else {
                    //String functionName = ExecutionManager.getInstance().getCurrentFunction().getMethodName();
                    //ExecutionManager.getInstance().getCurrentFunction().setValidReturns(true);
                    conditionalCommand.addNegativeCommand(newVarCommand);
                }
            }

            else if(statementControl.isInControlledCommand()) {
                IControlledCommand controlledCommand = (IControlledCommand) statementControl.getActiveControlledCommand();
                controlledCommand.addCommand(newVarCommand);
            }else {
                //ExecutionManager.getInstance().getCurrentFunction().setValidReturns(true);

                ExecutionManager.getInstance().addCommand(newVarCommand);
            }

            System.out.println("PRINT ALL VARS");
            SymbolTableManager.getInstance().getActiveLocalScope().printAllVars();
            System.out.println("PRINT ALL VARS");
        //}

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
                        System.out.println("CHECK THE TYPE ======?");
                        System.out.println(value);
                        System.out.println(ClypsValue.attemptTypeCast(value,SymbolTableManager.getInstance().getActiveLocalScope().searchVariableIncludingLocal(ctx.variableDeclarator().variableDeclaratorId().Identifier().getText()).getPrimitiveType()));
                        System.out.println(SymbolTableManager.getInstance().getActiveLocalScope().searchVariableIncludingLocal(ctx.variableDeclarator().variableDeclaratorId().Identifier().getText()).getPrimitiveType());
                        System.out.println("CHECK THE TYPE ======?");
                        if (ClypsValue.attemptTypeCast(value,SymbolTableManager.getInstance().getActiveLocalScope().searchVariableIncludingLocal(ctx.variableDeclarator().variableDeclaratorId().Identifier().getText()).getPrimitiveType())!=null)
                            SymbolTableManager.getInstance().getActiveLocalScope().setDeclaredVariable(ctx.variableDeclarator().variableDeclaratorId().Identifier().getText(), value);
                        else
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

        ReassignCommand reassignCommand = new ReassignCommand(ctx);

        StatementController statementControl = StatementController.getInstance();

        if(statementControl.isInConditionalCommand()) {
            IConditionalCommand conditionalCommand = (IConditionalCommand) statementControl.getActiveControlledCommand();

            if(statementControl.isInPositiveRule()) {
                conditionalCommand.addPositiveCommand(reassignCommand);
            }
            else {
                //String functionName = ExecutionManager.getInstance().getCurrentFunction().getMethodName();
                //ExecutionManager.getInstance().getCurrentFunction().setValidReturns(true);
                conditionalCommand.addNegativeCommand(reassignCommand);
            }
        }

        else if(statementControl.isInControlledCommand()) {
            IControlledCommand controlledCommand = (IControlledCommand) statementControl.getActiveControlledCommand();
            controlledCommand.addCommand(reassignCommand);
        }else {
            //ExecutionManager.getInstance().getCurrentFunction().setValidReturns(true);

            ExecutionManager.getInstance().addCommand(reassignCommand);
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

        BlankVarCommand blankVarCommand = new BlankVarCommand(ctx);

        StatementController statementControl = StatementController.getInstance();

        if(statementControl.isInConditionalCommand()) {
            IConditionalCommand conditionalCommand = (IConditionalCommand) statementControl.getActiveControlledCommand();

            if(statementControl.isInPositiveRule()) {
                conditionalCommand.addPositiveCommand(blankVarCommand);
            }
            else {
                //String functionName = ExecutionManager.getInstance().getCurrentFunction().getMethodName();
                //ExecutionManager.getInstance().getCurrentFunction().setValidReturns(true);
                conditionalCommand.addNegativeCommand(blankVarCommand);
            }
        }

        else if(statementControl.isInControlledCommand()) {
            IControlledCommand controlledCommand = (IControlledCommand) statementControl.getActiveControlledCommand();
            controlledCommand.addCommand(blankVarCommand);
        }else {
            //ExecutionManager.getInstance().getCurrentFunction().setValidReturns(true);

            ExecutionManager.getInstance().addCommand(blankVarCommand);
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
    public ClypsValue visitMethodInvocation(ClypsParser.MethodInvocationContext ctx) {
        System.out.println("ENTER FUNCTION INVOCATION");

        FunctionCallCommand functionCallCommand = new FunctionCallCommand(ctx);

        StatementController statementControl = StatementController.getInstance();

        if (statementControl.isInConditionalCommand()) {
            IConditionalCommand conditionalCommand = (IConditionalCommand) statementControl.getActiveControlledCommand();

            if (statementControl.isInPositiveRule()) {
                conditionalCommand.addPositiveCommand(functionCallCommand);
            } else {
                conditionalCommand.addNegativeCommand(functionCallCommand);
            }
        } else if (statementControl.isInControlledCommand()) {
            IControlledCommand controlledCommand = (IControlledCommand) statementControl.getActiveControlledCommand();
            controlledCommand.addCommand(functionCallCommand);
        } else {
            ExecutionManager.getInstance().addCommand(functionCallCommand);
        }



        return null;
    }

    @Override
    public ClypsValue visitMethodDeclaration(ClypsParser.MethodDeclarationContext ctx) {
        System.out.println("ENTER FUNCTION");
        System.out.println(ctx.getText());
        System.out.println(ctx.methodHeader().result().getText());
        System.out.println(ctx.methodHeader().methodDeclarator().Identifier().getText());
        Scope temp = SymbolTableManager.getInstance().getActiveLocalScope();

        if (SymbolTableManager.getInstance().functionLookup(ctx.methodHeader().methodDeclarator().Identifier().getText()) == null) {
            ClypsFunction function = new ClypsFunction();
            SymbolTableManager.getInstance().addFunction(ctx.methodHeader().methodDeclarator().Identifier().getText(), function);

            Scope scope = new Scope();
            function.setParentScope(scope);
            function.setReturnValue(function.identifyFunctionType(ctx.methodHeader().result().getText()));
            if (ctx.methodHeader().methodDeclarator().formalParameters()!=null){
                //String[] params = ctx.methodHeader().methodDeclarator().formalParameters().formalParameter();
                for (int i=0;i<ctx.methodHeader().methodDeclarator().formalParameters().formalParameter().size();i++){
                    System.out.println(ctx.methodHeader().methodDeclarator().formalParameters().formalParameter().get(i).unannType().getText());
                    System.out.println(ctx.methodHeader().methodDeclarator().formalParameters().formalParameter().get(i).variableDeclaratorId().Identifier().getText());
                    ClypsValue value = new ClypsValue();
                    value.setValue("-1");
                    //System.out.println("TYPE");
                    //System.out.println(ClypsValue.translateType(ctx.methodHeader().methodDeclarator().formalParameters().formalParameter().get(i).unannType().getText()));
                    value.setType(ClypsValue.translateType(ctx.methodHeader().methodDeclarator().formalParameters().formalParameter().get(i).unannType().getText()));
                    System.out.println(value.getValue());
                    System.out.println(value.getPrimitiveType());
                    function.addParameter(ctx.methodHeader().methodDeclarator().formalParameters().formalParameter().get(i).variableDeclaratorId().Identifier().getText(),value);

                    function.getParentScope().addEmptyVariableFromKeywords(
                                    ctx.methodHeader().methodDeclarator().formalParameters().formalParameter().get(i).unannType().getText()
                                    ,ctx.methodHeader().methodDeclarator().formalParameters().formalParameter().get(i).variableDeclaratorId().Identifier().getText());
                }
            }else {
                System.out.println("EMPTY PARAMS");

            }

            ExecutionManager.getInstance().openFunctionExecution(function);

            System.out.println("PRINT PARAMS");
            function.printParams();
            System.out.println("PRINT PARAMS");



            visitChildren(ctx);
        } else {
            editor.addCustomError("DUPLICATE FUNCTION DETECTED", ctx.start.getLine());
        }

        System.out.println("PRINT ALL FUNCTION");
        SymbolTableManager.getInstance().printAllFunctions();

        if (!ExecutionManager.getInstance().getCurrentFunction().isReturned&&ExecutionManager.getInstance().getCurrentFunction().getReturnType()!= ClypsFunction.FunctionType.VOID_TYPE){
            editor.addCustomError("MISSING RETURN STATEMENT", ctx.stop.getLine());
        }



        ExecutionManager.getInstance().closeFunctionExecution();



        return null;
    }

    @Override
    public ClypsValue visitReturnStatement(ClypsParser.ReturnStatementContext ctx) {

        if (ExecutionManager.getInstance().isInFunctionExecution()){
            ReturnCommand returnCommand = new ReturnCommand(ctx, ExecutionManager.getInstance().getCurrentFunction());
            StatementController statementControl = StatementController.getInstance();

            if(statementControl.isInConditionalCommand()) {
                IConditionalCommand conditionalCommand = (IConditionalCommand) statementControl.getActiveControlledCommand();

                if(statementControl.isInPositiveRule()) {
                    conditionalCommand.addPositiveCommand(returnCommand);
                }
                else {
                    String functionName = ExecutionManager.getInstance().getCurrentFunction().getMethodName();
                    ExecutionManager.getInstance().getCurrentFunction().setValidReturns(true);
                    conditionalCommand.addNegativeCommand(returnCommand);
                }
            }

            else if(statementControl.isInControlledCommand()) {
                IControlledCommand controlledCommand = (IControlledCommand) statementControl.getActiveControlledCommand();
                controlledCommand.addCommand(returnCommand);
            }else {
                ExecutionManager.getInstance().getCurrentFunction().setValidReturns(true);

                ExecutionManager.getInstance().addCommand(returnCommand);
            }
        }else {
            editor.addCustomError("INVALID USE OF RETURN STATEMENT", ctx.start.getLine());
        }

        return visitChildren(ctx);
    }

    @Override
    public ClypsValue visitPrintStatement(ClypsParser.PrintStatementContext ctx) {
        System.out.println("Added Print Command");

        PrintCommand printCommand = new PrintCommand(ctx);

        StatementController statementControl = StatementController.getInstance();

        //System.out.println(statementControl.getActiveControlledCommand());

        String value="";

        if (ctx.printBlock().printExtra().arrayCall()!=null){
            List<Integer> matchList = new ArrayList<Integer>();
            Pattern regex = Pattern.compile("\\[(.*?)\\]");
            System.out.println(ctx.printBlock().getText());
            Matcher regexMatcher = regex.matcher(ctx.printBlock().getText());

            while (regexMatcher.find()) {//Finds Matching Pattern in String
                matchList.add(Integer.parseInt(regexMatcher.group(1).trim()));//Fetching Group from String
            }
            value = ClypsCustomVisitor.testingExpression(ctx.printBlock().getText(),matchList,ctx.start.getLine());
        }else {
            List<Integer> dummy = null;
            value = ClypsCustomVisitor.testingExpression(ctx.printBlock().getText(),dummy,ctx.start.getLine());
        }

        if (statementControl.isInConditionalCommand()) {
            System.out.println("PRINT IN CONDITIONAL");
            IConditionalCommand conditionalCommand = (IConditionalCommand) statementControl.getActiveControlledCommand();

            if (statementControl.isInPositiveRule()) {
                conditionalCommand.addPositiveCommand(printCommand);
            } else {
                conditionalCommand.addNegativeCommand(printCommand);
            }
        } else if (statementControl.isInControlledCommand()) {
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
        System.out.println("INPUT: " + editor.getInput());


        return visitChildren(ctx);
    }

    @Override
    public ClypsValue visitIncDecStatement(ClypsParser.IncDecStatementContext ctx) {
        System.out.println("ENTER INC DEC COMMAND");
        String name = ctx.getText().replaceAll("\\+", "").replaceAll("-", "").replaceAll(";", "");
        name = name.replaceAll("\\[.*\\]", "");
        if (SymbolTableManager.searchVariableInLocalIterative(name, SymbolTableManager.getInstance().getActiveLocalScope()) != null ||
                SymbolTableManager.searchVariableInLocalIterative(name, SymbolTableManager.getInstance().getActiveLocalScope().getParent()) != null ||
                SymbolTableManager.getInstance().getActiveLocalScope().searchArray(name.replaceAll("\\[.*\\]", "")) != null) {
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
                //SymbolTableManager.getInstance().getActiveLocalScope().getParent().searchVariableIncludingLocal(name).getPrimitiveType()
                if (SymbolTableManager.searchVariableInLocalIterative(name, SymbolTableManager.getInstance().getActiveLocalScope()).getPrimitiveType() == ClypsValue.PrimitiveType.INT ||
                        SymbolTableManager.searchVariableInLocalIterative(name, SymbolTableManager.getInstance().getActiveLocalScope()).getPrimitiveType() == ClypsValue.PrimitiveType.DOUBLE ||
                        SymbolTableManager.searchVariableInLocalIterative(name, SymbolTableManager.getInstance().getActiveLocalScope()).getPrimitiveType() == ClypsValue.PrimitiveType.FLOAT) {
                    //SymbolTableManager.getInstance().getActiveLocalScope().getParent().searchVariableIncludingLocal(name).getPrimitiveType()
                    incDecCommand = new IncDecCommand(ctx, name, check, SymbolTableManager.searchVariableInLocalIterative(name, SymbolTableManager.getInstance().getActiveLocalScope()).getPrimitiveType());
                } else {
                    editor.addCustomError("CAN ONLY INC/DEC A NUMBER", ctx.start.getLine());
                }
            }
            StatementController statementControl = StatementController.getInstance();

            if (incDecCommand != null) {
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


        } else {
            editor.addCustomError("VARIABLE DOES NOT EXIST", ctx.start.getLine());
        }

        System.out.println("PRINT ALL VARS");
        SymbolTableManager.getInstance().getActiveLocalScope().printAllVars();
        System.out.println("PRINT ALL VARS");

        return visitChildren(ctx);
    }

    @Override
    public ClypsValue visitWhileStatement(ClypsParser.WhileStatementContext ctx) {
        System.out.println("ENTER WHILE COMMAND");
        System.out.println(ctx.conditionalExpression().getText());
        List<Integer> dummy = null;

        String value = testingExpression(ctx.conditionalExpression().getText(), dummy, ctx.start.getLine());
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
            editor.addCustomError("BOOLEAN STATEMENT REQUIRED", ctx.start.getLine());
        }

        if (!test && value.contains("true") || value.contains("false"))
            test = true;

        if (test) {
            WhileCommand whileCommand = new WhileCommand(ctx);
            StatementController.getInstance().openControlledCommand(whileCommand);

            visitChildren(ctx);

            StatementController.getInstance().compileControlledCommand();
        }else {
            editor.addCustomError("BOOLEAN STATEMENT REQUIRED", ctx.start.getLine());
        }



        System.out.println("EXIT WHILE COMMAND");

        return null;
    }


    @Override
    public ClypsValue visitForStatement(ClypsParser.ForStatementContext ctx) {
        System.out.println("ENTER FOR COMMAND");
        List<Integer> dummy = null;
        String start = ClypsCustomVisitor.testingExpression(ctx.forInit().variableDeclaratorList().variableDeclarator(0).variableInitializer().getText(), dummy, ctx.start.getLine());
        String end = ClypsCustomVisitor.testingExpression(ctx.assignmentExpression().getText(), dummy, ctx.start.getLine());
        int counter = Integer.parseInt(new Expression(start).eval().toPlainString());
        int stop = Integer.parseInt(new Expression(end).eval().toPlainString());

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
        String value = testingExpression(ctx.variableDeclaratorList().variableDeclarator(0).variableInitializer().getText(), dummy, ctx.start.getLine());
        System.out.println("ENTER FOR INIT");
        System.out.println(type);
        System.out.println(name);
        System.out.println(value);

        if (type.equals("int")) {
            if (SymbolTableManager.searchVariableInLocalIterative(name, SymbolTableManager.getInstance().getActiveLocalScope()) == null &&
                    SymbolTableManager.searchVariableInLocalIterative(name, SymbolTableManager.getInstance().getActiveLocalScope().getParent()) == null) {
                System.out.println("VAR NOT FOUND");
                SymbolTableManager.getInstance().getActiveLocalScope().addInitializedVariableFromKeywords(type, name, value);
            } else {
                editor.addCustomError("VARIABLE ALREADY EXISTS IN FOR LOOP", ctx.start.getLine());
            }
        } else {
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
            if(SymbolTableManager.searchVariableInLocalIterative(test[i], SymbolTableManager.getInstance().getActiveLocalScope()) != null ||
                                SymbolTableManager.searchVariableInLocalIterative(test[i], SymbolTableManager.getInstance().getActiveLocalScope().getParent()) != null){
                System.out.println("FOUND1");
                vars.add(test[i].replaceAll("\\[.*\\]", ""));
            }else if (SymbolTableManager.getInstance().getActiveLocalScope().searchVariableIncludingLocal(test[i]) != null ||
                    SymbolTableManager.getInstance().getActiveLocalScope().searchVariableIncludingLocal(test[i]) != null ||
                    SymbolTableManager.getInstance().getActiveLocalScope().searchArray(test[i].replaceAll("\\[.*\\]", "")) != null) {
                System.out.println("FOUND2");
                vars.add(test[i].replaceAll("\\[.*\\]", ""));
                System.out.println(test[i].replaceAll("\\[.*\\]", ""));
            } else if (ExecutionManager.getInstance().isInFunctionExecution()){
                System.out.println("did it get in?");
                System.out.println(test[i]);
                System.out.println(ExecutionManager.getInstance().getCurrentFunction().getParentScope().searchVariableIncludingLocal(test[i]));

                if (ExecutionManager.getInstance().getCurrentFunction().getParentScope().searchVariableIncludingLocal(test[i])!=null){
                    System.out.println("FUNCTION EXP TEST");
                    System.out.println(test[i].replaceAll("\\[.*\\]", ""));
                    vars.add(test[i].replaceAll("\\[.*\\]", ""));
                }else{
                    for (int j =0;j<ExecutionManager.getInstance().getCurrentFunction().getParameterValueSize();j++){
                        if (ExecutionManager.getInstance().getCurrentFunction().getParametername(j).equals(test[i])){
                            vars.add(test[i].replaceAll("\\[.*\\]", ""));
                        }
                    }
                }
            } else if (test[i].matches("[A-Za-z]+") && (!test[i].contains("true") && !test[i].contains("false"))) {
                System.out.println("Hey " + test[i]);

                System.out.println("TOLD YOU SO");
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
            if (SymbolTableManager.getInstance().getActiveLocalScope().searchVariableIncludingLocal(vars.get(i)) != null ||
                    SymbolTableManager.getInstance().getActiveLocalScope().searchVariableIncludingLocal(vars.get(i)) != null ||
                    SymbolTableManager.getInstance().getActiveLocalScope().searchArray(vars.get(i)) != null) {
                System.out.println("VAR FOUND HERE2");
                if (index == null) {
                    System.out.println("PROCESS REGULAR");
                    System.out.println("PRINT ALL VARS");
                    SymbolTableManager.getInstance().getActiveLocalScope().printAllVars();
                    System.out.println("PRINT ALL VARS");
                    if (SymbolTableManager.getInstance().getActiveLocalScope().searchVariableIncludingLocal(vars.get(i)) == null) {
                        editor.addCustomError("INCORRECT ASSIGNMENT", line);
                    } else {
                        if (ExecutionManager.getInstance().isInFunctionExecution()){
                            System.out.println("FUNCTION TESTING VALUE =+-+");
                            System.out.println(ExecutionManager.getInstance().getCurrentFunction().getParameterAt(1));
                            System.out.println(SymbolTableManager.getInstance().getActiveLocalScope().searchVariableIncludingLocal(vars.get(i)).getValue());
                            if (ExecutionManager.getInstance().getCurrentFunction().getParentScope().searchVariableIncludingLocal(vars.get(i))!=null){
                                System.out.println("?.?");
                                System.out.println(ExecutionManager.getInstance().getCurrentFunction().getParentScope().searchVariableIncludingLocal(vars.get(i)).getValue().toString());
                                store.add(ExecutionManager.getInstance().getCurrentFunction().getParentScope().searchVariableIncludingLocal(vars.get(i)).getValue().toString());
                            }


                        }else {
                            store.add(SymbolTableManager.getInstance().getActiveLocalScope().searchVariableIncludingLocal(vars.get(i)).getValue().toString());

                        }
                    }
                } else {
                    System.out.println("PROCESS ARRAY");
                    store.add(SymbolTableManager.getInstance().getActiveLocalScope().searchArray(vars.get(i)).getValueAt(index.get(i)).getValue().toString());
                }
            } else if (ExecutionManager.getInstance().isInFunctionExecution()){
                System.out.println("FUNCTION TESTING VALUE =+-+");
//                    if (ExecutionManager.getInstance().getCurrentFunction().getParentScope().searchVariableIncludingLocal(vars.get(i))!=null){
//                        System.out.println("?.?");
//                        System.out.println(ExecutionManager.getInstance().getCurrentFunction().getParentScope().searchVariableIncludingLocal(vars.get(i)).getValue().toString());
//
//                    }else {
                        for (int j =0;j<ExecutionManager.getInstance().getCurrentFunction().getParameterValueSize();j++){
                            if (ExecutionManager.getInstance().getCurrentFunction().getParametername(j).equals(vars.get(i))){
                                store.add(ExecutionManager.getInstance().getCurrentFunction().getParameterAt(j).getValue().toString());
                            }
                        }
//                    }


            }else if(SymbolTableManager.searchVariableInLocalIterative(vars.get(i), SymbolTableManager.getInstance().getActiveLocalScope()) != null ||
                    SymbolTableManager.searchVariableInLocalIterative(vars.get(i), SymbolTableManager.getInstance().getActiveLocalScope().getParent()) != null){
                System.out.println("VAR FOUND 1");
                System.out.println(SymbolTableManager.searchVariableInLocalIterative(vars.get(i), SymbolTableManager.getInstance().getActiveLocalScope()).getValue());
                if (index == null) {
                    System.out.println("PROCESS REGULAR");
                    System.out.println("PRINT ALL VARS");
                    //SymbolTableManager.getInstance().getActiveLocalScope().getParent().printAllVars();
                    System.out.println("PRINT ALL VARS");
                    if (SymbolTableManager.searchVariableInLocalIterative(vars.get(i), SymbolTableManager.getInstance().getActiveLocalScope()) == null) {
                        editor.addCustomError("INCORRECT ASSIGNMENT", line);
                    } else {
                        System.out.println("WE IN");
                        store.add(SymbolTableManager.searchVariableInLocalIterative(vars.get(i), SymbolTableManager.getInstance().getActiveLocalScope()).getValue().toString());
                    }
                } else {
                    System.out.println("PROCESS ARRAY");
                    store.add(SymbolTableManager.getInstance().getActiveLocalScope().searchArray(vars.get(i)).getValueAt(index.get(i)).getValue().toString());
                }
            }else  {
                System.out.println("this var not found?");
                editor.addCustomError("VAR NOT FOUND", line);
            }
        }

        System.out.println("SHOW STORED");
        for (String pr : store) {
            System.out.println(pr);
        }

        for (int i = 0; i < store.size(); i++) {
            if (store.size() == 1 && !value.contains("\"")) {
                value = value.replaceAll(vars.get(i), store.get(i));
            } else
                value = value.replaceAll("(?<=[+])" + vars.get(i), store.get(i));
            if (value.contains("[")) {
                value = value.replaceAll("\\[.*?\\]", "");
            }
        }

        System.out.println("NEW CREATED VALUE");
        System.out.println(value);


        return value;
    }

}