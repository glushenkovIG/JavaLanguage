package CacheProxy;

import java.io.*;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

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
            result = processCachedMethod(method, args);
        } else {
            result = method.invoke(delegate, args);
        }

        System.out.println("Finished " + method.getName() + ". \n Result " + result + "\n");
        return result;
    }

    private Object processCachedMethod(Method method, Object[] args) throws IllegalAccessException, InvocationTargetException {
        Object result = null;
        Cache cache = method.getAnnotation(Cache.class);

        switch (cache.cacheType()) {
            case FILE:
                try {
                    result = processFileStoreCase(method, args);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;

            case IN_MEMORY:
                result = processInMemoryStoreCase(method, args);
                break;

            default:
                break;
        }
        return result;
    }

    private Object processInMemoryStoreCase(Method method, Object[] args) throws IllegalAccessException, InvocationTargetException {
        Object result;
        if (cachedMethodsValues.containsKey(generateKey(method, args))) {
            System.out.println("Result from cached " + method.getName() + "\n");
            result = cachedMethodsValues.get(generateKey(method, args));
        } else {
            result = method.invoke(delegate, args);
            System.out.println("Caching method <" + method.getName() + "> with <" + Arrays.toString(args) + "> arguments");
            cachedMethodsValues.put(generateKey(method, args), result);
        }
        return result;
    }

    private Object processFileStoreCase(Method method, Object[] args) throws IOException, InvocationTargetException, IllegalAccessException {
        Object finalResult = null;

        // todo in separate function or with HashMap in terms of Single responsibility principle.
        Cache cache = method.getAnnotation(Cache.class);

        if (new File(cache.fileNamePrefix()).exists()) {
            if (!cache.zip()) {
                finalResult = loadFromFile(cache.fileNamePrefix());
            } else {
                finalResult = loadFromZip(cache.fileNamePrefix());
            }

        } else {
            finalResult = method.invoke(delegate, args);

            if (!(finalResult instanceof Serializable)) {
                IOException e = new IOException("Result of <" + method.getName() + "> is not serializable");
                throw e;
            }

            if (!cache.zip()) {
                storeInFile(finalResult, cache.fileNamePrefix());
            } else {
                storeInZip(finalResult, cache.fileNamePrefix());
            }
        }
        return finalResult;
    }

    private void storeInZip(Object result, String filePath) {
        try {

            FileOutputStream fos = new FileOutputStream(filePath);
            GZIPOutputStream gz = new GZIPOutputStream(fos);

            ObjectOutputStream oos = new ObjectOutputStream(gz);

            oos.writeObject(result);
            oos.close();

            System.out.println("Done");

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private Object loadFromZip(String filePath) {
        Object result = null;
        try {

            FileInputStream fos = new FileInputStream(filePath);
            GZIPInputStream gz = new GZIPInputStream(fos);

            ObjectInputStream oos = new ObjectInputStream(gz);

            result = oos.readObject();//todo maybe its a bad idea
            oos.close();

            System.out.println("Done");

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return result;
    }

    private void storeInFile(Object result, String filePath) {
        //todo normal io in files
        System.out.println("FileWriter with <" + filePath + "> fails, please check if your settings are correct");

        try {

            FileOutputStream f = new FileOutputStream(new File(filePath));
            ObjectOutputStream o = new ObjectOutputStream(f);

            // Write objects to file
            o.writeObject(result);

            o.close();
            f.close();

        } catch (FileNotFoundException e) {
            System.out.println("File not found");

        } catch (IOException e) {
            System.out.println("Error initializing stream");
        }
    }

    private Object loadFromFile(String fileName){
        Object ans = null;

        try {
            FileInputStream fi = new FileInputStream(new File(fileName));
            ObjectInputStream oi = new ObjectInputStream(fi);

            // Read objects
            ans = oi.readObject();

            oi.close();
            fi.close();

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return ans;
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
