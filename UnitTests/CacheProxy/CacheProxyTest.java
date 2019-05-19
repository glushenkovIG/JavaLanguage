package CacheProxy;

import org.junit.Test;

import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;

public class CacheProxyTest {
    @Test
    public void unit1(){
        CacheProxy cacheProxy = new CacheProxy(new Class[]{Service.class, Killer.class});
        Service service = cacheProxy.cache(new MyServiceImpl());

        double r1 = service.doHardWork(10); //считает результат
        double r2 = service.doHardWork(5);  //считает результат
        double r3 = service.doHardWork(10);
    }

}