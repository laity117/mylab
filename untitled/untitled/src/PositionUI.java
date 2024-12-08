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
import java.awt.event.ActionEvent;
import java.text.NumberFormat;
import java.util.Comparator;
import java.util.Vector;

public class PositionUI {
    private JFrame jf;

    public PositionUI(Crowd c, int index) {
        jf = new JFrame("位置");
        jf.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        jf.setSize(1000, 800);
        jf.setLocationRelativeTo(null);

        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        //System.out.println(b == null);
        Vector<Integer> position = new Vector<>(c.crowd.get(index).position);
        for (int i = 0; i < c.crowd.get(index).position.size(); i++) {
            int x = (i + 1) * 10000;
            dataset.setValue(position.get(i), "num", Integer.toString(x));
        }
        // 创建柱状图
        JFreeChart chart = ChartFactory.createBarChart(
                "位置分布",
                "位置",
                "次数",
                dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false);
        // 创建面板并添加柱状图到面板中
        //CategoryPlot plot = (CategoryPlot) chart.getPlot();
        //BarRenderer renderer = (BarRenderer) plot.getRenderer();
        //renderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator("{0} = {2}", NumberFormat.getNumberInstance()));
        //renderer.setBaseItemLabelsVisible(true);
        //renderer.setBasePositiveItemLabelPosition(new ItemLabelPosition(ItemLabelAnchor.OUTSIDE12, TextAnchor.BASELINE_LEFT));
        ChartPanel panel = new ChartPanel(chart);
        panel.setPreferredSize(new java.awt.Dimension(1000, 666));

        jf.add(panel);
        jf.pack();

        //jf.pack();
        jf.setVisible(true);
    }
}
