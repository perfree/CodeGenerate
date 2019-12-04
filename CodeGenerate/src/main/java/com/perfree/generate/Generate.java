package com.perfree.generate;

import com.perfree.common.YmlUtil;
import com.perfree.module.ClassParam;
import com.perfree.module.ProjectParam;
import com.perfree.module.Table;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 生成代码
 * @author YinPengFei
 */
@Component
public class Generate {
    private final static Logger logger = LoggerFactory.getLogger(Generate.class);
    private final static String SEPARATOR = System.getProperty("file.separator");

    /**
     * 生成文件
     *  @param projectParam      项目配置
     * @param classParam        类配置
     * @param table             表
     * @param templateDirectory 模板目录
     * @param TemplateName      模板名
     * @param fileName          文件名
     * @param outPath           输出目录
     */
    public static void generateCode(ProjectParam projectParam, ClassParam classParam, Table table, String templateDirectory,
                                    String TemplateName, String fileName, String outPath) throws Exception {
        Writer writer = null;
        // 准备工作
        classParam.setPackages(GenerateImportPackageUtils.getImportPackage(table.getTableFields()));

        Configuration configuration = new Configuration(Configuration.getVersion());
        configuration.setClassicCompatible(true);
        File codeTemplatePath = new File(templateDirectory);
        configuration.setDirectoryForTemplateLoading(codeTemplatePath);
        //获取模板
        Template template = configuration.getTemplate(TemplateName+".ftl");
        //设置数据并执行
        Map<String, Object> map = new HashMap<>();
        map.put("projectParam", projectParam);
        map.put("classParam", classParam);
        map.put("table", table);
        // 验证目录是否存在
        File file = new File(outPath);
        if (!file.exists()) {
            boolean mkdirs = file.mkdirs();
            if (!mkdirs) {
                logger.error(outPath + "目录创建失败!");
                throw new Exception(outPath + "目录创建失败!");
            }
        }
        // 输出文件路径
        writer = new OutputStreamWriter(new FileOutputStream(outPath + SEPARATOR + fileName));
        template.process(map, writer);
        writer.flush();
        writer.close();
        logger.info(fileName + "已生成");
    }

    /**
     * 获取所有模板
     *
     * @param generateTemplatesPath 模板目录
     * @return List<String> 模板名集合
     */
    public static List<Map<String,String>> getTemplates(String generateTemplatesPath) throws Exception {
        File templateDir = new File(generateTemplatesPath);
        File[] files = templateDir.listFiles();
        List<Map<String,String>> templates = new ArrayList<>();
        if (files == null) {
            logger.error("没有模板!");
            throw new FileNotFoundException("没有模板!");
        }
        for (File file : files) {
            if(file.isDirectory()){
                File[] templateFiles = file.listFiles();
                for (File templateFile : templateFiles) {
                    if (templateFile.getName().equals("templateConfig.yml")){
                        Map<String,String> map = new HashMap<>();
                        map.put("name",YmlUtil.getValue(templateFile,"template.name"));
                        map.put("path",file.getName());
                        templates.add(map);
                    }
                }
            }
        }
        return templates;
    }

    /**
     * 获取模板的配置
     * @param templateNme 模板名
     * @param generateTemplatesPath 模板目录
     * @return List<Map<String,String>>
     * @throws Exception e
     */
    public static Map<String,String> getTemplateParam(String templateNme, String generateTemplatesPath) throws Exception {
        Map<String,String> param = new HashMap<>();
        File templateFile = new File(generateTemplatesPath + templateNme);
        File[] listFiles = templateFile.listFiles();
        if (listFiles == null) {
            logger.error("当前模板下没有模板文件!");
            throw new FileNotFoundException("当前模板下没有模板文件!");
        }
        for (File file : listFiles) {
            if (file.getName().equals("templateConfig.yml")){
                param.put("templateName",YmlUtil.getValue(file, "template.name"));
                param.put("templatePath",templateFile.getName());
            }
        }
        if(param.isEmpty()){
            throw new FileNotFoundException("模板配置错误!");
        }
        return param;
    }

    /**
     * 获取指定模板下所有的模板文件
     *
     * @param templateNme           要获取的模板名称
     * @param generateTemplatesPath 模板目录
     * @return List<String> 模板文件名称集合
     */
    public static List<String> getTemplate(String templateNme, String generateTemplatesPath) throws FileNotFoundException {
        List<String> templates = new ArrayList<>();
        File templateFile = new File(generateTemplatesPath + templateNme);
        File[] listFiles = templateFile.listFiles();
        if (listFiles == null) {
            logger.error("当前模板目录下没有模板文件!");
            throw new FileNotFoundException("当前模板目录下没有模板文件!");
        }
        for (File file : listFiles) {
            String suffix  = file.getName().substring(file.getName().lastIndexOf(".") + 1);
            if (!file.getName().equals("templateConfig.yml") && suffix.equals("ftl")) {
                templates.add(file.getName());
            }
        }
        return templates;
    }
}
