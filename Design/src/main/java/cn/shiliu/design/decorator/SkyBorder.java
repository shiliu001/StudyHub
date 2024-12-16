package cn.shiliu.design.decorator;

/**
 * 功能描述：天空边框
 *
 * @author shiliu
 */
public class SkyBorder extends Border{
    public SkyBorder(Photo photo)
    {
        super(photo);
    }

    @Override
    String decorate()
    {
        return "-- " + photo.decorate() + " --";
    }
}
