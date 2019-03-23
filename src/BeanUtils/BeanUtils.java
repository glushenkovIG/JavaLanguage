package BeanUtils;

import org.junit.Assert;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Collection;

import static java.lang.Character.getType;

public class BeanUtils {


    /**
     * Scans object "from" for all getters. If object "to"
     * contains correspondent setter, it will invoke it to set property value for "to" which equals to the property of "from".
     * The type in setter should be compatible to the value returned
     * by getter (if not, no invocation performed).
     * Compatible means that parameter type in setter should be the same or be superclass of the return type of the getter.
     * The method takes care only about public methods.
     *
     * @param to   Object which properties will be set.
     * @param from Object which properties will be used to get values.
     */

    public static void assign(Object to, Object from) {
        Collection<Method> fromMethods = Arrays.asList(from.getClass().getMethods());
        Collection<Method> toMethods = Arrays.asList(to.getClass().getMethods());
        for (Method fromDescriptor : fromMethods) {
            for (Method toDescriptor : toMethods) {
                try {
                    if (areCorresponded(toDescriptor, fromDescriptor)) {
                        System.out.println("Correspondence found:\n" +
                                fromDescriptor+ "\n" +
                                toDescriptor + "\n");

                        // call corresponding method with got arguments
                        toDescriptor.invoke(to, fromDescriptor.invoke(from));
                    }
                } catch (NullPointerException | IllegalAccessException | InvocationTargetException o){}
            }
        }
    }


    static boolean areCorresponded(Method to, Method from){
        if(to.getName().substring(0, 3).equals("set") &
                from.getName().substring(0, 3).equals("get") &
                to.getName().substring(3).equals(from.getName().substring(3)) &
                to.getParameterCount() == 1){
            return to.getParameterTypes()[0].equals(from.getReturnType()) |
                    to.getParameterTypes()[0].equals(from.getReturnType().getSuperclass());
        }
        return false;
    }

    public static void main(String[] args) {
        Person person = new Person(1, 1);
        Person1 person1 = new Person1(2, 2);
        assign(person, person1);
        Assert.assertEquals(2, person.getAge());

        // Extended Person can be assigned with setPerson1(Person1 person1) setter
        ExtendedPerson extendedPerson = new ExtendedPerson(3, 3);
        assign(person, extendedPerson);
        System.out.println("person's age = " + person.getAge());
    }
}