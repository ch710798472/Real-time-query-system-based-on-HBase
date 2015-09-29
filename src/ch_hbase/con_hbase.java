package ch_hbase;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.KeyValue;
import org.apache.hadoop.hbase.MasterNotRunningException;
import org.apache.hadoop.hbase.ZooKeeperConnectionException;
import org.apache.hadoop.hbase.client.Delete;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.hbase.client.HConnection;
import org.apache.hadoop.hbase.client.HConnectionManager;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.util.Bytes;

public class con_hbase {
	// 使用线程池
	private static Configuration conf = null;
	private static HConnection connection = null;
	/**
	 * 初始化配置
	 */
	static {
		Configuration HBASE_CONFIG = new Configuration();
		// 与hbase/conf/hbase-site.xml中hbase.zookeeper.quorum配置的值相同
		HBASE_CONFIG.set("hbase.zookeeper.quorum", "127.0.0.1");
		// 与hbase/conf/hbase-site.xml中hbase.zookeeper.property.clientPort配置的值相同
		HBASE_CONFIG.set("hbase.zookeeper.property.clientPort", "2181");
		conf = HBaseConfiguration.create(HBASE_CONFIG);
		// connection =
		// HConnectionManager.createConnection(getHBaseConfiguration());
	}

	/**
	 * 创建一张表
	 */
	public static void creatTable(String tableName, String[] familys)
			throws Exception {
		HBaseAdmin admin = new HBaseAdmin(conf);
		if (admin.tableExists(tableName)) {
			System.out.println("table already exists!");
		} else {
			HTableDescriptor tableDesc = new HTableDescriptor(tableName);
			for (int i = 0; i < familys.length; i++) {
				tableDesc.addFamily(new HColumnDescriptor(familys[i]));
			}
			admin.createTable(tableDesc);
			System.out.println("create table " + tableName + " ok.");
		}
	}

