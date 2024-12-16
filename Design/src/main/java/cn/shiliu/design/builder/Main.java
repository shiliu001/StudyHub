package cn.shiliu.design.builder;

/**
 * 功能描述：测试类
 *
 * @author shiliu
 */
public class Main{
    public static void main(String[] args){
        // 两个建造者
        GameBuilder yupi = new YupiGameBuilder();
        GameBuilder laoGou = new LaoGouGameBuilder();
        // 创建技术总监，让鱼皮开发
        Director director = new Director(yupi);
        // 建造
        director.buildGame();
        // 让老狗开发
        director.setBuilder(laoGou);
        director.buildGame();
    }
}
