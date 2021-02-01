package commands;

import java.util.*;

import antlr.ClypsParser;
import execution.ExecutionManager;
import org.antlr.v4.runtime.Parser;

public class IFCommand implements ICommand{

    private List<ICommand> posCommands;
    private List<ICommand> negCommands;
    private ClypsParser.ConditionalExpressionContext condExp;

    private ArrayList<String> localVars = new ArrayList<>();
    private boolean returned;

    public IFCommand(ClypsParser.ConditionalExpressionContext condExp) {
        this.posCommands = new ArrayList<ICommand>();
        this.negCommands = new ArrayList<ICommand>();
        this.condExp = condExp;
    }

    @Override
    public void execute() {
        this.identifyVariables();

        //ExecutionMonitor Stuff

        try {
            if (ConditionEval.evaluateCondition(this.condExp)) {
                for (ICommand command : this.posCommands) {
                    //executionMonitor.tryExecution()
                    command.execute();

                    /*** To be implemented ***/
//                    LocalVarTracker.getInstance().populateLocalVars(command);
//
//                    if (command instanceof ReturnCommand) {
//                        returned = true;
//                        break;
//                    }
//
//                    if (ExecutionManager.getInstance().isAborted())
//                        break;
                }
            } else {
                for (ICommand command : this.negCommands) {
                    //executionMonitor.tryExecution()
                    command.execute();

                    /*** To be implemented ***/
//                    LocalVarTracker.getInstance().populateLocalVars(command);
//
//                    if (command instanceof ReturnCommand) {
//                        returned = true;
//                        break;
//                    }
//
//                    if (ExecutionManager.getInstance().isAborted())
//                        break;
                }
            }
            //to be changed to InterruptedException
        }catch(Exception exception){
            System.out.println("Oops... Something happened. " + exception.getMessage());

        }
    }

    public void clearCommands(){
        this.posCommands.clear();
        this.negCommands.clear();
    }

    public void addPositiveCommand(ICommand command){

        this.posCommands.add(command);

    }

    public void addNegativeCommand(ICommand command){

        this.negCommands.add(command);
    }

    private void identifyVariables(){
        //Value Stuff to be added
    }

    public boolean isReturned(){return returned;}

    public boolean checking(){
        if (ConditionEval.evaluateCondition(this.condExp))
            return true;
        else
            return false;
    }
}