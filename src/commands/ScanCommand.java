package commands;

import utils.StringUtils;

import javax.management.NotificationListener;


// To implement this, we need notifications
public class ScanCommand implements ICommand{

    private String displayMsg;
    private String variable;

    public ScanCommand(String displayMsg, String variable){
        this.displayMsg = StringUtils.removeQuotes(displayMsg);
        this.variable = variable;
    }

    @Override
    public void execute() {

    }
}
