/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ff.reportgenerator;

import java.io.*;
import java.awt.*;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

import org.jfree.data.*;
import org.jfree.data.general.*;
import org.jfree.data.category.*;
import org.jfree.chart.*;
import org.jfree.chart.servlet.ServletUtilities;
import org.jfree.chart.entity.*;
import org.jfree.chart.plot.*;
import org.jfree.chart.axis.*;
import org.jfree.chart.renderer.*;
import org.jfree.chart.plot.*;
import org.jfree.chart.entity.*;
import org.jfree.chart.urls.*;
import org.jfree.chart.servlet.*;
import org.jfree.chart.title.TextTitle;
import org.jfree.chart.labels.*;
import org.jfree.data.jdbc.JDBCPieDataset;


/**
 *
 * @author root
 */
public class Reporter {

    public static void main(String[] args) throws IOException {
        DefaultPieDataset data = getDataSet();
        JFreeChart chart = ChartFactory.createPieChart3D("水果产量图", // 图表标题
                data,
                true, // 是否显示图例
                false,
                false);
        //写图表对象到文件，参照柱状图生成源码
        FileOutputStream fos_jpg = null;
        try {
            fos_jpg = new FileOutputStream("/fruit.jpg");
            ChartUtilities.writeChartAsJPEG(fos_jpg, 1.0f, chart, 400, 300, null);
        } finally {
            try {
                fos_jpg.close();
            } catch (Exception e) {
            }
        }
    }

    /**
     * 获取一个演示用的简单数据集对象
     * @return
     */
    private static DefaultPieDataset getDataSet() {
        DefaultPieDataset dataset = new DefaultPieDataset();
        dataset.setValue("苹果", 100);
        dataset.setValue("梨子", 200);
        dataset.setValue("葡萄", 300);
        dataset.setValue("香蕉", 400);
        dataset.setValue("荔枝", 500);
        return dataset;
    }


    public static void createPieChart(ServletRequest req, ServletResponse res)
		throws ServletException, IOException
	{
		res.setContentType("image/jpeg");
		DefaultPieDataset data = getDataSet();
		JFreeChart chart = ChartFactory.createPieChart("kkk", data, true, false, false);

        //ChartUtilities.writeChartAsJPEG(out, quality, chart, width, height)
        ChartUtilities.writeChartAsJPEG(res.getOutputStream(), chart, 400, 300);
        
		//ChartUtilities.writeChartAsJPEG(res.getOutputStream(),0.5f,chart,400,300);

	}


    public static String createPieChart(HttpSession session, PrintWriter pw) {
        String filename = null;
        Font font;

        try {
            //  建立PieDataSet
            DefaultPieDataset data = getDataSet();

            //  生成chart物件
            PiePlot plot = new PiePlot(data);
            plot.setDataset(data);
            //plot.setInsets(new RectangleInsets(0, 5, 5, 5));

            plot.setToolTipGenerator(new StandardPieToolTipGenerator());

            font = new Font("黑体", Font.CENTER_BASELINE, 20);//这个地方是设置统计图标题的字体和大小

            JFreeChart chart = new JFreeChart("", JFreeChart.DEFAULT_TITLE_FONT, plot, true);

            /*
            TextTitle tt = new TextTitle(titles);
            tt.setFont(font);
            chart.setBackgroundPaint(java.awt.Color.white);//统计图片的底色
            chart.setTitle(tt);
             */
            //  把生成的文件写入到临时的目录中
            ChartRenderingInfo info = new ChartRenderingInfo(new StandardEntityCollection());
            filename = ServletUtilities.saveChartAsPNG(chart, 500, 300, info, session);
//选择存储成png格式的文件，当然你也可以使用saveChartAsJPEG的方法生成jpg图片

            //  把image map 写入到 PrintWriter
            ChartUtilities.writeImageMap(pw, filename, info, true);
            pw.flush();
        } catch (Exception ex) {
            System.out.println("error:" + ex.getMessage());
        }
        return filename;
    }


