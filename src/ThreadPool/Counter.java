package ThreadPool;

class Counter{
    private int count = 0;

    Counter(){}

    synchronized void add(){
        count++;
    }

    synchronized int get(){
        return count;
    }

    synchronized int addAndGet(){
        return ++count;
    }
}
