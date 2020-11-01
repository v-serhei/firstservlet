package by.verbitsky.servletdemo.model.dao.impl;

import by.verbitsky.servletdemo.entity.AudioContent;
import by.verbitsky.servletdemo.entity.ContentFactory;
import by.verbitsky.servletdemo.entity.ContentType;
import by.verbitsky.servletdemo.entity.ext.Song;
import by.verbitsky.servletdemo.entity.impl.AudioContentFactory;
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
            "Select song_title, upload_date, song_price, singer_name, album_title, genre_name " +
                    "from songs as so " +
                    "         left join singers as si on so.singer_id = si.singer_id " +
                    "         left join albums as al on so.album_id = al.album_id " +
                    "         left join genres as ge on so.genre_id = ge.genre_id " +
                    "ORDER BY so.upload_date";

    private static final String SELECT_SONGS_BY_FILTER =
            "Select song_id, song_title, upload_date, song_price, singer_name, album_title, al.creation_date, genre_name " +
                    "from songs as so " +
                    "         left join singers as si on so.singer_id = si.singer_id " +
                    "         left join albums as al on so.album_id = al.album_id " +
                    "         left join genres as ge on so.genre_id = ge.genre_id " +
                    "WHERE  song_title REGEXP ? and genre_name REGEXP ? and " +
                    "       singer_name REGEXP ? and album_title REGEXP ? " +
                    "ORDER BY upload_date " +
                    "limit ? offset ?";

    private static final String SELECT_SONG_COUNT =
            "Select COUNT(*) from songs as so " +
                    "         left join singers as si on so.singer_id = si.singer_id " +
                    "         left join albums as al on so.album_id = al.album_id " +
                    "         left join genres as ge on so.genre_id = ge.genre_id " +
                    "WHERE  song_title REGEXP ? and genre_name REGEXP ? and " +
                    "       singer_name REGEXP ? and album_title REGEXP ? ";

    private static final String SELECT_SONG_BY_ID =
            "Select song_id, song_title, upload_date, song_price, singer_name, album_title, genre_name " +
                    "from songs as so " +
                    "         left join singers as si on so.singer_id = si.singer_id " +
                    "         left join albums as al on so.album_id = al.album_id " +
                    "         left join genres as ge on so.genre_id = ge.genre_id " +
                    "WHERE song_id=?";

    private static final ContentFactory<AudioContent> factory = new AudioContentFactory<Song>();

/*    @Override
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
        if (filter == null) {
            throw new DaoException("SongDao CalculateRowCount: received null filter");
        }
        SongFilter songFilter = (SongFilter) filter;
        String title = SqlRegexGenerator.generateRegexFromParameter(songFilter.getSongTitle());
        String genre = SqlRegexGenerator.generateRegexFromParameter(songFilter.getSongGenre());
        String singer = SqlRegexGenerator.generateRegexFromParameter(songFilter.getSingerName());
        String album = SqlRegexGenerator.generateRegexFromParameter(songFilter.getAlbumTitle());
        long result = 0;
        try (PreparedStatement statement = connection.prepareStatement(SELECT_SONG_COUNT)) {
            statement.setString(1, title);
            statement.setString(2, genre);
            statement.setString(3, singer);
            statement.setString(4, album);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                result =resultSet.getLong(1);
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
                Optional<AudioContent> song = factory.createContent(resultSet, ContentType.SONG);
                song.ifPresent(result::add);
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return result;
    }

    @Override
    public Optional<AudioContent> findEntityById(Long id) throws DaoException {
        if (id == null) {
            throw new DaoException("SongDao FindEntityById: received null id");
        }
        Optional<AudioContent> result = Optional.empty();
        try (PreparedStatement statement = connection.prepareStatement(SELECT_SONG_BY_ID)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                result = factory.createContent(resultSet, ContentType.SONG);
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return result;
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
        if (connection == null) {
            throw new DaoException("SongDao findFilteredContent: received null filter");
        }
        SongFilter songFilter = (SongFilter) filter;
        String title = SqlRegexGenerator.generateRegexFromParameter(songFilter.getSongTitle());
        String genre = SqlRegexGenerator.generateRegexFromParameter(songFilter.getSongGenre());
        String singer = SqlRegexGenerator.generateRegexFromParameter(songFilter.getSingerName());
        String album = SqlRegexGenerator.generateRegexFromParameter(songFilter.getAlbumTitle());
        List<AudioContent> result = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(SELECT_SONGS_BY_FILTER)) {
            statement.setString(1, title);
            statement.setString(2, genre);
            statement.setString(3, singer);
            statement.setString(4, album);
            statement.setInt(5, limit);
            statement.setLong(6, offset);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Optional<AudioContent> song = factory.createContent(resultSet, ContentType.SONG);
                song.ifPresent(result::add);
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return result;
    }
}
