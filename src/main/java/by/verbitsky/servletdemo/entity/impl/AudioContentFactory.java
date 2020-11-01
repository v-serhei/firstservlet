package by.verbitsky.servletdemo.entity.impl;

import by.verbitsky.servletdemo.entity.AudioContent;
import by.verbitsky.servletdemo.entity.ContentFactory;
import by.verbitsky.servletdemo.entity.ContentType;
import by.verbitsky.servletdemo.entity.ext.Genre;
import by.verbitsky.servletdemo.entity.ext.Review;
import by.verbitsky.servletdemo.entity.ext.Singer;
import by.verbitsky.servletdemo.entity.ext.Song;
import by.verbitsky.servletdemo.entity.ext.notready.Album;
import by.verbitsky.servletdemo.entity.ext.notready.Compilation;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class AudioContentFactory<T extends AudioContent> implements ContentFactory<AudioContent> {

    private static final String COLUMN_SONG_ID = "song_id";
    private static final String COLUMN_SONG_TITLE = "song_title";
    private static final String COLUMN_SONG_UPLOAD_DATE = "upload_date";
    private static final String COLUMN_SONG_PRICE = "song_price";

    private static final String COLUMN_ALBUM_CREATION_DATE = "creation_date";
    private static final String COLUMN_ALBUM_TITLE = "album_title";

    private static final String COLUMN_SINGER_ID = "singer_id";
    private static final String COLUMN_SINGER_NAME = "singer_name";

    private static final String COLUMN_GENRE_ID = "genre_id";
    private static final String COLUMN_GENRE_NAME = "genre_name";

    private static final String COLUMN_REVIEW_ID = "review_id";
    private static final String COLUMN_REVIEW_TEXT = "review";

    private static final String COLUMN_USERS_USERNAME = "username";


    @Override
    public Optional<AudioContent> createContent(ResultSet resultSet, ContentType type) throws SQLException {
        if (resultSet != null && type != null) {
            AudioContent result;
            switch (type) {
                case ALBUM: {
                    //TODO stub
                    result = new Album();
                    return Optional.of(result);
                }
                case COMPILATION: {
                    //TODO stub
                    result = new Compilation();
                    return Optional.of(result);
                }
                case GENRE: {
                    result = new Genre();
                    result.setId(resultSet.getInt(COLUMN_GENRE_ID));
                    ((Genre)result).setGenreName(resultSet.getString(COLUMN_GENRE_NAME));
                    return Optional.of(result);
                }
                case REVIEW: {
                    result = new Review();
                    result.setId(resultSet.getLong(COLUMN_REVIEW_ID));
                    ((Review) result).setSongId(resultSet.getLong(COLUMN_SONG_ID));
                    ((Review) result).setSongTitle(
                            resultSet.getString(COLUMN_SONG_TITLE),
                            resultSet.getString(COLUMN_SINGER_NAME));
                    ((Review) result).setUserName(resultSet.getString(COLUMN_USERS_USERNAME));
                    ((Review) result).setReviewText(resultSet.getString(COLUMN_REVIEW_TEXT));
                    return Optional.of(result);
                }
                case SINGER: {
                    result = new Singer();
                    result.setId(resultSet.getInt(COLUMN_SINGER_ID));
                    ((Singer) result).setSingerName(resultSet.getString(COLUMN_SINGER_NAME));
                    return Optional.of(result);
                }
                case SONG: {
                    result = new Song();
                    result.setId(resultSet.getInt(COLUMN_SONG_ID));
                    ((Song) result).setSongTitle(resultSet.getString(COLUMN_SONG_TITLE));
                    ((Song) result).setAuthorName(resultSet.getString(COLUMN_SINGER_NAME));
                    ((Song) result).setAlbumTitle(resultSet.getString(COLUMN_ALBUM_TITLE));
                    ((Song) result).setAlbumCreationDate(resultSet.getDate(COLUMN_ALBUM_CREATION_DATE).toLocalDate());
                    ((Song) result).setGenre(resultSet.getString(COLUMN_GENRE_NAME));
                    ((Song) result).setUploadDate(resultSet.getDate(COLUMN_SONG_UPLOAD_DATE).toLocalDate());
                    ((Song) result).setPrice(resultSet.getDouble(COLUMN_SONG_PRICE));
                    return Optional.of(result);
                }
                default: {
                    return Optional.empty();
                }
            }
        } else {
            return Optional.empty();
        }
    }
}
