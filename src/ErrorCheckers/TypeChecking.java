package ErrorCheckers;

import antlr.ClypsBaseListener;
import antlr.ClypsParser;
import items.ClypsValue;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.ParseTreeListener;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.lang.reflect.Array;

public class TypeChecking implements ParseTreeListener{

    private ClypsValue baracoValue;
    private ClypsParser.ExpressionContext exprCtx;
    private int lineNumber;

    public TypeChecking(ClypsValue assignmentMobiValue, ClypsParser.ExpressionContext exprCtx) {
        this.baracoValue = assignmentMobiValue;
        this.exprCtx = exprCtx;

        this.lineNumber = exprCtx.getStart().getLine();
    }

    public void verify() {
        ParseTreeWalker treeWalker = new ParseTreeWalker();
        treeWalker.walk(this, this.exprCtx);
    }

    @Override
    public void visitTerminal(TerminalNode terminalNode) {

    }

    @Override
    public void visitErrorNode(ErrorNode errorNode) {

    }

    @Override
    public void enterEveryRule(ParserRuleContext ctx) {
//        if(ctx instanceof ExpressionContext) {
//
//            ExpressionContext expCtx = (ExpressionContext) ctx;
//
//            if (EvaluationCommand.isFunctionCall(exprCtx)){
//
//                ClassScope classScope = SymbolTableManager.getInstance().getClassScope(
//                        ParserHandler.getInstance().getCurrentClassName());
//                BaracoMethod baracoMethod = classScope.searchMethod(expCtx.getText());
//
//                if(baracoValue == null)
//                    return;
//
//                if(baracoMethod == null)
//                    return;
//
//                if(this.baracoValue.getPrimitiveType() == PrimitiveType.BOOL) {
//                    if(baracoMethod.getReturnType() != BaracoMethod.MethodType.BOOL_TYPE) {
//                        String additionalMessage = "Expected boolean.";
//                        BuildChecker.reportCustomError(ErrorRepository.TYPE_MISMATCH,  additionalMessage, this.lineNumber);
//                    }
//                }
//                else if(this.baracoValue.getPrimitiveType() == PrimitiveType.INT) {
//                    if(baracoMethod.getReturnType() != BaracoMethod.MethodType.INT_TYPE) {
//                        String additionalMessage = "Expected int.";
//                        BuildChecker.reportCustomError(ErrorRepository.TYPE_MISMATCH,  additionalMessage, this.lineNumber);
//                    }
//                }
//                else if(this.baracoValue.getPrimitiveType() == PrimitiveType.DECIMAL) {
//                    if(baracoMethod.getReturnType() != BaracoMethod.MethodType.DECIMAL_TYPE) {
//                        String additionalMessage = "Expected floating point or double.";
//                        BuildChecker.reportCustomError(ErrorRepository.TYPE_MISMATCH,  additionalMessage, this.lineNumber);
//                    }
//                }
//                else if(this.baracoValue.getPrimitiveType() == PrimitiveType.STRING) {
//                    if (baracoMethod.getReturnType() != BaracoMethod.MethodType.STRING_TYPE) {
//                        String additionalMessage = "Expected string.";
//                        BuildChecker.reportCustomError(ErrorRepository.TYPE_MISMATCH, additionalMessage, this.lineNumber);
//                    }
//                }
//
//            }
//        } else

        if(ctx instanceof ClypsParser.LiteralContext) {
            if(this.baracoValue == null) {
                return;
            }
            ClypsParser.LiteralContext literalCtx = (ClypsParser.LiteralContext) ctx;
            String expressionString = literalCtx.getText();

            if(this.baracoValue.getPrimitiveType() == ClypsValue.PrimitiveType.BOOLEAN) {
                if(literalCtx.BooleanLiteral() == null) {
                    String additionalMessage = "Expected boolean.";
                    System.out.println(additionalMessage);

                    //BuildChecker.reportCustomError(ErrorRepository.TYPE_MISMATCH,  additionalMessage, this.lineNumber);
                }
            }
            else if(this.baracoValue.getPrimitiveType() == ClypsValue.PrimitiveType.INT) {
                if(literalCtx.IntegerLiteral() == null) {
                    String additionalMessage = "Expected int.";
                    System.out.println(additionalMessage);

                    //BuildChecker.reportCustomError(ErrorRepository.TYPE_MISMATCH,  additionalMessage, this.lineNumber);
                }
            }
            else if(this.baracoValue.getPrimitiveType() == ClypsValue.PrimitiveType.FLOAT||this.baracoValue.getPrimitiveType() == ClypsValue.PrimitiveType.DOUBLE) {
                if(literalCtx.FloatingPointLiteral() == null) {
                    String additionalMessage = "Expected floating point or double.";
                    System.out.println(additionalMessage);
                    //BuildChecker.reportCustomError(ErrorRepository.TYPE_MISMATCH,  additionalMessage, this.lineNumber);
                }
            }
            else if(this.baracoValue.getPrimitiveType() == ClypsValue.PrimitiveType.STRING) {
                if(expressionString.charAt(0) != '\"' && expressionString.charAt(expressionString.length() - 1) != '\"') {
                    String additionalMessage = "Expected string.";
                    System.out.println(additionalMessage);

                    //BuildChecker.reportCustomError(ErrorRepository.TYPE_MISMATCH,  additionalMessage, this.lineNumber);
                }

                else if(literalCtx.StringLiteral() == null) {
                    String additionalMessage = "Expected string.";
                    System.out.println(additionalMessage);

                    // BuildChecker.reportCustomError(ErrorRepository.TYPE_MISMATCH,  additionalMessage, this.lineNumber);
                }
            }
       }
    }

    @Override
    public void exitEveryRule(ParserRuleContext parserRuleContext) {

    }
}
