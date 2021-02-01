package commands;

import antlr.ClypsParser;
import com.udojava.evalex.Expression;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.ParseTreeListener;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.antlr.v4.runtime.tree.TerminalNode;
import utils.StringUtils;

import java.math.BigDecimal;
import java.util.List;
import java.util.regex.Pattern;

public class CommEval implements ICommand, ParseTreeListener {

    private ClypsParser.ConditionalExpressionContext expression;
    private String newExp;
    private BigDecimal resultVal;
    private String result = "";

    private boolean isNumber;
    private boolean hasException = false;

    public CommEval(ClypsParser.ConditionalExpressionContext expressionCtx){
        this.expression  = expressionCtx;
    }


    @Override
    public void execute() {
        this.newExp = this.expression.getText();

        //Dont have to do forLoop because no List


        ParseTreeWalker treeWalker = new ParseTreeWalker();
        treeWalker.walk(this, this.expression);

        isNumber = !this.newExp.contains("\"") && this.newExp.contains("\'");

        if(!isNumber){
            System.out.println("PING");
            // for true or false expressions
            if(this.newExp.contains("==") || this.newExp.contains("!=")){

                String[] strings = {"", ""};

                if(this.newExp.contains("=="))
                    strings = this.newExp.split("==");
                if(this.newExp.contains("!="))
                    strings = this.newExp.split("!=");



                String equality = "STREQ("+strings[0]+", " + strings[1] + ")";

                if(this.newExp.contains("!="))
                    equality = "not(" + equality + ")";

                Expression e  = new Expression(equality);

                e.addLazyFunction(e.new LazyFunction("STREQ", 2) {

                    private Expression.LazyNumber ZERO = new Expression.LazyNumber() {
                        @Override
                        public BigDecimal eval() {
                            return BigDecimal.ZERO;
                        }

                        @Override
                        public String getString() {
                            return "0";
                        }
                    };

                    private Expression.LazyNumber ONE = new Expression.LazyNumber() {
                        @Override
                        public BigDecimal eval() {
                            return BigDecimal.ONE;
                        }

                        @Override
                        public String getString() {
                            return "1";
                        }
                    };
                    @Override
                    public Expression.LazyNumber lazyEval(List<Expression.LazyNumber> lazyList) {
                        if(lazyList.get(0).getString().equals(lazyList.get(1).getString())){
                            return ONE;
                        }
                        return ZERO;
                    }
                });

                this.resultVal = e.eval();
                isNumber = true;
            } else if(this.newExp.contains("<=") || this.newExp.contains(">=") || this.newExp.contains("<") || this.newExp.contains(">")){

                Expression e  = new Expression(this.newExp);

                this.resultVal = e.eval();
                isNumber = true;

            }
            else{
                this.result = StringUtils.removeQuotes(newExp);
            }
        } else{
            if(this.newExp.contains("!")){
                this.newExp = this.newExp.replaceAll("!", "not");
                this.newExp = this.newExp.replaceAll("not=", "!=");
            }
            if(this.newExp.contains("and")){
                this.newExp = this.newExp.replaceAll("and", "&&");
            }
            if(this.newExp.contains("or")){
                this.newExp = this.newExp.replaceAll("or", "||");
            }

            Expression evalEx = new Expression(this.newExp);

            try{
                this.resultVal = evalEx.eval(false);
                this.result = this.resultVal.toEngineeringString();
            }catch (Expression.ExpressionException exp){

                this.resultVal = new BigDecimal(0);
                this.result = "";
                this.hasException = true;

            }catch (ArithmeticException exp){
                //Add execution Manager here

                this.resultVal = new BigDecimal(0);
                this.result = "";
                this.hasException = true;
            }
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
        if(parserRuleContext instanceof ClypsParser.ConditionalExpressionContext){
            ClypsParser.ConditionalExpressionContext condCtx = (ClypsParser.ConditionalExpressionContext) parserRuleContext;
            if(CommEval.isFunctionCall(condCtx))
                this.evaluateFunctionCall(condCtx);
            else if(CommEval.isVariableOrConst(condCtx))
                this.evaluateVariable(condCtx);
        }

    }

    @Override
    public void exitEveryRule(ParserRuleContext parserRuleContext) {

    }

    public static boolean isFunctionCall(ClypsParser.ConditionalExpressionContext ctx){
        Pattern functionPattern = Pattern.compile("([a-zA-Z0-9]+)\\(([ ,.a-zA-Z0-9]*)\\)");

        if(functionPattern.matcher(ctx.getText()).matches())
            return true;
        else
            return false;
    }

    public static boolean isVariableOrConst(ClypsParser.ConditionalExpressionContext ctx){
        //Have to check if valid to just go the first part or needed to be in the most inner level

        //placeholder
        return true;

    }

    private void evaluateFunctionCall(ClypsParser.ConditionalExpressionContext ctx){

    }
    private void evaluateVariable(ClypsParser.ConditionalExpressionContext ctx){

    }

    public BigDecimal getResult(){return this.resultVal;}
    public String getStringResult(){return result;}
    public boolean isNumberResult(){return isNumber;}
    public boolean hasException(){return hasException;}

}

