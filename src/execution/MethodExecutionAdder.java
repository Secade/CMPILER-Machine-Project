package execution;

import commands.ICommand;
import controller.ClypsFunction;

public class MethodExecutionAdder implements IExecutionAdder {

    private ClypsFunction assignedMobiFunction;

    public MethodExecutionAdder(ClypsFunction mobiFunction) {
        this.assignedMobiFunction = mobiFunction;
    }

    @Override
    public void addCommand(ICommand command) {
        this.assignedMobiFunction.addCommand(command);
    }

    public ClypsFunction getAssignedFunction() {
        return this.assignedMobiFunction;
    }
}
