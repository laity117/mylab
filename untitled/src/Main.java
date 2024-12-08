import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        Book b = new Book("info_file.txt");
        Crowd c = new Crowd(b);
        MainUI u = new MainUI();
        //System.out.println(b == null);
        u.setC(c);
    }
}
