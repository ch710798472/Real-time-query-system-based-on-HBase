package ch_mysql;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class count_mysql {
	public int count() {
		int length=0;
		try {
			Class.forName("com.mysql.jdbc.Driver"); // 加载MYSQL JDBC驱动程序
			System.out.println("Success loading Mysql Driver!");
		} catch (Exception ex) {
			System.out.print("Error loading Mysql Driver!");
			ex.printStackTrace();
		}
		try {
			Connection connect = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/hbase", "root", "456123");
			System.out.println("Success connect Mysql server!");
			PreparedStatement ps=null;
			//hly_temp_normal一百万条记录 test_data一千万条记录
			ps= connect.prepareStatement("select count(1) from hly_temp_normal");
			ResultSet rs = ps.executeQuery();
			rs.next();
			length = Integer.valueOf(rs.getString(1));
			System.out.println("Mysql表中总共"+length+"行");
		} catch (Exception e) {
			System.out.print("get data error!");
			e.printStackTrace();
		}
		return length;
	}// end of count_mysql()

}
