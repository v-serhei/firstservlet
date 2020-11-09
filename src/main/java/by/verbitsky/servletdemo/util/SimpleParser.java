package by.verbitsky.servletdemo.util;

public class SimpleParser {

    private static final String OPEN_BRACER = "(";

    private SimpleParser() {
    }

    public static String parseSongTitleFromReview(String reviewTitle) {
        return reviewTitle.substring(0, reviewTitle.lastIndexOf(OPEN_BRACER) - 1).trim();
    }

    public static String parseAuthorNameFromReview(String reviewTitle) {
        return reviewTitle.substring(reviewTitle.lastIndexOf(OPEN_BRACER) + 1, reviewTitle.length() - 1).trim();
    }
}