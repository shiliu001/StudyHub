package cn.shiliu.design.abstract_factory.high;

import cn.shiliu.design.abstract_factory.common.Master;

/**
 * 功能描述：高配主机（具体零件）
 *
 * @author shiliu
 */
public class HighMaster extends Master{
    public HighMaster(String name, String price)
    {
        super(name, price);
    }

    @Override
    public void start()
    {
        System.out.println(name + "高速启动，" + "价格为" + price);
    }
}
