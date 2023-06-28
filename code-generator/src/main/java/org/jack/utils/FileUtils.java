package org.jack.utils;

import java.io.File;

public class FileUtils {
    public static File createJavaFile(File folder, String filename) {
        // filename 判断是否以.java结尾
        if (!filename.endsWith(".java")) {
            filename = filename + ".java";
        }
        File file = new File(folder, filename);
        return file;

    }
}
