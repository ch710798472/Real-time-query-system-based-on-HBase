package ch_hbase;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.KeyValue;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.common.SolrInputDocument;

public class solr_hbase {

    /**
     * @param args
     * @throws IOException
     * @throws SolrServerException
     */
    public static void main(String[] args) throws IOException,
            SolrServerException {
        final Configuration conf;
        HttpSolrServer solrServer = new HttpSolrServer("http://localhost:8983/solr"); // 因为服务端是用的Solr自带的jetty容器，默认端口号是8983

        conf = HBaseConfiguration.create();
        HTable table = new HTable(conf, "st"); // 这里指定HBase表名称
        Scan scan = new Scan();
        scan.addFamily(Bytes.toBytes("time")); // 这里指定HBase表的列族
        scan.setCaching(500);
        scan.setCacheBlocks(false);
        ResultScanner ss = table.getScanner(scan);

        System.out.println("start ...");
        int i = 0;
        try {
            for (Result r : ss) {
                SolrInputDocument solrDoc = new SolrInputDocument();
                solrDoc.addField("rowkey", new String(r.getRow()));
                for (KeyValue kv : r.raw()) {
                    String fieldName = new String(kv.getQualifier());
                    String fieldValue = new String(kv.getValue());
                    if (fieldName.equalsIgnoreCase("time")
                            || fieldName.equalsIgnoreCase("tebid")
                            || fieldName.equalsIgnoreCase("tetid")
                            || fieldName.equalsIgnoreCase("puid")
                            || fieldName.equalsIgnoreCase("mgcvid")
                            || fieldName.equalsIgnoreCase("mtcvid")
                            || fieldName.equalsIgnoreCase("smaid")
                            || fieldName.equalsIgnoreCase("mtlkid")) {
                        solrDoc.addField(fieldName, fieldValue);
                    }
                }
                solrServer.add(solrDoc);
                solrServer.commit(true, true, true);
                i = i + 1;
                System.out.println("已经成功处理 " + i + " 条数据");
            }
            ss.close();
            table.close();
            System.out.println("done !");
        } catch (IOException e) {
        } finally {
            ss.close();
            table.close();
            System.out.println("erro !");
        }
    }

}