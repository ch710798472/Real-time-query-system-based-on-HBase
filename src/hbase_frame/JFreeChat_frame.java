package hbase_frame;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.io.FileOutputStream;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartRenderingInfo;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.entity.StandardEntityCollection;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.category.DefaultCategoryDataset;

public class JFreeChat_frame {

	int width;// 图象宽度

	int height; // 图象高度

	String chartTitle;// 图表标题

	String subtitle;// 副标题

	String xTitle;// X轴标题

	String yTitle;// Y轴标题

	String[] cutline;// 图例名称
	
	String[] cutline1;// 图例名称

	String category[]; // 统计种类

	Double[][] data;// 绘图数据

	String servletURI = "/servlet/DisplayChart";// 映射路径

	public JFreeChat_frame() {
		this.width = 450;
		this.height = 325;
		this.chartTitle = "标题";
		this.subtitle = "";
		this.xTitle = "横坐标";
		this.yTitle = "纵坐标";
		this.cutline = new String[] { "123456789" };//线代表的含义
		this.cutline1 = new String[] { "sd" };//线代表的含义
		this.category = new String[] { "200809", "200810", "200811", "200812",
				"200901", "200902" };// 日期数组
		this.data = new Double[cutline.length][category.length];// 数据室double数组
		for (int m = 0; m < cutline.length; m++) {//cutline.length=1
			for (int n = 0; n < category.length; n++) {
				data[m][n] = 1 + Math.random() * 100;
			}
		}

	}

	public JFreeChat_frame(int width, int height, String chartTitle,
			String subtitle, String xTitle, String yTitle, String[] cutline,
			String[] category, Double[][] data) {
		this.width = width;
		this.height = height;
		this.chartTitle = chartTitle;
		this.subtitle = subtitle;
		this.xTitle = xTitle;
		this.yTitle = yTitle;
		this.cutline = cutline;
		this.category = category;
		this.data = data;
	}

	public JFreeChat_frame(int width, int height, String chartTitle,
			String subtitle, String xTitle, String yTitle, String[] cutline,
			String[] category, Double[][] data, String servletURI) {
		this.width = width;
		this.height = height;
		this.chartTitle = chartTitle;
		this.subtitle = subtitle;
		this.xTitle = xTitle;
		this.yTitle = yTitle;
		this.cutline = cutline;
		this.category = category;
		this.data = data;
		this.servletURI = servletURI;
	}

