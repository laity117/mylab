import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Set;

public class MainUI extends JFrame {
    private JFrame jf;
    private Crowd c;
    public MainUI() {
        jf = new JFrame("小说统计");
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.setSize(1000, 800);
        jf.setLocationRelativeTo(null);
        ImageIcon im = new ImageIcon("背景1.png");
        JLabel jl = new JLabel(im);
        jl.setSize(im.getIconWidth(), im.getIconHeight());
        jf.getLayeredPane().add(jl,new Integer(Integer.MIN_VALUE));
        JPanel jp = (JPanel) jf.getContentPane();
        jp.setOpaque(false);
        jp.setLayout(new FlowLayout());
        jp.setLayout(null);


        JButton jb5 = new JButton("人物位置分布");
        jb5.setBounds(400, 100, 200, 55);
        jb5.addActionListener(new Jb5());

        JButton jb1 = new JButton("人物出现次数");
        jb1.setBounds(400, 200, 200, 55);
        jb1.addActionListener(new Jb1());


        JButton jb2 = new JButton("篇幅跨度");
        jb2.setBounds(400, 300, 200, 55);
        jb2.addActionListener(new Jb2());

        JButton jb3 = new JButton("亲密度一览");
        jb3.setBounds(400, 400, 200, 55);
        jb3.addActionListener(new Jb3());

        JButton jb4 = new JButton("查询亲密度");
        jb4.setBounds(400, 500, 200, 55);
        jb4.addActionListener(new Jb4());

        JButton jb6 = new JButton("小团体");
        jb6.setBounds(400, 600, 200, 55);
        jb6.addActionListener(new Jb6());


        jp.add(jb1);
        jp.add(jb2);
        jp.add(jb3);
        jp.add(jb4);
        jp.add(jb5);
        jp.add(jb6);

        jf.setVisible(true);
    }

    public class Jb1 implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            new AppearUI(c);
        }
    }

    public class Jb6 implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String s = "";
            Integer i = 1;
            for (Set<Person> sp : c.team.get(0)) {
                if (sp.size() < 3) continue;
                s += "小团体";
                s += i.toString();
                s += ": ";
                i++;
                for (Person p : sp) {
                    s += p.names.get(0);
                    s += " ";
                }
                s += "\n";
            }
            new TeamUI(s);
        }
    }

    public class Jb2 implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            new SpanUI(c);
        }
    }

    public class Jb3 implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            new RelationUI(c);
        }
    }

    public class Jb5 implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String str = javax.swing.JOptionPane.showInputDialog("请输入要选择的人物");
            //System.out.println(str);
            int index = 0;
            boolean flag = false;
            for (int i = 0 ;i  < c.crowd.size(); i++) {
                if (flag) break;
                for (int j = 0; j < c.crowd.get(i).names.size(); j++) {
                    if (str.equals(c.crowd.get(i).names.get(j))) {
                        //str = c.crowd.get(i).names.get(0);
                        index = i;
                        flag = true;
                        break;
                    }
                }
            }
            new PositionUI(c, index);
        }
    }

    public class Jb4 implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String str = javax.swing.JOptionPane.showInputDialog("请输入要选择的人物");
            //System.out.println(str);
            int index = 0;
            boolean flag = false;
            for (int i = 0 ;i  < c.crowd.size(); i++) {
                if (flag) break;
                for (int j = 0; j < c.crowd.get(i).names.size(); j++) {
                    if (str.equals(c.crowd.get(i).names.get(j))) {
                        //str = c.crowd.get(i).names.get(0);
                        index = i;
                        flag = true;
                        break;
                    }
                }
            }
            new RelationQueryUI(c, index);
        }
    }

    public void setC(Crowd cc) {
        c = cc;
    }
}
