package com.perfree.common;

import com.perfree.module.TableField;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * 生成要导的包
 */
public class GeneratorImportPackageUtils {

    /**
     * 获取要导的包
     * @param fileds
     * @return
     */
    public static List<String> getImportPackage(List<TableField> fileds){
        HashSet<String> javaTypes = new HashSet<>();
        for (TableField tableField : fileds) {
            javaTypes.add(tableField.getFieldJavaType());
        }
        List<String> packages = new ArrayList<>();
        for(String javaType: javaTypes) {
            switch (javaType){
                case "Date":
                    packages.add("import java.util.Date;");
            }
        }
        return packages;
    }
}
