package commands;

import antlr.ClypsParser;
import com.udojava.evalex.Expression;
import controller.*;
import execution.ExecutionManager;

import java.util.List;

public class FunctionCallCommand implements ICommand{

    private ClypsFunction clypsFunction;
    private ClypsParser.MethodInvocationContext ctx;
    private String name;
    public boolean dError =false;

    public FunctionCallCommand(ClypsParser.MethodInvocationContext ctx){
        this.ctx=ctx;
        this.name=ctx.methodName().getText();
        this.clypsFunction= SymbolTableManager.getInstance().functionLookup(this.name);
        this.dError =false;
        List<Integer> dummy = null;
        if (this.clypsFunction==null){
            editor.addCustomError("FUNCTION DOES NOT EXIST", ctx.start.getLine());
        }else {
            if (ctx.argumentList()!=null){
                if (ctx.argumentList().assignmentExpression()!=null) {
                    for (int i = 0; i < ctx.argumentList().assignmentExpression().size(); i++) {
                        //System.out.println(ctx.argumentList().assignmentExpression().get(i).getText());
                        //System.out.println("HERE FCHECK");
                        //System.out.println(ctx.argumentList().assignmentExpression().get(i).assignment().getText());
                        ////System.out.println(ClypsValue.checkValueType(new Expression(ClypsCustomVisitor.testingExpression(ctx.argumentList().assignmentExpression().get(i).assignment().getText(), dummy, ctx.start.getLine())), clypsFunction.getParameterAt(i).getPrimitiveType()));
                        ////System.out.println(clypsFunction.getParameterAt(i).getPrimitiveType());

//                if (ClypsValue.checkValueType(ClypsValue.attemptTypeCast(ctx.argumentList().assignmentExpression().get(i).assignment().getText(),clypsFunction.getParameterAt(i).getPrimitiveType()),clypsFunction.getParameterAt(i).getPrimitiveType())){
//                    //System.out.println("IT WOKRSSS???");
//                }else {
//                    //System.out.println("no worr :(");
//                }

                        if (ClypsValue.checkValueType(ClypsValue.attemptTypeCast(ctx.argumentList().assignmentExpression().get(i).assignment().getText(), clypsFunction.getParameterAt(i).getPrimitiveType()), clypsFunction.getParameterAt(i).getPrimitiveType())) {
                            this.clypsFunction.mapParameterByValueAt(ClypsCustomVisitor.testingExpression(ctx.argumentList().assignmentExpression().get(i).assignment().getText(), dummy, ctx.start.getLine()), i);
                            //this.clypsFunction.ge
                        } else {
                            editor.addCustomError("PARAMETER TYPE MISMATCH", ctx.start.getLine());
                        }
                    }
                    //System.out.println("PRINT PARAMS");
                    this.clypsFunction.printParams();
                    //System.out.println("PRINT PARAMS");
                }
            }else {
                //System.out.println("NO ARGUEMENTS");
            }
        }


    }

    @Override
    public void execute() {
        Scope scope = SymbolTableManager.getInstance().getActiveLocalScope();
        this.clypsFunction.execute();
        SymbolTableManager.getInstance().setActiveScope(scope);
        //System.out.println("FUNCTION RETURN VALUE: ");
//        if (this.clypsFunction.getReturnType()!= ClypsFunction.FunctionType.VOID_TYPE)
//            //System.out.println(this.clypsFunction.getReturnValue().getValue());
//        else
//            //System.out.println("void");


    }
}
