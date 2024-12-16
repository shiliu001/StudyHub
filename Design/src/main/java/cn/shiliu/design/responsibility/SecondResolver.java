package cn.shiliu.design.responsibility;

/**
 * 功能描述：问题解决者（能否解决某种问题）
 *
 * @author shiliu
 */
public class SecondResolver extends Resolver{
    public SecondResolver(String name)
    {
        super(name);
    }

    @Override
    public boolean solve(Problem problem)
    {
        // 解决特定类型的问题
        if (problem.type == 2){
            return true;
        }
        return false;
    }
}
