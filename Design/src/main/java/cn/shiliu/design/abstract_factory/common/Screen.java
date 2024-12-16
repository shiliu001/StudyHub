package cn.shiliu.design.abstract_factory.common;

/**
 * 功能描述：屏幕（抽象零件）
 *
 * @author shiliu
 */
public abstract class Screen{
    public String name;
    public String price;

    public Screen(String name, String price)
    {
        this.name = name;
        this.price = price;
    }

    // 屏幕显示
    public abstract void show();
}
