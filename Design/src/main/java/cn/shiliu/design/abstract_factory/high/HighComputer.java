package cn.shiliu.design.abstract_factory.high;

import cn.shiliu.design.abstract_factory.common.Computer;
import cn.shiliu.design.abstract_factory.common.Master;
import cn.shiliu.design.abstract_factory.common.Screen;

/**
 * 功能描述：高配计算机（具体产品）
 *
 * @author shiliu
 */
public class HighComputer extends Computer{
    public HighComputer(Master master, Screen screen)
    {
        super(master, screen);
    }

    @Override
    public void printInfo()
    {
        System.out.println("高配计算机：");
        master.start();
        screen.show();
        System.out.println();
    }
}
