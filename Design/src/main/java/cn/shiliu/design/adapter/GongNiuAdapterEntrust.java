package cn.shiliu.design.adapter;

/**
 * 功能描述：公牛适配器（委托模式）
 *
 * @author shiliu
 */
public class GongNiuAdapterEntrust implements ThreeHolesPlug{
    private TwoHolesPlug twoHolesPlug;

    GongNiuAdapterEntrust(TwoHolesPlug twoHolesPlug){
        this.twoHolesPlug = twoHolesPlug;
        System.out.println("适配器：二孔插头已经接入到我这儿了！");
    }

    @Override
    public void chargingWithThreeHoles()
    {
        // 使用二孔插头具有的充电功能
        twoHolesPlug.charging();
        System.out.println("适配器：我正在适配，别比比！");
        System.out.println("适配器：适配成功，你现在能用三孔插头充电了！");
    }
}