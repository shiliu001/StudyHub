package cn.shiliu.design.factory_method;

/**
 * 功能描述：披萨（具体食物）
 *
 * @author shiliu
 */
class Pizza extends Food{
    @Override
    public void wrap()
    {
        System.out.println("用盒子包装" + name);
    }
}
