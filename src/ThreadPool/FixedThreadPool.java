package ThreadPool;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;

public class FixedThreadPool {
    // Количество потоков задается в конструкторе и не меняется.
    private List<Thread> threads;

    // Для отсутствия датарейсов
    private BlockingQueue<Runnable> tasks;
    private final int threadsCount;

    public FixedThreadPool(int threadsCount){
        threads = new ArrayList<>();
        this.threadsCount = threadsCount;
    }

    public void start(){
        for(int i = 0; i < threadsCount; i++){

            Thread newThread = new Thread(new  RunTasks(tasks));
            newThread.setDaemon(true); //чтоб не ждать завершения потока и мочь завершить JVM независимо

            newThread.run();

            threads.add(newThread);
        }
    }

    public void execute(Runnable runnable){
        tasks.add(runnable);
    }
}

class  RunTasks implements Runnable{
    private BlockingQueue<Runnable> tasks;

     RunTasks(BlockingQueue<Runnable> tasks) {
        this.tasks = tasks;
    }

    @Override
    public void run(){
        while(true){
            try {
                // метод take ждёт в случае если задач нет
                Runnable task = tasks.take();
                task.run();
            } catch(InterruptedException ignored){}
        }
    }
}