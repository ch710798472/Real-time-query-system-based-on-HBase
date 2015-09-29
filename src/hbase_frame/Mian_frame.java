package hbase_frame;

import java.awt.BorderLayout;
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
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.apache.hadoop.hbase.util.Bytes;

import ch_hbase.co_hbase;
import ch_hbase.con_hbase;
import ch_hbase.count_hbase;
import ch_mysql.con_mysql;
import ch_mysql.count_mysql;
public class Mian_frame {
	private String hbasetable = "hbase_temp";
	private String mysqltable = "hly_temp_normal";
	private long hbasecount=0;
	private int mysqlcount=0;
	private JTextField year;
	private JTextField mon;
	private JTextField day;
	private JButton searchjb;
	private JButton searchjb1;
	private JPanel contentJP;//內容顶层，再分左右两层
	private JPanel leftJP;
	private JPanel rightJP;
	private JPanel lefttopJP;
	private JPanel leftbuttomJP;
	private JFrame main_frame;
	private JLabel imgJL;
	private JLabel countJL;
	private JLabel countinfoJL;
	private JLabel timeinfoJL;
	private JLabel timeJL;
	private JLabel resultinfoJL;
	private JLabel resultJL;
	private JButton BThbase;
	private JButton BTmysql;
	private JButton count_mysqljb;
	private JButton count_hbasejb;
	private JButton morefunction;
	public static void main(String args[]){	
//  Double[][] data = new Double[3][24];// 数据室double数组
//		for (int n = 0; n < 24; n++) {
//			data[0][n] = 1 + Math.random() * 100;
//	}
//		for (int n = 0; n < 24; n++) {
//			data[1][n] = 1 + Math.random() * 100;
//	}
//		for (int n = 0; n < 24; n++) {
//			data[2][n] = 1 + Math.random() * 100;
//	}
//	JFreeChat_frame JF1 =new JFreeChat_frame(600, 500, "標題",
//			"副标题", "hhhhh", "zzzzzzz", new String[] { "1","2","3" },new String[]{"1","2","3","4","5","6","7","8","9","10","11","12","13","14"
//			,"15","16","17","18","19","20","21","22","23","24"}, data);
//	JF1.draw("/home/ch/hbase_pic");
		
		new Mian_frame();
		
		
	}
	public  Mian_frame(){
		//start---界面代码
				Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
				JFrame.setDefaultLookAndFeelDecorated(true);
				main_frame = new JFrame("基于Hbase的数据查询系统");
				main_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				main_frame.setSize(new Dimension(800, 600));//界面大小
				main_frame.setLocation((int) ((screenSize.getWidth() - main_frame.getWidth()) / 2),
						(int) (screenSize.getHeight() - main_frame.getHeight()) / 2);//居中显示
				
				contentJP =new JPanel();//內容顶层，再分左右两层
				leftJP = new JPanel();
				rightJP = new JPanel();
				lefttopJP = new JPanel();//左边分上下
				leftbuttomJP = new JPanel();
				
				BorderLayout BL = new BorderLayout();
				contentJP.setLayout(BL);
				BorderLayout BL1 = new BorderLayout();
				leftJP.setLayout(BL1);
				GridLayout GL = new GridLayout(4,2);
				leftbuttomJP.setLayout(GL);
				
				leftJP.setPreferredSize(new Dimension(180, 600));
				rightJP.setPreferredSize(new Dimension(700, 600));
				contentJP.add(leftJP,"West");
				contentJP.add(rightJP,"East");
				
				lefttopJP.setPreferredSize(new Dimension(180, 300));
				leftbuttomJP.setPreferredSize(new Dimension(180, 300));
				leftJP.add(lefttopJP,"North");
				leftJP.add(leftbuttomJP,"Center");
				leftbuttomJP.setBackground(java.awt.Color.GREEN);
				
				//左边面板
				JLabel yearJL = new JLabel("    id:");
				lefttopJP.add(yearJL);
				year =new JTextField();
				year.setText("USW00024033");
				lefttopJP.add(year);
				
				JLabel nullJL1 = new JLabel("  ");
				lefttopJP.add(nullJL1);
				
				JLabel monJL = new JLabel("month:");
				lefttopJP.add(monJL);
				mon =new JTextField(4);
				mon.setText("10");
				lefttopJP.add(mon);
				mon.addKeyListener(new KeyAdapter(){  //只能输入数字
		            public void keyTyped(KeyEvent e) {  
		                int keyChar = e.getKeyChar();      
		                if(keyChar >= KeyEvent.VK_0 && keyChar <= KeyEvent.VK_9){  
		                      
		                }else{  
		                    e.consume(); //关键，屏蔽掉非法输入  
		                }  
		            }  
		        });  
				JLabel nullJL2 = new JLabel("            ");
				lefttopJP.add(nullJL2);
				
				JLabel dayJL = new JLabel("    day:");
				lefttopJP.add(dayJL);
				day =new JTextField(4);
				day.setText("20");
				lefttopJP.add(day);
				day.addKeyListener(new KeyAdapter(){  //只能输入数字
		            public void keyTyped(KeyEvent e) {  
		                int keyChar = e.getKeyChar();    
		                if(keyChar >= KeyEvent.VK_0 && keyChar <= KeyEvent.VK_9){  
		                      
		                }else {  
		                    e.consume(); //关键，屏蔽掉非法输入  
		                }
		            }
		        });  
				JLabel nullJL3 = new JLabel("            ");
				lefttopJP.add(nullJL3);
				
				searchjb = new JButton("mysql查询");
				lefttopJP.add(searchjb);
				searchjb1 = new JButton("hbase查询");
				lefttopJP.add(searchjb1);
				count_mysqljb = new JButton("统计mysql行数");
				lefttopJP.add(count_mysqljb);
				count_hbasejb = new JButton("统计hbase行数");
				lefttopJP.add(count_hbasejb);
				morefunction = new JButton("更多功能");
				lefttopJP.add(morefunction);
				
				//左-下面板
				Font f1=new Font("宋体",Font.BOLD,10);
				countinfoJL = new JLabel("数据库行数  ->");
				leftbuttomJP.add(countinfoJL);
				countJL = new JLabel();
				leftbuttomJP.add(countJL);
				countinfoJL.setFont(f1);
				countJL.setFont(f1);
				
				timeinfoJL = new JLabel("查询时间    ->");
				leftbuttomJP.add(timeinfoJL);
				timeJL = new JLabel();
				leftbuttomJP.add(timeJL);
				timeinfoJL.setFont(f1);
				timeJL.setFont(f1);
				
				resultinfoJL = new JLabel("结果行数    ->");
				leftbuttomJP.add(resultinfoJL);
				resultJL = new JLabel();
				leftbuttomJP.add(resultJL);
				resultinfoJL.setFont(f1);
				resultJL.setFont(f1);
				
				Font f=new Font("宋体",Font.BOLD,10);//根据指定字体名称、样式和磅值大小，创建一个新 Font。
				
				BTmysql = new JButton("<html>10000次<br>Mysql查询</html>");
				BTmysql.setFont(f);
				BTmysql.setContentAreaFilled(false);
				leftbuttomJP.add(BTmysql);
				
				BThbase = new JButton("<html>10000次<br>HBase查询</html>");
				BThbase.setFont(f);
				BThbase.setContentAreaFilled(false);//透明，露出背景色
				leftbuttomJP.add(BThbase);
				
				//右边面板
				imgJL = new JLabel(new ImageIcon("/home/ch/hbase_pic/de.jpeg"));//原始圖片
				rightJP.add(imgJL);
				rightJP.setLayout(new FlowLayout(FlowLayout.RIGHT));
				
				main_frame.setContentPane(contentJP);
				main_frame.setResizable(false);//使窗口不能縮放
				//main_frame.pack();
				main_frame.setVisible(true);
				//end---界面代码
				
				
				//mysql查询代码
				searchjb.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent arg0) {
						// TODO Auto-generated method stub
					  String year_string = year.getText().trim();
					  String mon_string = mon.getText().trim();
					  String day_string = day.getText().trim();
					  //mysql数据库建立
					  con_mysql cmysql =new con_mysql();
					  Double[][] data = new Double[1][24];// 数据室double数组
					  //获取执行时间
					  Long mysql_startTime = System.currentTimeMillis();
					  data = cmysql.connect_mysql(year_string, mon_string, day_string);//获取数据
					  Long mysql_endTime = System.currentTimeMillis();  
					  Long mysql_time = mysql_endTime - mysql_startTime; 
					  System.out.println("MySql查询用时：" + mysql_time+"毫秒"); 
					  //设置左-下面板值
					  timeJL.setText(String.valueOf(mysql_time)+"毫秒");//执行时间
					  timeJL.setOpaque(true);//查询到数据显示变成红色
					  timeJL.setBackground(java.awt.Color.red);
					  
					  int result_mysql = cmysql.getcountresult();
					  System.out.println("MySql查询结果统计：" + result_mysql+"行"); 
					  resultJL.setText(result_mysql+"行");
					  resultJL.setOpaque(true);//查询到数据显示变成红色
					  resultJL.setBackground(java.awt.Color.red);
					  count_mysql countmysql = new count_mysql();
					  int length_mysql;
					  if(mysqlcount==0){
						  length_mysql = countmysql.count();
						  mysqlcount = length_mysql;
					  }else{
						  length_mysql = mysqlcount;
					  }
					  countJL.setText(length_mysql+"行");
					  countJL.setOpaque(true);//查询到数据显示变成红色
					  countJL.setBackground(java.awt.Color.red);
					  
					  String[] hours =new String[]{"1","2","3","4","5","6","7","8","9","10","11","12","13","14"
								,"15","16","17","18","19","20","21","22","23","24"};//横坐标
					  String[] cutline = new String[] { "平均气候值" };//折线代表的含义
					  String title ="从mysql查询数据";//标题
					  String subtitle = year_string+"--"+mon_string+"月"+day_string+"日";//副标题
					  String ytitle = "气候值";
					  String xtitle = "时间";
					  String path = "/home/ch/hbase_pic/mysql.jpeg";
					  JFreeChat_frame JF1 =new JFreeChat_frame(600, 500,title ,subtitle, xtitle, ytitle,cutline ,hours, data);
					  JF1.draw(path);
					  //imgJL.setIcon(new ImageIcon("/home/ch/hbase_pic/de1.jpeg"));内存不会自动更新，必须用下面这种方式
					  try {//更新图片
						imgJL.setIcon(new ImageIcon(ImageIO.read(new File("/home/ch/hbase_pic/mysql.jpeg"))));
					  } catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					  }
					 // contentJP.repaint();//重画JP
					}
				});
				
				//hbase查询代码
				searchjb1.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent arg0) {
						// TODO Auto-generated method stub
					  String year_string = year.getText().trim();
					  String mon_string = mon.getText().trim();
					  String day_string = day.getText().trim();
					  //数据读取
					  con_hbase chbase =new con_hbase();
					  //co_hbase cohbase = new co_hbase();
					  Double[][] data = new Double[1][24];// 数据室double数组
					  //获取执行时间
					  Long hbase_startTime = System.currentTimeMillis();
					  data = chbase.connect_hbase(hbasetable,year_string, mon_string, day_string);//获取数据
					  //data = cohbase.connect_hbase(hbasetable,year_string, mon_string, day_string);//预处理获取数据
					  Long hbase_endTime = System.currentTimeMillis();  
					  Long hbase_time = hbase_endTime - hbase_startTime; 
					  System.out.println("HBase查询用时：" + hbase_time+"毫秒"); 
					  //设置左-下面板值
					  timeJL.setText(String.valueOf(hbase_time)+"毫秒");//执行时间
					  timeJL.setOpaque(true);//查询到数据显示变成红色
					  timeJL.setBackground(java.awt.Color.red);
					  
					  long length_hbase;
					  if(hbasecount==0){//节约具体查询的时间不浪费在统计行数上
						  count_hbase counthbase = new count_hbase(Bytes.toBytes(hbasetable),Bytes.toBytes("n"));
						  length_hbase = counthbase.getcount();
						  hbasecount = length_hbase;
					  }else{
						  length_hbase =hbasecount;
					  }				  
					  countJL.setText(length_hbase+"行");
					  countJL.setOpaque(true);//查询到数据显示变成红色
					  countJL.setBackground(java.awt.Color.red);
					  System.out.println("HBase查询结果统计：1 行"); 
					  resultJL.setText("1 行");
					  resultJL.setOpaque(true);//查询到数据显示变成红色
					  resultJL.setBackground(java.awt.Color.red);
					  
					  String[] hours =new String[]{"1","2","3","4","5","6","7","8","9","10","11","12","13","14"
								,"15","16","17","18","19","20","21","22","23","24"};//横坐标
					  String[] cutline = new String[] { "平均气候值" };//折线代表的含义
					  String title ="从hbase查询数据";//标题
					  String subtitle = year_string+"--"+mon_string+"月"+day_string+"日";//副标题
					  String ytitle = "气候值";
					  String xtitle = "时间";
					  String path = "/home/ch/hbase_pic/hbase.jpeg";
					  JFreeChat_frame JF1 =new JFreeChat_frame(600, 500,title ,subtitle, xtitle, ytitle,cutline ,hours, data);
					  JF1.draw(path);
					  //imgJL.setIcon(new ImageIcon("/home/ch/hbase_pic/de1.jpeg"));内存不会自动更新，必须用下面这种方式
					  try {//更新图片
						imgJL.setIcon(new ImageIcon(ImageIO.read(new File("/home/ch/hbase_pic/hbase.jpeg"))));
					  } catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					  }
					 // contentJP.repaint();//重画JP
					}
				});
				
				//统计mysql行数
				count_mysqljb.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent arg0) {
						// TODO Auto-generated method stub
						count_mysql countmysql = new count_mysql();
						//获取执行时间
						Long mysql_startTime = System.currentTimeMillis();
						int length_mysql = countmysql.count();
						Long mysql_endTime = System.currentTimeMillis();  
						Long mysql_time = mysql_endTime - mysql_startTime; 
						System.out.println("MySql统计行数查询用时：" + mysql_time+"毫秒");
						mysqlcount = length_mysql;
						countJL.setText(length_mysql+"行");
						countJL.setOpaque(true);//查询到数据显示变成红色
						countJL.setBackground(java.awt.Color.red);
						timeJL.setText(String.valueOf(mysql_time)+"毫秒");//执行时间
						timeJL.setOpaque(true);//查询到数据显示变成红色
					    timeJL.setBackground(java.awt.Color.red);
						System.out.println("MySql查询结果统计：1行"); 
						resultJL.setText("1 行");
						resultJL.setOpaque(true);//查询到数据显示变成红色
						resultJL.setBackground(java.awt.Color.red);
					}
				});
				
				//统计hbase行数
				count_hbasejb.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent arg0) {
						// TODO Auto-generated method stub
						Long hbase_startTime = System.currentTimeMillis();
						count_hbase counthbase = new count_hbase(Bytes.toBytes(hbasetable),Bytes.toBytes("n"));
						long length_hbase = counthbase.getcount();
						Long hbase_endTime = System.currentTimeMillis();  
						Long hbase_time = hbase_endTime - hbase_startTime; 
						System.out.println("HBase统计行数查询用时：" + hbase_time+"毫秒");
						hbasecount=length_hbase;
						countJL.setText(length_hbase+"行");
						countJL.setOpaque(true);//查询到数据显示变成红色
						countJL.setBackground(java.awt.Color.red);
						timeJL.setText(String.valueOf(hbase_time)+"毫秒");//执行时间
						timeJL.setOpaque(true);//查询到数据显示变成红色
					    timeJL.setBackground(java.awt.Color.red);
						System.out.println("HBase查询结果统计：1行"); 
						resultJL.setText("1 行");
						resultJL.setOpaque(true);//查询到数据显示变成红色
						resultJL.setBackground(java.awt.Color.red);
					}
				});
				
				//更多功能
				morefunction.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent arg0) {
						// TODO Auto-generated method stub
						new More_frame();
					}
				});
				
				//10000次hbase查询
				BThbase.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent arg0) {
						// TODO Auto-generated method stub
						    String year_string = year.getText().trim();
						    String mon_string = mon.getText().trim();
						    String day_string = day.getText().trim();
						    //数据读取
						    con_hbase chbase =new con_hbase();
						   // co_hbase cohbase = new co_hbase();
						    Double[][] data = new Double[1][24];// 数据室double数组
						    Long hbase1000_startTime = System.currentTimeMillis();
						    for(int i=0;i<10000;i++){
							    data = chbase.connect_hbase(hbasetable,year_string, mon_string, day_string);//获取数据
							    //data = cohbase.connect_hbase(hbasetable,year_string, mon_string, day_string);//preGet获取数据
						    }				
						    Long hbase1000_endTime = System.currentTimeMillis();  
							Long hbase1000_time = hbase1000_endTime - hbase1000_startTime; 
							System.out.println("HBase10000次查询用时：" + hbase1000_time+"毫秒");
							if(hbasecount==0){
								count_hbase counthbase = new count_hbase(Bytes.toBytes(hbasetable),Bytes.toBytes("n"));
								long length_hbase = counthbase.getcount();
								hbasecount = length_hbase;
							}
							countJL.setText(hbasecount+"行");
							countJL.setOpaque(true);//查询到数据显示变成红色
							countJL.setBackground(java.awt.Color.red);
							timeJL.setText(String.valueOf(hbase1000_time)+"毫秒");//执行时间
							timeJL.setOpaque(true);//查询到数据显示变成红色
						    timeJL.setBackground(java.awt.Color.red);
							System.out.println("HBase10000次查询结果统计：10000行"); 
							resultJL.setText("10000 行");
							resultJL.setOpaque(true);//查询到数据显示变成红色
							resultJL.setBackground(java.awt.Color.red);
					}
				});
				
				//10000次mysql查询
				BTmysql.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent arg0) {
						// TODO Auto-generated method stub
						  String year_string = year.getText().trim();
						  String mon_string = mon.getText().trim();
						  String day_string = day.getText().trim();
						  //mysql数据库建立
						  con_mysql cmysql =new con_mysql();
						  Double[][] data = new Double[1][24];// 数据室double数组
						  //获取执行时间
						  Long mysql_startTime = System.currentTimeMillis();
						  for(int i=0;i<10000;i++)
							  data = cmysql.connect_mysql(year_string, mon_string, day_string);//获取数据
						  Long mysql_endTime = System.currentTimeMillis();  
						  Long mysql_time = mysql_endTime - mysql_startTime; 
						  System.out.println("MySql查询10000次用时：" + mysql_time+"毫秒"); 
						  //设置左-下面板值
						  timeJL.setText(String.valueOf(mysql_time)+"毫秒");//执行时间
						  timeJL.setOpaque(true);//查询到数据显示变成红色
						  timeJL.setBackground(java.awt.Color.red);
						  
						  System.out.println("MySql查询结果统计：10000 行"); 
						  resultJL.setText("10000行");
						  resultJL.setOpaque(true);//查询到数据显示变成红色
						  resultJL.setBackground(java.awt.Color.red);
						  if(mysqlcount==0){
							  count_mysql countmysql = new count_mysql();
							  int length_mysql = countmysql.count();
							  mysqlcount = length_mysql;
						  }
						  countJL.setText(mysqlcount+"行");
						  countJL.setOpaque(true);//查询到数据显示变成红色
						  countJL.setBackground(java.awt.Color.red);
					}
				});
	}
}
