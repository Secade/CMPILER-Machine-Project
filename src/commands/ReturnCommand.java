package commands;

import antlr.ClypsParser;
import controller.ClypsCustomVisitor;
import controller.ClypsFunction;
import controller.ClypsValue;
import controller.editor;
import execution.ExecutionManager;

import java.util.List;

public class ReturnCommand implements ICommand{

    private ClypsParser.ReturnStatementContext ctx;
    private ClypsFunction clypsFunction;

    public ReturnCommand(ClypsParser.ReturnStatementContext ctx, ClypsFunction clypsFunction){
        this.ctx=ctx;
        this.clypsFunction=clypsFunction;

        ClypsValue clypsValue = this.clypsFunction.getReturnValue();

        System.out.println("RETURN TYPE");
        System.out.println(this.clypsFunction.getReturnValue());
        if (clypsValue==null){
            editor.addCustomError("CANNOT HAVE RETURN IN VOID FUNCTION", ctx.start.getLine());
        }

        List<Integer> dummy = null;
        String value = ClypsCustomVisitor.testingExpression(ctx.expression().getText(),dummy,ctx.start.getLine());
        //System.out.println("RETURN NULL?");
        //System.out.println(this.clypsFunction.getReturnType());
        //ExecutionManager.getInstance().getCurrentFunction().getMethodName();
        if (this.clypsFunction.attemptFunctionTypeCast(value,this.clypsFunction.getReturnType())==null)
            editor.addCustomError("RETURN VALUE TYPE MISMATCH", ctx.start.getLine());
        else
            System.out.println("METHOD WITH CORRECT RETURN");

        ExecutionManager.getInstance().getCurrentFunction().isReturned=true;
    }

    @Override
    public void execute() {
        List<Integer> dummy = null;
        String value = ClypsCustomVisitor.testingExpression(ctx.expression().getText(),dummy,ctx.start.getLine());
        System.out.println("RETURN NULL?");
        System.out.println(this.clypsFunction.getReturnType());
        //ExecutionManager.getInstance().getCurrentFunction().getMethodName();
        if (this.clypsFunction.attemptFunctionTypeCast(value,this.clypsFunction.getReturnType())!=null){
            System.out.println("yep wroks");
            this.clypsFunction.changeReturnValue(value);
        }

        else
            editor.addCustomError("RETURN VALUE TYPE MISMATCH", ctx.start.getLine());
    }
}
