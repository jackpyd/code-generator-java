package org.jack;

import org.jack.bean.TableInfo;
import org.jack.builder.BuildBase;
import org.jack.builder.BuildPojo;
import org.jack.builder.BuildTable;

import java.util.List;

// 启动类
public class RunApplication {
    public static void main(String[] args) {

        List<TableInfo> tableInfoList = BuildTable.getTableInfoList();
        BuildBase.execution();
        for (TableInfo tableInfo : tableInfoList) {
            BuildPojo.buildPojo(tableInfo);
        }

    }
}
