package cc.chengheng.juc.创建执行线程4中方式;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * 创建执行线程的方式三：实现 Callable 接口
 *              相较于实现Runnable接口方式，方法可以有返回值并且可以跑出异常，就这2点区别
 *  执行Callable 方式，需要 FutureTask 实现类的支持，用于接收计算结果.
 *          FutureTask 是 Future 接口的实现类
 */
public class Callable_3 {
    public static void main(String[] args) {
        ThreadDemo td = new ThreadDemo();

        // 接收计算后的结果
        FutureTask<Integer> result = new FutureTask<>(td);

        new Thread(result).start();

        // 接收线程计算后的结果
        Integer integer = null;
        try {
            integer = result.get(); // FutureTask会等待线程执行完毕，才会执行， 也可以用于闭锁的操作
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        System.out.println(integer);
    }
}

class ThreadDemo implements Callable<Integer> {
    @Override
    public Integer call() throws Exception {
        int sum = 0;
        for (int i = 0; i <= 100; i++) {
            System.out.println(i);
            sum += i;
        }
        return sum;
    }
}

/*class ThreadDemo implements Runnable {
    @Override
    public void run() {

    }
}*/
