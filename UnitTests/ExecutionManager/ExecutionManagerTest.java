package ExecutionManager;

import ThreadPool.ExecutionManager;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.Callable;



public class ExecutionManagerTest {

    ExecutionManager executionManager = new ExecutionManager(2);

    private class MyRunnableStdout implements Runnable {

        private String s;
        MyRunnableStdout(String s){
            this.s = s;
        }
        @Override
        public void run() {
            System.out.println(s);
            try {
                this.wait(1000);
            } catch (Exception e) {
                //e.printStackTrace();
            }
        }
    }

    private class MyCallableStdout implements Callable {

        private String s;
        MyCallableStdout(String s){
            this.s = s;
        }

        @Override
        public Object call() throws Exception {
            System.out.println(s);
            return null;
        }
    }

    @Test
    public void unit1(){

        Collection<Runnable> tasks = new ArrayList<>();

        for (int i = 0; i < 100; i++) {
            tasks.add(new MyRunnableStdout( Math.random() + ""));
        }
        Runnable[] a = new Runnable[tasks.size()];

        MyRunnableStdout callable = new MyRunnableStdout("Successfully done!");
        executionManager.execute(callable, tasks.toArray(a));
    }
}