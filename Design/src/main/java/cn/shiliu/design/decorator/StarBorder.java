package cn.shiliu.design.decorator;

/**
 * 功能描述：小星星边框
 *
 * @author shiliu
 */
public class StarBorder extends Border{
    public StarBorder(Photo photo)
    {
        super(photo);
    }

    @Override
    String decorate()
    {
        return "** " + photo.decorate() + " **";
    }
}
