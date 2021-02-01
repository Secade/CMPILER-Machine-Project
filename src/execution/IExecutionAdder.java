package execution;

import commands.ICommand;

public interface IExecutionAdder {
    public abstract void addCommand(ICommand command);
}
