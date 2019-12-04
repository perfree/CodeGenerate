package com.perfree.service;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.ZipUtil;
import com.perfree.common.ResponseBean;
import com.perfree.controller.TableController;
import com.perfree.generate.Generate;
import com.perfree.generate.GenerateConfigParse;
import com.perfree.mapper.TableMapper;
import com.perfree.module.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;
import java.util.Map;

@Service
public class GenerateService {
    private final static Logger logger = LoggerFactory.getLogger(TableController.class);
    private final static String SEPARATOR = System.getProperty("file.separator");

    @Autowired(required = false)
    private TableMapper tableMapper;

    @Autowired(required = false)
    private TableService tableService;

    @Value("${generateCodeTempPath}")
    private String generateCodeTempPath;

    @Value("${generateTemplatesPath}")
    private String generateTemplatesPath;

    /**
     * 生成代码
     *
     * @param author        作者
     * @param projecVersion 项目版本
     * @param projectName   项目名
     * @param templateName  模板名
     * @param generateTable 要生成的表
     * @return ResponseBean 结果
     */
    public ResponseBean generateCode(String author, String projecVersion, String projectName, String basePackage,
                                     String templateName, List<String> generateTable, List<String> generateModel) {
        // 项目配置
        ProjectParam projectParam = new ProjectParam();
        projectParam.setProjectName(projectName);
        projectParam.setProjectVersion(projecVersion);
        projectParam.setAuthor(author);
        projectParam.setBasePackage(basePackage);
        long nowTime = System.currentTimeMillis();
        try {
            // 遍历模板
            for (String template : generateModel) {
                // 遍历要生成的表
                for (String tableName : generateTable) {
                    // 获取到表的信息
                    Table table = tableMapper.queryTableByName(tableName);
                    // 获取到模板的生成信息
                    TemplateParam templateParam = GenerateConfigParse.getTemplateConfig(templateName, template, table, projectParam, generateTemplatesPath);
                    if (templateParam == null) {
                        logger.error("读取模板配置文件出错");
                        return null;
                    }
                    // 配置类参数
                    ClassParam classParam = new ClassParam();
                    classParam.setDescription(table.getTableComments());
                    classParam.setPackageName(templateParam.getPackageName());
                    classParam.setClassName(templateParam.getClassName());
                    // 查询表中的字段
                    List<TableField> tableFields = tableService.queryFieldForTable(table.getTableName());
                    table.setTableFields(tableFields);
                    String codeOutPath = generateCodeTempPath + projectName + "_" + nowTime + SEPARATOR + templateParam.getOutpath();
                    Generate.generateCode(projectParam, classParam, table, generateTemplatesPath + templateName, template, templateParam.getFileName(), codeOutPath);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseBean(500, "生成失败:" + e.getMessage(), null);
        }
        File zip = ZipUtil.zip(generateCodeTempPath + projectName + "_" + nowTime);
        FileUtil.del(new File(generateCodeTempPath + projectName + "_" + nowTime));
        return new ResponseBean(200, "生成成功", zip.getName());
    }

    /**
     * 根据模板名读取该模板下的模块名
     *
     * @param templateName 模板名
     * @return List<String>
     */
    public List<Map<String, String>> getModel(String templateName) throws Exception {
        return GenerateConfigParse.getModels(templateName, generateTemplatesPath);
    }
}
