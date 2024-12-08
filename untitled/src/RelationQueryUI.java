import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.*;
import java.util.List;

public class RelationQueryUI {
    private JFrame jf;

    public RelationQueryUI(Crowd c, int index) {
        jf = new JFrame("亲密度查询");
        jf.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        jf.setSize(1000, 800);
        jf.setLocationRelativeTo(null);

        show(index, c);

        jf.setVisible(true);
    }

    class Cmp implements Comparator<Map.Entry<Vector<String>, Integer>> {

        @Override
        public int compare(Map.Entry<Vector<String>, Integer> o1, Map.Entry<Vector<String>, Integer> o2) {
            //System.out.println(o1.getValue());
            return o2.getValue().compareTo(o1.getValue());
        }
    }

    public void show(int index, Crowd c) {
        List<Map.Entry<String, Integer>> list = new ArrayList<Map.Entry<String, Integer>>(c.crowd.get(index).relationWithOther.entrySet());
        Cmp2 cmp = new Cmp2();
        list.sort(cmp);


        DefaultTableModel model = new DefaultTableModel(new Object[]{"排名", "人物", "亲密度"}, 0);
        // 创建一个表格并将模型添加到表格中
        JTable table = new JTable(model);

        table.setRowHeight(100);
        //table.setBounds(300, 100, 200,80);

        int rank = 1;
        for (int i = 0; i  < list.size(); i++) {
            if (list.get(i).getKey().equals(c.crowd.get(index).names.get(0))) continue;
            Object[] t = new Object[]{rank++, list.get(i).getKey(), list.get(i).getValue()};
            model.addRow(t);
        }

        // 添加表格到滚动窗格，并将滚动窗格添加到frame中
        JScrollPane scrollPane = new JScrollPane(table);
        //System.out.println(jf == null);
        jf.add(scrollPane, BorderLayout.CENTER);


    }

    class Cmp2 implements Comparator<Map.Entry<String, Integer>> {

        @Override
        public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
            //System.out.println(o1.getValue());
            return o2.getValue().compareTo(o1.getValue());
        }
    }

}
