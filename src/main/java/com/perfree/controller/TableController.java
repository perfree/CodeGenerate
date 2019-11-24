package com.perfree.controller;

import com.perfree.common.ClassGeneratorUtil;
import com.perfree.common.StringUtils;
import com.perfree.module.ClassParam;
import com.perfree.module.ProjectParam;
import com.perfree.module.Table;
import com.perfree.module.TableField;
import com.perfree.service.TableService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/table")
public class TableController {
    private final static Logger logger = LoggerFactory.getLogger(TableController.class);
    @Autowired
    private TableService tableService;

    @RequestMapping("/queryAllTable")
    public void queryAllTable() {
        // TODO 数据库类型转java类型,module类规整
        List<Table> tableNames = tableService.queryAllTable();
        // 项目配置
        ProjectParam projectParam = new ProjectParam();
        projectParam.setProjectName("test");
        projectParam.setProjectVersion("v1.0.0");
        for (Table table : tableNames) {
            // 类配置
            ClassParam classParam = new ClassParam();
            classParam.setAuthor("Perfree");
            classParam.setDescription(table.getTableComments());
            classParam.setPackageName("com.perfree");
            List<TableField> tableFields = tableService.queryFieldForTable(table.getTableName());
            table.setTableFields(tableFields);
            String packagePath = StringUtils.packageNametoDirStr(classParam.getPackageName());
            // 验证目录是否存在
            String codeOutPath = "E:/" + packagePath;
            ClassGeneratorUtil.generatorCode(projectParam, classParam, table, "templates/test", "module.ftl", ".java", codeOutPath);
        }
    }
}
