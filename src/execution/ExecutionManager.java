package execution;

import commands.ICommand;
import controller.ClypsFunction;

import javax.management.Notification;
import javax.management.NotificationListener;
import java.util.ArrayList;

public class ExecutionManager{

    private static ExecutionManager sharedInstance = null;

    public static ExecutionManager getInstance() {
        return sharedInstance;
    }

    private ArrayList<ICommand> executionList = new ArrayList<ICommand>();
    private boolean foundEntryPoint = false;

    private ExecutionThread executionThread;
    //private ExecutionMonitor executionMonitor;

    private IExecutionAdder activeExecutionAdder;
    private MainExecutionAdder mainExecutionAdder;

    private int currentCheckedLineNumber = -1;

    private ExecutionManager() {
        this.mainExecutionAdder = new MainExecutionAdder(this.executionList);
        this.activeExecutionAdder = this.mainExecutionAdder;
    }

    public static void initialize() {
        sharedInstance = new ExecutionManager();
    }

    public static void reset() {
        sharedInstance.foundEntryPoint = false;
        sharedInstance.clearAllActions();
    }

    public void setCurrentCheckedLineNumber (int n) {
        this.currentCheckedLineNumber = n;
    }

    public void reportFoundEntryPoint() {
        this.foundEntryPoint = true;
    }

    public boolean hasFoundEntryPoint() {
        return this.foundEntryPoint;
    }


    public void addCommand(ICommand command) {
        this.activeExecutionAdder.addCommand(command);
    }

    public void deleteCommand(ICommand command) {
        this.executionList.remove(command);
    }

    public void openFunctionExecution(ClypsFunction function) {
        MethodExecutionAdder methodExecutionAdder = new MethodExecutionAdder(function);
        this.activeExecutionAdder = methodExecutionAdder;
    }

    public boolean isInFunctionExecution() {
        return (this.activeExecutionAdder instanceof MethodExecutionAdder);
    }

    public ClypsFunction getCurrentFunction() {
        if(this.isInFunctionExecution()) {
            MethodExecutionAdder functionExecAdder = (MethodExecutionAdder) this.activeExecutionAdder;

            return functionExecAdder.getAssignedFunction();
        }
        else {
            return null;
        }
    }

    public void closeFunctionExecution() {
        this.activeExecutionAdder = this.mainExecutionAdder;
    }

    public void blockExecution() {
        this.executionThread.claimExecutionFlag();
    }

    public void resumeExecution() {
        this.executionThread.releaseExecutionFlag();
    }

    public void executeAllActions() {
        this.executionThread = new ExecutionThread(this.executionList);
        this.executionThread.start();
    }

    public void clearAllActions() {
        this.executionList.clear();
    }
}
