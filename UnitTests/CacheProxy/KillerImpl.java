package CacheProxy;

public class KillerImpl implements Killer {
    @Override
    public void killCPU(int power) {
        for (int i = 0; i < 100 * power; i++) {
            try {
                wait(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("CPU killed!");
    }
}
