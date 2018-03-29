package com.kouer;

import com.kouer.Worker;

import java.util.concurrent.CountDownLatch;

/**
 * ${DESCRIPTION}
 *
 * @author hongxubing
 * @create 2018-03-29 17:14
 **/
public abstract class AbstractWorker implements Worker {

    ConfigBean configBean;
    /**
     *  如果CountDownLatch 类不被spring扫描（Consider defining a bean of type 'java.util.concurrent.CountDownLatch' in your configuration），
     *  则出现 Parameter 1 of constructor in com.kouer.OracleConcurrencyThread required a bean of type 'java.util.concurrent.CountDownLatch' that could not be found.
     *  而configBean 与 countDownLatch 手动从构造器中传入，解决办法：此线程类无需增加@Component注解
     */

    CountDownLatch countDownLatch;

    abstract void doConnectToDb();

    @Override
    public void run() {
        for (int i = 1; i <= configBean.getThreadCount(); i++) {
           new Thread() {
                @Override
                public void run() {
                    doConnectToDb();
                }
            }.start();
        }
    }
}
