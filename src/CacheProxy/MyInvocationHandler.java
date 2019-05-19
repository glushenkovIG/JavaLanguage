package CacheProxy;

import java.io.*;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

class MyInvocationHandler implements InvocationHandler {
    private Object delegate;
    private Map<String, Object> cachedMethodsValues = new HashMap<>();

    <T> MyInvocationHandler(T delegate) {
        this.delegate = delegate;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("Started " + method.getName());
        Object result;
        if (method.isAnnotationPresent(Cache.class)) {
            if (cachedMethodsValues.containsKey(generateKey(method, args))) {
                System.out.println("Result from cached " + method.getName() + "\n");
                result = cachedMethodsValues.get(generateKey(method, args));
            } else {
                result = method.invoke(delegate, args);
                System.out.println("Caching method <" + method.getName() + "> with <" + Arrays.toString(args) + "> arguments");
                cachedMethodsValues.put(generateKey(method, args), result);
            }
        } else {
            result = method.invoke(delegate, args);
        }
        System.out.println("Finished " + method.getName() + ". \n Result " + result + "\n");
        return returnResult(method, result);
    }

    private Object returnResult(Method method, Object result) throws IOException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        Object finalResult = result;


        // todo in separate function or with HashMap in terms of Single responsibility principle.
        if(method.isAnnotationPresent(Cache.class)) {
            Cache cache = method.getAnnotation(Cache.class);
            if (cache.cacheType() == MyCacheTypes.FILE) {
                if (!(result instanceof Serializable)) {
                    IOException e = new IOException("Result of <" + method.getName() + "> is not serializable");
                    throw e;
                }

                if (!cache.zip()) {
                    //todo normal io in files
                    ObjectOutputStream writer = new ObjectOutputStream(new FileOutputStream(cache.fileNamePrefix()));
                    System.out.println("FileWriter with <" + cache.fileNamePrefix() + "> fails, please check if your settings are correct");
                    System.out.println(result.getClass());

                    ByteArrayOutputStream bos = new ByteArrayOutputStream();
                    ObjectOutput out = null;
                    try {
                        out = new ObjectOutputStream(bos);
                        out.writeObject(result);
                        out.flush();
                        writer.write(bos.toByteArray());
                        writer.close();
                    } finally {
                        try {
                            bos.close();
                        } catch (IOException ex) {
                            // ignore close exception
                        }
                    }

                } else if (cache.zip()) {
                    ZipOutputStream out = new ZipOutputStream(new FileOutputStream(new File(cache.fileNamePrefix())));
                    ZipEntry e = new ZipEntry((String) result);
                    out.putNextEntry(e);
                    out.close();
                }
            }
        }
        return finalResult;
    }

    String generateKey(Method method, Object[] args) {
        Cache cache = method.getAnnotation(Cache.class);

        StringBuilder resultKey = new StringBuilder(method.getName());
        for (Object o : cache.identityBy()) {
            //todo implement for multiple same types arguments
            if (Arrays.asList(method.getParameterTypes()).indexOf(o) != -1) {
                resultKey.append(args[Arrays.asList(method.getParameterTypes()).indexOf(o)]);
            }
        }
        return resultKey.toString();
    }
}
