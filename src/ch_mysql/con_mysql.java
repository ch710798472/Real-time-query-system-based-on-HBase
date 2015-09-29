package ch_mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class con_mysql {
	private int length = 0;

	public Double[][] connect_mysql(String stnid, String month, String day) {
		Double[][] data = new Double[1][24];
		try {
			Class.forName("com.mysql.jdbc.Driver"); // 加载MYSQL JDBC驱动程序
			System.out.println("Success loading Mysql Driver!");
		} catch (Exception ex) {
			System.out.print("Error loading Mysql Driver!");
			ex.printStackTrace();
		}
		Connection connect = null;
		PreparedStatement ps = null;
		try {
			connect = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/hbase", "root", "456123");
			System.out.println("Success connect Mysql server!");
			// insert into code
			// PreparedStatement
			// Statement=connect.prepareStatement("INSERT INTO user VALUES(?,?)");
			// Statement.setString(1,"ch");
			// Statement.setString(2,"aaa");
			// Statement.executeUpdate();
			// select code
			// Statement stmt = connect.createStatement();
			// hly_temp_normal一百万条记录 test_data一千万条记录
			ps = connect
					.prepareStatement("select * from hly_temp_normal where stnid=? and month=? and day=?");
			ps.setString(1, stnid);
			ps.setString(2, month);
			ps.setString(3, day);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				for (int i = 1; i <= 24; i++) {
					System.out.println(Double.valueOf(rs.getString("value" + i)
							.substring(0, rs.getString("value" + i).length()-1)));
					data[0][i - 1] = Double.valueOf(rs.getString("value" + i)
							.substring(0, rs.getString("value" + i).length()-1));
				}
				length++;
			}
		} catch (Exception e) {
			System.out.print("get data error!");
			e.printStackTrace();
		} finally// 处理大量连接处理方式，一定要关闭
		{
			if (ps != null)
				try {
					ps.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			if (connect != null)
				try {
					connect.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		return data;
	}// end of connect_mysql()

	public int getcountresult() {
		return length;
	}

	public Double[][] mysql_temp(int type, int flag) {
		Double[][] data = new Double[1][12];
		try {
			Class.forName("com.mysql.jdbc.Driver"); // 加载MYSQL JDBC驱动程序
			System.out.println("Success loading Mysql Driver!");
		} catch (Exception ex) {
			System.out.print("Error loading Mysql Driver!");
			ex.printStackTrace();
		}
		Connection connect = null;
		PreparedStatement ps = null;
		try {
			connect = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/hbase", "root", "456123");
			System.out.println("Success connect Mysql server!");
			// hly_temp_normal一百万条记录 test_data一千万条记录 test_temp十万条记录
			ps = connect.prepareStatement("select * from hly_temp_normal");// hly_temp_normal where stnid='AQW00061705'
			//ps = connect.prepareStatement("select * from test_temp");
			ResultSet rs = ps.executeQuery();
			double[] x = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
			double[] y = { 5000, 5000,5000,5000,5000,5000,5000,5000,5000,5000,5000,5000,5000 };
			int j;
			int[] count = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
			int c =0;
			while (rs.next()) {
				for (int i = 1; i <= 24; i++) {
					if (type == 1) {//最高值
						if(flag==5){//每月
							//System.out.println(rs.getString("stnid")+rs.getString("month")+rs.getString("day")+"value"+ i+rs.getString("value" + i));
							if (Double.valueOf(rs.getString("value" + i).substring(
									0, rs.getString("value" + i).length()-1)) > x[Integer.valueOf(rs
									.getString("month"))]) {
								x[Integer.valueOf(rs.getString("month"))] = Double
										.valueOf(rs.getString("value" + i)
												.substring(0, rs.getString("value" + i).length()-1));
							}
						}	
						if(flag==4){//全部
							if (Double.valueOf(rs.getString("value" + i).substring(
									0, rs.getString("value" + i).length()-1)) > x[Integer.valueOf(rs
									.getString("month"))]) {
								x[Integer.valueOf(rs.getString("month"))] = Double
										.valueOf(rs.getString("value" + i)
												.substring(0, rs.getString("value" + i).length()-1));
								
							}
						}	
					}
					if (type == 0) {//平均值
						if(flag==5){//每月
								x[Integer.valueOf(rs.getString("month"))] += Double
										.valueOf(rs.getString("value" + i)
												.substring(0, rs.getString("value" + i).length()-1));	
									count[Integer.valueOf(rs.getString("month"))]++;
						}	
						if(flag==4){//全部
								x[Integer.valueOf(rs.getString("month"))] += Double
										.valueOf(rs.getString("value" + i)
												.substring(0, rs.getString("value" + i).length()-1));
								c++;
						}
					}
					if (type == -1) {//最低值
						if(flag==5){//每月
							if (Double.valueOf(rs.getString("value" + i).substring(
									0, rs.getString("value" + i).length()-1)) < y[Integer.valueOf(rs
									.getString("month"))]&&Double.valueOf(rs.getString("value" + i).substring(
											0, rs.getString("value" + i).length()-1))!=-999) {
								y[Integer.valueOf(rs.getString("month"))] = Double
										.valueOf(rs.getString("value" + i)
												.substring(0, rs.getString("value" + i).length()-1));
								
							}
						}	
						if(flag==4){//全部
							if (Double.valueOf(rs.getString("value" + i).substring(
									0, rs.getString("value" + i).length()-1)) > x[Integer.valueOf(rs
									.getString("month"))]) {
								x[Integer.valueOf(rs.getString("month"))] = Double
										.valueOf(rs.getString("value" + i)
												.substring(0, rs.getString("value" + i).length()-1));
								
							}
						}
					}
				}
			}
			if (type == 0 && flag == 5) {//平均值//每月	
				for(j=1;j<13;j++)
					data[0][j-1] = x[j]/count[j];	
			}else if(type == 0 && flag ==4){
				double l=0;
				for(j=1;j<13;j++)
					l+= x[j];
				data[0][0]=l/c;
			}else if(type == -1 && flag ==5){
				for(j=1;j<13;j++)
					data[0][j-1] = y[j];
			}else{
				for(j=1;j<13;j++)
					data[0][j-1] = x[j];
			}
			System.out.println("get mysql data success!");
		} catch (Exception e) {
			System.out.print("get data error!");
			e.printStackTrace();
		} finally// 处理大量连接处理方式，一定要关闭
		{
			if (ps != null)
				try {
					ps.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			if (connect != null)
				try {
					connect.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		return data;
	}// end of mysql_temp()
}
