package testProxy;

import java.lang.reflect.Method;

/**
 * @author xiaowenpeng
 * @version 1.0
 * @date 2023/12/12
 * @description: TODO
 */
public class MyInvocationHandler implements java.lang.reflect.InvocationHandler {
    MyInvocationHandler(Object object){
        this.object=object;
    }
    Object object;
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("前置代理方法");
        method.invoke(object,null);
        System.out.println("后置代理方法");
        return null;
    }
}
