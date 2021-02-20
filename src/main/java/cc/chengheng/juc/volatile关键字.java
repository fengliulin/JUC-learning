package cc.chengheng.juc;

public class volatile关键字 implements Runnable{

    public static void main(String[] args) {
        // 一个线程修改值--写
        volatile关键字 td = new volatile关键字();
        new Thread(td).start();

        // 这个线程【读】取值
        while (true) { /* true 底层执行的速度非常的快，以至于 td.isFlag() 拿不到线程1 修改的值 */

            if (td.isFlag()) {
                System.out.println("----------");
                break;
            }

            /*
            // 同步锁可以解决无法读取主存的信息。
            // 同步锁每次都保存可以刷新缓存，从主存中，也就是堆中读取数据，写入到自己的缓存里
            synchronized (td) {
                if (td.isFlag()) {
                    System.out.println("----------");
                    break;
                }
            }*/
        }
    }



    /**
     * volatile保存内存可见性
     *
     * volatile 关键字，当多个线程进行操作共享数据时候，可以保存内存中的数据可见。
     *
     * 实际上是调用计算机底层的内存屏障
     *
     * 内存屏障：
     * 内存屏障，也称内存栅栏，内存栅障，屏障指令等， 是一类同步屏障指令，
     * 是CPU或编译器在对内存随机访问的操作中的一个同步点，
     * 使得此点之前的所有读写操作都执行后才可以开始执行此点之后的操作。
     *
     * volatile 修饰的变量就在主存中完成的，而不是在自己线程的缓存完成
     *
     * volatile 效率会降低（因为jvm底层有一个优化，叫重排序，volatile修饰以后不能重排序了），但是比synchronized锁的效率高
     */
    private volatile boolean flag = false;

    @Override
    public void run() {

        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        flag = true;

        System.out.println("flag = " + isFlag());

    }


    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }
}
