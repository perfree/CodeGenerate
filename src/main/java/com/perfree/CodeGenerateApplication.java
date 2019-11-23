package com.perfree;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.perfree.mapper")
public class CodeGenerateApplication {

    public static void main(String[] args) {
        SpringApplication.run(CodeGenerateApplication.class, args);
    }

}
