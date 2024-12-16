package cn.shiliu.design.iterator;

/**
 * 功能描述：迭代器接口
 *
 * @author shiliu
 */
public interface Iterator{
    // 是否已遍历结束
    boolean hasNext();
    // 获得下一个元素
    Object next();
}
