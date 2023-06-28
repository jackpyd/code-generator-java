package org.jack.builder;


import org.apache.commons.lang3.ArrayUtils;
import org.jack.bean.Constants;
import org.jack.bean.FieldInfo;
import org.jack.bean.PathInfo;
import org.jack.bean.TableInfo;
import org.jack.utils.DateTimeUtils;
import org.jack.utils.FileUtils;
import org.jack.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;


public class BuildPojo {
    private static Logger logger = LoggerFactory.getLogger(BuildPojo.class);

    public static void buildPojo(TableInfo tableInfo) {
        File folder = new File(PathInfo.POJO_PATH);
        String beanName = tableInfo.getBeanName();
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
            osw = new OutputStreamWriter(os, "UTF-8");
            bw = new BufferedWriter(osw);
            bw.write("package " + PathInfo.POJO_BASE + ";\n\n");

            bw.write("import java.io.Serializable;\n");

            if (tableInfo.isHasBigDecimal()) {
                bw.write("import java.math.BigDecimal;\n");
            }

            if (tableInfo.isHasDate() || tableInfo.isHasTime()) {
                bw.write("import java.util.Date;\n");

                // 日期序列化和反序列化
                bw.write(Constants.DB_DATE_FORMAT_IMPORT + "\n");
                bw.write(Constants.DB_DATE_PARSE_IMPORT + "\n");

                // 日期格式化工具类
                // todo:优化硬编码
                bw.write("import " + PathInfo.PACKAGE_UTILS + ".DateUtils;\n");
                bw.write("import " + PathInfo.PACKAGE_ENUMS + ".DateTimePatternEnum;\n");
            }

            // 导入需要忽略属性的包
            for (FieldInfo fieldInfo : tableInfo.getFieldInfoList()) {
                if (ArrayUtils.contains(Constants.DB_IGNORE_BEAN_FIELD, fieldInfo.getBeanPropertyName())) {
                    bw.write(Constants.DB_IGNORE_BEAN_IMPORT + "\n");
                    break;
                }
            }

            bw.newLine();
            bw.newLine();

            BuildComment.writeClassComment(bw, tableInfo.getComment());

            bw.write("public class " + beanName + " implements Serializable {\n");

            for (FieldInfo fieldInfo : tableInfo.getFieldInfoList()) {
                BuildComment.writeFieldComment(bw, fieldInfo.getComment());
                // 添加注解
                if (ArrayUtils.contains(Constants.SQL_DATE_TYPES, fieldInfo.getSqlFieldType())) {
                    bw.write("\t" + String.format(Constants.DB_DATE_FORMAT_EXPRESSION, DateTimeUtils.DATE_FORMAT_YYYY_MM_DD)
                            + "\n"
                    );
                    bw.write("\t" + String.format(Constants.DB_DATE_PARSE_EXPRESSION, DateTimeUtils.DATE_FORMAT_YYYY_MM_DD)
                            + "\n");
                }
                if (ArrayUtils.contains(Constants.SQL_DATE_TIME_TYPES, fieldInfo.getSqlFieldType())) {
                    bw.write("\t" + String.format(Constants.DB_DATE_FORMAT_EXPRESSION, DateTimeUtils.DATE_FORMAT_YYYY_MM_DD_HH_MM_SS) + "\n");
                    bw.write("\t" + String.format(Constants.DB_DATE_PARSE_EXPRESSION, DateTimeUtils.DATE_FORMAT_YYYY_MM_DD_HH_MM_SS) + "\n");
                }
                if (ArrayUtils.contains(Constants.DB_IGNORE_BEAN_FIELD, fieldInfo.getBeanPropertyName())) {
                    bw.write("\t" + Constants.DB_IGNORE_BEAN_EXPRESSION + "\n"
                    );
                }
                bw.write("    private " + fieldInfo.getJavaFieldType() + " " +
                        fieldInfo.getBeanPropertyName() + ";\n"
                );
            }
            // 生成get、set方法
            // todo:需要特殊处理bool类型的get、set方法
            for (FieldInfo fieldInfo : tableInfo.getFieldInfoList()) {
                BuildComment.writerMethodComment(bw, "提供" + fieldInfo.getComment() + "的get方法");
                bw.write("\tpublic " + fieldInfo.getJavaFieldType() + " get" + StringUtils.capitalizeName(fieldInfo.getBeanPropertyName()) + "(){");
                bw.newLine();
                bw.write("\t\treturn this." + fieldInfo.getBeanPropertyName() + ";");
                bw.newLine();
                bw.write("\t}");
                bw.newLine();
            }
            for (FieldInfo fieldInfo : tableInfo.getFieldInfoList()) {
                BuildComment.writerMethodComment(bw, "提供" + fieldInfo.getComment() + "的set方法");
                bw.write("\tpublic void set" + StringUtils.capitalizeName(fieldInfo.getBeanPropertyName())
                        + "( " + fieldInfo.getJavaFieldType() + " " + fieldInfo.getBeanPropertyName() + " ){");
                bw.newLine();
                bw.write("\t\tthis." + fieldInfo.getBeanPropertyName() + " = " + fieldInfo.getBeanPropertyName() + ";");
                bw.newLine();
                bw.write("\t}");
                bw.newLine();
            }
            // 生成 toString 方法
            StringBuffer toStringSB = new StringBuffer();
            for (FieldInfo fieldInfo : tableInfo.getFieldInfoList()) {
                String propertyName = fieldInfo.getBeanPropertyName();
                String wrapperPropertyName = propertyName;
                String sqlType = fieldInfo.getSqlFieldType();
                if (ArrayUtils.contains(Constants.SQL_DATE_TYPES, sqlType)) {
                    wrapperPropertyName = "DateUtils.format(" + propertyName + ", DateTimePatternEnum.YYYY_MM_DD.getPattern())";
                } else if (ArrayUtils.contains(Constants.SQL_DATE_TIME_TYPES, sqlType)) {
                    wrapperPropertyName = "DateUtils.format(" + propertyName + ", DateTimePatternEnum.YYYY_MM_DD_HH_MM_SS.getPattern())";
                }
                toStringSB.append(fieldInfo.getComment() + ":\" + (" + propertyName + " == null ? \"空\" : " + wrapperPropertyName + ") + "
                        + "\","
                );
            }
            String toStringStr = toStringSB.toString();
            toStringStr = toStringStr.substring(0, toStringStr.lastIndexOf("+"));
            bw.write("    @Override\n");
            bw.write("    public String toString() {\n");
            bw.write("        return \"" + toStringStr + ";\n");
            bw.write("    }");


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
