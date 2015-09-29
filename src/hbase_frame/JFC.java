package hbase_frame;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;

public class JFC {  
public JFC() {  
}  
 
public static void main(String[] args){  
DefaultPieDataset dpd = new DefaultPieDataset();  
dpd.setValue("管理人员",25);  
dpd.setValue("市场人员",25);  
dpd.setValue("开发人员",45);  
dpd.setValue("其他人员",5);  
 
//Create JFreeChart object  
//参数可以查看源码  
JFreeChart pieChart = ChartFactory.createPieChart("CityInfoPort公司组织架构图",dpd,true,true,false);  
ChartFrame pieFrame = new ChartFrame("CityInfoPort公司组织架构图",pieChart);  
pieFrame.pack();  
pieFrame.setVisible(true);  
}  
} 