package ch_hbase;

//新实现的类须继承BaseRegionObserver类

import java.io.IOException;
import java.util.List;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.KeyValue;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.coprocessor.BaseRegionObserver;
import org.apache.hadoop.hbase.coprocessor.ObserverContext;
import org.apache.hadoop.hbase.coprocessor.RegionCoprocessorEnvironment;
import org.apache.hadoop.hbase.util.Bytes;

public class co_hbase extends BaseRegionObserver {

	public static final byte[] FIXED_ROW = Bytes.toBytes("@@@GETTIME@@@");
	public static String tablename = "test_temp";
	public static String rowkey = "n";

	@Override
	public void preGet(final ObserverContext<RegionCoprocessorEnvironment> e,
			final Get get, final List<KeyValue> results) throws IOException {
		// if (Bytes.equals(get.getRow(), FIXED_ROW)) {
		// //书中原来的功能是如果查询的row为FIXED_ROW时，在结果返回系统时间
		KeyValue kv = new KeyValue(get.getRow(), FIXED_ROW, FIXED_ROW,
				Bytes.toBytes(System.currentTimeMillis()));
		results.add(kv);
		// }
	}

	public static Double[][] selectRow(String tablename, String rowKey)
			throws IOException {
		Configuration config = HBaseConfiguration.create();
		HTable table = new HTable(config, tablename);
		Get g = new Get(rowKey.getBytes());
		Result rs = table.get(g);
		Double[][] data= new Double[1][24];
        int i = 0;
		for (KeyValue kv : rs.raw()) {
			System.out.print(new String(kv.getRow()) + " ");
			System.out.print(new String(kv.getFamily()) + ":");
			System.out.print(new String(kv.getQualifier()) + " ");
			System.out.println(new String(kv.getValue()));
			String key_value = new String(kv.getValue());
            data[0][i++]=Double.valueOf(key_value.substring(0,3));
		}
		//table.close();
		return data;
	}
	public Double[][] connect_hbase(String tablename,String rowKey,String month,String day) {
    	Double[][] data = new Double[1][24];
    	try {
            System.out.println("hbase_success");
            if(Integer.valueOf(month)<10) month="0"+month;
            if(Integer.valueOf(day)<10) day="0"+day;
            rowKey=rowKey+month+day;
            data = selectRow(tablename, rowKey);
        } catch (Exception e) {
            e.printStackTrace();
        }
    	return data;
    }
    
}