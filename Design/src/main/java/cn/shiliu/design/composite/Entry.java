package cn.shiliu.design.composite;

/**
 * 功能描述：元素（把容器和内容都当作元素进行统一的处理）
 *
 * @author shiliu
 */
public abstract class Entry{
    String name;
    // 递归深度
    int depth;

    Entry(String name)
    {
        this.name = name;
    }

    // 输出名称
    abstract void getName(int depth);
}
