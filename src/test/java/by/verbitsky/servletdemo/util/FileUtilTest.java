package by.verbitsky.servletdemo.util;

import by.verbitsky.servletdemo.exception.FileUtilException;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FileUtilTest {

    @Test
    public void testGeneratePathToSongFilePositive() throws FileUtilException {
        String fileName = "fileName";
        String singerName = "singer";
        String albumTitle = "album";
        String expected = "E:\\Audiobox\\content\\singer\\album\\fileName";
        Optional<String> s = FileUtil.generatePathToSongFile(fileName, singerName, albumTitle);
        String actual = s.get();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testGeneratePathToSongFileNegative() throws FileUtilException {
        Optional<String> s = FileUtil.generatePathToSongFile(null, null, null);
        Assert.assertFalse(s.isPresent());
    }

    @Test
    public void testGenerateZipFileForOrderPositive() {
        String fileName = "fileName";
        List<String> orderList = new ArrayList<>();
        orderList.add(fileName);
        Optional<String> s = FileUtil.generateZipFileForOrder(orderList, 1L);
        String expected = "E:\\Audiobox\\orders\\order__1\\order__1.zip";
        String actual = s.get();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testGenerateZipFileForOrderNegative() {
        Optional<String> s = FileUtil.generateZipFileForOrder(null, 1L);
        Assert.assertFalse(s.isPresent());
    }
}