package com.kouer;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.concurrent.CountDownLatch;

/**
 * ${DESCRIPTION}
 *
 * @author hongxubing
 * @create 2018-03-29 17:24
 **/
@DBType(DBTypeEnum.mysql)
public class MysqlWorker extends AbstractWorker implements Worker {

    public MysqlWorker(ConfigBean configBean, CountDownLatch countDownLatch) {
        this.configBean = configBean;
        this.countDownLatch = countDownLatch;
    }


    @Override
    void doConnectToDb() {
        System.out.println("未实现 mysql 数据库的 连接测试");
        throw new NotImplementedException();
    }
}
