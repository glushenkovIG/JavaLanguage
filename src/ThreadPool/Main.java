package ThreadPool;

public class Main {
    public static void main(String[] args) throws Exception {
        Character[] c = {'\\', '|', '/', '-'};
        int i = 0;
        while(true){
            System.out.print("\rppido " + c[i]);
            i = (i + 1) % 4;
            Thread.sleep(282);
        }
    }
}
