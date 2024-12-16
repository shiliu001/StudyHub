package cn.shiliu.design.decorator;

/**
 * 功能描述：照片
 *
 * @author shiliu
 */
public abstract class Photo{
    // 装饰，定义在父类，使得所有子类都具有装饰功能
    abstract String decorate();

    void show()
    {
        System.out.println(decorate());
    }
}
