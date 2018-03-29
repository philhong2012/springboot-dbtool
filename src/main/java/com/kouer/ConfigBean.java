package com.kouer;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created by Administrator on 2017/12/1 0001.
 */


@Component
public class ConfigBean {

    @Value("${db.url}")
    private String url;
    @Value("${db.driver}")
    private String name;
    @Value("${db.user}")
    private String user;
    @Value("${db.password}")
    private String password;

    @Value("${threadCount}")
    private Integer threadCount;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getThreadCount() {
        return threadCount;
    }

    public void setThreadCount(Integer threadCount) {
        this.threadCount = threadCount;
    }
}
