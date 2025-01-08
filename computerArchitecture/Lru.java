package computerArchitecture;

import java.util.Arrays;
import java.util.Scanner;

public class Lru {
    public int[] readPage() {
        Scanner sc = new Scanner(System.in);
        System.out.print("请输入访问页面个数:");
        int n = sc.nextInt();
        System.out.print("请输入访问的序列:");
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) {
            arr[i] = sc.nextInt();
        }
        sc.close();
        return arr;
    }

    public void printCache(int[] cache) {
        for (int i = 0; i < cache.length; i++) {
            System.out.println("内存块" + i + ":" + cache[i]);
        }
        System.out.println("--------------------");
    }

    public void updateTime(int[] time, int idx) {
        for (int i = 0; i < time.length; i++) {
            time[i]++;
        }
        time[idx] = 0;
    }

    public void cache(int n, int[] pages) {
        int[] cache = new int[n];
        int[] time = new int[n];
        Arrays.fill(cache, -1);
        int m = pages.length;
        for (int page : pages) {
            int visit = -1;
            boolean hit = false;
            for (int i = 0; i < n; i++) {
                if (cache[i] == page) {
                    hit = true;
                    visit = i;
                    System.out.println(page + "命中!!!");
                    break;
                }
            }
            if (!hit) {
                boolean full = true;
                for (int i = 0; i < n; i++) {
                    if (cache[i] == -1) {
                        visit = i;
                        System.out.println("cache有空闲块,不考虑是否要置换");
                        cache[i] = page;
                        System.out.println(page + "被调入cache....");
                        full = false;
                        break;
                    }
                }
                if (full) {
                    System.out.println("cache已经满了,考虑是否置换");
                    int idx = 0, ti = time[0];
                    for (int i = 1; i < n; i++) {
                        if (time[i] > ti) {
                            ti = time[i];
                            idx = i;
                        }
                    }
                    visit = idx;
                    System.out.println(cache[idx] + "被" + page + "在cache的" + idx + "号块置换");
                    cache[idx] = page;
                }
            }
            printCache(cache);
            updateTime(time, visit);
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Lru lru = new Lru();
        System.out.print("请输入cache块数:");
        int n = sc.nextInt();
        int[] pages = lru.readPage();
        lru.cache(n, pages);
        sc.close();
    }
}
