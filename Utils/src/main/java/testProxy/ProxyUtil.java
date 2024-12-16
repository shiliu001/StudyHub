package testProxy;

import java.lang.reflect.Proxy;

/**
 * @author xiaowenpeng
 * @version 1.0
 * @date 2023/12/12
 * @description: TODO
 */
public class ProxyUtil {
    public static Object getProxy(CommonService commonService){
        Class<?>[] interfaces =new Class[]{CommonService.class};
        return Proxy.newProxyInstance(CommonService.class.getClassLoader(), interfaces, new MyInvocationHandler(commonService));
    }
}