	/**
	 * 删除表
	 */
	public static void deleteTable(String tableName) throws Exception {
		try {
			HBaseAdmin admin = new HBaseAdmin(conf);
			admin.disableTable(tableName);
			admin.deleteTable(tableName);
			System.out.println("delete table " + tableName + " ok.");
		} catch (MasterNotRunningException e) {
			e.printStackTrace();
		} catch (ZooKeeperConnectionException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 插入一行记录
	 */
	public static void addRecord(String tableName, String rowKey,
			String family, String qualifier, String value) throws Exception {
		try {
			HTable table = new HTable(conf, tableName);
			Put put = new Put(Bytes.toBytes(rowKey));
			put.add(Bytes.toBytes(family), Bytes.toBytes(qualifier),
					Bytes.toBytes(value));
			table.put(put);
			System.out.println("insert recored " + rowKey + " to table "
					+ tableName + " ok.");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 删除一行记录
	 */
	public static void delRecord(String tableName, String rowKey)
			throws IOException {
		HTable table = new HTable(conf, tableName);
		List list = new ArrayList();
		Delete del = new Delete(rowKey.getBytes());
		list.add(del);
		table.delete(list);
		System.out.println("del recored " + rowKey + " ok.");
	}

	/**
	 * 查找一行记录
	 */
	public static Double[][] getOneRecord(String tableName, String rowKey)
			throws IOException {
		HTable table = new HTable(conf, tableName);
		Get get = new Get(rowKey.getBytes());
		Result rs = table.get(get);
		Double[][] data = new Double[1][24];
		int i = 0;
		for (KeyValue kv : rs.raw()) {
			System.out.print(new String(kv.getRow()) + " ");
			System.out.print(new String(kv.getFamily()) + ":");
			System.out.print(new String(kv.getQualifier()) + " ");
			System.out.print(kv.getTimestamp() + " ");
			System.out.println(new String(kv.getValue()));
			String key_value = new String(kv.getValue());
			data[0][i++] = Double.valueOf(key_value.substring(0,
					key_value.length() - 1));
		}
		// table.close();占时间
		return data;
	}

	/**
	 * 显示所有数据
	 */
	public static Double[][] getAllRecord(String tableName, int type, int flag) {
		Double[][] data = new Double[1][24];
		int i = 0;
		double[] x = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		double[] y = { 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000,
				5000, 5000, 5000, 5000 };
		int j;
		int[] count = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		int c = 0;
		try {
			HTable table = new HTable(conf, tableName);
			Scan s = new Scan();
			ResultScanner ss = table.getScanner(s);
			for (Result r : ss) {
				for (KeyValue kv : r.raw()) {
//					System.out.print(new String(kv.getRow()) + " ");
//					System.out.print(new String(kv.getFamily()) + ":");
//					System.out.print(new String(kv.getQualifier()) + " ");
//					System.out.print(kv.getTimestamp() + " ");
//					System.out.println(new String(kv.getValue()));
					String key_value = new String(kv.getValue());
					String rowkey = new String(kv.getRow());
					// start for r.raw()
					if (type == 1) {// 最高值
						if (flag == 5) {// 每月
							if (Double.valueOf(key_value.substring(0,
									key_value.length() - 1)) > x[Integer
									.valueOf(rowkey.substring(
											rowkey.length() - 4,
											rowkey.length() - 2))]) {
								x[Integer.valueOf(rowkey.substring(
										rowkey.length() - 4,
										rowkey.length() - 2))] = Double
										.valueOf(key_value.substring(0,
												key_value.length() - 1));
							}
						}
						if (flag == 4) {// 全部
							if (Double.valueOf(key_value.substring(0,
									key_value.length() - 1)) > x[Integer
									.valueOf(rowkey.substring(
											rowkey.length() - 4,
											rowkey.length() - 2))]) {
								x[Integer.valueOf(rowkey.substring(
										rowkey.length() - 4,
										rowkey.length() - 2))] = Double
										.valueOf(key_value.substring(0,
												key_value.length() - 1));
							}
						}
					}
					if (type == 0) {// 平均值
						if (flag == 5) {// 每月
							x[Integer.valueOf(rowkey.substring(
									rowkey.length() - 4,
									rowkey.length() - 2))] += Double
									.valueOf(key_value.substring(0,
											key_value.length() - 1));
							count[Integer.valueOf(rowkey.substring(
									rowkey.length() - 4,
									rowkey.length() - 2))]++;
						}
						if (flag == 4) {// 全部
							x[Integer.valueOf(rowkey.substring(
									rowkey.length() - 4,
									rowkey.length() - 2))] += Double
									.valueOf(key_value.substring(0,
											key_value.length() - 1));
							c++;
						}
					}
					if (type == -1) {// 最低值
						if (flag == 5) {// 每月
							if (Double.valueOf(key_value.substring(0,
									key_value.length() - 1)) < y[Integer
									.valueOf(rowkey.substring(
											rowkey.length() - 4,
											rowkey.length() - 2))]
									&& Double.valueOf(key_value.substring(0,
											key_value.length() - 1)) != -999) {
								y[Integer.valueOf(rowkey.substring(
										rowkey.length() - 4,
										rowkey.length() - 2))] = Double
										.valueOf(key_value.substring(0,
												key_value.length() - 1));

							}
						}
						if (flag == 4) {// 全部
							if (Double.valueOf(key_value.substring(0,
									key_value.length() - 1)) < y[Integer
									.valueOf(rowkey.substring(
											rowkey.length() - 4,
											rowkey.length() - 2))]
									&& Double.valueOf(key_value.substring(0,
											key_value.length() - 1)) != -999) {
								y[Integer.valueOf(rowkey.substring(
										rowkey.length() - 4,
										rowkey.length() - 2))] = Double
										.valueOf(key_value.substring(0,
												key_value.length() - 1));

							}
						}
					}
					// end for r.raw()
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
			System.out.println("get hbase data success!");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return data;
	}

	public Double[][] connect_hbase(String tablename, String rowKey,
			String month, String day) {
		Double[][] data = new Double[1][24];
		try {
			// String tablename = "hbase_hly_temp";
			// String[] familys = {"grade", "course"};
			// test_hbase.creatTable(tablename, familys);
			System.out.println("hbase_success");
			if (Integer.valueOf(month) < 10)
				month = "0" + month;
			if (Integer.valueOf(day) < 10)
				day = "0" + day;
			rowKey = rowKey + month + day;
			data = getOneRecord(tablename, rowKey);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return data;
	}

}
