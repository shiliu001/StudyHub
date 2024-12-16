package cn.shiliu.design.template_method;

/**
 * 功能描述：老狗的作息（子类实现方法）
 *
 * @author shiliu
 */
public class LaoGouSchedule extends Schedule{
    @Override
    void getUp()
    {
        System.out.println("老狗起床");
    }

    @Override
    void doWork()
    {
        System.out.println("老狗在做小学数学");
    }

    @Override
    void writeCode()
    {
        System.out.println("老狗在写HelloWorld");
    }
}
