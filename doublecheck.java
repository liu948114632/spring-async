class Singleton{
    private volatile static Singleton instance = null;

    private Singleton() {

    }

    public static Singleton getInstance() {
        if(instance==null) {
            synchronized (Singleton.class) {
                if(instance==null)
                    instance = new Singleton();
            }
        }
        return instance;
    }
}

/**理解：
 *  1 为什么使用volatile关键字
 *    若没有使用关键字，线程1 在9行语句instance==null，会直接从线程1中的内存中拿到instance的值。而这个操作前，线程2 已经执行了new了一个instance。
 *    由于没有volatile关键字，主线程不会发出变量instance已经改变的信息，线程1 从内存中取到的instance还是null。然后再去创建一个instance
 *
 * 2 volatile并不会保证原子性
 *