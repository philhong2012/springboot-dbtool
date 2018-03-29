package com.kouer;

import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicInteger;


/**
 * Created by Administrator on 2017/11/21 0021.
 */
@Service
public class GlobalConst {
    /**
     * 连接成功计数器
     */
    public static AtomicInteger success = new AtomicInteger(0);
    /**
     * 连接失败计数器
     */
    public static AtomicInteger failed = new AtomicInteger(0);


}

