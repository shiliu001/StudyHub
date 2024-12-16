package cn.shiliu.design.abstract_factory.common;

/**
 * 功能描述：计算机（抽象产品）
 *
 * @author shiliu
 */
public abstract class Computer{
    public Master master;
    public Screen screen;

    public Computer(Master master, Screen screen)
    {
        this.master = master;
        this.screen = screen;
    }
    // 展示组装出的计算机
    public abstract void printInfo();
}
