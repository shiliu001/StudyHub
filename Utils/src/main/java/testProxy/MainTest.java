package testProxy;

/**
 * @author xiaowenpeng
 * @version 1.0
 * @date 2023/12/12
 * @description: TODO
 */
public class MainTest {
    public static void main(String[] args) throws InterruptedException {
        CommonService commonService=new CommonServiceImp();

        CommonService proxyCommonService=(CommonService)ProxyUtil.getProxy(commonService);


        proxyCommonService.testProxy();
        while (true){
            Thread.sleep(10000);
        }
    }
}

