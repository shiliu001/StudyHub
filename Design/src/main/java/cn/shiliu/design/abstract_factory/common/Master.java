package cn.shiliu.design.abstract_factory.common;

/**
 * 功能描述：主机（抽象零件）
 *
 * @author shiliu
 */
public abstract class Master{
    public String name;
    public String price;

    public Master(String name, String price)
    {
        this.name = name;
        this.price = price;
    }

    // 启动主机
    public abstract void start();
}
