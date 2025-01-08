package computerArchitecture;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.Scanner;

public class Hfm {
    public static class Code{
        public String name;
        public double p;
        public String code;

        public Code(String name, double p) {
            this.name = name;
            this.p = p;
        }

        public Code(String name, double p, String code) {
            this.name = name;
            this.p = p;
            this.code = code;
        }
    }

    public static class HfmNode {
        public String name;
        public double p;
        public String code;
        public HfmNode left;
        public HfmNode right;

        public HfmNode(String name, double p) {
            this.name = name;
            this.p = p;
        }

        public HfmNode(double P, HfmNode left, HfmNode right) {
            this.p = P;
            this.left = left;
            this.right = right;
        }
    }

    public Code[] readCodes() {
        int n;
        Scanner sc = new Scanner(System.in);
        System.out.print("请输入指令个数:");
        n = sc.nextInt();
        Code[] codes = new Code[n];
        for (int i = 0; i < n; i++) {
            System.out.print("请输入第" + (i + 1) + "个操作码:");
            String name = sc.next();
            System.out.print("请输入第" + (i + 1) + "个操作码的概率");
            double p = sc.nextDouble();
            codes[i] = new Code(name, p);
        }
        sc.close();
        return codes;
    }

    public HfmNode buildHfm(Code[] codes) {
        int n = codes.length;
        PriorityQueue<HfmNode> pq = new PriorityQueue<>((a, b) -> a.p - b.p == 0 ? 0 : a.p - b.p > 0 ? 1 : -1);
        for (Code code : codes) {
            pq.add(new HfmNode(code.name, code.p));
        }
        while (pq.size() != 1) {
            HfmNode a = pq.poll();
            HfmNode b = pq.poll();
            pq.add(new HfmNode(a.p + b.p, a, b));
        }
        return pq.poll();
    }

    public Code[] calcCodes(HfmNode hfmNode) {
        ArrayList<Code> codes = new ArrayList<>();
        calcCodes(hfmNode, "", codes);
        Code[] ans = codes.toArray(new Code[0]);
        Arrays.sort(ans, (a, b) -> a.code.length() - b.code.length());
        return ans;
    }

    public void calcCodes(HfmNode hfmNode, String code, ArrayList<Code> codes) {
        if (hfmNode.left == null) {
            codes.add(new Code(hfmNode.name, hfmNode.p, code));
            return;
        }
        calcCodes(hfmNode.left, code + "0", codes);
        calcCodes(hfmNode.right, code + "1", codes);
    }

    public void print(Code[] codes) {
        System.out.println("--------------------");
        System.out.println("操作码   概率     霍夫曼编码");
        for (Code code : codes) {
            System.out.printf("%-8s%.2f\t%-4s\n", code.name, code.p, code.code);
        }
        System.out.println("--------------------");
        double avgLength1 = 0;
        for (Code code : codes) {
            avgLength1 += code.p * code.code.length();
        }
        System.out.println("霍夫曼编码平均长度为" + avgLength1);
        double avgLength2 = Math.ceil(Math.log10(codes.length) / Math.log10(2));
        System.out.println("等长编码的平均长度为" + avgLength2);
        double avgLength3 = 0;
        int[] codeLen = new int[codes.length];
        codeLen[0] = 1;
        codeLen[1] = 2;
        codeLen[2] = 3;
        codeLen[3] = 5;
        for (int i = 4; i < codes.length; i++) {
            codeLen[i] = 5;
        }
        for (int i = 0; i < codes.length; i++) {
            avgLength3 += codeLen[i] * codes[i].p;
        }
        System.out.println("1-2-3-5扩展编码的平均长度为" + avgLength3);
        if (avgLength1 < avgLength2) {
            System.out.println("可见HUFFMAN编码的平均长度要比等长编码的平均长度短");
        } else {
            System.out.println("huffman编码有问题请仔细查看算法，以及输入的指令集的概率之和是否大于1");
        }
        if (avgLength1 < avgLength3) {
            System.out.println("可见HUFFMAN编码的平均长度要比1-2-3-5扩展编码的最短平均长度短");
        } else {
            System.out.println("huffman编码有问题请仔细查看算法，以及输入的指令集的概率之和是否大于1");
        }
    }

    public static void main(String[] args) {
        Hfm hfm = new Hfm();
        Code[] codes = hfm.readCodes();
        HfmNode hfmNode = hfm.buildHfm(codes);
        codes = hfm.calcCodes(hfmNode);
        hfm.print(codes);
    }
}
