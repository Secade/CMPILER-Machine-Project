package commands;

import antlr.ClypsParser;
import controller.ClypsFunction;
import items.ClypsValue;
import controller.Scope;
import controller.SymbolTableManager;
import utils.RecognizedKeywords;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.ParseTreeListener;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.math.BigDecimal;
import java.util.List;

public class EvaluationCommand implements ICommand, ParseTreeListener {

    private ClypsParser.ExpressionContext exprCtx;
    private String anotherExp;
    private BigDecimal value;

    public EvaluationCommand(ClypsParser.ExpressionContext expCtx){
        this.exprCtx = expCtx;
    }

    @Override
    public void execute() {
        this.anotherExp = this.exprCtx.getText();

        if(this.anotherExp.contains(RecognizedKeywords.BOOLEAN_TRUE)) {
            this.value = new BigDecimal(1);
        }
        else if(this.anotherExp.contains(RecognizedKeywords.BOOLEAN_FALSE)) {
            this.value = new BigDecimal(0);
        }
        else {
            ParseTreeWalker treeWalker = new ParseTreeWalker();
            treeWalker.walk(this, this.exprCtx);

            Expression evalEx = new Expression(this.anotherExp);
            this.value = evalEx.eval();
        }
    }

    @Override
    public void visitTerminal(TerminalNode terminalNode) {

    }

    @Override
    public void visitErrorNode(ErrorNode errorNode) {

    }

    @Override
    public void enterEveryRule(ParserRuleContext parserRuleContext) {
        if (parserRuleContext instanceof ClypsParser.ExpressionContext) {
            ClypsParser.ExpressionContext expCtx = (ClypsParser.ExpressionContext) parserRuleContext;
            if (EvaluationCommand.isFunctionCall(expCtx)) {
                this.evaluateFunctionCall(expCtx);
            }

            else if (EvaluationCommand.isVariableOrConst(expCtx)) {
                this.evaluateVariable(expCtx);
            }
        }
    }

    @Override
    public void exitEveryRule(ParserRuleContext parserRuleContext) {

    }

    public static boolean isFunctionCall(ClypsParser.ExpressionContext expCtx) {
        if (expCtx.arguments() != null) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isVariableOrConst(ClypsParser.ExpressionContext expCtx) {
        if (expCtx.primary() != null && expCtx.primary().Identifier() != null) {
            return true;
        } else {
            return false;
        }
    }

    private void evaluateFunctionCall(ClypsParser.ExpressionContext expCtx) {
        String functionName = expCtx.expression(0).Identifier().getText();


        Scope scope = SymbolTableManager.getInstance().getActiveLocalScope(ParserHandler.getInstance().getCurrentClassName());
        ClypsFunction clypsFunction = scope.searchFunction(functionName);

        if (expCtx.arguments().expressionList() != null) {
            List<ClypsParser.ExpressionContext> exprCtxList = expCtx.arguments().expressionList().expression();

            for (int i = 0; i < exprCtxList.size(); i++) {
                ClypsParser.ExpressionContext parExprCtx = exprCtxList.get(i);

                EvaluationCommand evalCommand = new EvaluationCommand(parExprCtx);
                evalCommand.execute();

                clypsFunction.mapParameterByValueAt(evalCommand.getResult().toEngineeringString(), i);
            }
        }

        clypsFunction.execute();

        System.out.println("EVALUATION COMMAND - Before modified EXP function call: " + this.anotherExp);
        this.anotherExp = this.anotherExp.replace(expCtx.getText(), clypsFunction.getReturnValue().getValue().toString());
        System.out.println("EVALUATION COMMAND - After modified EXP function call: " + this.anotherExp);
    }

    private void evaluateVariable(ClypsParser.ExpressionContext expCtx) {
        ClypsValue clpysValue = VariableSearcher.searchVariable(expCtx.getText());

        this.anotherExp = this.anotherExp.replaceFirst(expCtx.getText(), clpysValue.getValue().toString());
    }

}
