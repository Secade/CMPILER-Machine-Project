package controller;

import antlr.ClypsBaseListener;
import antlr.ClypsParser;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;

public class ClypsCustomListener extends ClypsBaseListener {
    Map<String, Map.Entry<String, String>> mapVar = new HashMap<String, Map.Entry<String, String>>();

    private String tempType;
    private String tempName;
    private String tempValue;

    @Override
    public void enterLocalVariableDeclarationStatement(ClypsParser.LocalVariableDeclarationStatementContext ctx) {

    }

    @Override
    public void exitLocalVariableDeclarationStatement(ClypsParser.LocalVariableDeclarationStatementContext ctx) {
//        System.out.println(ctx.toStringTree());
//        System.out.println(ctx.getChild(0).getChild(0).getChild(0).getText());
    }

    @Override
    public void exitIntegralType(ClypsParser.IntegralTypeContext ctx) {
        if(ctx.INT() != null) {
            this.tempType=ctx.INT().getText();
        }
    }

    @Override
    public void exitFloatingPointType(ClypsParser.FloatingPointTypeContext ctx) {

    }

    @Override
    public void exitUnannPrimitiveType(ClypsParser.UnannPrimitiveTypeContext ctx) {

    }

    @Override
    public void exitUnannTypeVariable(ClypsParser.UnannTypeVariableContext ctx) {

    }

    @Override
    public void exitVariableDeclaratorId(ClypsParser.VariableDeclaratorIdContext ctx) {
        if(ctx.Identifier() != null) {
            this.tempName=ctx.Identifier().getText();
        }
    }

    @Override
    public void exitLiteral(ClypsParser.LiteralContext ctx) {
        if(ctx.IntegerLiteral() != null) {
            this.tempValue=ctx.IntegerLiteral().getText();
            this.mapVar.put(this.tempName,new AbstractMap.SimpleEntry(this.tempType,this.tempValue));

        }
    }

    @Override
    public void exitPrintStatement(ClypsParser.PrintStatementContext ctx) {
//        if(ctx.printBlock().Identifier()!=null)
//            System.out.println("Map: " + this.mapVar.get(ctx.printBlock().Identifier().getText()).getValue());
//        if(ctx.printBlock().StringLiteral()!=null)
//            System.out.println("Map: " + ctx.printBlock().StringLiteral().getText());
    }

//    @Override
//    public void enterBlock(ClypsParser.BlockContext ctx) {
//        System.out.println("ENTER BLOCK");
//        System.out.println(ctx.getParent().getText());
//    }
//
//    @Override
//    public void exitBlock(ClypsParser.BlockContext ctx) {
//
//    }

}
