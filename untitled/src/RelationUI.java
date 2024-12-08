import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.*;
import java.util.List;

public class RelationUI {
    private JFrame jf;

    public RelationUI(Crowd c) {
        jf = new JFrame("亲密度一览");
        jf.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        jf.setSize(1000, 800);
        jf.setLocationRelativeTo(null);

        List<Map.Entry<Vector<String>, Integer>> list = new ArrayList<>(c.relation.entrySet());
        Cmp cmp = new Cmp();
        list.sort(cmp);

        DefaultTableModel model = new DefaultTableModel(new Object[]{"排名", "人物1", "人物2", "亲密度"}, 0);
        // 创建一个表格并将模型添加到表格中
        JTable table = new JTable(model);

        table.setRowHeight(50);

        for (int i = 0; i  <list.size(); i++) {
            System.out.println(list.get(i).getKey().get(0));
            System.out.println(list.get(i).getKey().get(1));
            System.out.println(list.get(i).getValue());
            Object[] t = new Object[]{i + 1, list.get(i).getKey().get(0), list.get(i).getKey().get(1), list.get(i).getValue()};
            model.addRow(t);
        }

        // 添加表格到滚动窗格，并将滚动窗格添加到frame中
        JScrollPane scrollPane = new JScrollPane(table);
        jf.add(scrollPane, BorderLayout.CENTER);

        jf.setVisible(true);

    }

    class Cmp implements Comparator<Map.Entry<Vector<String>, Integer>> {

        @Override
        public int compare(Map.Entry<Vector<String>, Integer> o1, Map.Entry<Vector<String>, Integer> o2) {
            //System.out.println(o1.getValue());
            return o2.getValue().compareTo(o1.getValue());
        }
    }
}
