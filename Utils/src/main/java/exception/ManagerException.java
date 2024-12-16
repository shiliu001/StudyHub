package exception;

/**
 * @author xiaowenpeng
 * @version 1.0
 * @date 2024/3/6
 * @description: manager通用业务层异常
 */
public class ManagerException extends BaseException{
    public ManagerException(){
        super();
    }
    public ManagerException(String message,Throwable e){
        super(message,e);
    }
    public ManagerException(String message){
        super(message);
    }
}
