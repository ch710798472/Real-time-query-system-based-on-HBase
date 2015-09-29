package hbase_frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.apache.hadoop.hbase.util.Bytes;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.CategoryDataset;

import ch_hbase.co_hbase;
import ch_hbase.con_hbase;
import ch_hbase.count_hbase;
import ch_mysql.con_mysql;
import ch_mysql.count_mysql;

public class More_frame {
	private String hbasetable = "hbase_test_temp";//hbase_temp一百万
	private String mysqltable = "hly_temp_normal";
	private Double[][] mysqlmaxdata = {{0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0}};
	private Double[][] mysqlmiddata = {{0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0}};
	private Double[][] mysqlmidtempdata = {{0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0}};
	private Double[][] mysqlmindata = {{0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0}};
	private Double[][] hbasemaxdata = {{0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0}};
	private Double[][] hbasemiddata = {{0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0}};
	private Double[][] hbasemidtempdata = {{0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0}};
	private Double[][] hbasemindata = {{0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0}};
	private JPanel contentJP;// 內容顶层，再分左右两层
	private JPanel leftJP;
	private JPanel rightJP;
	private JPanel lefttopJP;
	private JPanel leftbuttomJP;
	private JFrame main_frame;
	private JLabel imgJL;
	private JLabel toptemp;
	private JButton hbasetoptempmonth;
	private JButton hbasetoptempall;
	private JLabel midtemp;
	private JButton hbasemidtempmonth;
	private JButton hbasemidtempall;
	private JLabel buttomtemp;
	private JButton hbasebuttomtempmonth;
	private JButton hbasebuttomtempall;
	private JLabel mysqltemp;
	private JLabel emptyJL;
	private JLabel hbasetemp;
	private JButton mysqltoptempmonth;
	private JButton mysqltoptempall;
	private JButton mysqlmidtempmonth;
	private JButton mysqlmidtempall;
	private JButton mysqlbuttomtempmonth;
	private JButton mysqlbuttomtempall;

