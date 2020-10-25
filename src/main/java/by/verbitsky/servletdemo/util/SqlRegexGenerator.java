package by.verbitsky.servletdemo.util;

public class SqlRegexGenerator {
    private static final String DEFAULT_REGEX ="";
    private static final String WORD_DELIMITER =".*";
    private static final String DEFAULT_DELIMITER ="\\s";

    private SqlRegexGenerator() {
    }

    public static String generateRegexFromParameter(String parameter) {
        if (parameter == null || parameter.isEmpty()) {
            return DEFAULT_REGEX;
        }
        String[] words = parameter.trim().split(DEFAULT_DELIMITER);
        StringBuilder sb = new StringBuilder();
        for (String word : words) {
            sb.append(WORD_DELIMITER);
            sb.append(word);
        }
        sb.append(WORD_DELIMITER);
        return sb.toString();
    }
}
