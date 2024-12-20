package cn.shiliu.design.factory_method;

/**
 * 功能描述：测试类
 *
 * @author shiliu
 */
public class Main{
    public static void main(String[] args){
        FoodFactory breadFoodFactory = new BreadFactory();
        Food bread1 = breadFoodFactory.create("脏脏包");
        Food bread2 = breadFoodFactory.create("汉堡包");
        FoodFactory pizzaFoodFactory = new PizzaFactory();
        Food pizza1 = pizzaFoodFactory.create("夏威夷披萨");
        Food pizza2 = pizzaFoodFactory.create("金枕榴莲披萨");
    }
}
