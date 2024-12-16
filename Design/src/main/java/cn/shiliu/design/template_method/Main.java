package cn.shiliu.design.template_method;

/**
 * 功能描述：测试类
 *
 * @author shiliu
 */
public class Main{
    public static void main(String[] args){
        Schedule brotherSchedule = new BrotherSchedule();
        Schedule laoGouSchedule = new LaoGouSchedule();
        brotherSchedule.live();
        System.out.println();
        laoGouSchedule.live();
    }
}
