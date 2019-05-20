package CacheProxy;

import java.util.Date;
import java.util.List;

public class MyServiceImpl implements Service {
    public List<String> run(String item, double value, Date date) {
        return null;
    }

    @Override
    public List<String> work(Object... args) {
        return null;
    }

    @Override
    public String doHardWork(int a, String b) {
        return "HARD WORK DONE";
    }

    public List<String> work(String item) {
        return null;
    }
}
