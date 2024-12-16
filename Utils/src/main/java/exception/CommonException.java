package exception;

/**
 * @author xiaowenpeng
 * @version 1.0
 * @date 2024/3/6
 * @description: 非业务报错
 */
public class CommonException extends BaseException{
    private String code;
    public CommonException(){
        super();
    }
    public CommonException(String message,Throwable e){
        super(message,e);
    }
    public CommonException(String code,String message){
        super(message);
        this.code=code;
    }
    public CommonException(String message){
        super(message);
    }
    public CommonException(Throwable e){
        super(e);
    }
}
