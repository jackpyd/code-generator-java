package org.jack.builder;

import org.jack.bean.PathInfo;
import org.jack.utils.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

// 生成基础工具类
public class BuildBase {
    private static Logger logger = LoggerFactory.getLogger(BuildBase.class);
    //  头部包文件
    private static List<String> headersPackageList = new ArrayList<String>();

    public static void execution() {
        // 生成DateUtils.class工具类
        headersPackageList.add("package " + PathInfo.PACKAGE_UTILS + ";");
        // todo:优化硬编码
        build("DateUtils", PathInfo.PATH_PACKAGE_UTILS);

        headersPackageList.clear();

        // 生成DateTimePatternEnum.class工具类
        headersPackageList.add("package " + PathInfo.PACKAGE_ENUMS + ";");
        // todo:优化硬编码
        build("DateTimePatternEnum", PathInfo.PATH_PACKAGE_ENUMS);


    }

    public static void build(String fileName, String outputPath) {
        File folder = new File(outputPath);
        if (!folder.exists()) {
            folder.mkdirs();
        }
        File file = FileUtils.createJavaFile(folder, fileName);

        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                logger.info("创建文件失败:{}", e.getMessage());
            }
        }
//      生成基础的工具类的文件
        OutputStream os = null;
        OutputStreamWriter osw = null;
        BufferedWriter bw = null;

//        从resource中读取文件
        InputStream is = null;
        InputStreamReader isr = null;
        BufferedReader br = null;

        try {
            os = new FileOutputStream(file);
            osw = new OutputStreamWriter(os, "UTF-8");
            bw = new BufferedWriter(osw);

            // todo:不限定文件后缀为txt
            is = BuildPojo.class.getClassLoader().getResourceAsStream("template/" + fileName + ".txt");
            isr = new InputStreamReader(is, "UTF-8");
            br = new BufferedReader(isr);

            // 先写入头部的包
            for (Object headerPackage : headersPackageList) {
                String str = headerPackage.toString();
                bw.write(str);

                if (str.startsWith("package")) {
                    bw.newLine();
                    bw.newLine();
                } else if (str.startsWith("import")) {
                    bw.newLine();
                }
            }
            // 逐行写入
            String line = null;
            while ((line = br.readLine()) != null) {
                bw.write(line);
                bw.newLine();
                bw.flush();
            }

        } catch (Exception e) {
            logger.error("创建工具类文件失败，文件名:{} ", fileName, e);
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
                if (isr != null) {
                    isr.close();
                }
                if (is != null) {
                    is.close();
                }
                if (bw != null) {
                    bw.close();
                }
                if (osw != null) {
                    osw.close();
                }
                if (os != null) {
                    os.close();
                }
            } catch (IOException e) {
                logger.info("关闭流失败:{}", e.getMessage());
            }
        }


    }
}
