package com.perfree.controller;

import com.perfree.common.DynamicDataSource;
import com.perfree.common.ResponseBean;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.sql.DataSource;
import java.util.HashMap;

/**
 * 数据源配置controller
 * @author YinPengFei
 */
@Controller
public class DateSourceController {

    /**
     * 数据源配置页面
     * @return String
     */
    @RequestMapping("/datasource")
    public String index() {
        return "dataSource";
    }

    /**
     * 数据源配置
     * @param param 参数
     * @return ResponseBean
     */
    @RequestMapping("/datasource/setDataSource")
    @ResponseBody
    public ResponseBean setDataSource(@RequestBody HashMap<String, Object> param) {
        String type = (String) param.get("type");
        switch (type) {
            case "oracle":
                String ip = (String) param.get("ip");
                String port = (String) param.get("port");
                String sid = (String) param.get("sid");
                String userName = (String) param.get("userName");
                String password = (String) param.get("password");
                DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
                dataSourceBuilder.url("jdbc:oracle:thin:@" + ip + ":" + port + ":" + sid);
                dataSourceBuilder.username(userName);
                dataSourceBuilder.password(password);
                dataSourceBuilder.driverClassName("oracle.jdbc.driver.OracleDriver");
                DataSource dataSource = dataSourceBuilder.build();
                DynamicDataSource.clear();
                DynamicDataSource.setDataSource(dataSource);
                return new ResponseBean(200,"设置数据源成功",null);
            case "pdm":
                break;
            case "mysql":
                break;
        }
        return new ResponseBean(500,"设置数据源失败",null);
    }
}
