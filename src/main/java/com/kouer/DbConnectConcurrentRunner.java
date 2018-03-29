package com.kouer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.concurrent.CountDownLatch;

/**
 * Created by Administrator on 2017/12/1 0001.
 */
@Component
public class DbConnectConcurrentRunner implements CommandLineRunner {


    @Autowired
    ConfigBean configBean;

    private String getArgValue(String argName,String[] args) {
        for (String s : args) {
            String argName1 = s.substring(0,s.indexOf("="));
            if(argName.equals(argName1)) {
                return s.substring(s.indexOf("=") + 1);
            }
        }
        return "";
    }


    @Override
    public void run(String... args) throws Exception  {


        Integer threadCount = configBean.getThreadCount();


        String argThreadCount = getArgValue("threadCount",args);

        String url = getArgValue("url",args);

        String user = getArgValue("user",args);

        String password = getArgValue("password",args);

        String type = getArgValue("type",args);

        if(StringUtils.isEmpty(type)) {
            throw new IllegalArgumentException();
        }


        Assert.hasText(type,"数据库类型不可以为空");
        Assert.hasText(url,"数据库链接地址不能为空");
        Assert.hasText(user,"用户名不能为空");
        Assert.hasText(password,"密码不能为空");

        configBean.setUrl(url);
        configBean.setUser(user);
        configBean.setPassword(password);


        if( argThreadCount!= null && !"".equals(argThreadCount)) {

            threadCount = Integer.valueOf(argThreadCount);
            configBean.setThreadCount(threadCount);
            System.out.println("从启动参数中获取并发数：" + threadCount);
        } else {
            System.out.println("从配置中获取并发数:" + threadCount);
        }

        CountDownLatch countDownLatch = new CountDownLatch(configBean.getThreadCount());

        Worker worker = WorkerFactory.getWorker(type,configBean,countDownLatch);

        worker.run();

        countDownLatch.await();

        System.out.println("======================================================");
        System.out.print("总并发：" + configBean.getThreadCount() + " 成功: " + GlobalConst.success + " 失败: " + GlobalConst.failed);
        System.out.println();


    }
}
