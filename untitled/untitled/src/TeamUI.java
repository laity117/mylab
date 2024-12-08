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
import java.text.NumberFormat;
import java.util.Comparator;
import java.util.Vector;

public class TeamUI {
    private JFrame jf;

    public TeamUI(String s) {
        jf = new JFrame("小团体");

        JTextArea jtextarea = new JTextArea(200, 100); // 设置大小
        JLabel lab = new JLabel("小团体：");
        //在有带滚动条的面板中设置文本域，并设置垂直滚动条，垂直滚动条。
        JScrollPane scr = new JScrollPane(jtextarea,
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        //jf.setLayout(new GridLayout(, ));
        jtextarea.setFont(new Font("宋体", Font.PLAIN, 55));
        jtextarea.append(s);
        jf.add(lab);
        jf.add(scr);
        jf.setSize(1000, 800);
        jf.setLocation(300, 200);
        jf.setVisible(true);
    }
}
