package cn.shiliu.design.decorator;

/**
 * 功能描述：装饰边框
 *
 * @author shiliu
 */
public abstract class Border extends Photo{
    protected Photo photo;

    public Border(Photo photo)
    {
        this.photo = photo;
    }
}
