package cn.shiliu.design.abstract_factory.low;

import cn.shiliu.design.abstract_factory.common.Screen;

/**
 * 功能描述：低配屏幕（具体零件）
 *
 * @author shiliuu
 */
public class LowScreen extends Screen{
    public LowScreen(String name, String price)
    {
        super(name, price);
    }

    @Override
    public void show()
    {
        System.out.println(name + "渣画质显示，" + "价格为" + price);
    }
}
