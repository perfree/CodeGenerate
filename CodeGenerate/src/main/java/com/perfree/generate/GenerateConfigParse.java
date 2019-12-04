package com.perfree.generate;

import com.perfree.common.StringUtils;
import com.perfree.common.YmlUtil;
import com.perfree.module.ProjectParam;
import com.perfree.module.Table;
import com.perfree.module.TemplateParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * TODO
 *
 * @author YinPengFei
 */
public class GenerateConfigParse {

    private final static Logger logger = LoggerFactory.getLogger(GenerateConfigParse.class);
    /**
     * 获取模板设置
     *
     * @param templateDirNme 模板目录
     * @param templateName   模板名
     * @return TemplateParam 模板配置
     */
    public static TemplateParam getTemplateConfig(String templateDirNme, String templateName, Table table,
                                                  ProjectParam projectParam, String generateTemplatesPath) throws Exception {
        File file = new File(generateTemplatesPath + templateDirNme + "/templateConfig.yml");
        TemplateParam templateParam = new TemplateParam();
        templateName = templateName.replaceAll(".ftl", "");
        String fileName = YmlUtil.getValue(file,templateName + ".fileName");
        if (org.apache.commons.lang3.StringUtils.isBlank(fileName)) {
            throw new Exception(templateName + ".fileName模板配置不存在");
        }
        templateParam.setFileName(parseTemplateConfig(fileName, table, projectParam));
        String outpath = YmlUtil.getValue(file,templateName + ".outpath");
        if (org.apache.commons.lang3.StringUtils.isBlank(outpath)) {
            throw new Exception(templateName + ".outpath模板配置不存在");
        }
        templateParam.setOutpath(parseTemplateConfig(outpath, table, projectParam));
        String packageName = YmlUtil.getValue(file,templateName + ".package");
        if (org.apache.commons.lang3.StringUtils.isBlank(packageName)) {
            throw new Exception(templateName + ".package模板配置不存在");
        }
        templateParam.setPackageName(parseTemplateConfig(packageName, table, projectParam));
        String className = YmlUtil.getValue(file,templateName + ".className");
        if (org.apache.commons.lang3.StringUtils.isNotBlank(packageName)) {
            templateParam.setClassName(parseTemplateConfig(className, table, projectParam));
        }
        return templateParam;
    }

    /**
     * 获取模板下所有的模块名
     * @param templateDirNme
     * @param generateTemplatesPath
     * @return
     * @throws Exception
     */
    public static List<Map<String,String>> getModels(String templateDirNme,String generateTemplatesPath) throws Exception {
        File file = new File(generateTemplatesPath + templateDirNme);
        File[] files = file.listFiles();
        List<Map<String,String>> models = new ArrayList<>();
        for (File modelFile : files) {
            String templateName = modelFile.getName().replaceAll(".ftl", "");
            String suffix  = modelFile.getName().substring(modelFile.getName().lastIndexOf(".") + 1);
            if (suffix.equals("ftl")) {
                String modelName = YmlUtil.getValue(new File(file+"/templateConfig.yml"),templateName + ".modelName");
                if (org.apache.commons.lang3.StringUtils.isNotBlank(modelName)) {
                    Map<String,String> map = new HashMap<>();
                    map.put("modelName",modelName);
                    map.put("modelPath",templateName);
                    models.add(map);
                }
            }
        }
        return models;
    }
    /**
     * 模板内容解析
     *
     * @param param 模板参数
     * @return String
     */
    private static String parseTemplateConfig(String param, Table table, ProjectParam projectParam) {
        if (param.contains("${tableName}")) {
            param = param.replaceAll("\\$\\{tableName}", table.getTableName());
        } else if (param.contains("${tableNameUpperFirstLetter}")) {
            param = param.replaceAll("\\$\\{tableNameUpperFirstLetter}", StringUtils.strUpperFirst(table.getTableName()));
        } else if (param.contains("${tableNameLowerFirstLetter}")) {
            param = param.replaceAll("\\$\\{tableNameLowerFirstLetter}", StringUtils.strLowerFirst(table.getTableName()));
        } else if (param.contains("${basePackageName}")) {
            param = param.replaceAll("\\$\\{basePackageName}", projectParam.getBasePackage());
        } else if (param.contains("${basePackagePath}")) {
            String packagePath = projectParam.getBasePackage().replaceAll("\\.", "/");
            param = param.replaceAll("\\$\\{basePackagePath}", packagePath);
        }
        return param;
    }
}
