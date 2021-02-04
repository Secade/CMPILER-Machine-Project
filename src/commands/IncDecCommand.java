package commands;

import antlr.ClypsParser;
import controller.SymbolTableManager;
import controller.ClypsValue;

public class IncDecCommand implements ICommand{
    private ClypsParser.IncDecStatementContext ctx;
    private String name;
    private String check;
    private ClypsValue.PrimitiveType type;

    public IncDecCommand(ClypsParser.IncDecStatementContext ctx, String name, String check, ClypsValue.PrimitiveType type){
        this.ctx=ctx;
        this.name=name;
        this.check=check;
        this.type=type;
    }

    @Override
    public void execute() {
        int valueInt=0;
        double valueDouble=0;
        float valueFloat=0;
        String value="";

        if (this.ctx.getText().contains("[")){
            int index = Integer.parseInt(this.ctx.getText().substring(this.ctx.getText().indexOf("[")+1,this.ctx.getText().indexOf("]")).trim());
            //System.out.println("IND"+index);
            if (this.check.contains("pos")){
                if (this.type== ClypsValue.PrimitiveType.INT){
                    valueInt=Integer.parseInt(SymbolTableManager.getInstance().getActiveLocalScope().searchArray(this.name).getValueAt(index).getValue().toString());
                    valueInt++;
                    value=valueInt+"";
                }else if (this.type== ClypsValue.PrimitiveType.DOUBLE){
                    valueDouble=Double.parseDouble(SymbolTableManager.getInstance().getActiveLocalScope().searchArray(this.name).getValueAt(index).getValue().toString());
                    valueDouble++;
                    value=valueDouble+"";
                }else if(this.type== ClypsValue.PrimitiveType.FLOAT){
                    valueFloat=Float.parseFloat(SymbolTableManager.getInstance().getActiveLocalScope().searchArray(this.name).getValueAt(index).getValue().toString());
                    valueFloat++;
                    value=valueFloat+"";
                }
                ClypsValue temp = new ClypsValue();
                temp.setType(this.type);
                temp.setValue(value);
                //System.out.println("NEW VAL?");
                //System.out.println(this.type);
                //System.out.println(value);
                SymbolTableManager.getInstance().getActiveLocalScope().searchArray(this.name).updateValueAt(temp,index);
            }else if (this.check.contains("neg")){
                if (this.type== ClypsValue.PrimitiveType.INT){
                    valueInt=Integer.parseInt(SymbolTableManager.getInstance().getActiveLocalScope().searchVariableIncludingLocal(this.name).getValue().toString());
                    valueInt--;
                    value=valueInt+"";
                }else if (this.type== ClypsValue.PrimitiveType.DOUBLE){
                    valueDouble=Double.parseDouble(SymbolTableManager.getInstance().getActiveLocalScope().searchVariableIncludingLocal(this.name).getValue().toString());
                    valueDouble--;
                    value=valueDouble+"";
                }else if(this.type== ClypsValue.PrimitiveType.FLOAT){
                    valueFloat=Float.parseFloat(SymbolTableManager.getInstance().getActiveLocalScope().searchVariableIncludingLocal(this.name).getValue().toString());
                    valueFloat--;
                    value=valueFloat+"";
                }
                ClypsValue temp = new ClypsValue();
                temp.setType(this.type);
                temp.setValue(value);
                SymbolTableManager.getInstance().getActiveLocalScope().searchArray(this.name).updateValueAt(temp,index);
            }
        }else {
            if (this.check.contains("pos")){
                if (this.type== ClypsValue.PrimitiveType.INT){
                    valueInt=Integer.parseInt(SymbolTableManager.getInstance().getActiveLocalScope().searchVariableIncludingLocal(this.name).getValue().toString());
                    valueInt++;
                    value=valueInt+"";
                }else if (this.type== ClypsValue.PrimitiveType.DOUBLE){
                    valueDouble=Double.parseDouble(SymbolTableManager.getInstance().getActiveLocalScope().searchVariableIncludingLocal(this.name).getValue().toString());
                    valueDouble++;
                    value=valueDouble+"";
                }else if(this.type== ClypsValue.PrimitiveType.FLOAT){
                    valueFloat=Float.parseFloat(SymbolTableManager.getInstance().getActiveLocalScope().searchVariableIncludingLocal(this.name).getValue().toString());
                    valueFloat++;
                    value=valueFloat+"";
                }

                SymbolTableManager.getInstance().getActiveLocalScope().searchVariableIncludingLocal(this.name).setValue(value+"");
            }else if (this.check.contains("neg")){
                if (this.type== ClypsValue.PrimitiveType.INT){
                    valueInt=Integer.parseInt(SymbolTableManager.getInstance().getActiveLocalScope().searchVariableIncludingLocal(this.name).getValue().toString());
                    valueInt--;
                    value=valueInt+"";
                }else if (this.type== ClypsValue.PrimitiveType.DOUBLE){
                    valueDouble=Double.parseDouble(SymbolTableManager.getInstance().getActiveLocalScope().searchVariableIncludingLocal(this.name).getValue().toString());
                    valueDouble--;
                    value=valueDouble+"";
                }else if(this.type== ClypsValue.PrimitiveType.FLOAT){
                    valueFloat=Float.parseFloat(SymbolTableManager.getInstance().getActiveLocalScope().searchVariableIncludingLocal(this.name).getValue().toString());
                    valueFloat--;
                    value=valueFloat+"";
                }

                SymbolTableManager.getInstance().getActiveLocalScope().searchVariableIncludingLocal(this.name).setValue(value+"");
            }
        }



    }
}
