package ThreadPool;

import java.util.concurrent.Callable;

public class Task<T>{

    private Callable<? extends T> callable;

    private volatile boolean isYetCalculated = false;
    private volatile boolean inProgress = false;


    // hb через po и hb на volatile
    private T result;
    private TaskException exception = null;

    public Task(Callable<? extends T> callable){
        this.callable = callable;
    }

    public T get() {
        synchronized (this) {
            if (isYetCalculated | inProgress) {
                try {
                    while (inProgress) {
                        this.wait();
                    }
                } catch (Exception ignored) {}

                if (exception != null) {
                    throw exception;
                }
                return result;
            } else if (!isYetCalculated & !inProgress){
                inProgress = true;
            }
        }
        // сюда по плану должен пройти только один поток который как раз и выполнит таску
        // проверим это:
        // Поток прошёл сюда только если значение не было предподсчитано и не было в процессе подсчёта

        try {
            result = callable.call();
        } catch (Exception e) {
            exception = new TaskException(e);
        } finally {
            isYetCalculated = true;
            inProgress = false;
            //теперь можно всех будить ибо они попадут в перую секцию и сразу либо бросят исключение либо выведут результат
            // todo разобраться что выполняется первым catch или finally,
            //  возможно будет разумным перенести эти 4 строчки просто вниз без файнали
            this.notifyAll();
        }

        // проверим не надо ли нам самим бросить эксепшн
        if (exception != null) {
            throw exception;
        }
        return result;
    }
}


