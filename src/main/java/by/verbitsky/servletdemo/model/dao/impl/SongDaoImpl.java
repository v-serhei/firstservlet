package by.verbitsky.servletdemo.model.dao.impl;

import by.verbitsky.servletdemo.entity.AudioContent;
import by.verbitsky.servletdemo.entity.ext.Song;
import by.verbitsky.servletdemo.exception.DaoException;
import by.verbitsky.servletdemo.model.dao.AbstractDao;
import by.verbitsky.servletdemo.model.dao.ContentDao;
import by.verbitsky.servletdemo.model.service.ContentFilter;
import by.verbitsky.servletdemo.model.service.ext.SongFilter;
import by.verbitsky.servletdemo.util.SqlRegexGenerator;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SongDaoImpl extends AbstractDao implements ContentDao {

    private static final String SELECT_ALL_SONGS =
            "Select song_title, so.upload_date, singer_name, album_title, genre_name " +
                    "from songs as so " +
                    "         left join singers as si on so.singer_id = si.singer_id " +
                    "         left join albums as al on so.album_id = al.album_id " +
                    "         left join genres as ge on so.genre_id = ge.genre_id " +
                    "ORDER BY so.upload_date";

    private static final String SELECT_SONGS_BY_FILTER =
            "Select song_id, song_title, so.upload_date, singer_name, album_title, genre_name " +
                    "from songs as so " +
                    "         left join singers as si on so.singer_id = si.singer_id " +
                    "         left join albums as al on so.album_id = al.album_id " +
                    "         left join genres as ge on so.genre_id = ge.genre_id " +
                    "WHERE  song_title REGEXP ? and genre_name REGEXP ? and singer_name REGEXP ? " +
                    "ORDER BY upload_date " +
                    "limit ? offset ?";

    private static final String SELECT_SONG_COUNT =
            "Select COUNT(*) from songs as so " +
                    "         left join singers as si on so.singer_id = si.singer_id " +
                    "         left join albums as al on so.album_id = al.album_id " +
                    "         left join genres as ge on so.genre_id = ge.genre_id " +
                    "WHERE  song_title REGEXP ? and genre_name REGEXP ? and singer_name REGEXP ? ";

    private static final String COLUMN_ID = "song_id";
    private static final String COLUMN_TITLE = "song_title";
    private static final String COLUMN_SINGER = "singer_name";
    private static final String COLUMN_ALBUM_TITLE = "album_title";
    private static final String COLUMN_GENRE = "genre_name";
    private static final String COLUMN_UPLOAD_DATE = "upload_date";

   /* @Override
    public Optional<Song> findSongByTitle(String title) throws DaoException {
        Optional<Song> result;
        if (title != null) {
            try (PreparedStatement statement = connection.prepareStatement(SELECT_SONG_BY_TITLE)) {
                statement.setString(1, title);
                ResultSet resultSet = statement.executeQuery();
                if (resultSet.next()) {
                    Song song = createSongFromResultSet(resultSet);
                    result = Optional.of(song);
                } else {
                    result = Optional.empty();
                }
            } catch (SQLException e) {
                throw new DaoException(e);
            }
        } else {
            throw new DaoException("Find song by title: received null title");
        }
        return result;
    }*/

    @Override
    public long calculateRowCount(ContentFilter filter) throws DaoException {
        SongFilter songFilter = (SongFilter) filter;
        String title = SqlRegexGenerator.generateRegexFromParameter(songFilter.getSongTitle());
        String genre = SqlRegexGenerator.generateRegexFromParameter(songFilter.getSongGenre());
        String singer = SqlRegexGenerator.generateRegexFromParameter(songFilter.getSingerName());
        long result = 0;
        try (PreparedStatement statement = connection.prepareStatement(SELECT_SONG_COUNT)) {
            statement.setString(1, title);
            statement.setString(2, genre);
            statement.setString(3, singer);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                result = Long.parseLong(resultSet.getString(1));
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return result;
    }

    @Override
    public List<AudioContent> findAll() throws DaoException {
        List<AudioContent> result = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(SELECT_ALL_SONGS)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Song song = createSongFromResultSet(resultSet);
                result.add(song);
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return result;
    }

    @Override
    public Optional<AudioContent> findEntityById(Long id) throws DaoException {
        return Optional.empty();
    }

    @Override
    public boolean update(long id, AudioContent entity) {
        return false;
    }

    @Override
    public boolean create(AudioContent entity) throws DaoException {
        return false;
    }

    @Override
    public boolean delete(Long id) throws DaoException {
        return false;
    }

    @Override
    public List<AudioContent> findFilteredContent(long offset, int limit, ContentFilter filter) throws DaoException {
        SongFilter songFilter = (SongFilter) filter;
        String title = SqlRegexGenerator.generateRegexFromParameter(songFilter.getSongTitle());
        String genre = SqlRegexGenerator.generateRegexFromParameter(songFilter.getSongGenre());
        String singer = SqlRegexGenerator.generateRegexFromParameter(songFilter.getSingerName());
        List<AudioContent> result = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(SELECT_SONGS_BY_FILTER)) {
            statement.setString(1, title);
            statement.setString(2, genre);
            statement.setString(3, singer);
            statement.setInt(4, limit);
            statement.setLong(5, offset);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Song song = createSongFromResultSet(resultSet);
                result.add(song);
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return result;
    }


    private Song createSongFromResultSet(ResultSet resultSet) throws SQLException {
        Song result = new Song();
        result.setId(resultSet.getInt(COLUMN_ID));
        result.setSongTitle(resultSet.getString(COLUMN_TITLE));
        result.setAuthorName(resultSet.getString(COLUMN_SINGER));
        result.setAlbumTitle(resultSet.getString(COLUMN_ALBUM_TITLE));
        result.setGenre(resultSet.getString(COLUMN_GENRE));
        result.setUploadDate(resultSet.getDate(COLUMN_UPLOAD_DATE).toLocalDate());
        return result;
    }
}
