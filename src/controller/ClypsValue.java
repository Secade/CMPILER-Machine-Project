package controller;

import com.udojava.evalex.*;

import java.util.Locale;

public class ClypsValue {
    public enum PrimitiveType{
        NOT_YET_IDENTIFIED,
        BOOLEAN,
        INT,
        CHAR,
        FLOAT,
        DOUBLE,
        STRING,
        ARRAY
    }

    private Object value;
    private PrimitiveType type= PrimitiveType.NOT_YET_IDENTIFIED;
    private boolean finalFlag = false;

    public ClypsValue(){

    }

    public ClypsValue(Object value, PrimitiveType primitiveType){
//
//        PrimitiveType ty;
//        //System.out.println("Type: "+primitiveType.toLowerCase(Locale.ROOT));
//        switch (primitiveType.toLowerCase(Locale.ROOT)){
//            case "int":
//                ty = PrimitiveType.INT;
//                break;
//            case "char":
//                ty = PrimitiveType.CHAR;
//                break;
//            case "string":
//                ty = PrimitiveType.STRING;
//                break;
//            case "float":
//                ty = PrimitiveType.FLOAT;
//                break;
//            case "boolean":
//                ty = PrimitiveType.BOOLEAN;
//                break;
//            case "double":
//                ty = PrimitiveType.DOUBLE;
//                break;
//            default:
//                ty = PrimitiveType.NOT_YET_IDENTIFIED;
//        }
//        //|| TypeChecking.checkValueType(value,ty)
        //System.out.println(value);
        //System.out.println(primitiveType);
        if (value==null ||checkValueType(value,primitiveType)){
            //System.out.println("IN");
            this.value = value;
            this.type = primitiveType;
            if ((this.type==PrimitiveType.INT||this.type==PrimitiveType.FLOAT||this.type==PrimitiveType.DOUBLE||this.type==PrimitiveType.BOOLEAN)&&value!=null)
                tryEvaluate(value.toString());

        }else{
            //System.out.println("ERROR");
            editor.addError("Type Mismatch");
        }

    }

    public void setValue(String value){
        tryEvaluate(value);
    }

    public void setSCValue(String value){
        if (value.contains("\""))
            value=value.replaceAll("\"","");
        else
            value=value.replaceAll("'","");
        this.value=value;
    }

    public void setType(PrimitiveType primitiveType){
        this.type=primitiveType;
    }

    public Object getValue(){
        return this.value;
    }

    public PrimitiveType getPrimitiveType() {
        return this.type;
    }

    public void markFinal() {
        this.finalFlag = true;
    }

    public boolean isFinal() {
        return this.finalFlag;
    }

    private void tryEvaluate(String value){
        //System.out.println(value);
        //System.out.println(this.getPrimitiveType());

        if (this.getPrimitiveType()!=PrimitiveType.STRING&&this.getPrimitiveType()!=PrimitiveType.CHAR){
            try {
                this.value = new Expression(value).eval().toPlainString();
            }catch (Expression.ExpressionException e){
                //System.out.println("EHEEEE?");
                //editor.addCustomError("DUPLICATE FUNCTION DETECTED",0);
            }
        }else
            this.value=value;


        //System.out.println("EVAL: "+this.value);
    }

    public static boolean checkValueType(Object value, PrimitiveType primitiveType) {
        switch (primitiveType){
            case BOOLEAN:
                return value instanceof Boolean;
            case CHAR:
                return value instanceof Character;
            case INT:
                return value instanceof Integer;
            case FLOAT:
                return value instanceof Float;
            case DOUBLE:
                return value instanceof Double;
            case STRING:
                return value instanceof String;
            case ARRAY:
                return value instanceof Object;
            default:
                return false;
        }
    }

