package CacheProxy;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Date;
import java.util.List;

import static CacheProxy.MyCacheTypes.FILE;
import static CacheProxy.MyCacheTypes.IN_MEMORY;

enum MyCacheTypes{
    FILE, IN_MEMORY
}

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@interface Cache{
    MyCacheTypes cacheType() default IN_MEMORY;
    String fileNamePrefix() default "";
    boolean zip() default false;
    Class[] identityBy() default {String.class};
    int listList() default 100;
}

interface Service {

    @Cache(cacheType = FILE, fileNamePrefix = "data", zip = true, identityBy = {String.class, double.class})
    List<String> run(String item, double value, Date date);

    @Cache(cacheType = IN_MEMORY, listList = 100_000)
    List<String> work(Object ... args);

    @Cache(cacheType = IN_MEMORY,
            fileNamePrefix = "/Users/Ivan/Documents/MIPT/6_term/BIT_Java/JavaLanguage/src/CacheProxy/TestOutput/out.txt",
            listList = 100_000,
            identityBy = {int.class})
    double doHardWork(int a, String b);

}
