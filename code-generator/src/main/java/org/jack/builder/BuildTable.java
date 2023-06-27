package org.jack.builder;

import org.apache.commons.lang3.ArrayUtils;
import org.jack.bean.Constants;
import org.jack.bean.FieldInfo;
import org.jack.bean.TableInfo;
import org.jack.utils.JsonUtils;
import org.jack.utils.PropertiesUtils;
import org.jack.utils.StringUtils;
import org.jack.utils.TypeMapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BuildTable {
    private static Logger logger = LoggerFactory.getLogger(BuildTable.class);
    private static Connection conn = null;
    //    定义读取表信息的sql语句
    private static final String SQL_SHOW_TABLE_STATUS = "show table status";
    private static final String SQL_SHOW_TABLE_FIELDS = "show full fields from %s";
    private static final String SQL_SHOW_TABLE_INDEX = "show index from %s";


    static {
        // 连接数据库
        String driverName = PropertiesUtils.getProperty("db.driver.name");
        String url = PropertiesUtils.getProperty("db.url");
        String username = PropertiesUtils.getProperty("db.username");
        String password = PropertiesUtils.getProperty("db.password");
        try {
            Class.forName(driverName);
            conn = DriverManager.getConnection(url, username, password);
            logger.info("数据库连接成功");
        } catch (ClassNotFoundException e) {
            logger.error("数据库连接失败", e);
        } catch (SQLException e) {
            logger.error("数据库连接失败", e);
        }

    }

    // 读取表信息

    public static List<TableInfo> getTableInfoList() {
        ResultSet tableResult = null;
        PreparedStatement ps = null;
        List<TableInfo> tableInfoList = new ArrayList<TableInfo>();
        try {
            ps = conn.prepareStatement(SQL_SHOW_TABLE_STATUS);
            tableResult = ps.executeQuery();
            while (tableResult.next()) {
                String tableName = tableResult.getString("name");
                String comment = tableResult.getString("comment");
                TableInfo tableInfo = new TableInfo();
                String beanName = tableName;
                if (Constants.getIgnoreTablePrefix()) {
                    beanName = tableName.substring(tableName.indexOf("_") + 1);
                }
                beanName = StringUtils.NametoCamelCase('_', beanName, true) +
                        Constants.getBeanParamQuerySuffix();
                tableInfo.setBeanName(beanName);
                tableInfo.setComment(comment);
                tableInfo.setTableName(tableName);
                getFieldInfoList(tableInfo);
                getIndexInfoList(tableInfo);
                tableInfoList.add(tableInfo);
                logger.info("tableInfo:{}", JsonUtils.obj2Json(tableInfo));
            }
            return tableInfoList;
        } catch (Exception e) {
            logger.error("构建表错误", e);
        } finally {
            try {

                if (ps != null) {
                    ps.close();
                }
                if (conn != null) {
                    conn.close();
                }
                if (tableResult != null) {
                    tableResult.close();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return null;
    }

    // 读取字段信息
    public static List<FieldInfo> getFieldInfoList(TableInfo tableInfo) {
        PreparedStatement ps = null;
        ResultSet resultSet = null;

        List<FieldInfo> fieldInfoList = new ArrayList<FieldInfo>();

        try {
            ps = conn.prepareStatement(String.format(SQL_SHOW_TABLE_FIELDS, tableInfo.getTableName()));
            resultSet = ps.executeQuery();
            while (resultSet.next()) {
                FieldInfo fieldInfo = new FieldInfo();
                String fieldName = resultSet.getString("Field");
                String type = resultSet.getString("Type");
                String extra = resultSet.getString("Extra");
                String comment = resultSet.getString("Comment");
                // 获取bean属性名
                String beanPropertyName = StringUtils.NametoCamelCase('_', fieldName, false);
                // 去除type括号中的内容
                type = StringUtils.removeBrackets(type);
                fieldInfo.setSqlFieldType(type);
                fieldInfo.setBeanPropertyName(beanPropertyName);
                // 获取java类型
                fieldInfo.setJavaFieldType(TypeMapUtils.mysqlType2JavaType(type));
                // 获取是否自增长
                fieldInfo.setAutoIncrement("auto_increment".equals(extra) ? true : false);
                fieldInfo.setComment(comment);
                fieldInfo.setFieldName(fieldName);
                fieldInfoList.add(fieldInfo);

                // 判断是否含有时间类型
                if (ArrayUtils.contains(Constants.SQL_DATE_TYPES, type)) {
                    tableInfo.setHasDate(true);
                } else if (ArrayUtils.contains(Constants.SQL_DATE_TIME_TYPES, type)) {
                    tableInfo.setHasTime(true);
                } else if (ArrayUtils.contains(Constants.SQL_DOUBLE_TYPES, type)) {
                    tableInfo.setHasBigDecimal(true);
                }


            }
            tableInfo.setFieldInfoList(fieldInfoList);
//            logger.info("fieldInfoList:{}", fieldInfoList);
            return fieldInfoList;
        } catch (Exception e) {
            logger.error("读取表字段信息出错", e);
        } finally {
            try {

                if (ps != null) ps.close();
                if (resultSet != null) resultSet.close();


            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        return null;
    }


    //    读取表索引
    public static void getIndexInfoList(TableInfo tableInfo) {
        PreparedStatement ps = null;
        ResultSet resultSet = null;

        // 采用map建立字段名到字段对象的映射
        Map<String, FieldInfo> fileName2FileInfoMap = new HashMap<>();
        for (FieldInfo fieldInfo : tableInfo.getFieldInfoList()) {
            fileName2FileInfoMap.put(fieldInfo.getFieldName(), fieldInfo);
        }
        try {
            ps = conn.prepareStatement(String.format(SQL_SHOW_TABLE_INDEX, tableInfo.getTableName()));
            resultSet = ps.executeQuery();
            while (resultSet.next()) {
                String keyName = resultSet.getString("Key_name");
                Integer nonUnique = resultSet.getInt("Non_unique");
                String columnName = resultSet.getString("Column_name");
                if (nonUnique == 1) {
                    continue;
                }
                List<FieldInfo> fieldInfoList = tableInfo.getKeyIndexFieldInfoMap().get(keyName);
                if (fieldInfoList == null) {
                    fieldInfoList = new ArrayList<>();
                    tableInfo.getKeyIndexFieldInfoMap().put(keyName, fieldInfoList);
                }
                fieldInfoList.add(fileName2FileInfoMap.get(columnName));

            }
        } catch (Exception e) {
            logger.error("读取表索引信息出错", e);
        } finally {
            try {

                if (ps != null) ps.close();
                if (resultSet != null) resultSet.close();

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }


    }

}

