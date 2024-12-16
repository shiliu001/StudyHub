package exception;

/**
 * @author xiaowenpeng
 * @version 1.0
 * @date 2024/4/13
 * @description: 工具类异常
 */
public class UtilException extends BaseException{
    public UtilException(){
        super();
    }
    public UtilException(String message,Throwable e){
        super(message,e);
    }
    public UtilException(String message){
        super(message);
    }
    public UtilException(Throwable e){
        super(e);
    }
}
