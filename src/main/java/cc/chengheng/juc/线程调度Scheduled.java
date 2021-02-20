package cc.chengheng.juc;

import java.util.Random;
import java.util.concurrent.*;

public class 线程调度Scheduled {
    public static void main(String[] args) {
        ScheduledExecutorService pool = Executors.newScheduledThreadPool(5);

        for (int i = 0; i < 5; i++) {
            ScheduledFuture<Object> result = pool.schedule(new Callable<Object>() {
                @Override
                public Object call() throws Exception {
                    int num = new Random().nextInt(100); // 生成随机数
                    System.out.println(Thread.currentThread().getName() + " : " + num);
                    return num;
                }
            }, 3, TimeUnit.SECONDS);// 延迟3秒执行

            try {
                System.out.println(result.get());
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }


        pool.shutdown();
    }
}
