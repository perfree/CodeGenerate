package com.perfree.common;

import com.perfree.module.ClassParam;
import com.perfree.module.ProjectParam;
import com.perfree.module.Table;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * 生成器
 */
public class GeneratorUtils {

    private final static Logger logger = LoggerFactory.getLogger(GeneratorUtils.class);
    /**
     * 文件分割符
     */
    public static final String SEPARATOR = System.getProperty("file.separator");

    /**
     * 生成文件
     *
     * @param projectParam      项目配置
     * @param classParam        类配置
     * @param table             表
     * @param templateDirectory 模板目录
     * @param TemplateName      模板名
     * @param fileExtension     文件后缀
     * @param outPath           输出目录
     */
    public static void generatorCode(ProjectParam projectParam, ClassParam classParam, Table table, String templateDirectory,
                                     String TemplateName, String fileExtension, String outPath) {
        Writer writer = null;
        try {
            // 准备工作
            classParam.setClassName(StringUtils.strUpperFirst(table.getTableName()));
            classParam.setPackages(GeneratorImportPackageUtils.getImportPackage(table.getTableFields()));

            Configuration configuration = new Configuration(Configuration.getVersion());
            configuration.setClassicCompatible(true);
            URL uri = GeneratorUtils.class.getClassLoader().getResource(templateDirectory);
            if (uri == null) {
                logger.error(templateDirectory + "模板不存在!");
                return;
            }
            String codeTemplatePath = uri.getPath();
            configuration.setDirectoryForTemplateLoading(new File(codeTemplatePath));
            //获取模板
            Template template = configuration.getTemplate(TemplateName);
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
                    return;
                }
            }
            // 输出文件路径
            writer = new OutputStreamWriter(new FileOutputStream(outPath + classParam.getClassName() + fileExtension));
            template.process(map, writer);
            logger.info(classParam.getClassName() + fileExtension + "已生成");
        } catch (Exception e) {
            logger.error("生成" + classParam.getClassName() + fileExtension + "失败!");
        }finally {
            if(writer!= null){
                try {
                    writer.flush();
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }



}
