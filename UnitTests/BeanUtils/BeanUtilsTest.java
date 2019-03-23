package BeanUtils;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static BeanUtils.BeanUtils.assign;
import static org.junit.Assert.*;

public class BeanUtilsTest {

    @Test
    public void unit1() {
        Person person = new Person(1, 1);
        Person1 person1 = new Person1(2, 2);
        assign(person, person1);
        Assert.assertEquals(2, person.getAge());
    }
}