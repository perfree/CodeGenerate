package com.perfree.controller;

import com.perfree.generate.ClassGenerator;
import com.perfree.generate.ModuleClass;
import com.perfree.generate.ModuleField;
import com.perfree.module.Table;
import com.perfree.module.TableField;
import com.perfree.service.TableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/table")
public class TableController {

    @Autowired
    private TableService tableService;

    @RequestMapping("/queryAllTable")
    public void queryAllTable() {
        // TODO 数据库类型转java类型,module类规整
        List<Table> tableNames = tableService.queryAllTable();
        for (Table table : tableNames) {
            ModuleClass moduleClass = new ModuleClass();
            moduleClass.setClassName(table.getTable_name());
            List<ModuleField> list = new ArrayList<>();
            List<TableField> TableFields = tableService.queryFieldForTable(table.getTable_name());
            for (TableField tableField : TableFields) {
                ModuleField field = new ModuleField();
                field.setFieldName(tableField.getColumn_name());
                field.setFieldType(tableField.getData_type());
                field.setFieldRemarks("111");
                field.setFieldNameUpperFirstLetter(tableField.getColumn_name());
                list.add(field);
            }
            moduleClass.setFieldList(list);
            moduleClass.setAuthor("perfree");
            moduleClass.setComment("qwe");
            moduleClass.setPackageName("com.perfree");
            ClassGenerator.generatorModule(moduleClass, "templates/test", "module.ftl", ".java", "E:/");
        }
    }
}
