import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;


public class Main {

    void addSomething(Collection<? super Number> a){
        a.add(1);//хотя инт не является super от Number
    }

    Number getSomething(ArrayList<? extends Integer> a){
       return a.get(1);// возвращает Number хотя он не экстендит integer
    }

    public static void main(String[] args) {
        Collection<? super Integer> buf = new ArrayList<Object>(10); //integer/
        Collection<? extends Number> buf11 = new LinkedList<Integer>();
        Collection<Number> buf1 = new LinkedList<Number>();
        buf1.add(new Integer(1));
    }
}
