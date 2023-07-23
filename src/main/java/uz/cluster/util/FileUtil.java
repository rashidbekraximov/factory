package uz.cluster.util;

import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;

public class FileUtil {
    public static String getFileExtension(String path) {
        return path.substring(path.lastIndexOf("."));
    }

    public static String getResourcePath() throws FileNotFoundException {
        File file = ResourceUtils.getFile("classpath:application.properties");
        return file.getParentFile().getPath();
    }

    public static void main(String[] args) throws FileNotFoundException {
        System.out.println(getResourcePath());
    }
}
