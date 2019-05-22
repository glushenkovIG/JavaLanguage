package ThreadPool;

import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;

public class ExecutionManager {
    private int threadCount;

    public ExecutionManager(int threadCount){
        this.threadCount = threadCount;
        System.out.println("Constructor");
    }

    public Context execute(Runnable callback, Runnable[] tasks_) throws InterruptedException {

        Counter succeed = new Counter();
        Counter failed = new Counter();

        Queue<Runnable> tasks = new ConcurrentLinkedQueue<>(Arrays.asList(tasks_));

        Context context = new Context(tasks, failed, succeed);
        Runnable r;

        Counter startedThreadsCounter = new Counter();
        Counter terminatedThreadsCounter = new Counter();

        while((r = tasks.poll()) != null){
            while(startedThreadsCounter.get() - terminatedThreadsCounter.get() >= threadCount){
               synchronized (this) {
                   this.wait(10);
               }
            }
            Thread t = new Thread(new RunThreadWithCallback(r, succeed, failed, threadCount, callback, terminatedThreadsCounter));
            startedThreadsCounter.add();
            t.start();

            System.out.println(startedThreadsCounter.get() - terminatedThreadsCounter.get());
            if (succeed.get() + failed.get() ==  tasks_.length){
                callback.run();
            }
        }

        return context;
    }
}

class RunThreadWithCallback implements Runnable {

    private Runnable task;

    private Counter succeed;
    private Counter failed;
    private Counter terminatedThreadsCounter;

    private int threadsCount;

    private Runnable callback;

    RunThreadWithCallback(Runnable task, Counter succeed, Counter failed, int threadsCount,
                          Runnable callback, Counter terminatedThreadsCounter) {
        this.task = task;
        this.succeed = succeed;
        this.failed = failed;
        this.threadsCount = threadsCount;
        this.callback = callback;
        this.terminatedThreadsCounter = terminatedThreadsCounter;
    }

    @Override
    public void run(){
        try{
            Thread t = new Thread(task);
            t.start();
            synchronized (this){
                wait(40);
            }
            succeed.add();
        } catch (Exception e){
            failed.add();
        }
        terminatedThreadsCounter.add();
    }
}

