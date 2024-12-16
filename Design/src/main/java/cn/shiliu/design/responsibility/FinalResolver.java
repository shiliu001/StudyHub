package cn.shiliu.design.responsibility;

/**
 * 功能描述：问题解决者（能否解决某种问题）
 *
 * @author shiliu
 */
public class FinalResolver extends Resolver{
    public FinalResolver(String name)
    {
        super(name);
    }

    @Override
    public boolean solve(Problem problem)
    {
        // 解决特定类型的问题
        if (problem.type == 3){
            return true;
        }
        return false;
    }
}
