package commands;

import antlr.ClypsParser;
import com.udojava.evalex.Expression;

import java.math.BigDecimal;


public class ConditionEval{

    public static boolean evaluateCondition(ClypsParser.ConditionalExpressionContext expression){

        if(expression.getText().contains("(true)")){
            return true;
        }
        else if(expression.getText().contains("(false)")){
            return false;
        }

        CommEval evaluate = new CommEval(expression);
        evaluate.execute();

        int result = evaluate.getResult().intValue();

        return (result == 1);
    }

    public static boolean evaluateCondition(String condition){

        if(condition.contains("(true)"))
            return true;
        else if(condition.contains("(false)"))
            return false;

        if (condition.contains("!")) {
            condition = condition.replaceAll("!", "not");
            condition = condition.replaceAll("not=", "!=");
        }

        if(condition.contains("and"))
            condition = condition.replaceAll("and", "&&");

        if(condition.contains("or"))
            condition = condition.replaceAll("or", "||");

        Expression expression = new Expression(condition);
        BigDecimal result = expression.eval();

        return (1 == Integer.parseInt(result.toEngineeringString()));
    }
    //For double checking
//    public static boolean evaluateCondition(ClypsParser.ConditionalExpressionContext expression){
//        if(expression.getText().contains(("(true)")))
//            return true;
//        else if(expression.getText().contains(("(false)")))
//            return false;
//
//        CommEval evaluate = new CommEval(expression);
//        evaluate.execute();
//
//        int result = evaluate.getResult().intValue();
//
//        return (result == 1);
//    }

}