package cc.chengheng.juc;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * 线程池：提供了一个线程队列，队列中保存者所有等待状态的线程。避免了创建与销毁额外开销，提高了响应的速度
 *
 * 线程池的体系结构：
 *  java.util.concurrent.Executor: 负责线程的使用与调用的根接口
 *          |--ExecutorService 子接口：线程池的主要接口
 *              |--ThreadPoolExecutor实现类
 *              |--ScheduledExecutorService 子接口：负责线程调度
 *                  |--ScheduledThreadPoolExecutor 实现类
 *  工具类：Executors
 *      Executors.newCachedThreadPool()（缓存线程池，线程池的数量不固定，可以根据需求自动的更改数量）
 *      Executors.newFixedThreadPool(int) （固定大小的线程池）
 *      Executors.newSingleThreadExecutor()（创建单个线程池，线程池中只有一个线程）
 *
 *  ScheduledThreadPoolExecutor new ScheduledThreadPoolExecutor() 创建固定大小线程，可以延迟或定时的执行任务
 */
public class 线程池ThreadPool {
    public static void main(String[] args) {
        // 1、创建线程池
        ExecutorService pool = Executors.newFixedThreadPool(5); // 5个固定的线程池

        List<Future<Integer>> list = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            // 真是对象用的是FutureTask
            Future<Integer> future = pool.submit(new Callable<Integer>() {
                @Override
                public Integer call() throws Exception {
                    int sum = 0;
                    for (int i = 0; i < 100; i++) {
                        sum += i;
                    }
                    return sum;
                }
            });
            list.add(future);
        }

        for (Future<Integer> future : list) {
            try {
                System.out.println(future.get());
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }

        pool.shutdown();

       /* ThreadPoolDemo tpd = new ThreadPoolDemo();

        // 2、为线程池中的线程分配任务
        for (int i = 0; i <= 10; i++) {
            pool.submit(tpd); // 提交任务，执行线程
        }

        // 3、关闭线程池
        pool.shutdown();*/
    }

//    new Thread(tpd).start();
//    new Thread(tpd).start();
}

class ThreadPoolDemo implements Runnable {

    private int i = 0;

    @Override
    public void run() {
        while (i <= 100) {
            System.out.println(Thread.currentThread().getName() + ":" + i++);
        }
    }
}