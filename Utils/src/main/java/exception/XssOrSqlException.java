package exception;

/**
 * @author xiaowenpeng
 * @version 1.0
 * @date 2024/3/1
 * @description: TODO
 */
public class XssOrSqlException extends BaseException{
    public XssOrSqlException(){

    }
    public XssOrSqlException(String msg){
        super(msg);
    }
}
