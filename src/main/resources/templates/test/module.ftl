package ${classParam.packageName}<#-- 包名 -->;
<#-- 要导的包 -->
<#list classParam.packages as packae>
${packae}
</#list>

/**
 * ${classParam.description}<#-- 描述 -->
 * @author ${classParam.author}<#-- 作者 -->
 * @version ${projectParam.projectVersion}<#-- 版本 -->
 */
public class ${classParam.className} {

<#-- 字段 -->
<#list table.tableFields as field>
    //${field.fieldComments}
    private ${field.fieldJavaType} ${field.fieldNameLowerFirstLetter};

</#list>

<#-- get set -->
<#list table.tableFields as field>
    public ${field.fieldJavaType} get${field.fieldNameUpperFirstLetter}() {
        return ${field.fieldNameLowerFirstLetter};
    }

    public void set${field.fieldNameUpperFirstLetter}(${field.fieldJavaType} ${field.fieldNameLowerFirstLetter}) {
        this.${field.fieldNameLowerFirstLetter} = ${field.fieldNameLowerFirstLetter};
    }
</#list>
}