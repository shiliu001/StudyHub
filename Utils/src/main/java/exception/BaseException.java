package exception;

/**
 * @author xiaowenpeng
 * @version 1.0
 * @date 2024/4/12
 * @description: TODO
 */
public class BaseException extends RuntimeException{
    public boolean resetMessage=false;
    public BaseException(){
        super();
    }
    public BaseException(Throwable e){
        super(e);
    }
    public BaseException(String message,Throwable e){
        super(message,e);
        resetMessage=true;
    }
    public BaseException(String message){
        super(message);
        resetMessage=true;
    }
}
