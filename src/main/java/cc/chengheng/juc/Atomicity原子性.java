package cc.chengheng.juc;

import java.util.concurrent.atomic.AtomicInteger;

public class Atomicity原子性 implements Runnable{
    public static void main(String[] args) {
        Atomicity原子性 ad = new Atomicity原子性();
        for (int i = 0; i < 10; i++) {
            new Thread(ad).start();
        }
    }

//    private int serialNumber = 0;

    private AtomicInteger serialNumber = new AtomicInteger();


    @Override
    public void run() {
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(Thread.currentThread().getName() + ":" + getSerialNumber());
    }


    public int getSerialNumber() {
        return serialNumber.getAndIncrement();
    }

}
