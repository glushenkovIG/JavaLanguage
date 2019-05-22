package ThreadPool;

import java.util.Queue;

public class Context {
    private int tasksCount;
    private Queue<Runnable> tasks;
    private Counter failedTasks;
    private Counter completedTasks;
    private Counter interruptedTasks;

    public Context(Queue<Runnable> tasks, Counter failedTasks, Counter completedTasks){
        tasksCount = tasks.size();
        this.tasks = tasks;
        this.failedTasks = failedTasks;
        this.completedTasks = completedTasks;
        this.interruptedTasks =  new Counter();
    }

    int getCompletedTaskCount(){
        return completedTasks.get();
    }

    int getFailedTaskCount(){
        return failedTasks.get();
    }

    int getInterruptedTaskCount(){
        return interruptedTasks.get();
    }

    void interrupt(){
        while(tasks.poll() != null)
            interruptedTasks.add();
    }

    boolean isFinished() {
        return completedTasks.get() + interruptedTasks.get() == tasksCount;
    }
}
