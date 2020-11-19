package by.verbitsky.servletdemo.util;

import java.util.stream.Collectors;

/**
 * Class SqlRegexGenerator is utility class for SQL query parameter generation
 * <p>
 *
 * @author Verbitsky Sergey
 * @version 1.0
 */
public class SqlRegexGenerator {
    private static final String DEFAULT_REGEX = "";
    private static final String WORD_DELIMITER = ".*";
    private static final String DEFAULT_DELIMITER = "\\s";
    private static final String SPECIAL_SYMBOLS = ")([{$^|<>*+.'?\\";
    private static final String EMAIL_DELIMITER = "@";
    private static final String ESCAPE = "\\";

    private SqlRegexGenerator() {
    }

    /**
     * Escape @ symbol in email parameter
     *
     * @param parameter - user email
     * @return escaped string
     */
    public static String escapeEmailParameter(String parameter) {
        return parameter.replaceFirst(EMAIL_DELIMITER, (ESCAPE.concat(EMAIL_DELIMITER)));
    }

    /**
     * Generate regex from parameter string. Add characters for using in SQL regex query
     *
     * @param parameter - string query
     * @return query with additional characters
     */
    public static String generateRegexFromParameter(String parameter) {
        if (parameter == null || parameter.isEmpty()) {
            return DEFAULT_REGEX;
        }
        String escapedParam = escapeSpecialSymbols(parameter);
        String[] words = escapedParam.trim().split(DEFAULT_DELIMITER);
        StringBuilder sb = new StringBuilder();
        for (String word : words) {
            sb.append(WORD_DELIMITER);
            sb.append(word);
        }
        sb.append(WORD_DELIMITER);
        return sb.toString();
    }

    private static String escapeSpecialSymbols(String query) {
        String result = query.codePoints()
                .mapToObj(c -> SPECIAL_SYMBOLS.contains((char) c + "") ? ESCAPE + ((char) c + "") : ((char) c + ""))
                .collect(Collectors.joining());
        return result;
    }

}
