package com.perfree.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 建表语句解析
 */
public class CreateSqlAnalysisUtils {
    private final static Logger logger = LoggerFactory.getLogger(CreateSqlAnalysisUtils.class);

    public static void main(String args[]) {
        URL url = CreateSqlAnalysisUtils.class.getClassLoader().getResource("sql");
        if (url == null) {
            logger.error("sql文件不存在!");
            return;
        }
        String sqlPath = url.getPath() + "/SCOTT.sql";
        File file = new File(sqlPath);
        if (!file.exists()) {
            logger.error("sql文件不存在!");
            return;
        }
        String sql = readToString(file.getPath());
        String pattern = "(CREATE TABLE\\s*\"\\w+\".\\s*\"\\w+\"\\s*\\([\\s\\S]*\\))";
        // 创建 Pattern 对象
        Pattern r = Pattern.compile(pattern);

        // 现在创建 matcher 对象
        Matcher m = r.matcher(sql);
        if (m.find()) {
            System.out.println("Found value: " + m.group());
        } else {
            System.out.println("NO MATCH");
        }

    }


    private static String readToString(String fileName) {
        String encoding = "UTF-8";
        File file = new File(fileName);
        Long filelength = file.length();
        byte[] filecontent = new byte[filelength.intValue()];
        try {
            FileInputStream in = new FileInputStream(file);
            in.read(filecontent);
            in.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            return new String(filecontent, encoding);
        } catch (UnsupportedEncodingException e) {
            System.err.println("The OS does not support " + encoding);
            e.printStackTrace();
            return null;
        }
    }

   /* public static void main( String args[] ){

        // 按指定模式在字符串查找
        String line = "11CREATE TABLE \"SCOTT\".\"UPMS_PERMISSION\" (\n" +
                "  \"PERMISSION_ID\" NUMBER(9) NOT NULL ,\n" +
                "  \"SYSTEM_ID\" NUMBER(38) NOT NULL ,\n" +
                "  \"PID\" NUMBER(38) ,\n" +
                "  \"NAME\" VARCHAR2(64 BYTE) ,\n" +
                "  \"TYPE\" NUMBER(38) ,\n" +
                "  \"PERMISSION_VALUE\" VARCHAR2(50 BYTE) ,\n" +
                "  \"URI\" VARCHAR2(100 BYTE) ,\n" +
                "  \"ICON\" VARCHAR2(50 BYTE) ,\n" +
                "  \"STATUS\" NUMBER(38) ,\n" +
                "  \"CTIME\" NUMBER(38) ,\n" +
                "  \"ORDERS\" NUMBER(38) \n" +
                ")";
        String pattern = "(CREATE TABLE\\s*\"\\w+\".\\s*\"\\w+\"\\s*\\([\\s\\S]+\\))";

        // 创建 Pattern 对象
        Pattern r = Pattern.compile(pattern);

        // 现在创建 matcher 对象
        Matcher m = r.matcher(line);
        if (m.find( )) {
            System.out.println("Found value: " + m.group() );
        } else {
            System.out.println("NO MATCH");
        }
    }*/
}
