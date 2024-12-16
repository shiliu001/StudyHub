package exception;

/**
 * @author xiaowenpeng
 * @version 1.0
 * @date 2024/3/6
 * @description: esb层报错
 */
public class EsbException extends BaseException {
    public EsbException(){
        super();
    }
    public EsbException(String message,Throwable e){
        super(message,e);
    }
    public EsbException(String message){
        super(message);
    }
    public EsbException(Throwable e){
        super(e);
    }
}
