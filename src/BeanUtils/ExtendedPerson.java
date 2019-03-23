package BeanUtils;

public class ExtendedPerson extends Person1 {

    ExtendedPerson(int age, int height) {
        super(age, height);
    }

    public ExtendedPerson getPerson1(){
        return this;
    }
}
