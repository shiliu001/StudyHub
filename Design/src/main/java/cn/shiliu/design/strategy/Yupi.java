package cn.shiliu.design.strategy;

/**
 * 功能描述：使用策略去执行某种行为的主体（此处行为是抽奖，策略为抽奖的策略）
 *
 * @author shiliu
 */
public class Yupi{
    // 委托，便于替换策略
    private BuyStrategy strategy;

    Yupi(BuyStrategy strategy) {
        this.strategy = strategy;
    }

    public void setStrategy(BuyStrategy strategy)
    {
        this.strategy = strategy;
    }

    // 使用不同的策略去抽奖
    boolean nextDraw(){
        return strategy.draw();
    }
}
