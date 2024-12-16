package cn.shiliu.design.singleton;

/**
 * 功能描述：测试类
 *
 * @author shiliu
 */
public class Main{
    public static void main(String[] args)
    {
        // Singleton singleton = new Singleton();
        Singleton singleton1 = Singleton.getInstance();
        Singleton singleton2 = Singleton.getInstance();
        if (singleton1 == singleton2)
        {
            System.out.println("singleton1和singleton2是同一实例");
        }
    }
}

