import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Vector;

public class Book {
    private int numOfPerson = 10;
    private String name;
    public String contents;
    public Vector<Person> mainPerson;

    public Book(String filename) throws IOException {
        mainPerson = new Vector<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            name = br.readLine();
            setContents(name);
            while (true) {
                String s = br.readLine();
                if (s == null) break;
                Person p = new Person(s, contents);
                mainPerson.add(p);
            }
            for (Person p : mainPerson) p.setRelationWithOther(mainPerson, contents);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setContents(String filename) throws FileNotFoundException {
        contents = "";
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            while (true) {
                String s = br.readLine();
                if (s == null) break;
                contents += s;
            }
            //System.out.println(contents.length());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        //System.out.println(contents);
    }
}
