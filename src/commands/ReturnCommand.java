package commands;

import antlr.ClypsParser;
import items.ClypsValue;
import ErrorCheckers.TypeChecking;
import controller.ClypsFunction;
//import utils.AssignmentUtils;

public class ReturnCommand implements ICommand {

    private ClypsParser.ExpressionContext rtnCtx;
    private ClypsFunction function;

    public ReturnCommand(ClypsParser.ExpressionContext stateCtx, ClypsFunction assignedFunction){
        this.rtnCtx = stateCtx;
        this.function = assignedFunction;

        //To be checked
//        UndeclaredChecker undeclaredChecker = new UndeclaredChecker(rtnCtx);
//        undeclaredChecker.verify();

        //ClypsValue value = this.function.getReturnValue();

        //TypeChecking typeChecking = new TypeChecking(value, this.rtnCtx);
       // typeChecking.verify();

    }

    @Override
    public void execute() {
        EvaluationCommand evaluationCommand = new EvaluationCommand(this.rtnCtx);
        evaluationCommand.execute();

        //ClypsValue value = this.function.getReturnValue();

        //AssignmentUtils.assignAppropriateValue(value, evaluationCommand.getResult());
    }
}
