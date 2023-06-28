package org.jack.builder;

import org.apache.commons.lang3.ArrayUtils;
import org.jack.bean.Constants;
import org.jack.bean.FieldInfo;
import org.jack.bean.PathInfo;
import org.jack.bean.TableInfo;
import org.jack.utils.FileUtils;
import org.jack.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

// 生成查询对象
public class BuildQuery {
    private static Logger logger = LoggerFactory.getLogger(BuildQuery.class);

    public static void buildQuery(TableInfo tableInfo) {
        File folder = new File(PathInfo.PATH_PACKAGE_QUERY);
        String beanName = tableInfo.getBeanName() + PathInfo.SUFFIX_NAME_QUERY_CLASS;
        // 如果该路径不存在就创建该路径
        if (!folder.exists()) {
            folder.mkdirs();
        }
        File file = FileUtils.createJavaFile(folder, beanName);

        // 字节输出流
        OutputStream os = null;
        // 字符流转字节流，并指定编码
        OutputStreamWriter osw = null;
        // 将文本写入字符输出流
        BufferedWriter bw = null;
        try {
            os = new FileOutputStream(file);
            osw = new OutputStreamWriter(os, StandardCharsets.UTF_8);
            bw = new BufferedWriter(osw);
            bw.write("package " + PathInfo.PACKAGE_QUERY + ";\n\n");


            if (tableInfo.isHasBigDecimal()) {
                bw.write("import java.math.BigDecimal;\n");
            }

            if (tableInfo.isHasDate() || tableInfo.isHasTime()) {
                bw.write("import java.util.Date;\n");
            }


            bw.newLine();
            bw.newLine();

            /**
             * 构造类注释
             */
            BuildComment.writeClassComment(bw, beanName + "表的查询类");


            bw.write("public class " + beanName + " {\n");

            List<FieldInfo> newFieldList = new ArrayList<FieldInfo>();

            for (FieldInfo fieldInfo : tableInfo.getFieldInfoList()) {
                BuildComment.writeFieldComment(bw, fieldInfo.getComment());
                bw.write("    private " + fieldInfo.getJavaFieldType() + " " +
                        fieldInfo.getBeanPropertyName() + ";\n"
                );

                // 日期类型增加起始时间日期字段
                if (ArrayUtils.contains(Constants.SQL_DATE_TYPES, fieldInfo.getSqlFieldType()) || ArrayUtils.contains(Constants.SQL_DATE_TIME_TYPES, fieldInfo.getSqlFieldType())) {
                    String startDateBeanPropertyName = fieldInfo.getBeanPropertyName() + PathInfo.SUFFIX_NAME_START_QUERY_DATE;
                    String startDateComment = fieldInfo.getComment() + "开始时间";

                    BuildComment.writeFieldComment(bw, startDateComment);
                    bw.write("\tprivate String " + startDateBeanPropertyName + ";");
                    bw.newLine();
                    bw.newLine();

                    FieldInfo startDateFieldInfo = new FieldInfo();
                    startDateFieldInfo.setComment(startDateComment);
                    startDateFieldInfo.setJavaFieldType("String");
                    startDateFieldInfo.setBeanPropertyName(startDateBeanPropertyName);

                    newFieldList.add(startDateFieldInfo);

                    String endDateBeanPropertyName = fieldInfo.getBeanPropertyName() + PathInfo.SUFFIX_NAME_END_QUERY_DATE;
                    String endDateComment = fieldInfo.getComment() + "结束时间";

                    BuildComment.writeFieldComment(bw, endDateComment);
                    bw.write("\tprivate String " + endDateBeanPropertyName + ";");
                    bw.newLine();
                    bw.newLine();

                    FieldInfo endDateFieldInfo = new FieldInfo();
                    endDateFieldInfo.setComment(endDateComment);
                    endDateFieldInfo.setJavaFieldType("String");
                    endDateFieldInfo.setBeanPropertyName(endDateBeanPropertyName);

                    newFieldList.add(endDateFieldInfo);
                }
                // String类型字段添加模糊查找
                if (ArrayUtils.contains(Constants.SQL_STRING_TYPES, fieldInfo.getSqlFieldType())) {

                    String fuzzyBeanPropertyName = fieldInfo.getBeanPropertyName() + PathInfo.SUFFIX_NAME_QUERY_FUZZY;
                    String comment = fieldInfo.getComment() + "模糊匹配字段";

                    BuildComment.writeFieldComment(bw, comment);
                    bw.write("\tprivate String " + fuzzyBeanPropertyName + ";");
                    bw.newLine();
                    bw.newLine();

                    FieldInfo fuzzyFieldInfo = new FieldInfo();
                    fuzzyFieldInfo.setBeanPropertyName(fuzzyBeanPropertyName);
                    fuzzyFieldInfo.setJavaFieldType("String");
                    fuzzyFieldInfo.setComment(comment);
                    newFieldList.add(fuzzyFieldInfo);
                }


            }
            List<FieldInfo> fieldInfoList = new ArrayList<FieldInfo>();
            fieldInfoList.addAll(newFieldList);
            // 生成get、set方法
            // todo:需要特殊处理bool类型的get、set方法
            for (FieldInfo fieldInfo : fieldInfoList) {
                BuildComment.writerMethodComment(bw, "提供" + fieldInfo.getComment() + "的get方法");
                bw.write("\tpublic " + fieldInfo.getJavaFieldType() + " get" + StringUtils.capitalizeName(fieldInfo.getBeanPropertyName()) + "(){");
                bw.newLine();
                bw.write("\t\treturn this." + fieldInfo.getBeanPropertyName() + ";");
                bw.newLine();
                bw.write("\t}");
                bw.newLine();
            }
            for (FieldInfo fieldInfo : fieldInfoList) {
                BuildComment.writerMethodComment(bw, "提供" + fieldInfo.getComment() + "的set方法");
                bw.write("\tpublic void set" + StringUtils.capitalizeName(fieldInfo.getBeanPropertyName())
                        + "( " + fieldInfo.getJavaFieldType() + " " + fieldInfo.getBeanPropertyName() + " ){");
                bw.newLine();
                bw.write("\t\tthis." + fieldInfo.getBeanPropertyName() + " = " + fieldInfo.getBeanPropertyName() + ";");
                bw.newLine();
                bw.write("\t}");
                bw.newLine();
            }


            bw.write("}");
            // 将缓冲区的数据立即写入到底层输出流中
            bw.flush();


        } catch (Exception e) {
            logger.error("创建pojo时出错", e);
        } finally {
            // 先打开的后关闭
            try {
                if (bw != null) {
                    bw.close();
                }
                if (osw != null) {
                    osw.close();
                }
                if (os != null) {
                    os.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

}
