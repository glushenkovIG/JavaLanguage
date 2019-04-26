package ThreadPool;

public class Mutex {
    private boolean inside = false;

    public synchronized void lock() {
        while (inside) {
            try {
                this.wait();
            } catch (Exception ignore) {}
        }

        inside = true;
    }

    public synchronized void unlock() {
        inside = false;
        this.notifyAll();
    }
}
