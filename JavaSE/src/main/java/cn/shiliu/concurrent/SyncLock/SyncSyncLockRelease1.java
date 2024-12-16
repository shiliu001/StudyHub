package cn.shiliu.concurrent.SyncLock;

import lombok.SneakyThrows;
import org.openjdk.jol.info.ClassLayout;

public class SyncSyncLockRelease1 {
    public static void main(String[] args) throws InterruptedException {
        while (true) {
            Object obj = new Object();
            System.out.println("====A 加锁前===" + ClassLayout.parseInstance(obj).toPrintable());
            Thread A = new Thread() {
                @SneakyThrows
                @Override
                public void run() {
                    synchronized (obj) {
                        System.out.println("===A 加锁中===" + ClassLayout.parseInstance(obj).toPrintable());
                    }
                    Thread.sleep(200000);
                }
            };
            A.start();
            Thread.sleep(50000);
            System.out.println("====B加锁前===" + ClassLayout.parseInstance(obj).toPrintable());
            Thread B = new Thread() {
                @SneakyThrows
                @Override
                public void run() {
                    synchronized (obj) {
                        System.out.println("====B加锁中===" + ClassLayout.parseInstance(obj).toPrintable());
                        Thread.sleep(1000000);
                    }
                }
            };
            B.start();
        }
    }
}