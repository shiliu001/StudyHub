package cn.shiliu.design.abstract_factory.high;

import cn.shiliu.design.abstract_factory.common.Screen;

/**
 * 功能描述：高配屏幕（具体零件）
 *
 * @author shiliu
 */
public class HighScreen extends Screen{
    public HighScreen(String name, String price)
    {
        super(name, price);
    }

    @Override
    public void show()
    {
        System.out.println(name + "高清显示，" + "价格为" + price);
    }
}
