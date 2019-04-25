package ThreadPool;

import java.util.concurrent.Callable;

public class Task<T>{

    private Callable<? extends T> callable;
    private volatile T result;
    private volatile boolean isYetCalculated;
    private TaskException exception;

    public Task(Callable<? extends T> callable){
        this.callable = callable;
    }

    public T get(){
        if(!isYetCalculated) synchronized (this) {
            // проверим не надо ли нам бросить эксепшн
            if(exception != null) {
                throw exception;
            }

            // т.к. пока мы сравнивали кто-то мог изменить результат нам надо ещё раз проверить что всё чисто
            if (!isYetCalculated) {
                try {
                    isYetCalculated = true;
                    result = callable.call();

                } catch (Exception e) {
                    exception = new TaskException(e);
                    throw (exception);
                }
            }
        }
        return result;
    }
}


