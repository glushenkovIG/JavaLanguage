package CacheProxy;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;
import java.util.Date;
import java.util.List;

import static CacheProxy.MyCacheTypes.FILE;
import static CacheProxy.MyCacheTypes.IN_MEMORY;

enum MyCacheTypes{
    FILE, IN_MEMORY
}

@Target(ElementType.METHOD)
@interface Cache{
    MyCacheTypes cacheType() default IN_MEMORY;
    String fileNamePrefix() default "";
    boolean zip() default false;
    Class[] identityBy() default {};
    int listList() default 100;
}

interface Service {

    @Cache(cacheType = FILE, fileNamePrefix = "data", zip = true, identityBy = {String.class, double.class})
    List<String> run(String item, double value, Date date);

    @Cache(cacheType = IN_MEMORY, listList = 100_000)
    List<String> work(Object ... args);

    @Cache(cacheType = IN_MEMORY, listList = 100_000)
    double doHardWork(int amount, Object ... args);

}
