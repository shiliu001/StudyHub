package testutil.testmain;

import http.HttpUtils;

/**
 * @author xiaowenpeng
 * @version 1.0
 * @date 2023/9/1
 * @description: TODO
 */
public class TestHttpUtil {
    public static void main(String[] args) {
        String s = HttpUtils.doHttpGet("http://127.0.0.1:9001", null);
        System.out.println(s);
    }
}
