package ch_hbase;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.client.coprocessor.AggregationClient;
import org.apache.hadoop.hbase.util.Bytes;

public class count_scan { 
/*(1)disable指定表。hbase> disable 'mytable'

(2)添加aggregation hbase> alter 'mytable', METHOD => 'table_att','coprocessor'=>'|org.apache.hadoop.hbase.coprocessor.AggregateImplementation||'

(3)重启指定表 hbase> enable 'mytable'
 * */
private static final byte[] TABLE_NAME = Bytes.toBytes("hbase_hly_temp");//指定表名
private static final byte[] CF = Bytes.toBytes("n");//指定列族
public static void main(String[] args) throws Throwable {
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
long rowCount = aggregationClient.rowCount(TABLE_NAME, null, scan);
System.out.println("row count is " + rowCount);

}
}