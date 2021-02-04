package commands;

import antlr.ClypsParser;
import controller.ClypsArray;
import controller.ClypsValue;
import controller.SymbolTableManager;
import controller.editor;
import execution.ExecutionManager;
import sun.awt.Symbol;
import utils.StringUtils;

import javax.management.NotificationListener;


// To implement this, we need notifications
public class ScanCommand implements ICommand{

    private String displayMsg;
    private String variable;
    private ClypsParser.ArrayCallContext array;
    private String input;

    public ScanCommand(String displayMsg, String variable){
        this.displayMsg = StringUtils.removeQuotes(displayMsg);
        this.variable = variable;
        this.array = null;
    }

    public ScanCommand(String displayMsg, ClypsParser.ArrayCallContext ctx){
        this.displayMsg = StringUtils.removeQuotes(displayMsg);
        this.array = ctx;
    }

    @Override
    public void execute() {
        System.out.println("EXECUTING SCAN COMMAND");
        if(!(array == null)){
            Object x = editor.getInput();



            handleArray(x.toString());
        }
        else{
            System.out.println("In the scanCommand");
            ClypsValue clypsValue = SymbolTableManager.getInstance().getActiveLocalScope().searchVariableIncludingLocal(this.variable);
            System.out.println(SymbolTableManager.getInstance().getActiveLocalScope().searchVariableIncludingLocal(this.variable));
            System.out.println("Lemme Pause");

            String x = editor.getInput();
            System.out.println(x);
            clypsValue
                    .setValue(
                            x);
        }
    }

    private void handleArray(String result){
        int index = Integer.parseInt(this.array.getText().substring(this.array.getText().indexOf("[")+1, this.array.getText().indexOf("]")).trim());

        ClypsParser.ArrayCallContext arrayCtx = this.array;

        ClypsValue clypsValue = SymbolTableManager.getInstance().getActiveLocalScope().searchArray(arrayCtx.getText()).getValueAt(index);

        ClypsArray clypsArray = (ClypsArray) clypsValue.getValue();

        CommEval evaluateCommand = new CommEval(arrayCtx.expression().conditionalExpression());
        evaluateCommand.execute();

        ClypsValue newArrayValue = new ClypsValue(null, clypsArray.getPrimitiveType());
        newArrayValue.setValue(result);
        clypsArray.updateValueAt(newArrayValue, evaluateCommand.getResult().intValue());

    }
}
