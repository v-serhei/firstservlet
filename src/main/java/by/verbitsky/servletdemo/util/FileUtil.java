package by.verbitsky.servletdemo.util;

import by.verbitsky.servletdemo.exception.AudioBoxException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.zip.Deflater;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class FileUtil {
    private static final String CONTENT_DIR = "content.directory";
    private static final String ORDER_DIR = "order.directory";
    private static final String ORDER_PREFIX = "order__";
    private static final String ZIP_EXTENSION = ".zip";
    private static final UploadPropertyManager propertyManager = UploadPropertyManager.INSTANCE;
    private static final Logger logger = LogManager.getLogger();

    private FileUtil() {
    }

    public static Optional<String> buildPathToSongFile(String fileName, String singerName, String albumTitle) throws AudioBoxException {
        if (albumTitle == null || albumTitle.isEmpty() || singerName == null || singerName.isEmpty()) {
            return Optional.empty();
        }
        propertyManager.initManager();
        Optional<String> contentDirPath = propertyManager.getStringProperty(CONTENT_DIR);
        if (!contentDirPath.isPresent()) {
            return Optional.empty();
        }
        StringBuilder sb = new StringBuilder();
        sb.append(contentDirPath.get());
        sb.append(File.separator);
        sb.append(singerName.toLowerCase());
        sb.append(File.separator);
        sb.append(albumTitle.toLowerCase());
        sb.append(File.separator);
        File uploadDir = new File(sb.toString());
        uploadDir.mkdirs();
        sb.append(fileName);
        return Optional.of(sb.toString());
    }

    public static Optional<String> generateZipFileForOrder(List<String> orderFileList, long orderId) {
        try {
            Optional<String> orderDirectory = createOrderDirectory(orderId);
            if (orderDirectory.isPresent()) {
                String resultFile = prepareZipFile(orderDirectory.get(), orderId, orderFileList);
                return Optional.of(resultFile);
            } else {
                return Optional.empty();
            }
        } catch (AudioBoxException | IOException e) {
            logger.log(Level.ERROR, e.getMessage());
            return Optional.empty();
        }
    }

    private static String prepareZipFile(String zippedDirectoryPath, long orderId, List <String> fileList) throws IOException {
        StringBuilder sb = new StringBuilder(zippedDirectoryPath);
        sb.append(File.separator);
        sb.append(ORDER_PREFIX);
        sb.append(orderId);
        sb.append(ZIP_EXTENSION);
        String zipFileName = sb.toString();
        FileOutputStream fileOutputStream = new FileOutputStream(zipFileName);
        ZipOutputStream zipOutputStream = new ZipOutputStream(fileOutputStream);
        //media files can't be compressed effectively
        zipOutputStream.setLevel(Deflater.NO_COMPRESSION);
        for (String filePath : fileList) {
            ZipEntry entry = new ZipEntry(new File(filePath).getName());
            zipOutputStream.putNextEntry(entry);
            zipOutputStream.closeEntry();
        }
        zipOutputStream.close();
        fileOutputStream.close();
        return sb.toString();
    }

    private static Optional<String> createOrderDirectory(long orderId) throws AudioBoxException {
        propertyManager.initManager();
        Optional<String> orderDirPath = propertyManager.getStringProperty(ORDER_DIR);
        if (!orderDirPath.isPresent()) {
            return Optional.empty();
        }
        StringBuilder sb = new StringBuilder();
        sb.append(orderDirPath.get());
        sb.append(File.separator);
        sb.append(ORDER_PREFIX);
        sb.append(orderId);
        File uploadDir = new File(sb.toString());
        uploadDir.mkdirs();
        return Optional.of(sb.toString());
    }

}