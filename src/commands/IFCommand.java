package commands;

import java.util.*;

import antlr.ClypsParser;
import controller.ClypsCustomVisitor;
import execution.ExecutionManager;
import org.antlr.v4.runtime.Parser;

public class IFCommand implements ICommand, IConditionalCommand {

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
        //this.identifyVariables();

        //ExecutionMonitor Stuff

        try {
            if (ConditionEval.evaluateCondition(this.condExp)) {
                System.out.println("IF COMMAND RECEIVE TRUE");
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
                System.out.println("IF COMMAND RECEIVE FALSE");
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

    @Override
    public IControlledCommand.ControlTypeEnum getControlType() {
        return IControlledCommand.ControlTypeEnum.CONDITIONAL_IF;
    }

    public void addPositiveCommand(ICommand command){
        System.out.println("ADDED POSITIVE COMMANDS");
        this.posCommands.add(command);

    }

    public void addNegativeCommand(ICommand command){
        System.out.println("ADDED NEGATIVE COMMAND");
        this.negCommands.add(command);
    }

    private String identifyVariables(ClypsParser.ConditionalExpressionContext condExp){

        String condition = condExp.getText();
        String value = "";


        List<Integer> dummy = null;

        for(int i = 0; i < condition.length(); i++){

        }




        return value;

    }

    public boolean isReturned(){return returned;}

    public boolean checking(){
        if (ConditionEval.evaluateCondition(this.condExp))
            return true;
        else
            return false;
    }

    public List<ICommand> getPosCommands() {
        return posCommands;
    }

    public List<ICommand> getNegCommands() {
        return negCommands;
    }

    public void resetReturnFlag() {
        returned = false;
    }

  
}