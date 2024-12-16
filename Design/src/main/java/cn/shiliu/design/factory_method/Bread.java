package cn.shiliu.design.factory_method;

/**
 * 功能描述：面包（具体食物）
 *
 * @author shiliu
 */
public class Bread extends Food{

    @Override
    public void wrap()
    {
        System.out.println("用塑料袋包装" + name);
    }
}
