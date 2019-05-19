package CacheProxy;

import java.util.Date;
import java.util.List;

import static CacheProxy.MyCacheTypes.IN_MEMORY;

public class MyServiceImpl implements Service {
    public List<String> run(String item, double value, Date date) {
        return null;
    }

    @Override
    public List<String> work(Object... args) {
        return null;
    }

    @Override
    public double doHardWork(int a, String b) {
        return 0;
    }

    public List<String> work(String item) {
        return null;
    }
}
