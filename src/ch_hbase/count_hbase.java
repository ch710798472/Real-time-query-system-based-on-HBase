package ch_hbase;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.client.coprocessor.AggregationClient;
import org.apache.hadoop.hbase.util.Bytes;

public class count_hbase {
	private byte[] TABLE_NAME ;//= Bytes.toBytes("test_temp");//指定表名
	private byte[] CF ;//= Bytes.toBytes("n");//指定列族
	private long rowCount;
	public count_hbase(byte[] table,byte[] cf) {
		// TODO Auto-generated constructor stub		
		TABLE_NAME=table;
		CF=cf;
		Configuration customConf = new Configuration();
		customConf.setStrings("hbase.zookeeper.quorum",
		"localhost");
		//提高RPC通信时长
		customConf.setLong("hbase.rpc.timeout", 600000);
		//设置Scan缓存
		customConf.setLong("hbase.client.scanner.caching", 1000);
		Configuration configuration = HBaseConfiguration.create(customConf);
		AggregationClient aggregationClient = new AggregationClient(
		configuration);
		Scan scan = new Scan();
		//指定扫描列族，唯一值
		scan.addFamily(CF);
		try {
			rowCount = aggregationClient.rowCount(TABLE_NAME, null, scan);
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//System.out.println("row count is " + rowCount);
	}
	public long getcount(){		
		return rowCount;
	}
}
