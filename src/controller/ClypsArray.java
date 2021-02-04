package controller;

import java.util.Locale;

public class ClypsArray {

    private ClypsValue[] valueArray;
    private ClypsValue.PrimitiveType arrayType;
    private String arrayName;
    private boolean finalFlag = false;

    public ClypsArray(ClypsValue.PrimitiveType type){
        this.arrayType=type;
        //this.arrayName=name;
    }

    public void setPrimitiveType(ClypsValue.PrimitiveType primitiveType) {
        this.arrayType = primitiveType;
    }

    public ClypsValue.PrimitiveType getPrimitiveType() {
        return this.arrayType;
    }

    public void markFinal() {
        this.finalFlag = true;
    }

    public boolean isFinal() {
        return this.finalFlag;
    }

    public void initializeSize(int size) {
        try {
            this.valueArray = new ClypsValue[size];
        } catch (NegativeArraySizeException ex) {
            this.valueArray = null;
            //print negative array size
        }
    }

    public int getSize() {
        return this.valueArray.length;
    }

    public void updateValueAt(ClypsValue value, int index) {
        if(index >= this.valueArray.length || index <= -1) {
            ////System.out.println("ERROR: " + String.format(ErrorRepository.getErrorMessage(ErrorRepository.RUNTIME_ARRAY_OUT_OF_BOUNDS), this.arrayIdentifier));
            //ExecutionManager.getInstance().setCurrentCatchType(IAttemptCommand.CatchTypeEnum.ARRAY_OUT_OF_BOUNDS);
            //System.out.println("ARRAY OUT OF BOUNDS");
            return;
        }
        this.valueArray[index] = value;
    }

    public ClypsValue getValueAt(int index) {
        if(index >= this.valueArray.length || index <= -1) {
//            //System.out.println("ERROR: " + String.format(ErrorRepository.getErrorMessage(ErrorRepository.RUNTIME_ARRAY_OUT_OF_BOUNDS), this.arrayIdentifier));
            //ExecutionManager.getInstance().setCurrentCatchType(IAttemptCommand.CatchTypeEnum.ARRAY_OUT_OF_BOUNDS);

            return null;
        }
        else {
            return this.valueArray[index];
        }
    }

    public static ClypsArray createArray(String primitiveTypeString, String arrayIdentifier) {
        ClypsValue.PrimitiveType primitiveType = ClypsValue.PrimitiveType.NOT_YET_IDENTIFIED;

        if(primitiveTypeString.contains(ClypsValue.PrimitiveType.BOOLEAN.toString())) {
            primitiveType = ClypsValue.PrimitiveType.BOOLEAN;
        }
        else if(primitiveTypeString.contains(ClypsValue.PrimitiveType.CHAR.toString().toLowerCase(Locale.ROOT))) {
            primitiveType = ClypsValue.PrimitiveType.CHAR;
        }
        else if(primitiveTypeString.contains(ClypsValue.PrimitiveType.DOUBLE.toString())) {
            primitiveType = ClypsValue.PrimitiveType.DOUBLE;
        }
        else if(primitiveTypeString.contains(ClypsValue.PrimitiveType.FLOAT.toString())) {
            primitiveType = ClypsValue.PrimitiveType.FLOAT;
        }
        else if(primitiveTypeString.contains(ClypsValue.PrimitiveType.INT.toString())) {
            primitiveType = ClypsValue.PrimitiveType.INT;
        }
        else if(primitiveTypeString.toLowerCase(Locale.ROOT).contains(ClypsValue.PrimitiveType.STRING.toString().toLowerCase(Locale.ROOT))) {
            primitiveType = ClypsValue.PrimitiveType.STRING;
        }

        ClypsArray array = new ClypsArray(primitiveType);

        return array;
    }
}
