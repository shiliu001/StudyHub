package cn.shiliu.design.strategy;

/**
 * 功能描述：策略二：抽B
 *
 * @author shiliu
 */
public class StrategyTwo implements BuyStrategy{
    @Override
    public boolean draw()
    {
        System.out.println("抽B");
        // 100%中奖
        return true;
    }
}
