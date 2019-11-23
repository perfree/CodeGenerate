package com.perfree.generate;

import freemarker.template.Configuration;
import freemarker.template.Template;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 生成器
 */
public class ClassGenerator {

    private final static Logger logger = LoggerFactory.getLogger(ClassGenerator.class);
    /** 文件分割符 */
    private static final String SEPARATOR = System.getProperty("file.separator");

    public static void generatorModule(ModuleClass moduleClass, String templateDirectory, String TemplateName, String fileExtension, String outPath) {
        try {
            Configuration configuration = new Configuration(Configuration.getVersion());
            URL uri = ClassGenerator.class.getClassLoader().getResource(templateDirectory);
            if (uri == null) {
                logger.error(templateDirectory + "模板不存在!");
                return;
            }
            String codeTemplatePath = uri.getPath();
            configuration.setDirectoryForTemplateLoading(new File(codeTemplatePath));
            //获取模板
            Template template = configuration.getTemplate(TemplateName);
            //设置数据并执行
            Map<String, ModuleClass> map = new HashMap<>();
            map.put("param", moduleClass);
            // 组装包名路径
            String[] split = moduleClass.getPackageName().split("\\.");
            StringBuilder packageName = new StringBuilder();
            for (String packageStr : split) {
                packageName.append(packageStr).append(SEPARATOR);
            }
            // 验证目录是否存在
            String codeOutPath = outPath + packageName;
            File file = new File(codeOutPath);
            if (!file.exists()) {
                boolean mkdirs = file.mkdirs();
                if (!mkdirs) {
                    logger.error(codeOutPath + "目录创建失败!");
                    return;
                }
            }
            // 输出文件路径
            Writer writer = new OutputStreamWriter(new FileOutputStream(codeOutPath + moduleClass.getClassName() + fileExtension));
            // 生成代码
            template.process(map, writer);
            logger.info(moduleClass.getClassName() + fileExtension + "已生成");
        } catch (Exception e) {
            logger.error("生成" + moduleClass.getClassName() + fileExtension + "失败!");
        }
    }

    public static void main(String[] args) throws Exception {
        ModuleClass moduleClass = new ModuleClass();
        moduleClass.setClassName("user");
        List<ModuleField> list = new ArrayList<>();
        ModuleField field = new ModuleField();
        field.setFieldName("id");
        field.setFieldNameUpperFirstLetter("Id");
        field.setFieldRemarks("主键");
        field.setFieldType("int");
        list.add(field);
        moduleClass.setFieldList(list);
        moduleClass.setAuthor("perfree");
        moduleClass.setComment("用户表");
        moduleClass.setPackageName("com.perfree");
        ClassGenerator.generatorModule(moduleClass, "templates/test", "module.ftl", ".java", "E:/");
    }
}
