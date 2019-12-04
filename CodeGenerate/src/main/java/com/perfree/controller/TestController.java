package com.perfree.controller;

import com.perfree.service.TableService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@RequestMapping(value = "/test")
public class TestController {
    private final static Logger logger = LoggerFactory.getLogger(TableController.class);
    @Autowired
    private TableService tableService;

    @RequestMapping("/generate")
    public void generate() throws IOException {
    }

    public static void main( String args[] ){

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
    }
}
