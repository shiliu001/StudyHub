package cn.shiliu.design.template_method;

/**
 * 功能描述：老狗哥的作息（子类实现方法）
 *
 * @author shiliu
 */
public class BrotherSchedule extends Schedule{
    @Override
    void getUp()
    {
        System.out.println("老狗哥起床");
    }

    @Override
    void doWork()
    {
        System.out.println("老狗哥在做小学英语");
    }

    @Override
    void writeCode()
    {
        System.out.println("老狗哥在写牛逼的程序");
    }
}
