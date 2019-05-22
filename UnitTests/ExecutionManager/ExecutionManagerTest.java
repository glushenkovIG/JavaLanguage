package ExecutionManager;

import ThreadPool.ExecutionManager;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;



public class ExecutionManagerTest {

    ExecutionManager executionManager = new ExecutionManager(100);

    private class MyRunnableStdout implements Runnable {

        private String s;
        MyRunnableStdout(String s){
            this.s = s;
        }

        @Override
        public void run() {
            synchronized (this) {
                try {
                    this.wait(50);
                    //System.out.println(Math.random());
                } catch (Throwable e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Test
    public void unit1() throws InterruptedException {

        Collection<Runnable> tasks = new ArrayList<>();

        for (int i = 0; i < 3000; i++) {
            tasks.add(new MyRunnableStdout( Math.random() + ""));
        }
        Runnable[] a = new Runnable[tasks.size()];

        MyRunnableStdout runnable = new MyRunnableStdout("Successfully done!");
        //System.out.println((tasks.toArray(a)).length);
        executionManager.execute(runnable, tasks.toArray(a));
    }
    @Test
    public void unit2(){
        new Thread(()-> System.out.println("im very alive")).start();
    }
}