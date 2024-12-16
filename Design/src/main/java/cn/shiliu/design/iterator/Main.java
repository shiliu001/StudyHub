package cn.shiliu.design.iterator;

/**
 * 功能描述：测试类
 *
 * @author shiliu
 */
public class Main{
    public static void main(String[] args){
        BookShelf bookShelf = new BookShelf();
        Book book1 = new Book("六年级语文");
        Book book2 = new Book("大学语文");
        Book book3 = new Book("博士语文");
        bookShelf.addBook(book1).addBook(book2).addBook(book3);
        Iterator it = bookShelf.iterator();
        while (it.hasNext()){
            System.out.println(it.next());
        }
    }
}
