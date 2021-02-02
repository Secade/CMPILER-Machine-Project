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

        ExecutionManager.getInstance().getCurrentFunction().isReturned=true;
    }

    @Override
    public void execute() {
        List<Integer> dummy = null;
        String value = ClypsCustomVisitor.testingExpression(ctx.expression().getText(),dummy,ctx.start.getLine());
        System.out.println("RETURN NULL?");
        System.out.println(this.clypsFunction.getReturnType());
        //ExecutionManager.getInstance().getCurrentFunction().getMethodName();
        this.clypsFunction.changeReturnValue(value);
    }
}
