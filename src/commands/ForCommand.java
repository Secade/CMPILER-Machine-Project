package commands;

import antlr.ClypsParser;
import com.udojava.evalex.Expression;
import controller.ClypsCustomVisitor;
import controller.editor;

import java.util.ArrayList;
import java.util.List;

public class ForCommand implements IControlledCommand {
    private ClypsParser.ForStatementContext expr;
    private int counter = 0;
    private int stop=0;
    private String start;
    private String end;

    private List<ICommand> blockCommands;

    public ForCommand(ClypsParser.ForStatementContext ctx){
        this.expr=ctx;
        blockCommands = new ArrayList<>();

        List<Integer> dummy = null;

        this.start=ClypsCustomVisitor.testingExpression(this.expr.forInit().variableDeclaratorList().variableDeclarator(0).variableInitializer().getText(),dummy,ctx.start.getLine());
        this.end=ClypsCustomVisitor.testingExpression(this.expr.assignmentExpression().getText(),dummy,ctx.start.getLine());
        System.out.println("FOR TEST");
        System.out.println(start);
        System.out.println(end);
        this.counter=Integer.parseInt(new Expression(this.start).eval().toPlainString());
        this.stop=Integer.parseInt(new Expression(this.end).eval().toPlainString());


    }

    private boolean evaluate() {

        if ((this.expr.forMiddle().getText().contains("up to") && this.counter > this.stop) || (this.expr.forMiddle().getText().contains("down to") && this.counter < this.stop)) {
            editor.addCustomError("VALUE RANGE IS NOT POSSIBLE", this.expr.start.getLine());
        } else {
            if (this.expr.forMiddle().getText().contains("up to") && this.counter <= this.stop) {
                //System.out.println("Cond True");
                return true;
            } else if (this.expr.forMiddle().getText().contains("down to") && this.counter >= this.stop) {
                //System.out.println("Cond True");
                return true;
            } else {
                //System.out.println("Cond False");
                return false;
            }
        }


        return false;
    }

    @Override
    public void execute() {
        //System.out.println("EXECUTING FOR COMMAND");
        while (evaluate()){
            //System.out.println("LOOP: "+this.counter);
            for(ICommand command:blockCommands){
                //System.out.println("RUN COMMAND");
                //System.out.println(command.toString());
                command.execute();
            }

            if (this.expr.forMiddle().getText().contains("up to")&&this.counter<=this.stop){
                this.counter++;
            }else if (this.expr.forMiddle().getText().contains("down to")&&this.counter>=this.stop){
                this.counter--;
            }
        }
    }

    @Override
    public ControlTypeEnum getControlType() {
        return ControlTypeEnum.FOR_CONTROL;
    }

    public void addCommand(ICommand command) {
        this.blockCommands.add(command);
    }

    public int getCommandCount() {
        return this.blockCommands.size();
    }
}
