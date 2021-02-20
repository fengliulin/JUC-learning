package cc.chengheng.juc;

/**
 * 与一个参数版本中一样，可能会产生中断和虚假唤醒，并且应始终在循环中使用此方法：
 *      synchronized (obj) {
 *          while (<condition does not hold>)
 *              obj.wait();
 *          ... //执行合适的代码
 *      }
 */
public class 虚假唤醒_生产者和消费者案例 {
    public static void main(String[] args) {
        Clerk clerk = new Clerk();

        Producer producer = new Producer(clerk);
        Consumer consumer = new Consumer(clerk);

        new Thread(producer, "生产者A").start();
        new Thread(consumer, "消费者B").start();

        new Thread(producer, "生产者C").start();
        new Thread(consumer, "消费者D").start();
    }
}

/**
 * 店员
 */
class Clerk {
    /**
     * 产品
     */
    private int product = 0;

    /**
     * 进货
     *  访问共享数据，出现安全问题，所以方法上加上 synchronized
     */
    public synchronized void get() {
        while (product >= 1) {
            System.out.println("产品已满");
            try {
                this.wait(); // 产品已经满了，当前线程等待
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println(Thread.currentThread().getName() + " : " + ++product);
        this.notifyAll(); // 产品已经生产好了，当前线程等待
    }

    /**
     * 销售
     */
    public synchronized void sale() {
        while (product <= 0) {
            System.out.println("缺货!");
            try {
                this.wait(); // 产品已经卖完了，当前线程等待， 会释放锁，下一个线程进来，又等待，释放锁，在被另一个线程唤醒，就会产生负数
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println(Thread.currentThread().getName() + " : " + --product);
        this.notifyAll(); // 产品已经卖完了，当前线程等待
    }
}

/**
 * 生产者
 */
class Producer implements Runnable{
    /**
     * 店员
     */
    private Clerk clerk;

    public Producer(Clerk clerk) {
        this.clerk = clerk;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < 2; i++) {
            clerk.get(); // 店员获取生产出来的商品
        }
    }
}

/**
 * 消费者
 */
class Consumer implements Runnable {
    /**
     * 店员
     */
    private Clerk clerk;

    public Consumer(Clerk clerk) {
        this.clerk = clerk;
    }

    @Override
    public void run() {
        for (int i = 0; i < 1; i++) {
            clerk.sale(); // 店员销售产品
        }
    }
}