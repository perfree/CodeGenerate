package com.perfree.common;

import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * yml读取工具类
 * @author YinPengFei
 */
public class YmlUtil {
    /**
     * key:文件名索引
     * value:配置文件内容
     */
    private static Map<String, LinkedHashMap> ymls = new HashMap<>();

    /**
     * string:当前线程需要查询的文件名
     */
    private static ThreadLocal<String> nowFileName = new ThreadLocal<>();

    /**
     * 加载配置文件
     * @param ymlFile
     */
    public static void loadYml(File ymlFile) throws Exception {
        nowFileName.set(ymlFile.getName());
        ymls.clear();
        InputStream in = new FileInputStream(ymlFile);
        ymls.put(ymlFile.getName(), new Yaml().loadAs(in, LinkedHashMap.class));
        in.close();
    }

    public static String getValue(String key) throws Exception {
        // 首先将key进行拆分
        String[] keys = key.split("[.]");

        // 将配置文件进行复制
        Map ymlInfo = (Map) ymls.get(nowFileName.get()).clone();
        for (int i = 0; i < keys.length; i++) {
            if(ymlInfo.containsKey(keys[i])){
                Object value = ymlInfo.get(keys[i]);
                if (i < keys.length - 1) {
                    ymlInfo = (Map) value;
                } else if (value == null) {
                    return null;
                } else {
                    return value.toString();
                }
            }
        }
        return null;
    }

    public static String getValue(File ymlFile, String key) throws Exception {
        // 首先加载配置文件
        loadYml(ymlFile);
        return getValue(key);
    }


    public static void main(String[] args) throws Exception {
    }
}
