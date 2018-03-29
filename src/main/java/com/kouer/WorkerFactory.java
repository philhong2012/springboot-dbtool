package com.kouer;

import org.reflections.Reflections;

import java.lang.reflect.Constructor;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;

/**
 * ${DESCRIPTION}
 *
 * @author hongxubing
 * @create 2018-03-29 17:29
 **/
public class WorkerFactory {

    private static Map<String, Class> allStrategies = new HashMap<String, Class>();

    static {

        Reflections reflections = new Reflections("com.kouer");
        Set<Class<?>> annotatedClasses =
                reflections.getTypesAnnotatedWith(DBType.class);
        Map strategies = new ConcurrentHashMap<String, Class>();
        for (Class<?> clazz : annotatedClasses)
        {
            DBType type =  clazz.getAnnotation(DBType.class);
            allStrategies.put(type.value().toString(), clazz);
        }

        //allStrategies.putAll(Collections.unmodifiableMap(strategies));
    }

    public static Worker getWorker(String dbType, ConfigBean cb, CountDownLatch cdl) {
        Class clazz = allStrategies.get(dbType);
        if(clazz == null) {
            throw new IllegalArgumentException(String.format("不支持此类数据库 [%s]",dbType));
        }

        Worker worker = null;
        try {
            Constructor constructor = clazz.getConstructor(new Class[]{
                    ConfigBean.class,CountDownLatch.class
            });

            worker = (Worker) constructor.newInstance(cb,cdl);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {

        }
        return worker;
    }
}
