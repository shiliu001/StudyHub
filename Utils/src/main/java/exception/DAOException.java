package exception;

/**
 * @author xiaowenpeng
 * @version 1.0
 * @date 2024/3/6
 * @description: DAO层异常
 */
public class DAOException extends BaseException{
    public DAOException(){
        super();
    }
    public DAOException(String message,Throwable e){
        super(message,e);
    }
    public DAOException(String message){
        super(message);
    }
}
