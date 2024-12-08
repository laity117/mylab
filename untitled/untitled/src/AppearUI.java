import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.ItemLabelAnchor;
import org.jfree.chart.labels.ItemLabelPosition;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.TextAnchor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.util.Comparator;
import java.util.Vector;

public class AppearUI extends JFrame implements ActionListener {
    private JFrame jf;
    public AppearUI(Crowd c) {
        jf = new JFrame("出现次数");
        jf.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        jf.setSize(1000, 800);
        jf.setLocationRelativeTo(null);

        /*
        ImageIcon im = new ImageIcon("背景1.png");
        JLabel jl = new JLabel(im);
        jl.setSize(im.getIconWidth(), im.getIconHeight());
        jf.getLayeredPane().add(jl,new Integer(Integer.MIN_VALUE));

        JPanel jp = (JPanel) jf.getContentPane();
        jp.setOpaque(false);
        jp.setLayout(new FlowLayout());
        jp.setLayout(null);


        JButton jb1 = new JButton("人物出现次数及位置");
        jb1.setBounds(400, 200, 200, 55);


        //ActionListener apperaNumAndPosition;
        //Jb1 appearAndPosition = new Jb1();
        jb1.addActionListener(this);

        jp.add(jb1, 0);
        */


        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        //System.out.println(b == null);
        Vector<Person> crowd = new Vector<>(c.crowd);
        Cmp cmp = new Cmp();
        crowd.sort(cmp);
        for (int i = 0; i < c.crowd.size(); i++) {
            dataset.setValue(crowd.get(i).apperaNum, "count", crowd.get(i).names.get(0));
        }
        // 创建柱状图
        JFreeChart chart = ChartFactory.createBarChart(
                "出场次数",
                "人名",
                "次数",
                dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false);
        // 创建面板并添加柱状图到面板中
        CategoryPlot plot = (CategoryPlot) chart.getPlot();
        BarRenderer renderer = (BarRenderer) plot.getRenderer();
        renderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator("{0} = {2}", NumberFormat.getNumberInstance()));
        renderer.setBaseItemLabelsVisible(true);
        renderer.setBasePositiveItemLabelPosition(new ItemLabelPosition(ItemLabelAnchor.OUTSIDE12, TextAnchor.BASELINE_LEFT));
        ChartPanel panel = new ChartPanel(chart);
        panel.setPreferredSize(new java.awt.Dimension(1000, 666));

        jf.add(panel);
        jf.pack();

        //jf.pack();
        jf.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    class Cmp implements Comparator<Person> {

        @Override
        public int compare(Person o1, Person o2) {
            if (o1.apperaNum > o2.apperaNum) return -1;
            else if (o1 == o2) return 0;
            return 1;
        }
    }
}
