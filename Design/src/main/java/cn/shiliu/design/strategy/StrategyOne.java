package cn.shiliu.design.strategy;

/**
 * 功能描述：策略一：抽A
 *
 * @author shiliu
 */
public class StrategyOne implements BuyStrategy{
    @Override
    public boolean draw()
    {
        System.out.println("抽A");
        // 中奖率为50%
        return Math.random() > 0.5;
    }
}
