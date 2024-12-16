package cn.shiliu.design.prototype;

/**
 * 功能描述：漂亮的女人（具体类，实现了clone方法）
 *
 * @author shiliu
 */
public class BeautifulWoman extends Woman{
    // 克隆一个具体的女人
    @Override
    public Woman createClone()
    {
        try
        {
            return (Woman) clone();
        }
        // 注意必须支持克隆，否则会抛此异常
        catch (CloneNotSupportedException e)
        {
            e.printStackTrace();
            return null;
        }
    }
}
