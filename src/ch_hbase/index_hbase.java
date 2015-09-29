package ch_hbase;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.KeyValue;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.coprocessor.BaseRegionObserver;
import org.apache.hadoop.hbase.coprocessor.ObserverContext;
import org.apache.hadoop.hbase.coprocessor.RegionCoprocessorEnvironment;
import org.apache.hadoop.hbase.regionserver.wal.WALEdit;
import org.apache.hadoop.hbase.util.Bytes;
//协处理器其中的一个作用是使用Observer创建二级索引。先举个实际例子： 
//我们要查询指定店铺指定客户购买的订单，首先有一张订单详情表，它以被处理后的订单id作为rowkey；其次有一张以客户nick为rowkey的索引表，结构如下： 
//
//rowkey family 
//dp_id+buy_nick1 tid1:null tid2:null ... 
//dp_id+buy_nick2 tid3:null 
//先在索引表get索引表，获取tids，然后根据tids查询订单详情表。 
//当有多个查询条件（多张索引表），根据逻辑运算符（and 、or）确定tids。 
public class index_hbase extends BaseRegionObserver { 
    @Override 
     public void prePut(final ObserverContext<RegionCoprocessorEnvironment> e, 
     final Put put, final WALEdit edit, final boolean writeToWAL) 
     throws IOException { 
         Configuration conf = new Configuration(); 
         HTable table = new HTable(conf, "index_table"); 
         List<KeyValue> kv = put.get("data".getBytes(), "name".getBytes()); 
         Iterator<KeyValue> kvItor = kv.iterator(); 
         while (kvItor.hasNext()) { 
             KeyValue tmp = kvItor.next(); 
             Put indexPut = new Put(tmp.getValue()); 
             indexPut.add("index".getBytes(), tmp.getRow(), Bytes.toBytes(System.currentTimeMillis())); 
             table.put(indexPut); 
         } 
         table.close(); 
     } 
} 
