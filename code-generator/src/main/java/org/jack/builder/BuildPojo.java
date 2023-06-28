package org.jack.builder;


import org.apache.commons.lang3.ArrayUtils;
import org.jack.bean.Constants;
import org.jack.bean.FieldInfo;
import org.jack.bean.PathInfo;
import org.jack.bean.TableInfo;
import org.jack.utils.DateTimeUtils;
import org.jack.utils.FileUtils;
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
                bw.write("    private " + fieldInfo.getJavaFieldType() + " " +
                        fieldInfo.getBeanPropertyName() + ";\n"
                );
            }
            bw.write("}");
            // 将缓冲区的数据立即写入到底层输出流中
            bw.flush();


        } catch (Exception e) {
            logger.error("文件流出错", e);
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
