package cn.shiliu.design.strategy;

/**
 * 功能描述：做某事的策略
 *
 * @author shiliu
 */
public interface BuyStrategy{
    // 抽奖，返回值表示是否抽中
    boolean draw();
}