    // JDBC sample
    public static String createPieChart(String fieldName, String chartType, HttpSession session, JspWriter out) {
        String filename = null;
        Font font;

        try {
            PrintWriter pw = new PrintWriter(out);

            DynamicDatabase dd = new DynamicDatabase();
            String tableName = dd.getTableName();
            String query= "select "+fieldName+",count("+fieldName+") as "+ fieldName+" from "+tableName+" group by "+fieldName;
            System.out.println(query);
            JDBCPieDataset data = new JDBCPieDataset(dd.getConnection(),query);

            //DefaultPieDataset data = getDataSet();

            //  生成chart物件
            PiePlot plot = new PiePlot(data);
            plot.setDataset(data);
            //plot.setInsets(new RectangleInsets(0, 5, 5, 5));

            plot.setToolTipGenerator(new StandardPieToolTipGenerator());

            font = new Font("黑体", Font.CENTER_BASELINE, 20);//这个地方是设置统计图标题的字体和大小

            JFreeChart chart = new JFreeChart("", JFreeChart.DEFAULT_TITLE_FONT, plot, true);

            /*
            TextTitle tt = new TextTitle(titles);
            tt.setFont(font);
            chart.setBackgroundPaint(java.awt.Color.white);//统计图片的底色
            chart.setTitle(tt);
             */
            //  把生成的文件写入到临时的目录中
            ChartRenderingInfo info = new ChartRenderingInfo(new StandardEntityCollection());
            filename = ServletUtilities.saveChartAsPNG(chart, 500, 300, info, session);
//选择存储成png格式的文件，当然你也可以使用saveChartAsJPEG的方法生成jpg图片

            //  把image map 写入到 PrintWriter
            ChartUtilities.writeImageMap(pw, filename, info, true);
            pw.flush();
        } catch (Exception ex) {
            System.out.println("error:" + ex.getMessage());
        }

        return filename;
    }

    // This the real use case
    // scope: 10000- no scope;
    public static String createChart(String fieldName, String chartType, int scope, HttpSession session, JspWriter out) {
        String filename = null;
        Font font;

        try{
            PrintWriter pw = new PrintWriter(out);

            DynamicDatabase dd = new DynamicDatabase();
            DefaultPieDataset data = dd.queryDatabase(fieldName, scope);
            PiePlot plot = new PiePlot(data);
            plot.setDataset(data);
            //plot.setInsets(new RectangleInsets(0, 5, 5, 5));

            plot.setToolTipGenerator(new StandardPieToolTipGenerator());
            plot.setLabelGenerator(new StandardPieSectionLabelGenerator("{0}：{1}({2})"));

            font = new Font("黑体", Font.CENTER_BASELINE, 20);//这个地方是设置统计图标题的字体和大小

            JFreeChart chart = new JFreeChart("Segment Coverage", JFreeChart.DEFAULT_TITLE_FONT, plot, true);

            //  把生成的文件写入到临时的目录中
            ChartRenderingInfo info = new ChartRenderingInfo(new StandardEntityCollection());
            filename = ServletUtilities.saveChartAsPNG(chart, 500, 300, info, session);

            //  把image map 写入到 PrintWriter
            ChartUtilities.writeImageMap(pw, filename, info, true);
            pw.flush();

        }catch (Exception e) {
            e.printStackTrace();
        }

        return filename;
    }


    // real use case for defined bar chart
    public static String createDefinedBarChart(String fieldName, String chartType, String[] chartItems, HttpSession session, JspWriter out) {
        String filename = null;
        Font font;

        try{
            PrintWriter pw = new PrintWriter(out);

            DynamicDatabase dd = new DynamicDatabase();
            DefaultCategoryDataset data = dd.queryDatabase(fieldName, chartItems);
            //PiePlot plot = new PiePlot(data);
            //plot.setDataset(data);
            //plot.setInsets(new RectangleInsets(0, 5, 5, 5));

            //plot.setToolTipGenerator(new StandardPieToolTipGenerator());
            //plot.setLabelGenerator(new StandardCategoryItemLabelGenerator());

            font = new Font("黑体", Font.CENTER_BASELINE, 20);//这个地方是设置统计图标题的字体和大小

            //JFreeChart chart = new JFreeChart("", JFreeChart.DEFAULT_TITLE_FONT, plot, true);

            //ChartFactory.createBarChart(filename, filename, filename, data, orientation, legend, tooltips, urls)

            JFreeChart chart = ChartFactory.createBarChart(
							"Key Sun IP Coverage", // 图表标题
							"Key Sun IPs", // 目录轴的显示标签
							"Adoptions", // 数值轴的显示标签
							data, // 数据集
							PlotOrientation.HORIZONTAL, // 图表方向：水平、垂直
							false, 	// 是否显示图例(对于简单的柱状图必须是false)
							false, 	// 是否生成工具
							false 	// 是否生成URL链接
							);

            //  把生成的文件写入到临时的目录中
            ChartRenderingInfo info = new ChartRenderingInfo(new StandardEntityCollection());
            filename = ServletUtilities.saveChartAsPNG(chart, 500, 300, info, session);

            //  把image map 写入到 PrintWriter
            ChartUtilities.writeImageMap(pw, filename, info, true);
            pw.flush();

        }catch (Exception e) {
            e.printStackTrace();
        }

        return filename;
    }


}
