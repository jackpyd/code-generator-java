package org.jack.builder;

import org.jack.utils.PropertiesUtils;
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

    public static void getTableStatus() {

        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = conn.prepareStatement(SHOW_TABLE_STATUS);
            rs = ps.executeQuery();
            while (rs.next()) {
                String tableName = rs.getString("name");
                String comment = rs.getString("comment");
                logger.info("tableName:{} comment:{}", tableName, comment);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }


    }
}
