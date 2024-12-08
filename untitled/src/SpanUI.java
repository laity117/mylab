import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.ItemLabelAnchor;
import org.jfree.chart.labels.ItemLabelPosition;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DatasetUtilities;
import org.jfree.ui.TextAnchor;

import javax.swing.*;
import java.awt.*;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Comparator;
import java.util.Vector;

public class SpanUI {
    private JFrame jf;
    public SpanUI(Crowd c) {
        jf = new JFrame("跨度");
        jf.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        jf.setSize(1000, 800);
        jf.setLocationRelativeTo(null);


        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        //System.out.println(b == null);
        Vector<Person> crowd = new Vector<>(c.crowd);
        Cmp cmp = new Cmp();
        crowd.sort(cmp);
        for (int i = 0; i < c.crowd.size(); i++) {
            dataset.setValue(crowd.get(i).span, "span", crowd.get(i).names.get(0));
        }
        // 创建柱状图
        JFreeChart chart = ChartFactory.createBarChart(
                "篇幅跨度",
                "人名",
                "跨度",
                dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false);
        //NumberFormat numFormater = NumberFormat.getPercentInstance();
        //DecimalFormat numFormater = new DecimalFormat("0.00%");
        // 创建面板并添加柱状图到面板中
        CategoryPlot plot = (CategoryPlot) chart.getPlot();
        BarRenderer renderer = (BarRenderer) plot.getRenderer();
        renderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator("{0} = {2}", NumberFormat.getPercentInstance()));
        renderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator("{2}", new DecimalFormat("0.00%")));
        renderer.setBaseItemLabelsVisible(true);
        renderer.setBasePositiveItemLabelPosition(new ItemLabelPosition(ItemLabelAnchor.OUTSIDE12, TextAnchor.BASELINE_LEFT));
        ChartPanel panel = new ChartPanel(chart);
        panel.setPreferredSize(new Dimension(1000, 666));

        jf.add(panel);
        jf.pack();


        jf.setVisible(true);
    }

    class Cmp implements Comparator<Person> {

        @Override
        public int compare(Person o1, Person o2) {
            if (o1.span > o2.span) return -1;
            else if (o1 == o2) return 0;
            return 1;
        }
    }
}
