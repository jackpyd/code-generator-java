package org.jack.builder;

import org.jack.bean.Constants;
import org.jack.utils.DateTimeUtils;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Date;

// 创建注释
public class BuildComment {

    // 添加字段注解
    public static void writeFieldComment(BufferedWriter bw, String comment) {
        try {
            bw.write("\t/**");
            bw.newLine();
            bw.write("\t * " + comment);
            bw.newLine();
            bw.write("\t */");
            bw.newLine();
            bw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 添加类注解
    public static void writeClassComment(BufferedWriter bw, String comment) {
        try {
            bw.write("/**");
            bw.newLine();
            bw.write(" * @Author\t" + Constants.getAuthor());
            bw.newLine();
            bw.write(" * @create\t" + DateTimeUtils.format(new Date(), DateTimeUtils.DATE_FORMAT_NYR));
            bw.newLine();
            bw.write(" * @Description\t" + comment);
            bw.newLine();
            bw.write(" * @Version\t");
            bw.newLine();
            bw.write(" */");
            bw.newLine();
            bw.newLine();
            bw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 构造方法注解
    public static void writerMethodComment(BufferedWriter bw, String comment) {
        try {
            bw.write("\t/**");
            bw.newLine();
            bw.write("\t * " + comment);
            bw.newLine();
            bw.write("\t */");
            bw.newLine();
            bw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
