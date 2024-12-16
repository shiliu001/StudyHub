package cn.shiliu.design.adapter;

/**
 * 功能描述：二孔插头（已有类，被适配的类）
 *
 * @author shiliu
 */
public class TwoHolesPlug{
    // 充电功能
    void charging(){
        System.out.println("二孔插头：我能充电！");
        System.out.println("二孔插头：我只能插二孔插座！");
    }
}
