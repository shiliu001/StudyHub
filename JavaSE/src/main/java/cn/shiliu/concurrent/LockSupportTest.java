package cn.shiliu.concurrent;

import java.util.concurrent.locks.LockSupport;

public class LockSupportTest {
    public static void main(String[] args) {
        LockSupport.park();
        LockSupport.unpark(new Thread());
    }
}
