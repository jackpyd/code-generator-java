package org.jack.builder;

import org.jack.bean.Constants;
import org.jack.bean.TableInfo;
import org.jack.utils.PropertiesUtils;
import org.jack.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;

public class BuildTable {
    private static Logger logger = LoggerFactory.getLogger(BuildTable.class);
    private static Connection conn = null;
    private static final String SHOW_TABLE_STATUS = "show table status";

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

    public static void buildTable() {
        ResultSet tableResult = null;
        PreparedStatement ps = null;

        try {
            ps = conn.prepareStatement(SHOW_TABLE_STATUS);
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
                logger.info("tableName:{} comment:{} beanName:{}", tableName, comment, beanName);
            }
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

    }


}

