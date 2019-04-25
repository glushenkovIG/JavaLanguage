package ThreadPool;

public class TaskException extends RuntimeException {
    public TaskException(Exception e){
        super(e);
    }
}
