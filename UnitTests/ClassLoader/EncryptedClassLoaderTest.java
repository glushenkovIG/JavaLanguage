package ClassLoader;

import org.junit.Test;

import java.io.*;

public class EncryptedClassLoaderTest {
    @Test
    public void unit1() throws IllegalAccessException, InstantiationException, IOException, ClassNotFoundException {
        String pathToCompiledTestClass = "/Users/Ivan/Documents/MIPT/6_term/BIT_Java/JavaLanguage/out/production/JavaLanguage/";
        String pathToEncryptedClassesDirectory = "/Users/Ivan/Documents/MIPT/6_term/BIT_Java/JavaLanguage/src/";

        int key = 1;

        EncryptedClassLoader classLoader = new EncryptedClassLoader(
                key,
                new File(pathToCompiledTestClass),
                getClass().getClassLoader());

        //classLoader.loadClassData("TestClass");
        //os.write(encrypt(new File(pathToCompiledTestClass), key));

        System.out.println(classLoader.findClass("TestClass").newInstance());
    }


}