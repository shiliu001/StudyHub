package cn.shiliu.design.factory_method;

/**
 * 功能描述：抽象工厂类（定义生产流程）
 *
 * @author shiliu
 */
public abstract class FoodFactory{
    // 生产流程
    final Food create(String name)
    {
        Food food = createFood();
        food.name = name;
        safetyProof(name);
        food.wrap();
        System.out.println();
        return food;
    }
    // 生产食物
    abstract Food createFood();
    // 安全证明
    abstract void safetyProof(String name);
}
