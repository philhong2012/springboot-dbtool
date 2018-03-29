package com.kouer;


import java.sql.*;
import java.util.concurrent.CountDownLatch;

/**
 * ${DESCRIPTION}
 *
 * @author hongxubing
 * @create 2018-03-29 17:13
 **/
@DBType(DBTypeEnum.oracle)
public class OracleWorker extends AbstractWorker {

    public OracleWorker(ConfigBean configBean, CountDownLatch countDownLatch) {
        this.configBean = configBean;
        this.countDownLatch = countDownLatch;
    }


    @Override
    public void doConnectToDb() {

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
            System.out.println(Thread.currentThread().getName() + "  查询结果:" + id + "   开始时间:" + startTime + "  结束时间:" + end + "  用时:" + (end - startTime) + "ms");
            GlobalConst.success.incrementAndGet();


        } else {
            System.out.println(Thread.currentThread().getName() + "数据库连接失败:");
            GlobalConst.failed.incrementAndGet();
        }

        countDownLatch.countDown();
    }
}
