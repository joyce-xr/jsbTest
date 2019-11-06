import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class MysqlUtil {
    public static Object[][] getSigalDataFromMySql(String searchSql) {

        System.out.println("-----------MysqlUtil.getSigalDataFromMySql-----------");

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://172.23.6.92:3306/jsb", "testuser", "test&20190911");
            Statement statement = conn.createStatement();

            //查询已到车签收的运单号mainsn
//            String sql = "select ShowSN from waybill_logistics where DepartID=\n" +
//                    "(select id from depart where ArriveNetID=\n" +
//                    "(select NetID from jsb.logistics_net_employee where UserID=(select id from mml.`user` where BindPhone=12711111135)) and IsSign=1 ORDER BY id DESC limit 1)";

            ResultSet rs = statement.executeQuery(searchSql);

            //不知道有多少个包裹，定义一个List
            List<Object[]> records = new ArrayList<Object[]>();
            while (rs.next()) {
                String record[] = new String[1];
                record[0] = rs.getString(1);//注意索引值从1开始
                System.out.println("-----------DataProvider中打印运单号(record[0])-----------" + record[0]);
                records.add(record);
            }

            //将list转化为二位数组
            Object[][] results = new Object[records.size()][];
            for (int i = 0; i < records.size(); i++) {
                results[i] = records.get(i);//get方法索引值从0开始
            }

            //打印二维数组
            for (int i = 0; i < results.length; i++) {
                for (int j = 0; j < results[i].length; j++) {
                    System.out.println("-------------mainsn：打印二维数组results[][]-----------" + results[i][j]);
                }
            }

            return results;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }
}
