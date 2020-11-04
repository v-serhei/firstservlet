package by.verbitsky.servletdemo.entity.impl;

import by.verbitsky.servletdemo.entity.AudioContent;
import by.verbitsky.servletdemo.entity.ContentFactory;
import by.verbitsky.servletdemo.entity.ContentType;
import by.verbitsky.servletdemo.entity.ext.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AudioContentFactory<T extends AudioContent> implements ContentFactory<AudioContent> {

    private static final String COLUMN_SONG_ID = "song_id";
    private static final String COLUMN_SONG_TITLE = "song_title";
    private static final String COLUMN_SONG_UPLOAD_DATE = "upload_date";
    private static final String COLUMN_SONG_PRICE = "song_price";

    private static final String COLUMN_ALBUM_CREATION_DATE = "album_date";
    private static final String COLUMN_ALBUM_TITLE = "album_title";

    private static final String COLUMN_SINGER_ID = "singer_id";
    private static final String COLUMN_SINGER_NAME = "singer_name";

    private static final String COLUMN_GENRE_ID = "genre_id";
    private static final String COLUMN_GENRE_NAME = "genre_name";

    private static final String COLUMN_REVIEW_ID = "review_id";
    private static final String COLUMN_REVIEW_TEXT = "review";

    private static final String COLUMN_USER_NAME = "username";

    private static final String COLUMN_COMPILATION_ID = "comp_id";
    private static final String COLUMN_COMPILATION_TITLE = "compilation_name";
    private static final String COLUMN_COMPILATION_DATE = "compilation_date";
    private static final String COLUMN_COMPILATION_TYPE = "compilation_type";


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
                default: {
                    return Optional.empty();
                }
            }
        } else {
            return Optional.empty();
        }
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
        Compilation result = new Compilation();
        result.setId(resultSet.getLong(COLUMN_COMPILATION_ID));
        result.setCompilationTitle(resultSet.getString(COLUMN_COMPILATION_TITLE));
        result.setCompilationType(resultSet.getString(COLUMN_COMPILATION_TYPE));
        result.setCompilationCreationDate(resultSet.getDate(COLUMN_COMPILATION_DATE).toLocalDate());
        return Optional.of(result);
    }

    /*
    private List<AudioContent> createCompilationList(ResultSet resultSet) throws SQLException {
        List<AudioContent> result = new ArrayList<>();
        Compilation compilation = null;
        long currentCompilationId = -1;
        while (resultSet.next()) {
            long resultSetCompId = resultSet.getLong(COLUMN_COMPILATION_ID);
            //если это не первый элемент и уже создана компиляция,
            // значит либо все песни уже добавлены и компиляцию нужно положить в результирующий лист
            // и создать новую для последующего заполнения

            // либо еще дозаполняем
            if (currentCompilationId == resultSetCompId) {
                //если id из сета  = id компиляции, то создаем песнб и ложим в текущую компиляцию
                addSongToResult(resultSet, compilation);
            } else {
                //иначе проверим:
                //если id текущей компиляции = -1 - значит компиляция еще не была создана
                if (currentCompilationId == -1) {
                    currentCompilationId = resultSetCompId;
                    compilation = new Compilation();
                    compilation.setId(currentCompilationId);
                    addSongToResult(resultSet, compilation);
                } else {
                    //если id текущей компиляции не -1 и не = id из резалт сета, значит компиляция была заполнена
                    // и ее надо добавить в результат, а текущую песню добавим уже в новую компиляцию
                    result.add(compilation);
                    currentCompilationId = resultSetCompId;
                    compilation = new Compilation();
                    compilation.setId(currentCompilationId);
                    addSongToResult(resultSet, compilation);
                }
            }
        }
        return result;
    }
*/
    private Optional<AudioContent> createSong(ResultSet resultSet) throws SQLException {
        Song result = new Song();
        result.setId(resultSet.getInt(COLUMN_SONG_ID));
        result.setSongTitle(resultSet.getString(COLUMN_SONG_TITLE));
        result.setAuthorName(resultSet.getString(COLUMN_SINGER_NAME));
        result.setAlbumTitle(resultSet.getString(COLUMN_ALBUM_TITLE));
        result.setAlbumCreationDate(resultSet.getDate(COLUMN_ALBUM_CREATION_DATE).toLocalDate());
        result.setGenre(resultSet.getString(COLUMN_GENRE_NAME));
        result.setUploadDate(resultSet.getDate(COLUMN_SONG_UPLOAD_DATE).toLocalDate());
        result.setPrice(resultSet.getDouble(COLUMN_SONG_PRICE));
        return Optional.of(result);
    }

    private Optional<AudioContent> createSinger(ResultSet resultSet) throws SQLException {
        Singer result = new Singer();
        result.setId(resultSet.getInt(COLUMN_SINGER_ID));
        result.setSingerName(resultSet.getString(COLUMN_SINGER_NAME));
        return Optional.of(result);
    }

    private Optional<AudioContent> createReview(ResultSet resultSet) throws SQLException {
        Review result = new Review();
        result.setId(resultSet.getLong(COLUMN_REVIEW_ID));
        result.setSongId(resultSet.getLong(COLUMN_SONG_ID));
        result.setSongTitle(
                resultSet.getString(COLUMN_SONG_TITLE),
                resultSet.getString(COLUMN_SINGER_NAME));
        result.setUserName(resultSet.getString(COLUMN_USER_NAME));
        result.setReviewText(resultSet.getString(COLUMN_REVIEW_TEXT));
        return Optional.of(result);
    }


    private Optional<AudioContent> createGenre(ResultSet resultSet) throws SQLException {
        Genre result = new Genre();
        result.setId(resultSet.getInt(COLUMN_GENRE_ID));
        result.setGenreName(resultSet.getString(COLUMN_GENRE_NAME));
        return Optional.of(result);
    }
}
