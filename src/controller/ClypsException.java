package controller;

public class ClypsException extends RuntimeException{
    //throw new controller.ClypsException("EHE");
    public ClypsException(String error){
        throw new RuntimeException(error);
    }
}
