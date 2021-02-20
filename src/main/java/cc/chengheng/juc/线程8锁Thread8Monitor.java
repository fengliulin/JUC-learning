package cc.chengheng.juc;

/**
 * 题目：判断打印的 "one" or "two" ?
 *
 * 1、两个普通同步方法，两个线程，标准打印，打印? one two
 * 2、新增Thread.sleep(3000); 给getOne， 打印? one two
 * 3、新增普通方法getThree() 打印 ? three one two
 * 4、两个普通的同步方法，两个Number对象，打印？ two one
 * 5、修改getOne()为静态同步方法，getTwo()普通同步方法，打印？ two one
 * 6、两个静态同步方法，一个Number对象,打印？? one two
 * 7、一个静态同步方法，一个非静态同步方法，两个Number对象,打印? two one
 * 8、两个静态同步方法，两个Number对象，打印? one two
 *
 * 线程八锁的关键：
 *      非静态方法的锁默认为 this;       静态方法的锁为 cc.chengheng.juc.Number.class
 *      某一个时刻内，只能有一个线程持有锁，无论几个方法。
 *
 *
 *

 * synchronized块中this和class对象的主要应用区别
 *      1.非静态方法可以使用this或者class对象来同步，而静态方法必须使用class对象来同步并且它们互不影响
 *      2.this是针对当前引用的对象，class对象就是原始对象本身
 *
 */
public class 线程8锁Thread8Monitor {
    public static void main(String[] args) {
        Number number = new Number();
        Number number2 = new Number();

        new Thread(() -> {
            number2.getOne();
        }).start();

        new Thread(()->{
            number2.getTwo();
        }).start();

        new Thread(() -> {
            number2.getThree();
        }).start();
    }
}

class Number {
    public static synchronized void getOne() {

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("one");
    }

    public static synchronized void getTwo() {
        System.out.println("two");
    }

    public static synchronized void getThree() {
        System.out.println("three");
    }
}