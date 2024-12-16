package cn.shiliu.design.factory_method;

/**
 * 功能描述：食物（一类产品）
 *
 * @author shiliu
 */
public abstract class Food{
    // 产品名称
    String name;

    // 食物的包装方法
    abstract void wrap();
}
