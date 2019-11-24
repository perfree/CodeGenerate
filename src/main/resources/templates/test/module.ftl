package ${classParam.packageName};

/**
 * ${classParam.description}
 * @author ${classParam.author}
 */
public class ${classParam.className} {

<#list table.tableFields as field>
    //${field.fieldComments}
    private ${field.fieldJavaType} ${field.fieldNameLowerFirstLetter};
</#list>

<#list table.tableFields as field>
    public ${field.fieldJavaType} get${field.fieldNameUpperFirstLetter}() {
        return ${field.fieldNameLowerFirstLetter};
    }

    public void set${field.fieldNameUpperFirstLetter}(${field.fieldJavaType} ${field.fieldNameLowerFirstLetter}) {
        this.${field.fieldNameLowerFirstLetter} = ${field.fieldNameLowerFirstLetter};
    }
</#list>
}