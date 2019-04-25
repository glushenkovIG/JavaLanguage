package ThreadPool;

import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;

public class ExecutionManager {
    private int threadCount;

    public ExecutionManager(int threadCount){
        this.threadCount = threadCount;
    }

    public Context execute(Runnable callback, Runnable[] tasks){

        Counter succeed = new Counter();
        Counter failed = new Counter();
        Counter threadsFinished = new Counter();

        List<Thread> threads = new ArrayList<>();
        Queue<Runnable> tasks1 = new ConcurrentLinkedQueue<>(Arrays.asList(tasks));

        for(int i = 0; i < threadCount; i++)
            threads.add(new Thread(new RunThreadWithCallback(tasks1, succeed, failed, threadsFinished, threadCount, callback)));

        Context context = new Context(tasks1, failed, succeed);

        for(Thread thread : threads)
            thread.run();

        return context;
    }
}

class RunThreadWithCallback implements Runnable{

    private Queue<Runnable> tasks;

    private Counter succeed;
    private Counter failed;
    private Counter threadsFinished;

    private int threadsCount;

    private Runnable callback;

    RunThreadWithCallback(Queue<Runnable> tasks, Counter succeed, Counter failed, Counter threadsFinished, int threadsCount, Runnable callback){
        this.tasks = tasks;
        this.succeed = succeed;
        this.failed = failed;
        this.threadsFinished = threadsFinished;
        this.threadsCount = threadsCount;
        this.callback = callback;
    }

    @Override
    public void run(){
        Runnable r;
        while((r = tasks.poll()) != null){
            try{
                r.run();
                succeed.add();
            }
            catch(Exception e){
                failed.add();
            }
        }

        if(threadsCount == threadsFinished.addAndGet())
            callback.run();
    }
}

