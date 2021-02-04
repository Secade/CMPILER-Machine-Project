package commands;

import antlr.ClypsParser;
import com.udojava.evalex.Expression;
import controller.ClypsCustomVisitor;

import java.util.ArrayList;
import java.util.List;

public class WhileCommand implements IControlledCommand{

    private ClypsParser.WhileStatementContext expr;
    private boolean isGo;
    private String value;

    private List<ICommand> blockCommands;

    public WhileCommand(ClypsParser.WhileStatementContext ctx){
        this.expr=ctx;
        blockCommands = new ArrayList<>();
        List<Integer> dummy = null;
        this.value=ClypsCustomVisitor.testingExpression(this.expr.conditionalExpression().getText(),dummy,ctx.start.getLine());

        this.isGo=false;


    }


    private boolean evaluate() {
        List<Integer> dummy = null;
        boolean check = new Expression(ClypsCustomVisitor.testingExpression(this.expr.conditionalExpression().getText(),dummy,expr.start.getLine())).isBoolean();
        //System.out.println("CHECK "+check);
        if (this.expr.conditionalExpression().getText().matches("true|false")){
            check=true;
        }
        if (check){
            String swi = new Expression(ClypsCustomVisitor.testingExpression(this.expr.conditionalExpression().getText(),dummy,expr.start.getLine())).eval().toPlainString();
            if (Integer.parseInt(swi)==1){
                //System.out.println("IS TRUE");
                isGo=true;
            }else {
                //System.out.println("IS FALSE");
                isGo=false;
            }

            if (isGo){
                return true;
            }else {
                return false;
            }
        }else {
            //System.out.println("NOT BOOLEAN");
            return false;
        }


        //return false;
    }

    @Override
    public void execute() {
        ////System.out.println("EXECUTING FOR COMMAND");
        while (evaluate()){
            ////System.out.println("LOOP: "+this.counter);
            for(ICommand command:blockCommands){
                ////System.out.println("RUN COMMAND");
                ////System.out.println(command.toString());
                command.execute();
            }

        }
    }

    @Override
    public IControlledCommand.ControlTypeEnum getControlType() {
        return IControlledCommand.ControlTypeEnum.WHILE_CONTROL;
    }

    public void addCommand(ICommand command) {
        this.blockCommands.add(command);
    }

    public int getCommandCount() {
        return this.blockCommands.size();
    }
}
