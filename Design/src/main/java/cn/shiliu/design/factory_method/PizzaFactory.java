package cn.shiliu.design.factory_method;

/**
 * 功能描述：披萨工厂（具体工厂）
 *
 * @author shiliu
 */
public class PizzaFactory extends FoodFactory{
    // 具体生产某种食物
    @Override
    Food createFood()
    {
        return new Pizza();
    }

    // 具体的安全证明
    @Override
    void safetyProof(String name)
    {
        System.out.println("产品名：" + name + " 该披萨保证安全");
    }
}
