package org.jack.bean;

// 字段信息
public class FieldInfo {

    //    字段名称
    private String FieldName;
    //    bean属性名称
    private String BeanPropertyName;
    //    sql字段类型
    private String sqlFieldType;
    //    java字段类型
    private String javaFieldType;


    public String getFieldName() {
        return FieldName;
    }

    public void setFieldName(String fieldName) {
        FieldName = fieldName;
    }

    public String getBeanPropertyName() {
        return BeanPropertyName;
    }

    public void setBeanPropertyName(String beanPropertyName) {
        BeanPropertyName = beanPropertyName;
    }

    public String getSqlFieldType() {
        return sqlFieldType;
    }

    public void setSqlFieldType(String sqlFieldType) {
        this.sqlFieldType = sqlFieldType;
    }

    public String getJavaFieldType() {
        return javaFieldType;
    }

    public void setJavaFieldType(String javaFieldType) {
        this.javaFieldType = javaFieldType;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public boolean isAutoIncrement() {
        return isAutoIncrement;
    }

    public void setAutoIncrement(boolean autoIncrement) {
        isAutoIncrement = autoIncrement;
    }

    //    字段备注
    private String comment;
    //    字段是否自增长
    private boolean isAutoIncrement;
}
