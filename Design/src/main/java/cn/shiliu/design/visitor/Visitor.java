package cn.shiliu.design.visitor;

/**
 * 功能描述：访客
 *
 * @author shiliu
 */
public interface Visitor{
    // 访问公园
    void visit(Park park);
    // 吃桃
    void eatPeach(Park park);
}
