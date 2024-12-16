package cn.shiliu.design.iterator;

/**
 * 功能描述：书本（元素）
 *
 * @author shiliu
 */
public class Book{
    private String name;

    public Book(String name) {
        this.name = name;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    @Override
    public String toString()
    {
        return this.getName();
    }
}
