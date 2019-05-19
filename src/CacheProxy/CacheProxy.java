package CacheProxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
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

class MyInvocationHandler implements InvocationHandler {
    private Object delegate;

    <T> MyInvocationHandler(T delegate){
        this.delegate = delegate;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("Started " + method.getName());
        Object result = method.invoke(delegate, args);
        System.out.println("Finished " + method.getName() + ". \n Result " + result);
        return result;
    }
}


