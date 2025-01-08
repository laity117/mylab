package computerArchitecture;

public class Pipeline {
    public int space = 4;
    public int num = 5;
    public int time = space + num - 1;
    public int[][] ts = new int[time][space];
    public String[] ins = new String[] { "ED", "EA", "MA", "NL" };

    public void pipeline() {
        for (int i = 0; i < num; i++) {
            int t = i;
            for (int j = 0; j < 4; j++) {
                ts[t][j] = i + 1;
                t++;
            }
        }
    }

    public void print() {
        for (int t = 0; t < time; t++) {
            System.out.println((t + 1) + "秒后");
            for (int j = space - 1; j >= 0; j--) {
                for (int i = 0; i < time; i++) {
                    if (ts[i][j] == 0) {
                        System.out.print("     ");
                    } else {
                        int x = ts[i][j] - 1 + j;
                        if (x > t) {
                            System.out.print("     ");
                        } else {
                            System.out.print(ins[j]);
                            System.out.printf("%-3d", ts[i][j]);
                        }
                    }
                }
                System.out.println();
            }
        }
    }

    public static void main(String[] args) {
        Pipeline pipeline = new Pipeline();
        System.out.println("开始");
        pipeline.pipeline();
        pipeline.print();
        System.out.println("吞吐率为" + (double)pipeline.num / pipeline.time);
        System.out.println("加速比为" + (double)pipeline.num * pipeline.space / pipeline.time);
        System.out.println("效率为" + ((double)pipeline.space * pipeline.num) / (pipeline.time * pipeline.space));
    }
}
