package cc.chengheng.juc;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * ReadWriteLock 读写锁
 *  一个写 另一个也写 操作要互斥
 *  一个读一个写，也要互斥
 *
 *  一个读，另一个也读，不需要互斥
 */
public class ReadWriteLock读写锁 {
    public static void main(String[] args) {
        ReadWriteLockDemo rw = new ReadWriteLockDemo();

        new Thread(() -> {
            rw.set((int)(Math.random() * 101));
        }, "Write:").start();

        for (int i = 0; i < 100; i++) {
            new Thread(() -> {
                rw.get();
            }).start();
        }
    }
}

class ReadWriteLockDemo {
    private int number = 0;

    private ReadWriteLock lock = new ReentrantReadWriteLock();

    // 读
    public void get() {
        lock.readLock().lock(); // 上锁

        try {
            System.out.println(Thread.currentThread().getName() + ":" + number);
        } finally {
            lock.readLock().unlock(); // 释放锁
        }
    }

    // 写
    public void set(int number) {
        lock.writeLock().lock();

        try {
            System.out.println(Thread.currentThread().getName());
            this.number = number;
        } finally {
            lock.writeLock().unlock();
        }
    }
}
