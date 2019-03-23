package BeanUtils;

public class Person{
    int age;
    int height;
    Person1 person1;

    Person(int age, int height) {
        this.age = age;
        this.height = height;
    }

    int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
    public void setPerson1(Person1 person1){
        this.person1 = person1;
        this.age = person1.getAge();
    }
}
