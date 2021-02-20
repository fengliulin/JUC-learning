package cc.chengheng.juc;

import sun.rmi.runtime.Log;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;
import java.util.stream.LongStream;

public class ForkJoinPool模拟 {
    public static void main(String[] args) {


        // 自己模拟的 ------- 一个大任务拆分了一个一个小任务执行，在合并
        Instant start = Instant.now();
        ForkJoinPool pool = new ForkJoinPool();

        ForkJoinTask<Long> task = new ForkJoinSumCalculate(0L, 100000000L);

        // 执行
        Long sum = pool.invoke(task);

        System.out.println(sum);

        Instant end = Instant.now();

        System.out.println("耗费时间为：" + Duration.between(start, end).toMillis());

        //=======================================================================================


        System.out.println("=============下面是普通的for直接执行的没有拆分对比=================");

        // ========================================计算时间====================================================
        /*Instant start = Instant.now();

        long sum = 0l;
        for (long i = 0L; i <= 10000000L; i++) {
            sum += i;
        }
        System.out.println(sum);

        Instant end = Instant.now();

        System.out.println("耗费时间为：" + Duration.between(start, end).toMillis());*/
        //===================================================


        System.out.println("=====================JDK1.8：优化了之前的ForkJoin，不必再自定义Fork-Join任务类============================");
        Instant start1 = Instant.now();
        Long sumA = LongStream.rangeClosed(0l, 100000000L)
                                .parallel()
                                .reduce(0L, Long::sum);
        System.out.println(sumA);
        Instant end1 = Instant.now();
        System.out.println("耗费时间为：" + Duration.between(start1, end1).toMillis());
    }

}

// 自己模拟的
class ForkJoinSumCalculate extends RecursiveTask<Long> {

    private long start;
    private long end;

    private static final long THRESHOLD = 10000L; // 临界值

    public ForkJoinSumCalculate(long start, long end) {
        this.start = start;
        this.end = end;
    }

    @Override
    protected Long compute() {
        long length = end - start;

        if (length <= THRESHOLD) {
            long sum = 0L;

            for (long i = start; i <= end; i++) {
                sum += i;
            }
            return sum;

        } else {
            // 不断拆分，最后合并
            long middle = (start + end) / 2;
            ForkJoinSumCalculate left = new ForkJoinSumCalculate(start, middle);
            left.fork(); // 进行拆分，同时压入线程队列

            ForkJoinSumCalculate right = new ForkJoinSumCalculate(middle+1, end);
            right.fork();

            return left.join() + right.join(); // 小任务合并
        }
    }
}
