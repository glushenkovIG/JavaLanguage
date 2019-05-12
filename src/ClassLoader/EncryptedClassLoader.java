package ClassLoader;

import java.io.*;
import java.rmi.server.RMIClassLoader;

public class EncryptedClassLoader extends ClassLoader {
    private final int key;

    private final File dir;

    public EncryptedClassLoader(int key, File dir, ClassLoader parent) {
        super(parent);
        this.key = key;
        this.dir = dir;
    }

    @Override
    public Class<?> findClass(String name){
        // пробуем загрузить родителем
        try {
            return super.findClass(name);
        } catch (ClassNotFoundException e) {
            //e.printStackTrace();
        }

        // не вышло, загружаем сами
        byte[] bt = new byte[0];
        try {
            bt = loadClassData(name);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        bt = decrypt(bt);
        return defineClass(name, bt, 0, bt.length);
    }

    private byte[] encrypt(byte[] bt){
        return bt;
    }

    private byte[] decrypt(byte[] bt) {
        return bt;
    }

    byte[] loadClassData(String className) throws FileNotFoundException {
        //read class
        InputStream is = new FileInputStream(dir.getAbsolutePath() + "/" + className.replace(".", "/") + ".class");
        ByteArrayOutputStream byteSt = new ByteArrayOutputStream();
        //write into byte
        int len = 0;
        try {
            while((len = is.read()) != -1){
                byteSt.write(len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        //convert into byte array
        return byteSt.toByteArray();
    }
}