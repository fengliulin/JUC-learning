package cc.chengheng.juc;

import java.util.concurrent.CountDownLatch;

/**
 * 闭锁：当你要完成一些运算，如果其他所有的线程的运算没有完成，那么就等待， 其他线程的运算都完成了，我在执行
 */
public class TestCountDownLatch闭锁 {
    public static void main(String[] args) {
        final CountDownLatch latch = new CountDownLatch(5); // 每次一个线程执行都会递减1，内部维护了一个 private volatile int state;
        LatchDemo latchDemo = new LatchDemo(latch);

        long start = System.currentTimeMillis();

        for (int i = 0; i < 5; i++) {
            new Thread(latchDemo).start();
        }

        // 线程没有执行完，闭锁，让主线程等待
        try {
            latch.await(); // 等待其他线程完毕
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        long end = System.currentTimeMillis();

        System.out.println("耗费时间为:" + (end - start));

    }
}

class LatchDemo implements Runnable {
    private CountDownLatch latch;

    public LatchDemo(CountDownLatch latch) {
        this.latch = latch;
    }

    @Override
    public void run() {
        // 多个线程访问也会有线程安全问题
        synchronized (this) {
            try {
                for (int i = 0; i < 50000; i++) {
                    if (i % 2 == 0) {
                        System.out.println(i);
                    }
                }
            } finally {
                latch.countDown(); // 必须让它执行，一定要执行就放到finally里
            }
        }
    }
}
