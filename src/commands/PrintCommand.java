package commands;

import antlr.ClypsParser;
import com.udojava.evalex.Expression;
import controller.ClypsCustomVisitor;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.ParseTreeListener;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PrintCommand implements ICommand, ParseTreeListener {

    private ClypsParser.PrintStatementContext expCtx;
    private String printStatement = "";
    private boolean evalExp = false;

    public PrintCommand(ClypsParser.PrintStatementContext stateCtx){

        this.expCtx = stateCtx;

        //To be checked
//        UndeclaredChecker undeclaredChecker = new UndeclaredChecker(expCtx);
//        undeclaredChecker.verify();

        //printStatement = "";
    }

    @Override
    public void execute() {

        //System.out.println(expCtx.printBlock().getText());

        String value="";

        if (expCtx.printBlock().printExtra().arrayCall()!=null){
            List<Integer> matchList = new ArrayList<Integer>();
            Pattern regex = Pattern.compile("\\[(.*?)\\]");
            System.out.println(this.expCtx.printBlock().getText());
            Matcher regexMatcher = regex.matcher(this.expCtx.printBlock().getText());

            while (regexMatcher.find()) {//Finds Matching Pattern in String
                matchList.add(Integer.parseInt(regexMatcher.group(1).trim()));//Fetching Group from String
            }
            value = ClypsCustomVisitor.testingExpression(expCtx.printBlock().getText(),matchList,this.expCtx.start.getLine());
        }else {
            List<Integer> dummy = null;
            value = ClypsCustomVisitor.testingExpression(expCtx.printBlock().getText(),dummy,this.expCtx.start.getLine());
            try {
                value = new Expression(value).eval().toPlainString();
            }catch (NullPointerException e){
                value = ClypsCustomVisitor.testingExpression(expCtx.printBlock().getText(),dummy,this.expCtx.start.getLine());
            }

        }


        //CAUTION: REPLACES ALL + SIGNS
        value=value.replaceAll("\"","").replaceAll("\\+","");
        //System.out.println("ACTUAL PRINT");
        if (expCtx.printHead().getText().contains("ln"))
            System.out.println(value);
        else
            System.out.print(value);


    }

    @Override
    public void visitTerminal(TerminalNode terminalNode) {

    }

    @Override
    public void visitErrorNode(ErrorNode errorNode) {

    }

    @Override
    public void enterEveryRule(ParserRuleContext parserRuleContext) {

        if(parserRuleContext instanceof ClypsParser.ConditionalExpressionContext && !evalExp){
            ClypsParser.ConditionalExpressionContext eCtx = (ClypsParser.ConditionalExpressionContext) parserRuleContext;

            CommEval evCom = new CommEval(eCtx);
            evCom.execute();

            if(evCom.isNumberResult())
                printStatement += evCom.getResult().toEngineeringString();
            else
                printStatement += evCom.getStringResult();

            evalExp = true;
        }

    }

    @Override
    public void exitEveryRule(ParserRuleContext parserRuleContext) {

    }
    public String getPrintStatement(){return this.printStatement;}
}
