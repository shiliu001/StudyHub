package cn.shiliu.design.template_method;

/**
 * 功能描述：作息时间表（父类制定方法模板）
 *
 * @author shiliu
 */
public abstract class Schedule{
    // 起床
    abstract void getUp();
    // 做作业
    abstract void doWork();
    // 敲代码
    abstract void writeCode();
    // 一天的作息，方法的流程模板
    final void live(){
        System.out.println("5:00 到了");
        getUp();
        System.out.println("6:00 到了");
        doWork();
        System.out.println("24:00 到了");
        writeCode();
    }
}