	public String draw(String path) {

		// 创建绘图数据集
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		for (int m = 0; m < cutline.length; m++) {
			for (int n = 0; n < category.length; n++) {
				dataset.addValue(data[m][n], cutline[m], category[n]);//第一个参数:数量,对应纵坐标,第二个参数:类别,区分不同类别,第三个参数:横坐标
				//cutline[m]是一个 字符串
			}
		}

		// 创建图表对象
		JFreeChart chart = ChartFactory.createLineChart(chartTitle, // 图表标题
				xTitle, // X轴标题
				yTitle, // Y轴标题
				dataset, // 绘图数据集
				PlotOrientation.VERTICAL, // 绘制方向
				true, // 显示图例
				true, // 采用标准生成器
				false // 生成链接
				);
		// 自定义图表开始：

		if (subtitle.length() > 0) {// 添加副标题
			chart.addSubtitle(new TextTitle(subtitle));
		}

		GradientPaint chartGP = new GradientPaint(0, 0,
				new Color(219, 227, 127), 0, height, Color.WHITE, false);// 创建渐变色对象
		chart.setBackgroundPaint(chartGP);// 设置图片背景色

		// 通过绘图区对象，可以设置更多的绘图属性
		CategoryPlot plot = chart.getCategoryPlot();

		plot.setBackgroundPaint(Color.WHITE);// 设置绘图区背景色

		plot.setRangeGridlinePaint(Color.RED);// 设置水平方向背景线颜色
		plot.setRangeGridlinesVisible(true);// 设置是否显示水平方向背景线,默认值为true

		plot.setDomainGridlinePaint(Color.RED);// 设置垂直方向背景线颜色
		plot.setDomainGridlinesVisible(true);// 设置是否显示垂直方向背景线,默认值为false

		// 获取折线对象
		LineAndShapeRenderer renderer = (LineAndShapeRenderer) plot
				.getRenderer();

		// 定义折线颜色
		Color color[] = new Color[cutline.length];
		color[0] = Color.GREEN;
		for (int i = 0; i < color.length; i++) {
			renderer.setSeriesPaint(i, color[i]);
		}

		// 定义折线绘制风格
		BasicStroke realLine = new BasicStroke(1.6f);
		float dashes[] = { 8.0f };// 定义虚线数组
		BasicStroke brokenLine = new BasicStroke(1.6f,// 线条粗细
				BasicStroke.CAP_ROUND,// 端点风格
				BasicStroke.JOIN_ROUND,// 折点风格
				8.f,// 折点处理办法
				dashes,// 虚线数组
				0.0f);// 虚线偏移量
		int special = 0;//每一条线是画实线还是虚线
		for (int i = 0; i < cutline.length; i++) {
			if (i == special) {
				renderer.setSeriesStroke(i, realLine);
			} else {
				renderer.setSeriesStroke(i, realLine);
			}
		}

		// 获得横轴对象
		CategoryAxis domainAxis = plot.getDomainAxis();

		// 设置横轴绘制属性
		domainAxis.setAxisLineStroke(new BasicStroke(1.6f));// 设置轴线粗细
		domainAxis.setAxisLinePaint(Color.BLACK);// 设置轴线颜色
		domainAxis.setCategoryLabelPositionOffset(5);// 设置统计种类与轴线的颜色
		domainAxis.setLabelFont(new Font("黑体", Font.BOLD, 16));// 设置坐标轴标题字体
		domainAxis.setLabelPaint(Color.BLACK);// 设置坐标轴标题颜色
		domainAxis.setCategoryLabelPositions(CategoryLabelPositions.STANDARD);// 设置坐标轴标题旋转角度

		// 获得纵轴对象
		ValueAxis rangeAxis = plot.getRangeAxis();

		// 设置纵轴绘制属性
		rangeAxis.setAxisLineStroke(new BasicStroke(1.6f));// 设置轴线粗细
		rangeAxis.setAxisLinePaint(Color.BLACK);// 设置轴线颜色
		// rangeAxis.setUpperBound(100.0f);// 设置坐标最大值
		// rangeAxis.setTickMarkStroke(new BasicStroke(1.6f));// 设置坐标标记大小
		rangeAxis.setTickMarkPaint(Color.BLACK);// 设置坐标标记颜色
		rangeAxis.setLabelFont(new Font("黑体", Font.BOLD, 16));// 设置坐标轴标题字体
		rangeAxis.setLabelPaint(Color.BLACK);// 设置坐标轴标题颜色
		rangeAxis.setLabelAngle(Math.PI / 2);// 设置坐标轴标题旋转角度

		// 自定义图表结束！

		// 固定用法
		ChartRenderingInfo info = new ChartRenderingInfo(
				new StandardEntityCollection());

		// 解决中文乱码问题
		CategoryPlot categoryplot = (CategoryPlot) chart.getPlot();
		NumberAxis numberaxis = (NumberAxis) categoryplot.getRangeAxis();
		CategoryAxis domainAxis1 = categoryplot.getDomainAxis();
		TextTitle textTitle = chart.getTitle();

		textTitle.setFont(new Font("黑体", Font.PLAIN, 20));

		domainAxis1.setTickLabelFont(new Font("sans-serif", Font.PLAIN, 11));

		domainAxis1.setLabelFont(new Font("宋体", Font.PLAIN, 12));

		numberaxis.setTickLabelFont(new Font("sans-serif", Font.PLAIN, 12));

		numberaxis.setLabelFont(new Font("黑体", Font.PLAIN, 12));

		chart.getLegend().setItemFont(new Font("宋体", Font.PLAIN, 12));

		// 把图片写入指定路径，生成图片
		FileOutputStream fos_jpg = null;
		//String imagePath = path + "/de1.jpeg";
		try {
			fos_jpg = new FileOutputStream(path);
			ChartUtilities.writeChartAsJPEG(fos_jpg, chart, width, height);
		} catch (Exception e) {
		} finally {
			try {
				fos_jpg.close();
			} catch (Exception e) {
			}
		}

		// 返回图片浏览路径
		return null;

	}

}
