package cn.shiliu.design.abstract_factory.low;

import cn.shiliu.design.abstract_factory.common.Computer;
import cn.shiliu.design.abstract_factory.common.Factory;
import cn.shiliu.design.abstract_factory.common.Master;
import cn.shiliu.design.abstract_factory.common.Screen;

/**
 * 功能描述：生产低配零件和产品的工厂
 *
 * @author shiliu
 */
public class LowFactory extends Factory{

    // 创建具体主机
    @Override
    public Master createMaster(String name, String price)
    {
        return new LowMaster(name, price);
    }

    // 创建具体屏幕
    @Override
    public Screen createScreen(String name, String price)
    {
        return new LowScreen(name, price);
    }

    // 组装具体电脑
    @Override
    public Computer createComputer(Master master, Screen screen)
    {
        return new LowComputer(master, screen);
    }
}
