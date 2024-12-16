package cn.shiliu.design.decorator;

/**
 * 功能描述：鱼皮的照片
 *
 * @author shiliu
 */
public class YupiPhoto extends Photo{

    protected String content;

    public YupiPhoto(String content)
    {
        this.content = content;
    }

    @Override
    String decorate()
    {
        return "鱼皮的" + content + "主角：鱼皮";
    }

}
