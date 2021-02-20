package cc.chengheng.juc;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * CopyOnWriteArrayList 写入并复制
 */
public class TestCopyOnWriteArrayList {
    public static void main(String[] args) {
        HelloThread ht = new HelloThread();
        for (int i = 0; i < 10; i++) {
            new Thread(ht).start();
        }
    }
}

class HelloThread implements Runnable {
    // 包装方式，就是把方法变成了同步方法
//    private static List<String> list = Collections.synchronizedList(new ArrayList<>());

    // 用这个就不会产生并发修改异常
    // 当你每次add，添加时，它会在底层复制一个新的列表，然后在添加，每次写入都会复制
    // 每次添加都复制，效率比较低，考虑线程安全的时候可以考虑
    private static CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>();

    static {
        list.add("AA");
        list.add("BB");
        list.add("CC");
    }

    @Override
    public void run() {
        Iterator<String> it = list.iterator();
        while (it.hasNext()) {
            String next = it.next();
            System.out.println(next);
            list.add("AA");
        }
    }
}