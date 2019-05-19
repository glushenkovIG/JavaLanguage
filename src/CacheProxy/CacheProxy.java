package CacheProxy;

import java.lang.reflect.Proxy;

public class CacheProxy {
    private ClassLoader loader = ClassLoader.getSystemClassLoader();
    private Class[] interfaces;

    CacheProxy(Class[] interfaces){
        this.interfaces = interfaces;
    }

    CacheProxy(ClassLoader loader, Class[] interfaces){

        this.loader = loader;
        this.interfaces = interfaces;
    }

    public <T> T cache(T service) {
        return (T) Proxy.newProxyInstance(
                ClassLoader.getSystemClassLoader(),
                interfaces,
                new MyInvocationHandler(service)
        );
    }
}


