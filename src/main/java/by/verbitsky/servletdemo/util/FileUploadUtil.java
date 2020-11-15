package by.verbitsky.servletdemo.util;

import by.verbitsky.servletdemo.exception.AudioBoxException;

import java.io.File;
import java.util.Optional;

public class FileUploadUtil {
    private static final String CONTENT_DIR = "content.directory";
    private static final String ORDER_DIR = "order.directory";
    private static final String ORDER_PREFIX = "order.prefix";
    private static final String ORDER_EXTENSION = ".zip";
    private static final UploadPropertyManager propertyManager = UploadPropertyManager.INSTANCE;

    private FileUploadUtil() {
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

    public static Optional<String> buildPathToOrderFile(String userName, long orderId) throws AudioBoxException {
        if (userName == null || userName.isEmpty()) {
            return Optional.empty();
        }
        propertyManager.initManager();
        Optional<String> orderDirPath = propertyManager.getStringProperty(ORDER_DIR);
        Optional<String> prefix = propertyManager.getStringProperty(ORDER_PREFIX);
        if (!orderDirPath.isPresent() || !prefix.isPresent()) {
            return Optional.empty();
        }
        StringBuilder sb = new StringBuilder();
        sb.append(orderDirPath.get());
        sb.append(File.separator);
        sb.append(userName.toLowerCase());
        sb.append(File.separator);
        File uploadDir = new File(sb.toString());
        uploadDir.mkdirs();
        sb.append(prefix.get());
        sb.append(orderId);
        sb.append(ORDER_EXTENSION);
        return Optional.of(sb.toString());
    }
}