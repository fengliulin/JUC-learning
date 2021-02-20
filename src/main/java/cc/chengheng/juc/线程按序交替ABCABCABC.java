package cc.chengheng.juc;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 编写一个程序，开启3个线程，这个三个线程的ID分别为A、B、C，
 * 每个线程将自己的id在屏幕上打印10遍，要求输出结果必须按顺序显示
 *  如：ABCABCABC.... 依次递归
 */
public class 线程按序交替ABCABCABC {
    public static void main(String[] args) {
        AlternateDemo ad = new AlternateDemo();

        new Thread(() -> {
            for (int i = 0; i <= 10; i++) {
                ad.loopA(i);
            }
        }, "A").start();

        new Thread(() -> {
            for (int i = 0; i <= 10; i++) {
                ad.loopB(i);
            }
        }, "B").start();

        new Thread(() -> {
            for (int i = 0; i <= 10; i++) {
                ad.loopC(i);
                System.out.println("===================");
            }

        }, "C").start();
    }
}

class AlternateDemo {
    /**
     * 当前正在执行线程的标记
     */
    private int number = 1;

    /**
     * 获取Lock锁
     */
    private Lock lock = new ReentrantLock();

    /**
     * 获取三个条件变量，控制线程的等待和唤醒
     */
    private Condition condition1 = lock.newCondition();
    private Condition condition2 = lock.newCondition();
    private Condition condition3 = lock.newCondition();


    /**
     * 初始 number 等1， 当开启三个线程的时候，其他的线程都不成立，等待被唤醒
     *
     * 第一个number控制的 不等1 为falsh， 继续执行，执行之后重制number的值，并且唤醒，判断为falsh在执行，在
     * 重置number的值，在唤醒，依次类推，来控制线程交替执行
     */



    /**
     *
     * @param totalLoop 循环第几轮
     */
    public void loopA(int totalLoop) {
        lock.lock();
        try {
            // 1、判断
            if (number != 1) {
                condition1.await();
            }

            // 2、打印
            for (int i = 0; i < 1; i++) {
                System.out.println(Thread.currentThread().getName() + " \t" + i + "\t" +totalLoop);
            }

            // 3、唤醒
            number = 2;
            condition2.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    /**
     *
     * @param totalLoop 循环第几轮
     */
    public void loopB(int totalLoop) {
        lock.lock();
        try {
            // 1、判断
            if (number != 2) {
                condition2.await();
            }

            // 2、打印
            for (int i = 0; i < 1; i++) {
                System.out.println(Thread.currentThread().getName() + " \t" + i + "\t" + totalLoop);
            }

            // 3、唤醒
            number = 3;
            condition3.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    /**
     *
     * @param totalLoop 循环第几轮
     */
    public void loopC(int totalLoop) {
        lock.lock();
        try {
            // 1、判断
            if (number != 3) {
                condition3.await();
            }

            // 2、打印
            for (int i = 0; i < 1; i++) {
                System.out.println(Thread.currentThread().getName() + " \t" + i + "\t" + totalLoop);
            }

            // 3、唤醒
            number = 1;
            condition1.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}