    public static Object attemptTypeCast(String value, PrimitiveType primitiveType) {
        //System.out.println("Attempting");
        //System.out.println(value);
        //System.out.println(primitiveType);
        switch (primitiveType) {
            case BOOLEAN:
//                //System.out.println("A - BOOLEAN");
//                //System.out.println(Boolean.valueOf(value));
                return Boolean.valueOf(value);
            case CHAR:
                return Character.valueOf(value.charAt(0));
            case INT:
                ////System.out.println("I - INTEGER");
                ////System.out.println(Integer.valueOf(value));
                try{
                    return Integer.valueOf(value);
                }catch (NumberFormatException e){
                    return null;
                }
            case FLOAT:
                try{
                    //System.out.println("yeah");
                    return Float.valueOf(value);
                }catch (NumberFormatException e){
                    //System.out.println("no");
                    return null;
                }
            case DOUBLE:
                try{
                    return Double.valueOf(value);
                }catch (NumberFormatException e){
                    return null;
                }
            case STRING:
                return value;
            default:
                return null;
        }
    }

    public static PrimitiveType translateType(String primitiveTypeString){
        PrimitiveType primitiveType = PrimitiveType.NOT_YET_IDENTIFIED;
        //System.out.println("ATTEMPT TRANSLATE");
        //System.out.println(primitiveTypeString);

        if(primitiveTypeString.toLowerCase(Locale.ROOT).contains(PrimitiveType.BOOLEAN.toString().toLowerCase(Locale.ROOT))) {
            primitiveType = PrimitiveType.BOOLEAN;
        }
        else if(primitiveTypeString.toLowerCase(Locale.ROOT).contains(PrimitiveType.CHAR.toString().toLowerCase(Locale.ROOT))) {
            primitiveType = PrimitiveType.CHAR;
        }
        else if(primitiveTypeString.toLowerCase(Locale.ROOT).contains(PrimitiveType.DOUBLE.toString().toLowerCase(Locale.ROOT))) {
            primitiveType = PrimitiveType.DOUBLE;
        }
        else if(primitiveTypeString.toLowerCase(Locale.ROOT).contains(PrimitiveType.FLOAT.toString().toLowerCase(Locale.ROOT))) {
            primitiveType = PrimitiveType.FLOAT;
        }
        else if(primitiveTypeString.toLowerCase(Locale.ROOT).contains(PrimitiveType.INT.toString().toLowerCase(Locale.ROOT))) {
            primitiveType = PrimitiveType.INT;
        }
        else if(primitiveTypeString.toLowerCase(Locale.ROOT).toLowerCase(Locale.ROOT).contains(PrimitiveType.STRING.toString().toLowerCase(Locale.ROOT))) {
            primitiveType = PrimitiveType.STRING;
        }

        //System.out.println("TRANS "+primitiveType);

        return primitiveType;
    }

    public static ClypsValue createEmptyVariableFromKeywords(String primitiveTypeString) {

        //identify primitive type
        PrimitiveType primitiveType = PrimitiveType.NOT_YET_IDENTIFIED;

        if(primitiveTypeString.contains(PrimitiveType.BOOLEAN.toString())) {
            primitiveType = PrimitiveType.BOOLEAN;
        }
        else if(primitiveTypeString.contains(PrimitiveType.CHAR.toString().toLowerCase(Locale.ROOT))) {
            primitiveType = PrimitiveType.CHAR;
        }
        else if(primitiveTypeString.contains(PrimitiveType.DOUBLE.toString())) {
            primitiveType = PrimitiveType.DOUBLE;
        }
        else if(primitiveTypeString.contains(PrimitiveType.FLOAT.toString())) {
            primitiveType = PrimitiveType.FLOAT;
        }
        else if(primitiveTypeString.contains(PrimitiveType.INT.toString())) {
            primitiveType = PrimitiveType.INT;
        }
        else if(primitiveTypeString.toLowerCase(Locale.ROOT).contains(PrimitiveType.STRING.toString().toLowerCase(Locale.ROOT))) {
            primitiveType = PrimitiveType.STRING;
        }

        ClypsValue mobiValue = new ClypsValue(null, primitiveType);

        return mobiValue;
    }
}
