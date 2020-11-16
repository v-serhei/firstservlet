package by.verbitsky.servletdemo.model.dao.impl;

import by.verbitsky.servletdemo.entity.AudioContent;
import by.verbitsky.servletdemo.model.dao.ContentFactory;
import by.verbitsky.servletdemo.entity.ContentType;
import by.verbitsky.servletdemo.entity.ext.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AudioContentFactory<T extends AudioContent> implements ContentFactory<AudioContent> {

    private static final String COLUMN_ALBUM_CREATION_DATE = "album_date";
    private static final String COLUMN_ALBUM_TITLE = "album_title";
    private static final String COLUMN_ALBUM_ID = "album_id";
    private static final String COLUMN_COMPILATION_DATE = "compilation_date";
    private static final String COLUMN_COMPILATION_ID = "comp_id";
    private static final String COLUMN_COMPILATION_TITLE = "compilation_name";
    private static final String COLUMN_COMPILATION_TYPE = "compilation_type";
    private static final String COLUMN_GENRE_ID = "genre_id";
    private static final String COLUMN_GENRE_NAME = "genre_name";
    private static final String COLUMN_REVIEW_ID = "review_id";
    private static final String COLUMN_REVIEW_TEXT = "review";
    private static final String COLUMN_SINGER_ID = "singer_id";
    private static final String COLUMN_SINGER_NAME = "singer_name";
    private static final String COLUMN_SONG_FILE_PATH = "file_path";
    private static final String COLUMN_SONG_ID = "song_id";
    private static final String COLUMN_SONG_PRICE = "song_price";
    private static final String COLUMN_SONG_TITLE = "song_title";
    private static final String COLUMN_SONG_UPLOAD_DATE = "upload_date";
    private static final String COLUMN_USER_ID = "user_id";
    private static final String COLUMN_USER_NAME = "username";

    @Override
    public List<AudioContent> createContentList(ResultSet resultSet, ContentType type) throws SQLException {
        List<AudioContent> result = new ArrayList<>();
        if (resultSet != null && type != null) {
            switch (type) {
                case COMPILATION: {
                    return createCompilationList(resultSet);
                }
                case SONG: {
                    return createSongList(resultSet);
                }
                case REVIEW: {
                    return createReviewList(resultSet);
                }
                case GENRE: {
                    return createGenreList(resultSet);
                }
                case SINGER: {
                    return createSingerList(resultSet);
                }
                case ALBUM: {
                    return createAlbumList(resultSet);
                }
                default: {
                    return result;
                }
            }
        }
        return result;
    }

       @Override
    public Optional<AudioContent> createSingleContent(ResultSet resultSet, ContentType type) throws SQLException {
        if (resultSet != null && type != null) {
            switch (type) {
                case COMPILATION: {
                    return createCompilation(resultSet);
                }
                case GENRE: {
                    return createGenre(resultSet);
                }
                case REVIEW: {
                    return createReview(resultSet);
                }
                case SINGER: {
                    return createSinger(resultSet);
                }
                case SONG: {
                    return createSong(resultSet);
                }
                case ALBUM: {
                    return createAlbum(resultSet);
                }
                default: {
                    return Optional.empty();
                }
            }
        } else {
            return Optional.empty();
        }
    }

    private List<AudioContent> createAlbumList(ResultSet resultSet) throws SQLException {
        List<AudioContent> result = new ArrayList<>();
        while (resultSet.next()) {
            Optional<AudioContent> album = createAlbum(resultSet);
            album.ifPresent(result::add);
        }
        return result;
    }

    private List<AudioContent> createSingerList(ResultSet resultSet) throws SQLException {
        List<AudioContent> result = new ArrayList<>();
        while (resultSet.next()) {
            Optional<AudioContent> singer = createSinger(resultSet);
            singer.ifPresent(result::add);
        }
        return result;
    }

    private List<AudioContent> createGenreList(ResultSet resultSet) throws SQLException {
        List<AudioContent> result = new ArrayList<>();
        while (resultSet.next()) {
            Optional<AudioContent> genre = createGenre(resultSet);
            genre.ifPresent(result::add);
        }
        return result;
    }

    private List<AudioContent> createReviewList(ResultSet resultSet) throws SQLException {
        List<AudioContent> result = new ArrayList<>();
        while (resultSet.next()) {
            Optional<AudioContent> review = createReview(resultSet);
            review.ifPresent(result::add);
        }
        return result;
    }

    private List<AudioContent> createSongList(ResultSet resultSet) throws SQLException {
        List<AudioContent> result = new ArrayList<>();
        while (resultSet.next()) {
            Optional<AudioContent> song = createSong(resultSet);
            song.ifPresent(result::add);
        }
        return result;
    }

    private List<AudioContent> createCompilationList(ResultSet resultSet) throws SQLException {
        List<AudioContent> result = new ArrayList<>();
        while (resultSet.next()) {
            Optional<AudioContent> compilation = createCompilation(resultSet);
            compilation.ifPresent(result::add);
        }
        return result;
    }

    private Optional<AudioContent> createCompilation(ResultSet resultSet) throws SQLException {
        int contentId = resultSet.getInt(COLUMN_COMPILATION_ID);
        if (contentId <= 0){
            return Optional.empty();
        }
        Compilation result = new Compilation();
        result.setId(contentId);
        result.setCompilationTitle(resultSet.getString(COLUMN_COMPILATION_TITLE));
        result.setCompilationType(resultSet.getString(COLUMN_COMPILATION_TYPE));
        result.setCompilationCreationDate(resultSet.getDate(COLUMN_COMPILATION_DATE).toLocalDate());
        return Optional.of(result);
    }

    private Optional<AudioContent> createSong(ResultSet resultSet) throws SQLException {
        int contentId = resultSet.getInt(COLUMN_SONG_ID);
        if (contentId <= 0){
            return Optional.empty();
        }
        Song result = new Song();
        result.setId(contentId);
        result.setSongTitle(resultSet.getString(COLUMN_SONG_TITLE));
        result.setAuthorName(resultSet.getString(COLUMN_SINGER_NAME));
        result.setAlbumTitle(resultSet.getString(COLUMN_ALBUM_TITLE));
        result.setAlbumCreationDate(resultSet.getDate(COLUMN_ALBUM_CREATION_DATE).toLocalDate());
        result.setGenre(resultSet.getString(COLUMN_GENRE_NAME));
        result.setUploadDate(resultSet.getDate(COLUMN_SONG_UPLOAD_DATE).toLocalDate());
        result.setPrice(resultSet.getBigDecimal(COLUMN_SONG_PRICE));
        result.setFilePath(resultSet.getString(COLUMN_SONG_FILE_PATH));
        result.setSongMergedTitle();
        return Optional.of(result);
    }

    private Optional<AudioContent> createSinger(ResultSet resultSet) throws SQLException {
        int contentId = resultSet.getInt(COLUMN_SINGER_ID);
        if (contentId <= 0){
            return Optional.empty();
        }
        Singer result = new Singer();
        result.setId(contentId);
        result.setSingerName(resultSet.getString(COLUMN_SINGER_NAME));
        return Optional.of(result);
    }

    private Optional<AudioContent> createReview(ResultSet resultSet) throws SQLException {
        int contentId = resultSet.getInt(COLUMN_REVIEW_ID);
        if (contentId <= 0){
            return Optional.empty();
        }
        Review result = new Review();
        result.setId(contentId);
        result.setSongId(resultSet.getLong(COLUMN_SONG_ID));
        result.setSongTitle(
                resultSet.getString(COLUMN_SONG_TITLE),
                resultSet.getString(COLUMN_SINGER_NAME));
        result.setUserName(resultSet.getString(COLUMN_USER_NAME));
        result.setReviewText(resultSet.getString(COLUMN_REVIEW_TEXT));
        result.setUserId(resultSet.getLong(COLUMN_USER_ID));
        return Optional.of(result);
    }

    private Optional<AudioContent> createGenre(ResultSet resultSet) throws SQLException {
        int contentId = resultSet.getInt(COLUMN_GENRE_ID);
        if (contentId <= 0){
            return Optional.empty();
        }
        Genre result = new Genre();
        result.setId(contentId);
        result.setGenreName(resultSet.getString(COLUMN_GENRE_NAME));
        return Optional.of(result);
    }

    private Optional<AudioContent> createAlbum(ResultSet resultSet) throws SQLException {
        int contentId = resultSet.getInt(COLUMN_ALBUM_ID);
        if (contentId <= 0){
            return Optional.empty();
        }
        Album result = new Album();
        result.setId(contentId);
        result.setAlbumTitle(resultSet.getString(COLUMN_ALBUM_TITLE));
        result.setSingerId(resultSet.getLong(COLUMN_SINGER_ID));
        result.setSingerName(resultSet.getString(COLUMN_SINGER_NAME));
        result.setAlbumDate(resultSet.getDate(COLUMN_ALBUM_CREATION_DATE).toLocalDate());
        result.setMergedTitle();
        return Optional.of(result);
    }
}