package cc.chengheng.juc;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * synchronized：隐私锁    1.5 之前用这两种
 *      1、同步代码块
 *      2、同步方法
 *
 * 同步锁：显示锁 		1.5 之后出现的
 * lock 通过lock() 方法上锁，必须通过 unlock() 方法释放锁，一般放到finally 里面
 */
public class 同步锁lock {
    public static void main(String[] args) {
        Ticket ticket = new Ticket();

        new Thread(ticket, "1号窗口").start();
        new Thread(ticket, "2号窗口").start();
        new Thread(ticket, "3号窗口").start();
    }
}

class Ticket implements Runnable {
    private int tick = 100;

    private Lock lock = new ReentrantLock();

    @Override
    public void run() {
        while (true) {
            lock.lock(); // 上锁
            try {
                if (tick > 0) {
                    /*try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }*/
                    System.out.println(Thread.currentThread().getName() + " 完成售票，剩余: " + --tick);
                }
            } finally {
                lock.unlock(); // 释放锁
            }

        }
    }
}
