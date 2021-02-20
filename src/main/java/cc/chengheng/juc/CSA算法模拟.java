package cc.chengheng.juc;

public class CSA算法模拟 {
    public static void main(String[] args) {

        final CompareAndSwap cas = new CompareAndSwap();

        for (int i = 0; i < 100; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    int expectedValue = cas.get();
                    boolean b = cas.compareAndSet(expectedValue, (int) (Math.random() * 101));
                    System.out.println(b);
                }
            }).start();
        }
    }
}

/**
 * synchronized 方法，模式CSA算法，但是jvm底层没有，用的硬件的东西，不知道对不对，暂时这么理解
 */
class CompareAndSwap {
    private int value;

    /**
     * 获取内存值
     * @return 内存值
     */
    public synchronized int get() {
        return value;
    }

    /**
     * 比较
     * @param expectedValue 期望值
     * @param newValue 新值
     * @return 内存值
     */
    public synchronized int compareAndSwap(int expectedValue, int newValue) {
        int oldValue = value;

        // 读取的旧的内存值等于期望值就更新
        if (oldValue == expectedValue) {
            this.value = newValue;
        }

        return oldValue;
    }

    /**
     * 设置，期望值和内存值比较
     * @param expectedValue 期望值
     * @param newValue 新值
     * @return 如果为true，就更新值，否则什么也不做
     */
    public synchronized boolean compareAndSet(int expectedValue, int newValue) {
        return expectedValue == compareAndSwap(expectedValue, newValue);
    }
}
