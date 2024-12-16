package cn.shiliu.design.composite;

/**
 * 功能描述：文件
 *
 * @author shiliu
 */
public class File extends Entry{

    File(String name)
    {
        super(name);
    }

    // 输出文件名
    @Override
    void getName(int depth)
    {
        for (int i = 0; i < depth; i++)
        {
            System.out.print("--");
        }
        System.out.println("文件：" + this.name);
    }
}
