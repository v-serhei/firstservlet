package by.verbitsky.servletdemo.util;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Optional;

public class ParameterParser {

    private static final String OPEN_BRACER = "(";

    private ParameterParser() {
    }

    public static String parseSongTitleFromReviewTitle(String reviewTitle) {
        return reviewTitle.substring(0, reviewTitle.lastIndexOf(OPEN_BRACER) - 1).trim();
    }

    public static String parseAuthorNameFromReviewTitle(String reviewTitle) {
        return reviewTitle.substring(reviewTitle.lastIndexOf(OPEN_BRACER) + 1, reviewTitle.length() - 1).trim();
    }

    public static Optional<LocalDate> parseDateFromParameter (String dateParameter) {
        Optional<LocalDate> result;
        try {
          result  = Optional.of(LocalDate.parse(dateParameter));
        }catch (DateTimeParseException e) {
            result = Optional.empty();
        }
         return result;
    }
}