	public More_frame() {
		// start---界面代码
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		JFrame.setDefaultLookAndFeelDecorated(true);
		main_frame = new JFrame("基于Hbase的数据查询系统");
		main_frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		main_frame.setSize(new Dimension(800, 600));// 界面大小
		main_frame.setLocation(
				(int) ((screenSize.getWidth() - main_frame.getWidth()) / 2),
				(int) (screenSize.getHeight() - main_frame.getHeight()) / 2);// 居中显示

		contentJP = new JPanel();// 內容顶层，再分左右两层
		leftJP = new JPanel();
		rightJP = new JPanel();
		lefttopJP = new JPanel();// 左边分上下
		leftbuttomJP = new JPanel();

		BorderLayout BL = new BorderLayout();
		contentJP.setLayout(BL);
		BorderLayout BL1 = new BorderLayout();
		leftJP.setLayout(BL1);
		GridLayout GL = new GridLayout(4, 3);
		leftbuttomJP.setLayout(GL);
		GridLayout GL1 = new GridLayout(4, 3);
		lefttopJP.setLayout(GL1);

		leftJP.setPreferredSize(new Dimension(200, 600));
		rightJP.setPreferredSize(new Dimension(580, 600));
		contentJP.add(leftJP, "West");
		contentJP.add(rightJP, "East");

		lefttopJP.setPreferredSize(new Dimension(300, 300));
		leftbuttomJP.setPreferredSize(new Dimension(300, 300));
		leftJP.add(lefttopJP, "North");
		leftJP.add(leftbuttomJP, "Center");
		leftbuttomJP.setBackground(java.awt.Color.GREEN);
		lefttopJP.setBackground(java.awt.Color.GREEN);

		// 左-上面板
		Font f1 = new Font("宋体", Font.BOLD, 10);
		mysqltemp = new JLabel("Mysql:");
		lefttopJP.add(mysqltemp);
		mysqltemp.setFont(f1);
		emptyJL = new JLabel();
		lefttopJP.add(emptyJL);
		emptyJL = new JLabel();
		lefttopJP.add(emptyJL);
		toptemp = new JLabel("最高值   ->");
		lefttopJP.add(toptemp);
		toptemp.setFont(f1);
		mysqltoptempmonth = new JButton("每月");
		lefttopJP.add(mysqltoptempmonth);
		mysqltoptempmonth.setFont(f1);
		mysqltoptempall = new JButton("全部");
		lefttopJP.add(mysqltoptempall);
		mysqltoptempall.setFont(f1);

		midtemp = new JLabel("平均值   ->");
		lefttopJP.add(midtemp);
		midtemp.setFont(f1);
		mysqlmidtempmonth = new JButton("每月");
		lefttopJP.add(mysqlmidtempmonth);
		mysqlmidtempmonth.setFont(f1);
		mysqlmidtempall = new JButton("全部");
		lefttopJP.add(mysqlmidtempall);
		mysqlmidtempall.setFont(f1);

		buttomtemp = new JLabel("最低值   ->");
		lefttopJP.add(buttomtemp);
		buttomtemp.setFont(f1);
		mysqlbuttomtempmonth = new JButton("每月");
		lefttopJP.add(mysqlbuttomtempmonth);
		mysqlbuttomtempmonth.setFont(f1);
		mysqlbuttomtempall = new JButton("全部");
		lefttopJP.add(mysqlbuttomtempall);
		mysqlbuttomtempall.setFont(f1);

		// 左-下面板
		hbasetemp = new JLabel("Hbase:");
		leftbuttomJP.add(hbasetemp);
		hbasetemp.setFont(f1);
		emptyJL = new JLabel();
		leftbuttomJP.add(emptyJL);
		emptyJL = new JLabel();
		leftbuttomJP.add(emptyJL);
		toptemp = new JLabel("最高值   ->");
		leftbuttomJP.add(toptemp);
		toptemp.setFont(f1);
		hbasetoptempmonth = new JButton("每月");
		leftbuttomJP.add(hbasetoptempmonth);
		hbasetoptempmonth.setFont(f1);
		hbasetoptempall = new JButton("全部");
		leftbuttomJP.add(hbasetoptempall);
		hbasetoptempall.setFont(f1);

		midtemp = new JLabel("平均值   ->");
		leftbuttomJP.add(midtemp);
		midtemp.setFont(f1);
		hbasemidtempmonth = new JButton("每月");
		leftbuttomJP.add(hbasemidtempmonth);
		hbasetoptempmonth.setFont(f1);
		hbasemidtempall = new JButton("全部");
		leftbuttomJP.add(hbasemidtempall);
		hbasetoptempall.setFont(f1);

		buttomtemp = new JLabel("最低值   ->");
		leftbuttomJP.add(buttomtemp);
		buttomtemp.setFont(f1);
		hbasebuttomtempmonth = new JButton("每月");
		leftbuttomJP.add(hbasebuttomtempmonth);
		hbasebuttomtempmonth.setFont(f1);
		hbasebuttomtempall = new JButton("全部");
		leftbuttomJP.add(hbasebuttomtempall);
		hbasebuttomtempall.setFont(f1);

		// 右边面板
		 imgJL = new JLabel(new
		 ImageIcon("/home/ch/hbase_pic/bartopmysql.jpeg"));//原始圖片
		 rightJP.add(imgJL);
		 rightJP.setLayout(new FlowLayout(FlowLayout.RIGHT));

		main_frame.setContentPane(contentJP);
		main_frame.setResizable(false);// 使窗口不能縮放
		// main_frame.pack();
		main_frame.setVisible(true);
		// end---界面代码
		
		//MySQL#############################################################################################
		//mysql每月最大值
		mysqltoptempmonth.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				//折线图
				//mysql数据库建立
//				  con_mysql conmysql =new con_mysql();
//				  Double[][] data = new Double[1][13];// 数据室double数组
//				  //获取执行时间
//				  Long mysql_startTime = System.currentTimeMillis();
//				  data = conmysql.mysql_temp(1,5);//获取数据
//				  Long mysql_endTime = System.currentTimeMillis();  
//				  Long mysql_time = mysql_endTime - mysql_startTime; 
//				  System.out.println("MySql查询用时：" + mysql_time+"毫秒"); 				  
//				  String[] hours =new String[]{"1","2","3","4","5","6","7","8","9","10","11","12"};//横坐标
//				  String[] cutline = new String[] { "最大值" };//折线代表的含义
//				  String title ="从mysql查询数据";//标题
//				  String subtitle ="mysql每月最大值";
//				  String ytitle = "气候值";
//				  String xtitle = "时间";
//				  String path = "/home/ch/hbase_pic/mysqltopmonth.jpeg";
//				  JFreeChat_frame JF1 =new JFreeChat_frame(580, 500,title ,subtitle, xtitle, ytitle,cutline ,hours, data);
//				  JF1.draw(path);
//				  //imgJL.setIcon(new ImageIcon("/home/ch/hbase_pic/de1.jpeg"));内存不会自动更新，必须用下面这种方式
//				  try {//更新图片
//					imgJL.setIcon(new ImageIcon(ImageIO.read(new File("/home/ch/hbase_pic/mysqltopmonth.jpeg"))));
//				  } catch (IOException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				  }
				
				//柱状图
				  con_mysql conmysql =new con_mysql();
				  Double[][] data = new Double[1][12];// 数据室double数组
				  //获取执行时间
				  Long mysql_startTime = System.currentTimeMillis();
				  if(mysqlmaxdata[0][0] == 0.0){
					  data = conmysql.mysql_temp(1,5);//获取数据
					  mysqlmaxdata =data;
				  }else{
					  data = mysqlmaxdata;
				  }
				  Long mysql_endTime = System.currentTimeMillis();  
				  Long mysql_time = mysql_endTime - mysql_startTime; 
				  System.out.println("MySql查询用时：" + mysql_time+"毫秒"); 
				JFreeChat_ChartBar bar = new JFreeChat_ChartBar();
				// 步骤1：创建CategoryDataset对象（准备数据）
				String[] col = new String[]{"1月","2月","3月","4月","5月","6月","7月","8月","9月","10月","11月","12月"};
				String[] row = { "" };;
				double[][] da = new double[1][12];
				for(int j=0;j<12;j++)
					da[0][j] = data[0][j].doubleValue();
				CategoryDataset dataset = bar.createDataset(da,row,col);
				// 步骤2：根据Dataset 生成JFreeChart对象，以及做相应的设置
				JFreeChart freeChart = bar.createChart(dataset,"mysql每月最大值");
				// 步骤3：将JFreeChart对象输出到文件，Servlet输出流等
				bar.saveAsFile(freeChart, "/home/ch/hbase_pic/bartopmysql.jpeg", 580, 500);
				try {//更新图片
					imgJL.setIcon(new ImageIcon(ImageIO.read(new File("/home/ch/hbase_pic/bartopmysql.jpeg"))));
				  } catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				  }
				mysqltoptempmonth.setContentAreaFilled(false);//透明，露出背景色
			}
		});
		
				//mysql全部最高值
				mysqltoptempall.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent arg0) {
						// TODO Auto-generated method stub
						//mysql数据库建立
						  con_mysql conmysql =new con_mysql();
						  Double[][] data = new Double[1][12];// 数据室double数组
						  //获取执行时间
						  Long mysql_startTime = System.currentTimeMillis();
						  if(mysqlmaxdata[0][0] == 0.0){
							  data = conmysql.mysql_temp(1,5);//获取数据
							  mysqlmaxdata =data;
						  }else{
							  data = mysqlmaxdata;
						  }
						  Long mysql_endTime = System.currentTimeMillis();  
						  Long mysql_time = mysql_endTime - mysql_startTime; 
						  System.out.println("MySql查询用时：" + mysql_time+"毫秒");
						  double x=0;
						  for(int i=0;i<12;i++)
							  if(data[0][i]>x)
								  x=data[0][i];
						  JOptionPane.showMessageDialog(null, "全年最高值："+x);
						  System.out.println("全年最高值："+x);
						  mysqltoptempall.setContentAreaFilled(false);//透明，露出背景色
					}
				});
				
				//mysql每月平均值
				mysqlmidtempmonth.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent arg0) {
						// TODO Auto-generated method stub
						//折线图
						//mysql数据库建立
//						  con_mysql conmysql =new con_mysql();
//						  Double[][] data = new Double[1][12];// 数据室double数组
//						  //获取执行时间
//						  Long mysql_startTime = System.currentTimeMillis();
//						  data = conmysql.mysql_temp(0,5);//获取数据
//						  Long mysql_endTime = System.currentTimeMillis();  
//						  Long mysql_time = mysql_endTime - mysql_startTime; 
//						  System.out.println("MySql查询用时：" + mysql_time+"毫秒");
//						  String[] hours =new String[]{"1","2","3","4","5","6","7","8","9","10","11","12"};//横坐标
//						  String[] cutline = new String[] { "平均值" };//折线代表的含义
//						  String title ="从mysql查询数据";//标题
//						  String subtitle ="mysql每月平均值";
//						  String ytitle = "气候值";
//						  String xtitle = "时间";
//						  String path = "/home/ch/hbase_pic/mysqlmidmonth.jpeg";
//						  JFreeChat_frame JF1 =new JFreeChat_frame(580, 500,title ,subtitle, xtitle, ytitle,cutline ,hours, data);
//						  JF1.draw(path);
//						  //imgJL.setIcon(new ImageIcon("/home/ch/hbase_pic/de1.jpeg"));内存不会自动更新，必须用下面这种方式
//						  try {//更新图片
//							imgJL.setIcon(new ImageIcon(ImageIO.read(new File("/home/ch/hbase_pic/mysqlmidmonth.jpeg"))));
//						  } catch (IOException e) {
//							// TODO Auto-generated catch block
//							e.printStackTrace();
//						  }
						  
						  
						//柱状图
						  con_mysql conmysql =new con_mysql();
						  Double[][] data = new Double[1][12];// 数据室double数组
						  //获取执行时间
						  Long mysql_startTime = System.currentTimeMillis();
						  if(mysqlmiddata[0][0] == 0.0){
							  data = conmysql.mysql_temp(0,5);//获取数据
							  mysqlmiddata =data;
						  }else{
							  data = mysqlmiddata;
						  }
						  Long mysql_endTime = System.currentTimeMillis();  
						  Long mysql_time = mysql_endTime - mysql_startTime; 
						  System.out.println("MySql查询用时：" + mysql_time+"毫秒"); 
						JFreeChat_ChartBar bar = new JFreeChat_ChartBar();
						// 步骤1：创建CategoryDataset对象（准备数据）
						String[] col = new String[]{"1月","2月","3月","4月","5月","6月","7月","8月","9月","10月","11月","12月"};
						String[] row = { "" };;
						double[][] da = new double[1][12];
						for(int j=0;j<12;j++)
							da[0][j] = data[0][j].doubleValue();
						CategoryDataset dataset = bar.createDataset(da,row,col);
						// 步骤2：根据Dataset 生成JFreeChart对象，以及做相应的设置
						JFreeChart freeChart = bar.createChart(dataset,"mysql每月平均值");
						// 步骤3：将JFreeChart对象输出到文件，Servlet输出流等
						bar.saveAsFile(freeChart, "/home/ch/hbase_pic/barmidmysql.jpeg", 580, 500);
						try {//更新图片
							imgJL.setIcon(new ImageIcon(ImageIO.read(new File("/home/ch/hbase_pic/barmidmysql.jpeg"))));
						  } catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						  }
						mysqlmidtempmonth.setContentAreaFilled(false);//透明，露出背景色
					}
				});
				
				//mysql全部平均值
				mysqlmidtempall.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent arg0) {
						// TODO Auto-generated method stub
						//mysql数据库建立
						  con_mysql conmysql =new con_mysql();
						  Double[][] data = new Double[1][12];// 数据室double数组
						  //获取执行时间
						  Long mysql_startTime = System.currentTimeMillis(); 
						  if(mysqlmidtempdata[0][0] == 0.0){
							  data = conmysql.mysql_temp(0,4);//获取数据
							  mysqlmidtempdata =data;
						  }else{
							  data = mysqlmidtempdata;
						  }
						  Long mysql_endTime = System.currentTimeMillis();  
						  Long mysql_time = mysql_endTime - mysql_startTime; 
						  System.out.println("MySql查询用时：" + mysql_time+"毫秒");
						  JOptionPane.showMessageDialog(null, "全年平均值："+data[0][0]);
						  System.out.println("全年平均值："+data[0][0]);
						  mysqlmidtempall.setContentAreaFilled(false);//透明，露出背景色
					}
				});
				
				//mysql每月最低值
				mysqlbuttomtempmonth.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent arg0) {
						// TODO Auto-generated method stub
						//折线图
						//mysql数据库建立
//						  con_mysql conmysql =new con_mysql();
//						  Double[][] data = new Double[1][12];// 数据室double数组
//						  //获取执行时间
//						  Long mysql_startTime = System.currentTimeMillis();
//						  data = conmysql.mysql_temp(-1,5);//获取数据
//						  Long mysql_endTime = System.currentTimeMillis();  
//						  Long mysql_time = mysql_endTime - mysql_startTime; 
//						  System.out.println("MySql查询用时：" + mysql_time+"毫秒");
//						  String[] hours =new String[]{"1","2","3","4","5","6","7","8","9","10","11","12"};//横坐标
//						  String[] cutline = new String[] { "最低值" };//折线代表的含义
//						  String title ="从mysql查询数据";//标题
//						  String subtitle ="mysql每月最低值";
//						  String ytitle = "气候值";
//						  String xtitle = "时间";
//						  String path = "/home/ch/hbase_pic/mysqlbuttommonth.jpeg";
//						  JFreeChat_frame JF1 =new JFreeChat_frame(580, 500,title ,subtitle, xtitle, ytitle,cutline ,hours, data);
//						  JF1.draw(path);
//						  //imgJL.setIcon(new ImageIcon("/home/ch/hbase_pic/de1.jpeg"));内存不会自动更新，必须用下面这种方式
//						  try {//更新图片
//							imgJL.setIcon(new ImageIcon(ImageIO.read(new File("/home/ch/hbase_pic/mysqlbuttommonth.jpeg"))));
//						  } catch (IOException e) {
//							// TODO Auto-generated catch block
//							e.printStackTrace();
//						  }
						  
						  
						//柱状图
						  con_mysql conmysql =new con_mysql();
						  Double[][] data = new Double[1][12];// 数据室double数组
						  //获取执行时间
						  Long mysql_startTime = System.currentTimeMillis();
						  if(mysqlmindata[0][0] == 0.0){
							  data = conmysql.mysql_temp(-1,5);//获取数据
							  mysqlmindata =data;
						  }else{
							  data = mysqlmindata;
						  }
						  Long mysql_endTime = System.currentTimeMillis();  
						  Long mysql_time = mysql_endTime - mysql_startTime; 
						  System.out.println("MySql查询用时：" + mysql_time+"毫秒"); 
						JFreeChat_ChartBar bar = new JFreeChat_ChartBar();
						// 步骤1：创建CategoryDataset对象（准备数据）
						String[] col = new String[]{"1月","2月","3月","4月","5月","6月","7月","8月","9月","10月","11月","12月"};
						String[] row = { "" };;
						double[][] da = new double[1][12];
						for(int j=0;j<12;j++)
							da[0][j] = data[0][j].doubleValue();
						CategoryDataset dataset = bar.createDataset(da,row,col);
						// 步骤2：根据Dataset 生成JFreeChart对象，以及做相应的设置
						JFreeChart freeChart = bar.createChart(dataset,"mysql每月最低值");
						// 步骤3：将JFreeChart对象输出到文件，Servlet输出流等
						bar.saveAsFile(freeChart, "/home/ch/hbase_pic/barbuttommysql.jpeg", 580, 500);
						try {//更新图片
							imgJL.setIcon(new ImageIcon(ImageIO.read(new File("/home/ch/hbase_pic/barbuttommysql.jpeg"))));
						  } catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						  }
						mysqlbuttomtempmonth.setContentAreaFilled(false);//透明，露出背景色
					}
				});
				
				//mysql全部最低值
				mysqlbuttomtempall.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent arg0) {
						// TODO Auto-generated method stub
						//mysql数据库建立
						  con_mysql conmysql =new con_mysql();
						  Double[][] data = new Double[1][12];// 数据室double数组
						  //获取执行时间
						  Long mysql_startTime = System.currentTimeMillis();
						  if(mysqlmindata[0][0] == 0.0){
							  data = conmysql.mysql_temp(-1,5);//获取数据
							  mysqlmindata =data;
						  }else{
							  data = mysqlmindata;
						  }
						  Long mysql_endTime = System.currentTimeMillis();  
						  Long mysql_time = mysql_endTime - mysql_startTime; 
						  System.out.println("MySql查询用时：" + mysql_time+"毫秒");
						  double x=5000;
						  for(int i=0;i<12;i++)
							  if(data[0][i]<x)
								  x=data[0][i];
						  JOptionPane.showMessageDialog(null, "全年最低值："+x);
						  System.out.println("全年最低值："+x);
						  mysqlbuttomtempall.setContentAreaFilled(false);//透明，露出背景色
					}
				});
				
				//HBase###########################################################################################
				//hbase每月最大值
				hbasetoptempmonth.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent arg0) {
						// TODO Auto-generated method stub
						//柱状图
						//数据读取
						  con_hbase chbase =new con_hbase();
						  //co_hbase cohbase = new co_hbase();
						  Double[][] data = new Double[1][24];// 数据室double数组
						  //获取执行时间
						  Long hbase_startTime = System.currentTimeMillis();
						  if(hbasemaxdata[0][0] == 0.0){
							  data = chbase.getAllRecord(hbasetable,1,5);//获取数据
							  hbasemaxdata = data;
						  }else{
							  data =hbasemaxdata;
						  }
						  //data = cohbase.connect_hbase(hbasetable,year_string, mon_string, day_string);//预处理获取数据
						  Long hbase_endTime = System.currentTimeMillis();  
						  Long hbase_time = hbase_endTime - hbase_startTime; 
						  System.out.println("HBase查询用时：" + hbase_time+"毫秒"); 
						JFreeChat_ChartBar bar = new JFreeChat_ChartBar();
						// 步骤1：创建CategoryDataset对象（准备数据）
						String[] col = new String[]{"1月","2月","3月","4月","5月","6月","7月","8月","9月","10月","11月","12月"};
						String[] row = { "" };;
						double[][] da = new double[1][12];
						for(int j=0;j<12;j++)
							da[0][j] = data[0][j].doubleValue();
						CategoryDataset dataset = bar.createDataset(da,row,col);
						// 步骤2：根据Dataset 生成JFreeChart对象，以及做相应的设置
						JFreeChart freeChart = bar.createChart(dataset,"hbase每月最大值");
						// 步骤3：将JFreeChart对象输出到文件，Servlet输出流等
						bar.saveAsFile(freeChart, "/home/ch/hbase_pic/bartophbase.jpeg", 580, 500);
						try {//更新图片
							imgJL.setIcon(new ImageIcon(ImageIO.read(new File("/home/ch/hbase_pic/bartophbase.jpeg"))));
						  } catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						  }
						hbasetoptempmonth.setContentAreaFilled(false);//透明，露出背景色
					}
				});
				
						//hbase全部最高值
				hbasetoptempall.addActionListener(new ActionListener() {

							@Override
							public void actionPerformed(ActionEvent arg0) {
								// TODO Auto-generated method stub
								//数据读取
								  con_hbase chbase =new con_hbase();
								  //co_hbase cohbase = new co_hbase();
								  Double[][] data = new Double[1][24];// 数据室double数组
								  //获取执行时间
								  Long hbase_startTime = System.currentTimeMillis();
								  if(hbasemaxdata[0][0] == 0.0){
									  data = chbase.getAllRecord(hbasetable,1,5);//获取数据
									  hbasemaxdata = data;
								  }else{
									  data =hbasemaxdata;
								  }
								  //data = cohbase.connect_hbase(hbasetable,year_string, mon_string, day_string);//预处理获取数据
								  Long hbase_endTime = System.currentTimeMillis();  
								  Long hbase_time = hbase_endTime - hbase_startTime; 
								  System.out.println("HBase查询用时：" + hbase_time+"毫秒");
								  double x=0;
								  for(int i=0;i<12;i++)
									  if(data[0][i]>x)
										  x=data[0][i];
								  JOptionPane.showMessageDialog(null, "全年最高值："+x);
								  System.out.println("全年最高值："+x);
								  hbasetoptempall.setContentAreaFilled(false);//透明，露出背景色
							}
						});
						
						//hbase每月平均值
				hbasemidtempmonth.addActionListener(new ActionListener() {

							@Override
							public void actionPerformed(ActionEvent arg0) {
								// TODO Auto-generated method stub
								//柱状图
								//数据读取
								  con_hbase chbase =new con_hbase();
								  //co_hbase cohbase = new co_hbase();
								  Double[][] data = new Double[1][24];// 数据室double数组
								  //获取执行时间
								  Long hbase_startTime = System.currentTimeMillis();
								  if(hbasemiddata[0][0] == 0.0){
									  data = chbase.getAllRecord(hbasetable,0,5);//获取数据
									  hbasemiddata = data;
								  }else{
									  data =hbasemiddata;
								  }
								  //data = cohbase.connect_hbase(hbasetable,year_string, mon_string, day_string);//预处理获取数据
								  Long hbase_endTime = System.currentTimeMillis();  
								  Long hbase_time = hbase_endTime - hbase_startTime; 
								  System.out.println("HBase查询用时：" + hbase_time+"毫秒"); 
								JFreeChat_ChartBar bar = new JFreeChat_ChartBar();
								// 步骤1：创建CategoryDataset对象（准备数据）
								String[] col = new String[]{"1月","2月","3月","4月","5月","6月","7月","8月","9月","10月","11月","12月"};
								String[] row = { "" };;
								double[][] da = new double[1][12];
								for(int j=0;j<12;j++)
									da[0][j] = data[0][j].doubleValue();
								CategoryDataset dataset = bar.createDataset(da,row,col);
								// 步骤2：根据Dataset 生成JFreeChart对象，以及做相应的设置
								JFreeChart freeChart = bar.createChart(dataset,"hbase每月平均值");
								// 步骤3：将JFreeChart对象输出到文件，Servlet输出流等
								bar.saveAsFile(freeChart, "/home/ch/hbase_pic/barmidhbase.jpeg", 580, 500);
								try {//更新图片
									imgJL.setIcon(new ImageIcon(ImageIO.read(new File("/home/ch/hbase_pic/barmidhbase.jpeg"))));
								  } catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								  }
								hbasemidtempmonth.setContentAreaFilled(false);//透明，露出背景色
							}
						});
						
						//hbase全部平均值
				hbasemidtempall.addActionListener(new ActionListener() {

							@Override
							public void actionPerformed(ActionEvent arg0) {
								// TODO Auto-generated method stub
								//数据读取
								  con_hbase chbase =new con_hbase();
								  //co_hbase cohbase = new co_hbase();
								  Double[][] data = new Double[1][24];// 数据室double数组
								  //获取执行时间
								  Long hbase_startTime = System.currentTimeMillis();
								  if(hbasemidtempdata[0][0] == 0.0){
									  data = chbase.getAllRecord(hbasetable,0,4);//获取数据
									  hbasemidtempdata = data;
								  }else{
									  data =hbasemidtempdata;
								  }
								  //data = cohbase.connect_hbase(hbasetable,year_string, mon_string, day_string);//预处理获取数据
								  Long hbase_endTime = System.currentTimeMillis();  
								  Long hbase_time = hbase_endTime - hbase_startTime; 
								  System.out.println("HBase查询用时：" + hbase_time+"毫秒"); 
								  JOptionPane.showMessageDialog(null, "全年平均值："+data[0][0]);
								  System.out.println("全年平均值："+data[0][0]);
								  hbasemidtempall.setContentAreaFilled(false);//透明，露出背景色
							}
						});
						
						//hbase每月最低值
				hbasebuttomtempmonth.addActionListener(new ActionListener() {

							@Override
							public void actionPerformed(ActionEvent arg0) {
								// TODO Auto-generated method stub
								//柱状图
								//数据读取
								  con_hbase chbase =new con_hbase();
								  //co_hbase cohbase = new co_hbase();
								  Double[][] data = new Double[1][24];// 数据室double数组
								  //获取执行时间
								  Long hbase_startTime = System.currentTimeMillis();
								  if(hbasemindata[0][0] == 0.0){
									  data = chbase.getAllRecord(hbasetable,-1,5);//获取数据
									  hbasemindata = data;
								  }else{
									  data =hbasemindata;
								  }
								  //data = cohbase.connect_hbase(hbasetable,year_string, mon_string, day_string);//预处理获取数据
								  Long hbase_endTime = System.currentTimeMillis();  
								  Long hbase_time = hbase_endTime - hbase_startTime; 
								  System.out.println("HBase查询用时：" + hbase_time+"毫秒"); 
								JFreeChat_ChartBar bar = new JFreeChat_ChartBar();
								// 步骤1：创建CategoryDataset对象（准备数据）
								String[] col = new String[]{"1月","2月","3月","4月","5月","6月","7月","8月","9月","10月","11月","12月"};
								String[] row = { "" };;
								double[][] da = new double[1][12];
								for(int j=0;j<12;j++)
									da[0][j] = data[0][j].doubleValue();
								CategoryDataset dataset = bar.createDataset(da,row,col);
								// 步骤2：根据Dataset 生成JFreeChart对象，以及做相应的设置
								JFreeChart freeChart = bar.createChart(dataset,"hbase每月最低值");
								// 步骤3：将JFreeChart对象输出到文件，Servlet输出流等
								bar.saveAsFile(freeChart, "/home/ch/hbase_pic/barbuttomhbase.jpeg", 580, 500);
								try {//更新图片
									imgJL.setIcon(new ImageIcon(ImageIO.read(new File("/home/ch/hbase_pic/barbuttomhbase.jpeg"))));
								  } catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								  }
								hbasebuttomtempmonth.setContentAreaFilled(false);//透明，露出背景色
							}
						});
						
						//hbase全部最低值
				hbasebuttomtempall.addActionListener(new ActionListener() {

							@Override
							public void actionPerformed(ActionEvent arg0) {
								// TODO Auto-generated method stub
								//数据读取
								  con_hbase chbase =new con_hbase();
								  //co_hbase cohbase = new co_hbase();
								  Double[][] data = new Double[1][24];// 数据室double数组
								  //获取执行时间
								  Long hbase_startTime = System.currentTimeMillis();
								  if(hbasemindata[0][0] == 0.0){
									  data = chbase.getAllRecord(hbasetable,-1,5);//获取数据
									  hbasemindata = data;
								  }else{
									  data =hbasemindata;
								  }
								  //data = cohbase.connect_hbase(hbasetable,year_string, mon_string, day_string);//预处理获取数据
								  Long hbase_endTime = System.currentTimeMillis();  
								  Long hbase_time = hbase_endTime - hbase_startTime; 
								  System.out.println("HBase查询用时：" + hbase_time+"毫秒"); 
								  double x=5000;
								  for(int i=0;i<12;i++)
									  if(data[0][i]<x)
										  x=data[0][i];
								  JOptionPane.showMessageDialog(null, "全年最低值："+x);
								  System.out.println("全年最低值："+x);
								  hbasebuttomtempall.setContentAreaFilled(false);//透明，露出背景色
							}
						});
				
	}
}
