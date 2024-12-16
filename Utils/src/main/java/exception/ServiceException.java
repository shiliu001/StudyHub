package exception;

/**
 * @author xiaowenpeng
 * @version 1.0
 * @date 2024/3/6
 * @description: serviceException
 */
public class ServiceException extends BaseException{
    public ServiceException(){
        super();
    }
    public ServiceException(String message,Throwable e){
        super(message,e);
    }
    public ServiceException(String message){
        super(message);
    }
}
