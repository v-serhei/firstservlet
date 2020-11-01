package by.verbitsky.servletdemo.util;

import java.util.stream.Collectors;

public class SqlRegexGenerator {
    private static final String DEFAULT_REGEX = "";
    private static final String WORD_DELIMITER = ".*";
    private static final String DEFAULT_DELIMITER = "\\s";
    private static final String SPECIAL_SYMBOLS = ")([{$^|<>*+.'?\\";
    private static final String ESCAPE = "\\";

    private SqlRegexGenerator() {
    }

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
