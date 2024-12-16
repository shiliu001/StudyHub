package file;

import org.apache.commons.io.monitor.FileAlterationMonitor;
import org.apache.commons.io.monitor.FileAlterationObserver;
import sun.misc.Unsafe;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author xiaowenpeng
 * @version 1.0
 * @date 2024/2/21
 * @description: TODO
 */
public class Main {
    public static void main(String[] args) throws Exception {
        FileAlterationMonitor fileAlterationMonitor=new FileAlterationMonitor(1000L);


        String filePath="C:\\Users\\79362\\Desktop\\xinhua\\test";
        File file=new File(filePath);

        List<File>listenFiles=new ArrayList<>();
        listenFiles.add(file);

        for (File listenFile : listenFiles) {
            FileAlterationObserver fileAlterationObserver=new FileAlterationObserver(listenFile);

            FileListener fileListener=new FileListener();

            fileAlterationObserver.addListener(fileListener);
            fileAlterationMonitor.addObserver(fileAlterationObserver);
        }

        fileAlterationMonitor.start();
    }
}
