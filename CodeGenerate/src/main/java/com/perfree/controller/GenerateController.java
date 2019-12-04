package com.perfree.controller;

import cn.hutool.core.io.FileUtil;
import com.perfree.common.DownlaodUtils;
import com.perfree.common.DynamicDataSource;
import com.perfree.common.ResponseBean;
import com.perfree.generate.Generate;
import com.perfree.module.Table;
import com.perfree.service.GenerateService;
import com.perfree.service.TableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 代码生成controller
 *
 * @author Perfree
 */
@Controller
public class GenerateController {

    @Value("${generateTemplatesPath}")
    private String generateTemplatesPath;

    @Autowired(required = false)
    private TableService tableService;
    @Autowired(required = false)
    private GenerateService generateService;
    @Value("${generateCodeTempPath}")
    private String generateCodeTempPath;

    /**
     * 代码生成页面
     *
     * @param model
     * @return
     */
    @RequestMapping("/codeGenerate")
    public String index(Model model) {
        try {
            model.addAttribute("templates", Generate.getTemplates(generateTemplatesPath));
        } catch (Exception e) {
            model.addAttribute("templates", null);
        }
        return "generate";
    }

    /**
     * 获取所有的表
     *
     * @return ResponseBean
     */
    @RequestMapping("/codeGenerate/getTables")
    @ResponseBody
    public ResponseBean getTables() {
        DataSource dataSource = DynamicDataSource.getDataSource();
        if (dataSource == null) {
            return new ResponseBean(500, "请配置数据源!", null);
        }
        List<Table> tables;
        try {
            tables = tableService.queryAllTable();
        } catch (Exception e) {
            return new ResponseBean(500, "请正确配置数据源!", null);
        }
        return new ResponseBean(200, "获取表成功", tables);
    }

    /**
     * 获取模板内所有的模块
     *
     * @param param 参数
     * @return ResponseBean
     */
    @RequestMapping("/codeGenerate/getModel")
    @ResponseBody
    public ResponseBean getModel(@RequestBody HashMap<String, Object> param) {
        String template = (String) param.get("template");
        List<Map<String, String>> model;
        try {
            model = generateService.getModel(template);
            return new ResponseBean(200, "获取成功", model);
        } catch (Exception e) {
            return new ResponseBean(500, "获取失败", null);
        }
    }

    /**
     * 生成代码
     *
     * @param param
     * @return
     */
    @RequestMapping("/codeGenerate/generate")
    @ResponseBody
    public ResponseBean generateCode(@RequestBody HashMap<String, Object> param) {
        String author = (String) param.get("author");
        String projecVersion = (String) param.get("projecVersion");
        String projectName = (String) param.get("projectName");
        String template = (String) param.get("template");
        String basePackage = (String) param.get("basePackage");
        List<String> generateTable = (List<String>) param.get("generateTable");
        List<String> generateModel = (List<String>) param.get("generateModel");
        return generateService.generateCode(author, projecVersion, projectName, basePackage, template, generateTable, generateModel);
    }

    /**
     * 下载文件
     *
     * @param request
     * @param response
     */
    @GetMapping("/codeGenerate/download")
    public void downloadFile(HttpServletRequest request, HttpServletResponse response) {
        String fileName = request.getParameter("fileName");
        if (fileName != null) {
            //设置文件路径
            File file = new File(generateCodeTempPath + fileName);
            DownlaodUtils.downloadFile(file, response, true);
        }
    }

}
