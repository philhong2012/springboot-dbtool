package com.kouer;


import java.sql.*;
import java.util.concurrent.CountDownLatch;

/**
 * Created by Administrator on 2017/11/21 0021.
 */
@Deprecated
public class OracleConcurrencyThread extends Thread {

    ConfigBean configBean;
    /**
     *  如果CountDownLatch 类不被spring扫描（Consider defining a bean of type 'java.util.concurrent.CountDownLatch' in your configuration），
     *  则出现 Parameter 1 of constructor in com.kouer.OracleConcurrencyThread required a bean of type 'java.util.concurrent.CountDownLatch' that could not be found.
     *  而configBean 与 countDownLatch 手动从构造器中传入，解决办法：此线程类无需增加@Component注解
     */


    CountDownLatch countDownLatch;

    public OracleConcurrencyThread(ConfigBean configBean, CountDownLatch countDownLatch) {
        this.configBean = configBean;
        this.countDownLatch = countDownLatch;
    }


    public void run() {
        Connection conn = null;
        try {
            Class.forName(configBean.getName());
            //获取连接
            conn = DriverManager.getConnection(configBean.getUrl(), configBean.getUser(), configBean.getPassword());
            //关闭自动提交，不然conn.commit()运行到这句会报错
            conn.setAutoCommit(false);
        } catch (ClassNotFoundException e1) {
            e1.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (conn != null) {
            //开始时间
            Long startTime = System.currentTimeMillis();
            //测试sql语句
            String sql = "select 1 from dual";
            String id = null;
            try {
                Statement stmt = conn.createStatement();
                //结果集
                ResultSet rs = stmt.executeQuery(sql);
                if (rs.next()) {
                    id = rs.getString("1");
                }
                Thread.sleep(2000);
                conn.commit();
                stmt.close();
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {

            }
            Long end = System.currentTimeMillis();
            System.out.println(currentThread().getName() + "  查询结果:" + id + "   开始时间:" + startTime + "  结束时间:" + end + "  用时:" + (end - startTime) + "ms");
            GlobalConst.success.incrementAndGet();


        } else {
            System.out.println(currentThread().getName() + "数据库连接失败:");
            GlobalConst.failed.incrementAndGet();
        }

        countDownLatch.countDown();
    }

}

