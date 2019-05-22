package ThreadPool;

import java.util.List;
import java.util.concurrent.BlockingQueue;

public class ScalableThreadPool {
    private BlockingQueue<Runnable> tasks;
    private int maxExtraThreadsAmount;
    private List<Thread> extraThreads;
    private int min;
    private int max;

    // задаём кол-во потоков от минимального к максимальному
    public ScalableThreadPool(int  min, int max){
        maxExtraThreadsAmount = max - min;
    }

    public void start(){
        // тоже самое что и в FixedThreadPool
        for(int i = 0; i < min; i++){
            Thread t = new Thread(new RunTasks(tasks));
            t.setDaemon(true);
            t.start();
        }
    }

    public void execute(Runnable runnable){
        // убъём мёртвые потоки
        for(Thread t : extraThreads)
            if(!t.isAlive()) {
                extraThreads.remove(t);
            }

        if(extraThreads.size() < maxExtraThreadsAmount && tasks.size() > 1){
            // все потоки заняты т.к. есть незабранная таска.
            Thread t = new Thread(new RunOneMoreTask(tasks));
            t.run();
            extraThreads.add(t);
        }
        tasks.add(runnable);
    }
}

class RunOneMoreTask implements Runnable{
    private BlockingQueue<Runnable> tasks;

    RunOneMoreTask(BlockingQueue<Runnable> tasks){
        this.tasks = tasks;
    }

    @Override
    public void run(){
        Runnable r;
        // помогаем пока есть таски
        while((r = tasks.poll()) != null){
            r.run();
        }
    }
}
