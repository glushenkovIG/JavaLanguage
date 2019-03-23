package BeanUtils;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;


public class TutorialExamples {
    public static void main(String[] args) {
        Class myObjectClass = Person.class;
        Method[] methods = Person.class.getDeclaredMethods();

        for (Method method : methods) {
            System.out.println("method = " + method.getName());
        }
    }
}
