package file;

import org.apache.commons.io.monitor.FileAlterationListenerAdaptor;
import org.apache.commons.io.monitor.FileAlterationObserver;

import java.io.File;

/**
 * @author xiaowenpeng
 * @version 1.0
 * @date 2024/2/21
 * @description: TODO
 */
public class FileListener extends FileAlterationListenerAdaptor {
    @Override
    public void onDirectoryCreate(File directory) {
        super.onDirectoryCreate(directory);
    }

    @Override
    public void onDirectoryChange(File directory) {
        super.onDirectoryChange(directory);
    }

    @Override
    public void onDirectoryDelete(File directory) {
        super.onDirectoryDelete(directory);
    }

    @Override
    public void onFileCreate(File file) {
        System.out.println(file.getName()+"发生创建");
    }

    @Override
    public void onFileChange(File file) {
        System.out.println(file.getName()+"发生变化");
    }

    @Override
    public void onFileDelete(File file) {
        super.onFileDelete(file);
    }

    @Override
    public void onStop(FileAlterationObserver observer) {
        super.onStop(observer);
    }
}
