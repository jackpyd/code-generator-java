package org.jack.bean;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

// 记录表信息
public class TableInfo {
    //    表名
    private String tableName;
    //    bean名称
    private String beanName;
    //    参数名称
    private String beanParamName;
    //    表注解
    private String comment;
    //    字段信息
    private List<FieldInfo> fieldInfoList;

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getBeanName() {
        return beanName;
    }

    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }

    public String getBeanParamName() {
        return beanParamName;
    }

    public void setBeanParamName(String beanParamName) {
        this.beanParamName = beanParamName;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public List<FieldInfo> getFieldInfoList() {
        return fieldInfoList;
    }

    public void setFieldInfoList(List<FieldInfo> fieldInfoList) {
        this.fieldInfoList = fieldInfoList;
    }


    public boolean isHasDate() {
        return hasDate;
    }

    public void setHasDate(boolean hasDate) {
        this.hasDate = hasDate;
    }

    public boolean isHasTime() {
        return hasTime;
    }

    public void setHasTime(boolean hasTime) {
        this.hasTime = hasTime;
    }

    public boolean isHasBigDecimal() {
        return hasBigDecimal;
    }

    public void setHasBigDecimal(boolean hasBigDecimal) {
        this.hasBigDecimal = hasBigDecimal;
    }

    //    唯一索引结合
    private Map<String, List<FieldInfo>> keyIndexFieldInfoMap = new LinkedHashMap<>();
    //    是否有date类型
    private boolean hasDate;

    public Map<String, List<FieldInfo>> getKeyIndexFieldInfoMap() {
        return keyIndexFieldInfoMap;
    }

    public void setKeyIndexFieldInfoMap(Map<String, List<FieldInfo>> keyIndexFieldInfoMap) {
        this.keyIndexFieldInfoMap = keyIndexFieldInfoMap;
    }

    //    是否有时间(Time)类型
    private boolean hasTime;
    //    是否有bigdecimal类型
    private boolean hasBigDecimal;
}