package controller;

import antlr.ClypsBaseVisitor;
import antlr.ClypsLexer;
import antlr.ClypsParser;
import com.udojava.evalex.Expression;
import commands.*;
import execution.ExecutionManager;
import org.antlr.v4.runtime.tree.TerminalNode;
import sun.awt.Symbol;
import execution.ExecutionThread;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ClypsCustomVisitor extends ClypsBaseVisitor<ClypsValue> {

    @Override
    public ClypsValue visitLocalVariableDeclarationStatement(ClypsParser.LocalVariableDeclarationStatementContext ctx) {
        //System.out.print("NEW VAR");

        visitChildren(ctx);

        //System.out.print(ctx.getText());

//        if (ctx.localVariableDeclaration().unannType(0)==null&&ctx.localVariableDeclaration().expression()!=null){
//            return null;
//        }
        String type = ctx.localVariableDeclaration().unannType(0).getText();
        if (ctx.localVariableDeclaration().unannType().size() > 1)
            return visitChildren(ctx);
        String name = ctx.localVariableDeclaration().variableDeclaratorList().variableDeclarator().get(0).variableDeclaratorId().getText();
        String value1 = ctx.localVariableDeclaration().variableDeclaratorList().variableDeclarator().get(0).variableInitializer().getText();
//        //System.out.print(ctx.localVariableDeclaration().unannType(0).getText());
//        //System.out.print(ctx.localVariableDeclaration().variableDeclaratorList().variableDeclarator().get(0).variableDeclaratorId().getText());
//        //System.out.print(ctx.localVariableDeclaration().variableDeclaratorList().variableDeclarator().get(0).variableInitializer().getText());

        //System.out.print("----");
        List<Integer> dummy = null;
        String value = testingExpression(value1, dummy, ctx.start.getLine());

        if (SymbolTableManager.searchVariableInLocalIterative(name, SymbolTableManager.getInstance().getActiveLocalScope()) == null &&
                SymbolTableManager.searchVariableInLocalIterative(name, SymbolTableManager.getInstance().getActiveLocalScope().getParent()) == null) {
            //System.out.print("VAR NOT FOUND");
            boolean test1 = value.contains("\"");
            boolean test2 = value.contains("'");

            //System.out.print(test2);
            //System.out.print(type);
            //System.out.print(ClypsValue.translateType(type));
            if (ClypsValue.translateType(type) != ClypsValue.PrimitiveType.STRING && !test1 && ClypsValue.translateType(type) != ClypsValue.PrimitiveType.CHAR && !test2) {
                //System.out.print(value);
                value = testingExpression(value, dummy, ctx.start.getLine());
                //System.out.print(value);
                if (value.contains("f") && !value.contains("false"))
                    value = value.replaceAll("f", "");
                if (value.contains("!")) {
                    value = value.replaceAll("!", "not");
                }
                //System.out.print("((((((((");
                boolean test = false;
                try {
                    test = new Expression(value).isBoolean();
                } catch (Expression.ExpressionException e) {
                    //editor.addCustomError("DIS ONE?", ctx.start.getLine());
                    //editor.addCustomError("TYPE MISMATCH", ctx.start.getLine());
                }

                if (!test && value.contains("true") || value.contains("false"))
                    test = true;
                //System.out.print("====");
                //System.out.print(test);
                if (ClypsValue.translateType(type) == ClypsValue.PrimitiveType.BOOLEAN && test) {
                    //System.out.print("IS BOOLEAN");
                    SymbolTableManager.getInstance().getActiveLocalScope().addInitializedVariableFromKeywords(type, name, value);
                    if (!ctx.localVariableDeclaration().variableModifier().isEmpty()) {
                        if (ctx.localVariableDeclaration().variableModifier().get(0).getText().contains("final")) {
                            SymbolTableManager.getInstance().getActiveLocalScope().searchVariableIncludingLocal(name).markFinal();
                        }
                    }
                } else if (ClypsValue.translateType(type) != ClypsValue.PrimitiveType.BOOLEAN && (test || value.contains("true") || value.contains("false"))) {
                    //System.out.print("NOT BOOLEAN");
                    editor.addCustomError("TYPE MISMATCH 1", ctx.start.getLine());
                } else if (ClypsValue.translateType(type) != ClypsValue.PrimitiveType.BOOLEAN && !test) {
                    //System.out.print("IS DECIMAL");
                    //System.out.print("JUAN: "+value);
                    //System.out.print("TWU: "+ClypsValue.translateType(type));
                    //System.out.print("TREE:" +ClypsValue.checkValueType(ClypsValue.attemptTypeCast(value,ClypsValue.translateType(type)),ClypsValue.translateType(type)));
                    //System.out.print("IS DECCIII");
                    if (!value.matches(".*[a-zA-Z]+.*")){
                        value = new Expression(value).eval().toPlainString();
                        //System.out.print("NEW VALUE HERE: "+value);
                    }
                    if (ClypsValue.checkValueType(ClypsValue.attemptTypeCast(value,ClypsValue.translateType(type)),ClypsValue.translateType(type))){
                        SymbolTableManager.getInstance().getActiveLocalScope().addInitializedVariableFromKeywords(type, name, value);

                        if (!ctx.localVariableDeclaration().variableModifier().isEmpty()) {
                            if (ctx.localVariableDeclaration().variableModifier().get(0).getText().contains("final")) {
                                SymbolTableManager.getInstance().getActiveLocalScope().searchVariableIncludingLocal(name).markFinal();
                            }
                        }
                    }else if(value.matches(".*[a-zA-Z]+.*")){

                    }else
                        editor.addCustomError("TYPE MISMATCH 2", ctx.start.getLine());

                } else {
                    editor.addCustomError("TYPE MISMATCH 3", ctx.start.getLine());
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
                editor.addCustomError("TYPE MISMATCH 4", ctx.start.getLine());
            }

        } else {
            editor.addCustomError("DUPLICATE VAR DETECTED", ctx.start.getLine());
        }

        //if (ExecutionManager.getInstance().isInFunctionExecution()){
        NewVarCommand newVarCommand = new NewVarCommand(ctx);

        StatementController statementControl = StatementController.getInstance();

        if (statementControl.isInConditionalCommand()) {
            IConditionalCommand conditionalCommand = (IConditionalCommand) statementControl.getActiveControlledCommand();

            if (statementControl.isInPositiveRule()) {
                conditionalCommand.addPositiveCommand(newVarCommand);
            } else {
                //String functionName = ExecutionManager.getInstance().getCurrentFunction().getMethodName();
                //ExecutionManager.getInstance().getCurrentFunction().setValidReturns(true);
                conditionalCommand.addNegativeCommand(newVarCommand);
            }
        } else if (statementControl.isInControlledCommand()) {
            IControlledCommand controlledCommand = (IControlledCommand) statementControl.getActiveControlledCommand();
            controlledCommand.addCommand(newVarCommand);
        } else {
            //ExecutionManager.getInstance().getCurrentFunction().setValidReturns(true);

            ExecutionManager.getInstance().addCommand(newVarCommand);
        }

        //System.out.print("PRINT ALL VARS");
        //SymbolTableManager.getInstance().getActiveLocalScope().printAllVars();
        //System.out.print("PRINT ALL VARS");
        //}

        return null;
    }

    @Override
    public ClypsValue visitVariableDeclarationStatement(ClypsParser.VariableDeclarationStatementContext ctx) {
        ////System.out.print(ctx.variableDeclarator().variableDeclaratorId().Identifier());
        //System.out.print("REASSINING PART");

        visitChildren(ctx);
        if (ctx.variableDeclarator().variableDeclaratorId().getText().contains("[")) {
            List<Integer> dummy = null;
            //System.out.print(ctx.variableDeclarator().variableDeclaratorId().Identifier().getText());
            //System.out.print(ctx.variableDeclarator().variableInitializer().getText());
            //System.out.print("DEM BOI");
            //System.out.print(ctx.variableDeclarator().variableDeclaratorId().expression().getText());
            int index = 0;
            try {
                index = Integer.parseInt(testingExpression(ctx.variableDeclarator().variableDeclaratorId().expression().getText(), dummy, ctx.start.getLine()));
            } catch (NumberFormatException e) {

            }
            //System.out.print(index);
            //System.out.print("CHECK POINT");
            String value = testingExpression(ctx.variableDeclarator().variableInitializer().getText(), dummy, ctx.start.getLine());
            //System.out.print(SymbolTableManager.getInstance().getActiveLocalScope().searchArray(ctx.variableDeclarator().variableDeclaratorId().Identifier().getText()));
            //System.out.print(SymbolTableManager.getInstance().getActiveLocalScope().getParent().searchArray(ctx.variableDeclarator().variableDeclaratorId().Identifier().getText()));
            if (SymbolTableManager.getInstance().getActiveLocalScope().searchArray(ctx.variableDeclarator().variableDeclaratorId().Identifier().getText()) != null) {
                //System.out.print("WE IN");
                ClypsArray te = SymbolTableManager.getInstance().getActiveLocalScope().searchArray(ctx.variableDeclarator().variableDeclaratorId().Identifier().getText());
                ClypsValue temp = new ClypsValue();
                temp.setType(te.getPrimitiveType());
                temp.setValue(value);
                //System.out.print("FOR CHECKING");
                //System.out.print(temp.getValue());
                //System.out.print(temp.getPrimitiveType());
                if (index >= te.getSize() || index <= -1) {
                    editor.addCustomError("ARRAY OUT OF BOUNDS", ctx.start.getLine());
                } else {
                    if (ClypsValue.attemptTypeCast(value, SymbolTableManager.getInstance().getActiveLocalScope().searchArray(ctx.variableDeclarator().variableDeclaratorId().Identifier().getText()).getPrimitiveType()) != null)
                        SymbolTableManager.getInstance().getActiveLocalScope().searchArray(ctx.variableDeclarator().variableDeclaratorId().Identifier().getText()).updateValueAt(temp, index);
                    else
                        editor.addCustomError("TYPE MISMATCH", ctx.start.getLine());
                }
            }else if (SymbolTableManager.getInstance().getActiveLocalScope().getParent().searchArray(ctx.variableDeclarator().variableDeclaratorId().Identifier().getText()) != null) {
                //System.out.print("WE IN");
                ClypsArray te = SymbolTableManager.getInstance().getActiveLocalScope().getParent().searchArray(ctx.variableDeclarator().variableDeclaratorId().Identifier().getText());
                ClypsValue temp = new ClypsValue();
                temp.setType(te.getPrimitiveType());
                temp.setValue(value);
                //System.out.print("FOR CHECKING");
                //System.out.print(temp.getValue());
                //System.out.print(temp.getPrimitiveType());
                if (index >= te.getSize() || index <= -1) {
                    editor.addCustomError("ARRAY OUT OF BOUNDS", ctx.start.getLine());
                } else {
                    if (ClypsValue.attemptTypeCast(value, SymbolTableManager.getInstance().getActiveLocalScope().getParent().searchArray(ctx.variableDeclarator().variableDeclaratorId().Identifier().getText()).getPrimitiveType()) != null)
                        SymbolTableManager.getInstance().getActiveLocalScope().getParent().searchArray(ctx.variableDeclarator().variableDeclaratorId().Identifier().getText()).updateValueAt(temp, index);
                    else
                        editor.addCustomError("TYPE MISMATCH", ctx.start.getLine());
                }
            } else {
                ////System.out.print("DUPLICATE VAR");
                //HAJIAWODJAWOLDJHNAWLIKODHJNAWDLOK
                //editor.addCustomError("VAR DOES NOT EXIST 1 "+ctx.variableDeclarator().variableDeclaratorId().Identifier().getText(), ctx.start.getLine());
                ////System.out.print(editor.errors.get(editor.errors.size()-1));
            }
//            //System.out.print("PRINT ALL ARRAYS");
//            SymbolTableManager.getInstance().getActiveLocalScope().printAllArrays();
//            //System.out.print("PRINT ALL VALUES");
//            SymbolTableManager.getInstance().getActiveLocalScope().printArrayValues();
            //System.out.print("END PRINT");
        } else {
            if (SymbolTableManager.getInstance().getActiveLocalScope().searchVariableIncludingLocal(ctx.variableDeclarator().variableDeclaratorId().Identifier().getText()) != null) {
                if (!SymbolTableManager.getInstance().getActiveLocalScope().searchVariableIncludingLocal(ctx.variableDeclarator().variableDeclaratorId().Identifier().getText()).isFinal()) {
                    if (SymbolTableManager.getInstance().getActiveLocalScope().searchVariableIncludingLocal(ctx.variableDeclarator().variableDeclaratorId().Identifier().getText()) != null) {
                        //System.out.print("REASSIGN");
                        String value;
                        //System.out.print(ctx.variableDeclarator().variableDeclaratorId().getText());
                        List<Integer> dummy = null;
                        if (!ctx.variableDeclarator().variableInitializer().getText().contains("[")) {
                            //System.out.print("not array");
                            value = testingExpression(ctx.variableDeclarator().variableInitializer().getText(), dummy, ctx.start.getLine());
                        } else {
                            //System.out.print("array");
                            List<Integer> matchList = new ArrayList<Integer>();
                            Pattern regex = Pattern.compile("\\[(.*?)\\]");
                            //System.out.print(ctx.variableDeclarator().variableInitializer().getText());
                            Matcher regexMatcher = regex.matcher(ctx.variableDeclarator().variableInitializer().getText());

                            while (regexMatcher.find()) {//Finds Matching Pattern in String
                                matchList.add(Integer.parseInt(regexMatcher.group(1).trim()));//Fetching Group from String
                            }
                            value = testingExpression(ctx.variableDeclarator().variableInitializer().getText(), matchList, ctx.start.getLine());
                        }
                        //System.out.print("CHECK THE TYPE ======?");
                        //CHANGE ME
                        try {
                            value = new Expression(value).eval().toPlainString();
                        }catch (ArithmeticException e){

                        }

                        //System.out.print(value);
                        //System.out.print(ClypsValue.attemptTypeCast(value, SymbolTableManager.getInstance().getActiveLocalScope().searchVariableIncludingLocal(ctx.variableDeclarator().variableDeclaratorId().Identifier().getText()).getPrimitiveType()));
                        //System.out.print(SymbolTableManager.getInstance().getActiveLocalScope().searchVariableIncludingLocal(ctx.variableDeclarator().variableDeclaratorId().Identifier().getText()).getPrimitiveType());
                        //System.out.print("CHECK THE TYPE ======?");
                        if (ClypsValue.attemptTypeCast(value, SymbolTableManager.getInstance().getActiveLocalScope().searchVariableIncludingLocal(ctx.variableDeclarator().variableDeclaratorId().Identifier().getText()).getPrimitiveType()) != null)
                            SymbolTableManager.getInstance().getActiveLocalScope().setDeclaredVariable(ctx.variableDeclarator().variableDeclaratorId().Identifier().getText(), value);
                        //else
                            //editor.addCustomError("TYPE MISMATCH:", ctx.start.getLine());
                    } else {
                        ////System.out.print("DUPLICATE VAR");
                        editor.addCustomError("VAR DOES NOT EXIST 2", ctx.start.getLine());
                        ////System.out.print(editor.errors.get(editor.errors.size()-1));
                    }
                } else {
                    editor.addCustomError("CANNOT CHANGE CONSTANT VARIABLE", ctx.start.getLine());
                }
            }


        }

        ReassignCommand reassignCommand = new ReassignCommand(ctx);

        StatementController statementControl = StatementController.getInstance();

        if (statementControl.isInConditionalCommand()) {
            IConditionalCommand conditionalCommand = (IConditionalCommand) statementControl.getActiveControlledCommand();

            if (statementControl.isInPositiveRule()) {
                conditionalCommand.addPositiveCommand(reassignCommand);
            } else {
                //String functionName = ExecutionManager.getInstance().getCurrentFunction().getMethodName();
                //ExecutionManager.getInstance().getCurrentFunction().setValidReturns(true);
                conditionalCommand.addNegativeCommand(reassignCommand);
            }
        } else if (statementControl.isInControlledCommand()) {
            IControlledCommand controlledCommand = (IControlledCommand) statementControl.getActiveControlledCommand();
            controlledCommand.addCommand(reassignCommand);
        } else {
            //ExecutionManager.getInstance().getCurrentFunction().setValidReturns(true);

            ExecutionManager.getInstance().addCommand(reassignCommand);
        }


        //System.out.print("PRINT ALL VARS");
        //SymbolTableManager.getInstance().getActiveLocalScope().printAllVars();
        //System.out.print("PRINT ALL VARS");
        return null;
    }

    @Override
    public ClypsValue visitVariableNoInit(ClypsParser.VariableNoInitContext ctx) {
        String type = ctx.unannType().getText();
        String name = ctx.variableDeclaratorId().Identifier().getText();

        if (SymbolTableManager.searchVariableInLocalIterative(name, SymbolTableManager.getInstance().getActiveLocalScope()) == null &&
                SymbolTableManager.searchVariableInLocalIterative(name, SymbolTableManager.getInstance().getActiveLocalScope().getParent()) == null) {
            //System.out.print("VAR NOT FOUND");

            //System.out.print(ClypsValue.translateType(type));
            if (ClypsValue.translateType(type) != ClypsValue.PrimitiveType.STRING) {
                if (ClypsValue.translateType(type) == ClypsValue.PrimitiveType.BOOLEAN) {
                    //System.out.print("IS BOOLEAN");
                    SymbolTableManager.getInstance().getActiveLocalScope().addEmptyVariableFromKeywords(type, name);
                } else if (ClypsValue.translateType(type) != ClypsValue.PrimitiveType.BOOLEAN) {
                    //System.out.print("IS DECIMAL");
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

        if (statementControl.isInConditionalCommand()) {
            IConditionalCommand conditionalCommand = (IConditionalCommand) statementControl.getActiveControlledCommand();

            if (statementControl.isInPositiveRule()) {
                conditionalCommand.addPositiveCommand(blankVarCommand);
            } else {
                //String functionName = ExecutionManager.getInstance().getCurrentFunction().getMethodName();
                //ExecutionManager.getInstance().getCurrentFunction().setValidReturns(true);
                conditionalCommand.addNegativeCommand(blankVarCommand);
            }
        } else if (statementControl.isInControlledCommand()) {
            IControlledCommand controlledCommand = (IControlledCommand) statementControl.getActiveControlledCommand();
            controlledCommand.addCommand(blankVarCommand);
        } else {
            //ExecutionManager.getInstance().getCurrentFunction().setValidReturns(true);

            ExecutionManager.getInstance().addCommand(blankVarCommand);
        }

        //System.out.print("PRINT ALL VARS");
        //SymbolTableManager.getInstance().getActiveLocalScope().printAllVars();
        //System.out.print("PRINT ALL VARS");

        return visitChildren(ctx);
    }

    @Override
    public ClypsValue visitArrayCreationExpression(ClypsParser.ArrayCreationExpressionContext ctx) {
        //System.out.print("INIT ARRAY");
        String firstType = ctx.unannArrayType().getText();
        String name = ctx.Identifier().getText();
        String secondType = ctx.unannType().getText();
        List<Integer> dummy = null;
        String size1 = ctx.dimExpr().getText().replaceAll("\\[", "").replaceAll("\\]", "");
        String size2 = testingExpression(size1, dummy, ctx.start.getLine());
        //System.out.print("After DUmmy " + size2);
        //System.out.print(name);
        //System.out.print(SymbolTableManager.getInstance().getActiveLocalScope().searchArray(name));
       //String se = new Expression(size2).eval().toPlainString();
        if (size2.matches("[^A-Za-z]+")) {
            String size = new Expression(size2).eval().toPlainString();
            //System.out.print("LOOK HERE =====aaaa");
            //System.out.print(size);
            if (SymbolTableManager.searchVariableInLocalIterative(name, SymbolTableManager.getInstance().getActiveLocalScope()) == null &&
                    SymbolTableManager.searchVariableInLocalIterative(name, SymbolTableManager.getInstance().getActiveLocalScope().getParent()) == null &&
                    SymbolTableManager.getInstance().getActiveLocalScope().searchArray(name) == null) {
                if (Integer.parseInt(size) > 0) {
                    if (firstType.contains(secondType)) {
                        SymbolTableManager.getInstance().getActiveLocalScope().addArray(secondType, name, size);
                    } else {
                        editor.addCustomError("INVALID TYPE INITIALIZATION", ctx.start.getLine());
                    }
                }else  if(size.contains("-999")){
                    SymbolTableManager.getInstance().getActiveLocalScope().addArray(secondType, name, 1+"");
                } else if(Integer.parseInt(size) <= 0 && !ExecutionManager.getInstance().isInFunctionExecution()) {
                    //HEY YOU FIX THIS
                    //editor.addCustomError("ZERO AND NEGATIVE VALUES ARE NOT ALLOWED", ctx.start.getLine());
                    SymbolTableManager.getInstance().getActiveLocalScope().addArray(secondType, name, 1+"");
                }
            } else {
                editor.addCustomError("ARRAY NAME IN USE", ctx.start.getLine());
            }

        }else if(size2.matches("[A-Za-z]+")){
            //System.out.print("yessing");
        }else {
            editor.addCustomError("INVALID SIZE ALLOTMENT", ctx.start.getLine());
        }


        //System.out.print("PRINT ALL ARRAYS");
        //SymbolTableManager.getInstance().getActiveLocalScope().printAllArrays();
        //System.out.print("PRINT ALL VALUES");
        //SymbolTableManager.getInstance().getActiveLocalScope().printArrayValues();
        //System.out.print("END PRINT");

        ArrayInitializeCommand arrayInitializeCommand = new ArrayInitializeCommand(ctx);

        StatementController statementControl = StatementController.getInstance();

        if (statementControl.isInConditionalCommand()) {
            IConditionalCommand conditionalCommand = (IConditionalCommand) statementControl.getActiveControlledCommand();

            if (statementControl.isInPositiveRule()) {
                conditionalCommand.addPositiveCommand(arrayInitializeCommand);
            } else {

                conditionalCommand.addNegativeCommand(arrayInitializeCommand);
            }
        } else if (statementControl.isInControlledCommand()) {
            IControlledCommand controlledCommand = (IControlledCommand) statementControl.getActiveControlledCommand();
            controlledCommand.addCommand(arrayInitializeCommand);
        } else {

            ExecutionManager.getInstance().addCommand(arrayInitializeCommand);
        }

        return visitChildren(ctx);
    }

    @Override
    public ClypsValue visitIfThenStatement(ClypsParser.IfThenStatementContext ctx) {
        IFCommand ifCommand = new IFCommand(ctx.conditionalExpression());


        StatementController statementControl = StatementController.getInstance();
        StatementController.getInstance().openConditionalCommand(ifCommand);


        visitChildren(ctx);

        StatementController.getInstance().compileControlledCommand();

        statementControl.reportExitPositiveRule();

        return null;
    }

    @Override
    public ClypsValue visitBlock(ClypsParser.BlockContext ctx) {
        //System.out.print("ENTER BLOCK");
        SymbolTableManager.getInstance().openLocalScope();

        visitChildren(ctx);

        SymbolTableManager.getInstance().closeLocalScope();
        //System.out.print("EXIT BLOCK");
        return null;
    }

    @Override
    public ClypsValue visitMethodInvocation(ClypsParser.MethodInvocationContext ctx) {
        //System.out.print("ENTER FUNCTION INVOCATION");

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
        //System.out.print("ENTER FUNCTION");
        //System.out.print(ctx.getText());
        //System.out.print(ctx.methodHeader().result().getText());
        //System.out.print(ctx.methodHeader().methodDeclarator().Identifier().getText());
        Scope temp = SymbolTableManager.getInstance().getActiveLocalScope();

        if (SymbolTableManager.getInstance().functionLookup(ctx.methodHeader().methodDeclarator().Identifier().getText()) == null) {
            ClypsFunction function = new ClypsFunction();
            SymbolTableManager.getInstance().addFunction(ctx.methodHeader().methodDeclarator().Identifier().getText(), function);

            Scope scope = new Scope();
            function.setParentScope(scope);
            function.setReturnValue(function.identifyFunctionType(ctx.methodHeader().result().getText()));
            if (ctx.methodHeader().methodDeclarator().formalParameters() != null) {
                //System.out.print("Has Params");
                //String[] params = ctx.methodHeader().methodDeclarator().formalParameters().formalParameter();
                for (int i = 0; i < ctx.methodHeader().methodDeclarator().formalParameters().formalParameter().size(); i++) {
                    //System.out.print(ctx.methodHeader().methodDeclarator().formalParameters().formalParameter().get(i).unannType().getText());
                    //System.out.print(ctx.methodHeader().methodDeclarator().formalParameters().formalParameter().get(i).variableDeclaratorId().Identifier().getText());
                    ClypsValue value = new ClypsValue();
                    value.setValue("-999");
                    ////System.out.print("TYPE");
                    ////System.out.print(ClypsValue.translateType(ctx.methodHeader().methodDeclarator().formalParameters().formalParameter().get(i).unannType().getText()));
                    value.setType(ClypsValue.translateType(ctx.methodHeader().methodDeclarator().formalParameters().formalParameter().get(i).unannType().getText()));
                    //System.out.print(value.getValue());
                    //System.out.print(value.getPrimitiveType());
                    function.addParameter(ctx.methodHeader().methodDeclarator().formalParameters().formalParameter().get(i).variableDeclaratorId().Identifier().getText(), value);

                    function.getParentScope().addEmptyVariableFromKeywords(
                            ctx.methodHeader().methodDeclarator().formalParameters().formalParameter().get(i).unannType().getText()
                            , ctx.methodHeader().methodDeclarator().formalParameters().formalParameter().get(i).variableDeclaratorId().Identifier().getText());
                }
            } else {
                //System.out.print("EMPTY PARAMS");

            }

            ExecutionManager.getInstance().openFunctionExecution(function);

            //System.out.print("PRINT PARAMS");
            function.printParams();
            //System.out.print("PRINT PARAMS");


            visitChildren(ctx);

            if (!ExecutionManager.getInstance().getCurrentFunction().isReturned &&
                    ExecutionManager.getInstance().getCurrentFunction().getReturnType() != ClypsFunction.FunctionType.VOID_TYPE) {
                editor.addCustomError("MISSING RETURN STATEMENT", ctx.stop.getLine());
            }
        } else {
            editor.addCustomError("DUPLICATE FUNCTION DETECTED", ctx.start.getLine());
        }

        //System.out.print("PRINT ALL FUNCTION");
        //SymbolTableManager.getInstance().printAllFunctions();


        ExecutionManager.getInstance().closeFunctionExecution();

        //to be removed
        //editor.addCustomError("MISSING RETURN PATH", 7);

        return null;
    }

    @Override
    public ClypsValue visitReturnStatement(ClypsParser.ReturnStatementContext ctx) {

        if (ExecutionManager.getInstance().isInFunctionExecution()) {
            ReturnCommand returnCommand = new ReturnCommand(ctx, ExecutionManager.getInstance().getCurrentFunction());
            StatementController statementControl = StatementController.getInstance();

            if (statementControl.isInConditionalCommand()) {
                IConditionalCommand conditionalCommand = (IConditionalCommand) statementControl.getActiveControlledCommand();

                if (statementControl.isInPositiveRule()) {
                    conditionalCommand.addPositiveCommand(returnCommand);
                } else {
                    String functionName = ExecutionManager.getInstance().getCurrentFunction().getMethodName();
                    ExecutionManager.getInstance().getCurrentFunction().setValidReturns(true);
                    conditionalCommand.addNegativeCommand(returnCommand);
                }
            } else if (statementControl.isInControlledCommand()) {
                IControlledCommand controlledCommand = (IControlledCommand) statementControl.getActiveControlledCommand();
                controlledCommand.addCommand(returnCommand);
            } else {
                ExecutionManager.getInstance().getCurrentFunction().setValidReturns(true);

                ExecutionManager.getInstance().addCommand(returnCommand);
            }
        } else {
            editor.addCustomError("INVALID USE OF RETURN STATEMENT", ctx.start.getLine());
        }

        return visitChildren(ctx);
    }

    @Override
    public ClypsValue visitPrintStatement(ClypsParser.PrintStatementContext ctx) {
        //System.out.print("Added Print Command");

        PrintCommand printCommand = new PrintCommand(ctx);

        StatementController statementControl = StatementController.getInstance();

        ////System.out.print(statementControl.getActiveControlledCommand());

        String value = "";
        //if (ctx.printBlock()!=null){
        try {
            if (ctx.printBlock().printExtra().arrayCall()!=null){

                List<Integer> matchList = new ArrayList<Integer>();
                Pattern regex = Pattern.compile("\\[(.*?)\\]");
                //System.out.print(ctx.printBlock().getText());
                Matcher regexMatcher = regex.matcher(ctx.printBlock().getText());
                List<Integer> dummy = null;
                while (regexMatcher.find()) {//Finds Matching Pattern in String
                    matchList.add(Integer.parseInt(ClypsCustomVisitor.testingExpression(regexMatcher.group(1).trim(), dummy, ctx.start.getLine())));//Fetching Group from String
                }
                value = ClypsCustomVisitor.testingExpression(ctx.printBlock().getText(), matchList, ctx.start.getLine());

            }else {
                List<Integer> dummy = null;
                value = ClypsCustomVisitor.testingExpression(ctx.printBlock().getText(), dummy, ctx.start.getLine());
            }
        }catch (NullPointerException e){

        }

        //}


        if (statementControl.isInConditionalCommand()) {
            //System.out.print("PRINT IN CONDITIONAL");
            IConditionalCommand conditionalCommand = (IConditionalCommand) statementControl.getActiveControlledCommand();

            if (statementControl.isInPositiveRule()) {
                conditionalCommand.addPositiveCommand(printCommand);
            } else {
                conditionalCommand.addNegativeCommand(printCommand);
            }
        } else if (statementControl.isInControlledCommand()) {
            //System.out.print("PRINT IN CONTROLLED");
            IControlledCommand controlledCommand = (IControlledCommand) statementControl.getActiveControlledCommand();
            controlledCommand.addCommand(printCommand);
        } else {
            //System.out.print("PRINT IN OPEN ");
            ExecutionManager.getInstance().addCommand(printCommand);
        }

        return visitChildren(ctx);
    }

    @Override
    public ClypsValue visitScanStatement(ClypsParser.ScanStatementContext ctx) {

        //PLACEHOLDER ONLY
//        //System.out.print("INPUT: " + editor.getInput());
        //System.out.print("Added SCAN Command");
        ScanCommand scan;

        if (ctx.scanBlock().arrayCall() == null) {
            //System.out.print("I got in1");
            visitChildren(ctx);
            //System.out.print("HELLO " + ctx.scanBlock().StringLiteral().toString());
            //System.out.print("Hello " + ctx.scanBlock().scanExtra(0).Identifier().toString());
            scan = new ScanCommand(ctx.scanBlock().StringLiteral().toString(), ctx.scanBlock().scanExtra(0).Identifier().toString());
            //scan.execute();
        } else {
            //System.out.print("I got in");
            visitChildren(ctx);
            scan = new ScanCommand(ctx.scanBlock().StringLiteral().toString(), ctx.scanBlock().arrayCall());

            //ExecutionManager.getInstance().addCommand(scan);
        }

        StatementController statementControl = StatementController.getInstance();

            if (statementControl.isInConditionalCommand()) {
                //System.out.print("SCAN IN CONDITIONAL");
                IConditionalCommand conditionalCommand = (IConditionalCommand) statementControl.getActiveControlledCommand();

                if (statementControl.isInPositiveRule()) {
                    conditionalCommand.addPositiveCommand(scan);
                } else {
                    conditionalCommand.addNegativeCommand(scan);
                }
            } else if (statementControl.isInControlledCommand()) {
                //System.out.print("SCAN IN CONTROLLED");
                IControlledCommand controlledCommand = (IControlledCommand) statementControl.getActiveControlledCommand();
                controlledCommand.addCommand(scan);
            } else {
                //System.out.print("SCAN IN OPEN ");
                ExecutionManager.getInstance().addCommand(scan);
            }



        return null;
    }

    @Override
    public ClypsValue visitIncDecStatement(ClypsParser.IncDecStatementContext ctx) {
        //System.out.print("ENTER INC DEC COMMAND");
        String name = ctx.getText().replaceAll("\\+", "").replaceAll("-", "").replaceAll(";", "");
        name = name.replaceAll("\\[.*\\]", "");
        if (SymbolTableManager.searchVariableInLocalIterative(name, SymbolTableManager.getInstance().getActiveLocalScope()) != null ||
                SymbolTableManager.searchVariableInLocalIterative(name, SymbolTableManager.getInstance().getActiveLocalScope().getParent()) != null ||
                SymbolTableManager.getInstance().getActiveLocalScope().searchArray(name.replaceAll("\\[.*\\]", "")) != null) {
            //FIX NULL

            //System.out.print(name);

            String check = "";
            if (ctx.getText().contains("++"))
                check = "pos";
            else if (ctx.getText().contains("--"))
                check = "neg";

            //System.out.print("SIII");
            //System.out.print(name);

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
                    //System.out.print("PRINT IN CONDITIONAL");
                    IConditionalCommand conditionalCommand = (IConditionalCommand) statementControl.getActiveControlledCommand();

                    if (statementControl.isInPositiveRule()) {
                        conditionalCommand.addPositiveCommand(incDecCommand);
                    } else {
                        conditionalCommand.addNegativeCommand(incDecCommand);
                    }
                } else if (statementControl.isInControlledCommand()) {
                    //System.out.print("PRINT IN CONTROLLED");
                    IControlledCommand controlledCommand = (IControlledCommand) statementControl.getActiveControlledCommand();
                    controlledCommand.addCommand(incDecCommand);
                } else {
                    //System.out.print("PRINT IN OPEN ");
                    ExecutionManager.getInstance().addCommand(incDecCommand);
                }
            }


        } else {
            //editor.addCustomError("INCORRECT ASSIGNMENT EXPRESSION: " + name, ctx.start.getLine());
            return null;
        }

        //System.out.print("PRINT ALL VARS");
        // SymbolTableManager.getInstance().getActiveLocalScope().printAllVars();
        //System.out.print("PRINT ALL VARS");

        return visitChildren(ctx);
    }

    @Override
    public ClypsValue visitWhileStatement(ClypsParser.WhileStatementContext ctx) {
        //System.out.print("ENTER WHILE COMMAND");
        //System.out.print(ctx.conditionalExpression().getText());
        List<Integer> dummy = null;

        String value = testingExpression(ctx.conditionalExpression().getText(), dummy, ctx.start.getLine());
        //System.out.print(value);
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
        } else {
            editor.addCustomError("BOOLEAN STATEMENT REQUIRED", ctx.start.getLine());
        }


        //System.out.print("EXIT WHILE COMMAND");

        return null;
    }


    @Override
    public ClypsValue visitForStatement(ClypsParser.ForStatementContext ctx) {
        //System.out.print("ENTER FOR COMMAND");
        List<Integer> dummy = null;
        String start = new Expression(ClypsCustomVisitor.testingExpression(ctx.forInit().variableDeclaratorList().variableDeclarator(0).variableInitializer().getText(), dummy, ctx.start.getLine())).eval().toPlainString();
        String end = new Expression(ClypsCustomVisitor.testingExpression(ctx.assignmentExpression().getText(), dummy, ctx.start.getLine())).eval().toPlainString();
        //System.out.print(start);
        //System.out.print(end);
        //System.out.print("++++++++");
        int counter = -1;
        int stop = -1;
        if (ClypsValue.checkValueType(ClypsValue.attemptTypeCast(start, ClypsValue.PrimitiveType.INT), ClypsValue.PrimitiveType.INT) && ClypsValue.checkValueType(ClypsValue.attemptTypeCast(end, ClypsValue.PrimitiveType.INT), ClypsValue.PrimitiveType.INT)) {
            //System.out.print("pASS");
            SymbolTableManager.getInstance()
                    .getActiveLocalScope()
                    .addInitializedVariableFromKeywords("int",
                            ctx.forInit().variableDeclaratorList().variableDeclarator().get(0).variableDeclaratorId().getText(), start);
            counter = Integer.parseInt(new Expression(start).eval().toPlainString());
            stop = Integer.parseInt(new Expression(end).eval().toPlainString());
        } else {
            editor.addCustomError("FOR LOOP ONLY ACCEPTS INTEGERS", ctx.start.getLine());
        }
        //System.out.print("CONTINUED");


        if ((ctx.forMiddle().getText().contains("up to") && counter > stop) || (ctx.forMiddle().getText().contains("down to") && counter < stop)) {
            //HEY YOU FIX THIS
            //editor.addCustomError("VALUE RANGE IS NOT POSSIBLE", ctx.start.getLine());
        } else {
            //SymbolTableManager.getInstance().getActiveLocalScope().addInitializedVariableFromKeywords();
            ForCommand forCommand = new ForCommand(ctx);
            StatementController.getInstance().openControlledCommand(forCommand);
            //System.out.print(ctx.block().blockStatements().getChildCount());


            //ExecutionManager.getInstance().addCommand(forCommand);
            visitChildren(ctx);

            StatementController.getInstance().compileControlledCommand();

        }

        //System.out.print("EXIT FOR COMMAND");

        return null;
    }

    @Override
    public ClypsValue visitIfThenElseStatement(ClypsParser.IfThenElseStatementContext ctx) {

        IFCommand ifCommand = new IFCommand(ctx.conditionalExpression());


        StatementController.getInstance().openConditionalCommand(ifCommand);
        visitChildren(ctx.block(0));

        StatementController.getInstance().reportExitPositiveRule();

        if (isELSEStatement(ctx)) {

            //System.out.print("CHECKING IF POSITIVE: " + StatementController.getInstance().isInPositiveRule());
            visitChildren(ctx.block(1));
        }
        StatementController.getInstance().compileControlledCommand();


        return null;
    }


    public static String testingExpression(String value, List<Integer> index, int line) {
        //System.out.print("START OF TESTING EXPRESSION");
        //String[] test = value.split("[^A-Za-z]+");
        String[] test = value.split("[-+*/\\(\\)&|><=!]+");
        ArrayList<String> vars = new ArrayList<>();
        ArrayList<String> store = new ArrayList<>();
        //System.out.print("------");

        for (int i = 0; i < test.length; i++) {
            //System.out.print("==--==");
            //System.out.print(test[i]);
            //System.out.print(test[i].replaceAll("\\[.*\\]", ""));
            //System.out.print(SymbolTableManager.getInstance().getActiveLocalScope().searchArray(test[i].replaceAll("\\[.*\\]", "")));
            if (SymbolTableManager.searchVariableInLocalIterative(test[i], SymbolTableManager.getInstance().getActiveLocalScope()) != null ||
                    SymbolTableManager.searchVariableInLocalIterative(test[i], SymbolTableManager.getInstance().getActiveLocalScope().getParent()) != null) {
                //System.out.print("FOUND1");
                vars.add(test[i].replaceAll("\\[.*\\]", ""));
            } else if (SymbolTableManager.getInstance().getActiveLocalScope().searchVariableIncludingLocal(test[i]) != null ||
                    SymbolTableManager.getInstance().getActiveLocalScope().searchVariableIncludingLocal(test[i]) != null ||
                    SymbolTableManager.getInstance().getActiveLocalScope().searchArray(test[i].replaceAll("\\[.*\\]", "")) != null) {
                //System.out.print("FOUND2");
                vars.add(test[i].replaceAll("\\[.*\\]", ""));
                //System.out.print(test[i].replaceAll("\\[.*\\]", ""));
            }
//            else if (SymbolTableManager.getInstance().getActiveLocalScope().getParent().searchArray(test[i].replaceAll("\\[.*\\]", "")) != null) {
//                //System.out.print("FOUND3");
//                vars.add(test[i].replaceAll("\\[.*\\]", ""));
//                //System.out.print(test[i].replaceAll("\\[.*\\]", ""));
//            }
            else if (ExecutionManager.getInstance().isInFunctionExecution()) {
                //System.out.print("did it get in?");
                //System.out.print(test[i]);
                //System.out.print(ExecutionManager.getInstance().getCurrentFunction().getParentScope().searchVariableIncludingLocal(test[i]));

                if (ExecutionManager.getInstance().getCurrentFunction().getParentScope().searchVariableIncludingLocal(test[i]) != null) {
                    //System.out.print("FUNCTION EXP TEST");
                    //System.out.print(test[i].replaceAll("\\[.*\\]", ""));
                    vars.add(test[i].replaceAll("\\[.*\\]", ""));
                } else if (ExecutionManager.getInstance().getCurrentFunction().getParameterValueSize() != 0) {
                    for (int j = 0; j < ExecutionManager.getInstance().getCurrentFunction().getParameterValueSize(); j++) {
                        if (ExecutionManager.getInstance().getCurrentFunction().getParametername(j).equals(test[i])) {
                            vars.add(test[i].replaceAll("\\[.*\\]", ""));
                        }
                    }
                } else if (test[i].matches("[A-Za-z]+") && (!test[i].contains("true") && !test[i].contains("false"))) {
                    //System.out.print("YEP ITS HERE");
                    editor.addCustomError("VARIABLE DOES NOT EXIST " + test[i], line);
                    break;
                }
            } else if (SymbolTableManager.getInstance().getFunctions().containsKey(test[i])) {
                //System.out.print("FOUND LE FUNCTION");
                vars.add(test[i].replaceAll("\\[.*\\]", ""));

            } else if (test[i].matches("[A-Za-z]+") && (!test[i].contains("true") && !test[i].contains("false"))) {
                //System.out.print("Hey " + test[i]);

                //System.out.print("TOLD YOU SO");
                //editor.addCustomError("VARIABLE DOES NOT EXIST " + test[i], line);
                break;
            }
        }

        //System.out.print("NEW LIST");
        for (String print : vars) {
            //System.out.print(print);
        }

        //System.out.print("START VARS");
        for (int i = 0; i < vars.size(); i++) {
            //System.out.print(vars.get(i));
            //System.out.print(index);
            if (SymbolTableManager.getInstance().getActiveLocalScope().searchVariableIncludingLocal(vars.get(i)) != null ||
                    SymbolTableManager.getInstance().getActiveLocalScope().searchVariableIncludingLocal(vars.get(i)) != null ||
                    SymbolTableManager.getInstance().getActiveLocalScope().searchArray(vars.get(i)) != null) {
                //System.out.print("VAR FOUND HERE2");
                if (index == null) {
                    //System.out.print("PROCESS REGULAR");
                    //System.out.print("PRINT ALL VARS");
                    //SymbolTableManager.getInstance().getActiveLocalScope().printAllVars();
                    //System.out.print("PRINT ALL VARS");
                    if (SymbolTableManager.getInstance().getActiveLocalScope().searchVariableIncludingLocal(vars.get(i)) == null) {
                        editor.addCustomError("INCORRECT ASSIGNMENT", line);
                    } else {
                        if (ExecutionManager.getInstance().isInFunctionExecution()) {
                            //System.out.print("FUNCTION TESTING VALUE =+-+");
                            //System.out.print(ExecutionManager.getInstance().getCurrentFunction().getParameterAt(1));
                            //System.out.print(SymbolTableManager.getInstance().getActiveLocalScope().searchVariableIncludingLocal(vars.get(i)).getValue());
                            //ExecutionManager.getInstance().getCurrentFunction().getParentScope().searchVariableIncludingLocal(vars.get(i))!=null
                            if (SymbolTableManager.getInstance().getActiveLocalScope().searchVariableIncludingLocal(vars.get(i)) != null) {
                                //System.out.print("?.?");
                                ////System.out.print(ExecutionManager.getInstance().getCurrentFunction().getParentScope().searchVariableIncludingLocal(vars.get(i)).getValue().toString());
                                if (SymbolTableManager.getInstance().getActiveLocalScope().searchVariableIncludingLocal(vars.get(i)).getValue() != null) {
                                    store.add(SymbolTableManager.getInstance().getActiveLocalScope().searchVariableIncludingLocal(vars.get(i)).getValue().toString());
                                }

                            } else if (SymbolTableManager.getInstance().getActiveLocalScope().getParent().searchVariableIncludingLocal(vars.get(i)) != null) {
                                //System.out.print(":o");
                                store.add(SymbolTableManager.getInstance().getActiveLocalScope().getParent().searchVariableIncludingLocal(vars.get(i)).getValue().toString());
                            } else {
                                //System.out.print("walang nahanap");
                            }


                        } else {
                            try {
                                store.add(SymbolTableManager.getInstance().getActiveLocalScope().searchVariableIncludingLocal(vars.get(i)).getValue().toString());

                            }catch (NullPointerException e){

                            }

                        }
                    }
                } else {
                    //System.out.print("PROCESS ARRAY");
                    //System.out.print(index.get(i));
                    //System.out.print(SymbolTableManager.getInstance().getActiveLocalScope().searchArray(vars.get(i)).getSize());
                    if (index.get(i)<SymbolTableManager.getInstance().getActiveLocalScope().searchArray(vars.get(i)).getSize())
                        store.add(SymbolTableManager.getInstance().getActiveLocalScope().searchArray(vars.get(i)).getValueAt(index.get(i)).getValue().toString());
                    else
                        editor.addCustomError("ARRAY OUT OF BOUNDS", line);
                }
            } else if (ExecutionManager.getInstance().isInFunctionExecution()) {
                //System.out.print("FUNCTION TESTING VALUE =+-+");
                if (SymbolTableManager.getInstance().getActiveLocalScope().getParent().searchVariableIncludingLocal(vars.get(i)) != null) {
                    //System.out.print(":o");
                    store.add(SymbolTableManager.getInstance().getActiveLocalScope().getParent().searchVariableIncludingLocal(vars.get(i)).getValue().toString());
                } else {
                    //System.out.print(">:(");
                    for (int j = 0; j < ExecutionManager.getInstance().getCurrentFunction().getParameterValueSize(); j++) {
                        if (ExecutionManager.getInstance().getCurrentFunction().getParametername(j).equals(vars.get(i))) {
                            store.add(ExecutionManager.getInstance().getCurrentFunction().getParameterAt(j).getValue().toString());
                            break;
                        }
                    }
                }


            } else if (SymbolTableManager.searchVariableInLocalIterative(vars.get(i), SymbolTableManager.getInstance().getActiveLocalScope()) != null ||
                    SymbolTableManager.searchVariableInLocalIterative(vars.get(i), SymbolTableManager.getInstance().getActiveLocalScope().getParent()) != null) {
                //System.out.print("VAR FOUND 1");
                ////System.out.print(SymbolTableManager.getInstance().getActiveLocalScope().searchVariableIncludingLocal(vars.get(i)).getValue());
                if (index == null) {
                    //System.out.print("PROCESS REGULAR");
                    //System.out.print("PRINT ALL VARS");
                    //SymbolTableManager.getInstance().getActiveLocalScope().getParent().printAllVars();
                    //System.out.print("PRINT ALL VARS");
                    if (SymbolTableManager.searchVariableInLocalIterative(vars.get(i), SymbolTableManager.getInstance().getActiveLocalScope().getParent()) == null) {
                        editor.addCustomError("INCORRECT ASSIGNMENT", line);
                    } else {
                        //System.out.print("WE IN");
                        store.add(SymbolTableManager.searchVariableInLocalIterative(vars.get(i), SymbolTableManager.getInstance().getActiveLocalScope().getParent()).getValue().toString());
                    }
                } else {
                    //System.out.print("PROCESS ARRAY");
                    store.add(SymbolTableManager.getInstance().getActiveLocalScope().searchArray(vars.get(i)).getValueAt(index.get(i)).getValue().toString());
                }
            } else if (SymbolTableManager.getInstance().getFunctions().containsKey(vars.get(i))) {
                //System.out.print("FOUND LE OMELLETT");
                store.add((SymbolTableManager.getInstance().functionLookup(vars.get(i)).getReturnValue().getValue().toString()));

            } else {
                //System.out.print("this var not found?");
                editor.addCustomError("VAR NOT FOUND", line);
            }
        }

        //System.out.print("NEW LIST");
        for (String print : vars) {
            //System.out.print(print);
        }

        //System.out.print("SHOW STORED");
        for (String pr : store) {
            //System.out.print(pr);
        }

        for (int i = 0; i < store.size(); i++) {
            //System.out.print("LOOP: "+i);
            //System.out.print(vars.get(i));
            //System.out.print(store.get(i));

            if (store.size() == 1 && !value.contains("\"")) {
                value = value.replaceAll(vars.get(i), store.get(i));
            } else{
                //System.out.print("CHECK THIS");
                value = value.replaceAll("(?<=[+\\-*/(])" + vars.get(i), store.get(i));
                value = value.replaceAll(vars.get(i), store.get(i));
                //System.out.print(value);
            }

//\-*/(
            if (value.contains("[")) {
                value = value.replaceAll("\\[.*?\\]", "");
            }
            if (value.contains("()")) {
                value = value.replaceAll("\\(.*?\\)", "");
            }
        }

        //System.out.print("NEW CREATED VALUE");
        //System.out.print(value);

        if (!value.contains("\"")&&value.matches("[A-Za-z]+"))
            return 0+"";

        return value;
    }

    public static boolean isELSEStatement(ClypsParser.IfThenElseStatementContext ctx) {

        List<TerminalNode> tokenList = ctx.getTokens(ClypsLexer.ELSE);
        //System.out.print("ELSE STATEMENT? " + tokenList.size());


        return (tokenList.size() != 0);
    }

}