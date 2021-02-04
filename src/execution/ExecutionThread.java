package execution;

import commands.ICommand;

import java.util.*;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ExecutionThread extends Thread{
    private ArrayList<ICommand> commandList = new ArrayList<>();
    private boolean execFlag = true;

    private final Lock lock = new ReentrantLock();
    private final Condition execGate = lock.newCondition();

    public ExecutionThread(ArrayList<ICommand> commandList){
        this.commandList = commandList;
    }

    @Override
    public void run() {
        try{
            for(ICommand command : this.commandList){
                this.tryExecution();
                command.execute();
            }
        }catch (InterruptedException e){
            //System.out.println("Interrupted!");
        }
    }

    public void tryExecution() throws InterruptedException{
        this.lock.lock();

        try{
            while(execFlag == false){
                //System.out.println("Flag is false. Currently sleeping");
                this.execGate.await();
            }
        } finally {

            this.lock.unlock();
        }
    }

    //halts commands
    public void claimExecutionFlag(){
        this.execFlag = false;
    }

    public void releaseExecutionFlag(){
        this.lock.lock();
        this.execFlag = true;
        this.execGate.signal();
        this.lock.unlock();
    }

}
