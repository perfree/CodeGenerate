package ${param.packageName};

/**
 * ${param.comment}
 * @author ${param.author}
 */
public class ${param.className} {

<#list param.fieldList as field>
    //${field.fieldRemarks}
    private ${field.fieldType} ${field.fieldName};
</#list>

<#list param.fieldList as field>
    public ${field.fieldType} get${field.fieldNameUpperFirstLetter}() {
        return ${field.fieldName};
    }

    public void set${field.fieldNameUpperFirstLetter}(${field.fieldType} ${field.fieldName}) {
        this.${field.fieldName} = ${field.fieldName};
    }
</#list>
}