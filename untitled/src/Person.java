import java.util.*;

import static java.lang.Math.max;
import static java.lang.Math.min;

public class Person {
    Vector<String> names;
    public int firstAppear;
    public int lastAppear;
    public int aliveTime;
    public double span;
    public int apperaNum;
    Vector<Integer> position;
    public Map<String, Integer> relationWithOther;

    public Person(String s, String content) {
        setNames(s);
        setFirstAppear(content);
        setLastAppear(content);
        setSpan(content.length());
        setApperaNum(content);
        setPosition(content);
    }

    public void setPosition(String content) {
        int n = content.length() / 500;
        position = new Vector<>();
        for (int i = 0; i < n; i++) {
            String s = content.substring(i * 500, i * 500 + 500);
            int count = 0;
            for (int j = 0; j < names.size(); j++) {
                int index = 0;
                while ((index = s.indexOf(names.get(j), index)) != -1) {
                    count++;
                    index += names.get(j).length();
                }
            }
            position.add(count);
        }
    }

    private void setNames(String s) {
        names = new Vector<>();
        String[] arr = s.split("@");
        names.addAll(Arrays.asList(arr));
        //System.out.println(names.get(0));
        /*
        for (String x : names) {
            System.out.println(x);
        }
         */
    }

    public void setFirstAppear(String content) {
        int t = content.indexOf(names.get(0));
        if (t != -1) {
            firstAppear = t;
        }
        else firstAppear = 1000000000;
        for (int i = 1; i < names.size(); i++) {
            t = content.indexOf(names.get(i));
            if (t != -1)
                firstAppear = min(firstAppear, t);
        }
    }

    public void setLastAppear(String content) {
        lastAppear = content.lastIndexOf(names.get(0));
        for (int i = 1; i < names.size(); i++) {
            lastAppear = max(lastAppear, content.lastIndexOf(names.get(i)));
        }
    }

    public void setApperaNum(String content) {
        apperaNum = 0;
        for (int i = 0; i < names.size(); i++) {
                int count = 0;
                int index = 0;
                while ((index = content.indexOf(names.get(i), index)) != -1) {
                    count++;
                    index += names.get(i).length();
                }
                apperaNum += count;
        }
    }

    public void setSpan(int length) {
        aliveTime = lastAppear - firstAppear;
        span = (double)aliveTime / (double)length;
    }

    private int getIndex(String content, int position) {
        int ans = content.indexOf(names.get(0), position);
        for (int i = 1; i < names.size(); i++) {
            ans = min(ans, content.indexOf(names.get(i), position));
        }
        return ans;
    }

    private boolean contain(String content, Person p) {
        for (int i = 0; i < p.names.size(); i++) {
            if (content.contains(p.names.get(i))) return true;
        }
        return false;
    }

    public void setRelationWithOther(Vector<Person> other, String content) {
        relationWithOther = new HashMap<>();
        relationWithOther.put(names.get(0), 0);
        for (Person person : other) {
            if (Objects.equals(person.names.get(0), names.get(0))) continue;
            int count = 0;
            int position = 500;
            while (position + 500 < content.length()) {
                int index = getIndex(content, position);
                if (index == -1) break;
                String t = content.substring(position - 500, position + 500);
                if (contain(t, person)) count++;
                position += 500;
            }
            relationWithOther.put(person.names.get(0), count);
        }
    }
}
