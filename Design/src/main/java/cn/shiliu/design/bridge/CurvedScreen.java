package cn.shiliu.design.bridge;

/**
 * 功能描述：曲屏显示器（对显示器功能的扩展）
 *
 * @author shiliu
 */
public class CurvedScreen extends Screen{

    CurvedScreen(ScreenImpl screen)
    {
        super(screen);
    }

    // 曲屏显示
    void curvedDisplay() {
        System.out.println("曲面屏");
    }
}
