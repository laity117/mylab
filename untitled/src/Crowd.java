import java.security.KeyPair;
import java.util.*;

import static java.util.Collection.*;

public class Crowd {
    //boolean[][] g;
    //boolean[] visited;
    int n;
    Vector<Person> crowd;
    Map<Vector<String>, Integer> relation;
    Vector<Vector<Set<Person>>> team;

    Book book;

    public Crowd(Book b) {
        //Set<Person> s = new HashSet<>();
        crowd = new Vector<>(b.mainPerson);
        n = crowd.size();
        //s.add(crowd.get(0));
        //s.add(crowd.get(1));
        //s.add(crowd.get(2));
        //System.out.println(isTeam(s, b.contents));
        book = b;
        //for (int i = 0; i < crowd.size(); i++) System.out.println(crowd.get(i).names.get(0));
        setRelation(b.mainPerson);
        setTeam(b.contents);
    }

    public void setTeam(String content) {

        team = new Vector<>();
        for (int i = 0; i <= n; i++) {
            Vector<Set<Person>> v = new Vector<>();
            team.add(v);
        }

        //System.out.println(team.size());

        //二维数组的第i行代表不算前i - 1个人形成的小团体
        //那么n + 1行显然是空的
        //怎么用第i + 1行算出来的小团体求第i行呢
        //就是i + 1行原封不动的搬过来，并且对i + 1行的每个小团体加上i行这个人再判断一次，并且再加上他自己
        for (int i = n - 1; i >= 0; i--) {
            for (Set<Person> w : team.get(i + 1)) {
                Set<Person> t = new HashSet<>(w);
                t.add(crowd.get(i));
                if (isTeam(t, content)) team.get(i).add(t);//i + 1行的每个小团体加上i行这个人再判断一次
                //差了这一行
                team.get(i).add(w);//i + 1行原封不动的搬过来
            }
            Set<Person> w = new HashSet<>();
            w.add(crowd.get(i));
            team.get(i).add(w);//并且再加上他自己
        }

        /*
        for (Set<Person> sp : team.get(0)) {
            for (Person p : sp) {
                System.out.print(p.names.get(0) + " ");
            }
            System.out.println();
        }

         */

    }

    private void setRelation(Vector<Person> p) {
        relation = new HashMap<>();
        for (int i = 0; i < p.size(); i++) {
            for (int j = i + 1; j < p.size(); j++) {
                Vector<String> name_pair = new Vector<>();
                name_pair.add(p.get(i).names.get(0));
                name_pair.add(p.get(j).names.get(0));
                int x = p.get(i).relationWithOther.get(p.get(j).names.get(0));
                //System.out.println("Hello");
                relation.put(name_pair, x);
            }
        }
    }

    private boolean contain(Person p, String s) {
        for (int i = 0; i < p.names.size(); i++) {
            if (s.contains(p.names.get(i))) return true;
        }
        return false;
    }

    public boolean isTeam(Set<Person> p, String content) {
        int count = 0;
        //System.out.println(content.length() / 1000);
        for (int i = 0; i * 1000 + 1000 < content.length(); i++) {
            String s = content.substring(i * 1000, i * 1000 + 1000);
            //System.out.println(s);
            boolean flag = true;
            for (Person q : p) {
                if (!contain(q, s)) {
                    flag = false;
                    break;
                }
                //else System.out.println(1);
            }
            if (flag) count++;
        }
        //System.out.println(count);
        return count >= 66;
    }

}
