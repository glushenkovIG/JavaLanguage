package CacheProxy;

import org.junit.Test;

public class CacheProxyTest {
    @Test
    public void unit1(){
        CacheProxy cacheProxy = new CacheProxy(new Class[]{Service.class, Killer.class});
        Service service = cacheProxy.cache(new MyServiceImpl());
/*
        double r1 = service.doHardWork(10, "weff"); //считает результат
        double r2 = service.doHardWork(5, "weff");  //считает результат
        double r3 = service.doHardWork(10, "weff"); // возвращает из кеша игнорируя появление второго аргумента
        double r4 = service.doHardWork(10, "mdak"); // считает новый
        */

        String r1 = service.doHardWork(10, "weff"); //считает результат
        String r2 = service.doHardWork(5, "weff");  //считает результат
        String r3 = service.doHardWork(10, "weff"); // возвращает из кеша игнорируя появление второго аргумента
        String r4 = service.doHardWork(10, "mdak"); // считает новый
    }

}