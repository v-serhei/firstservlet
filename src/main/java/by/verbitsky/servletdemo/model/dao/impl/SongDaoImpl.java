package by.verbitsky.servletdemo.model.dao.impl;

import by.verbitsky.servletdemo.entity.AudioContent;
import by.verbitsky.servletdemo.model.dao.ContentFactory;
import by.verbitsky.servletdemo.entity.ContentType;
import by.verbitsky.servletdemo.entity.User;
import by.verbitsky.servletdemo.entity.ext.Song;
import by.verbitsky.servletdemo.exception.DaoException;
import by.verbitsky.servletdemo.model.dao.AbstractDao;
import by.verbitsky.servletdemo.model.dao.ContentDao;
import by.verbitsky.servletdemo.model.service.ContentFilter;
import by.verbitsky.servletdemo.model.service.ext.SongFilter;
import by.verbitsky.servletdemo.util.SqlRegexGenerator;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class SongDaoImpl extends AbstractDao implements ContentDao {
    private static final String SELECT_ALL_SONGS =
            "Select song_id," +
                    " song_title," +
                    " upload_date," +
                    " song_price," +
                    " singer_name," +
                    " album_title," +
                    " album.creation_date as album_date," +
                    " genre_name, " +
                    " file_path " +
                    "from songs as so " +
                    "         left join singers as si on so.singer_id = si.singer_id " +
                    "         left join albums as album on so.album_id = album.album_id " +
                    "         left join genres as ge on so.genre_id = ge.genre_id " +
                    "order by so.upload_date";

    private static final String SELECT_SONGS_BY_FILTER =
            "Select song_id," +
                    " song_title," +
                    " upload_date," +
                    " song_price," +
                    " singer_name," +
                    " album_title," +
                    " album.creation_date as album_date," +
                    " genre_name, " +
                    " file_path " +
                    "from songs as so " +
                    "         left join singers as si on so.singer_id = si.singer_id " +
                    "         left join albums as album on so.album_id = album.album_id " +
                    "         left join genres as ge on so.genre_id = ge.genre_id " +
                    "where  song_title regexp ? and genre_name regexp ? and " +
                    "       singer_name regexp ? and album_title regexp ? " +
                    "order by upload_date " +
                    "limit ? offset ?";

    private static final String SELECT_SONG_COUNT =
            "Select COUNT(*) from songs as so " +
                    "         left join singers as si on so.singer_id = si.singer_id " +
                    "         left join albums as al on so.album_id = al.album_id " +
                    "         left join genres as ge on so.genre_id = ge.genre_id " +
                    "where  song_title regexp ? and genre_name regexp ? and " +
                    "       singer_name regexp ? and album_title regexp ? ";

    private static final String SELECT_SONG_BY_ID =
            "Select song_id," +
                    "       song_title," +
                    "       upload_date," +
                    "       song_price," +
                    "       singer_name," +
                    "       album_title," +
                    "       album.creation_date as album_date," +
                    "       genre_name, " +
                    "       file_path " +
                    "from songs as so " +
                    "         left join singers as si on so.singer_id = si.singer_id " +
                    "         left join albums as album on so.album_id = album.album_id " +
                    "         left join genres as ge on so.genre_id = ge.genre_id " +
                    "where song_id=?";

    private static final String SELECT_SONGS_BY_COMPILATION_ID =
            "Select song.song_id, " +
                    "       song.song_title," +
                    "       song.upload_date," +
                    "       song.song_price," +
                    "       singer.singer_name," +
                    "       album.album_title," +
                    "       album.creation_date as album_date," +
                    "       ge.genre_name, " +
                    "       song.file_path " +
                    "from compilations as compilation " +
                    "         left join compilation_description compilation_desc" +
                    "                   on compilation.comp_id = compilation_desc.compilation_id" +
                    "         left join songs song on compilation_desc.song_id = song.song_id" +
                    "         left join singers singer on song.singer_id = singer.singer_id" +
                    "         left join albums album on album.album_id = song.album_id" +
                    "         left join genres ge on ge.genre_id = song.genre_id " +
                    "where comp_id = ?;";

    private static final String UPDATE_SONG =
            "Update songs " +
                    "Set song_title = ?," +
                    "    singer_id  = ?," +
                    "    album_id   = ?," +
                    "    genre_id   = ?," +
                    "    song_price = ? " +
                    "where songs.song_id = ?;";

    private static final String INSERT_SONG =
            "Insert Into songs (" +
                    "   song_title, " +
                    "   singer_id, " +
                    "   album_id, " +
                    "   genre_id, " +
                    "   file_path, " +
                    "   upload_date, " +
                    "   song_price) " +
                    "values (?, ?, ?, ?, ?, ?, ?);";

    private static final ContentFactory<AudioContent> factory = new AudioContentFactory<Song>();

    @Override
    public long calculateRowCount(ContentFilter filter) throws DaoException {
        if (filter == null) {
            throw new DaoException("SongDaoImpl CalculateRowCount: received null filter");
        }
        if (connection == null) {
            throw new DaoException("SongDaoImpl CalculateRowCount: received null connection");
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
                result = resultSet.getLong(1);
            }
        } catch (SQLException e) {
            throw new DaoException("SongDaoImpl CalculateRowCount: error while calculating rows");
        }
        return result;
    }

    @Override
    public List<AudioContent> findAll() throws DaoException {
        if (connection == null) {
            throw new DaoException("SongDaoImpl findAll: received null connection");
        }
        List<AudioContent> result;
        try (PreparedStatement statement = connection.prepareStatement(SELECT_ALL_SONGS)) {
            ResultSet resultSet = statement.executeQuery();
            result = factory.createContentList(resultSet, ContentType.SONG);
        } catch (SQLException e) {
            throw new DaoException("SongDaoImpl findAll: error while searching all song", e);
        }
        return result;
    }

    @Override
    public Optional<AudioContent> findEntityById(Long id) throws DaoException {
        if (id == null) {
            throw new DaoException("SongDaoImpl FindEntityById: received null id");
        }
        if (connection == null) {
            throw new DaoException("SongDaoImpl findEntityById: received null connection");
        }
        Optional<AudioContent> result = Optional.empty();
        try (PreparedStatement statement = connection.prepareStatement(SELECT_SONG_BY_ID)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                result = factory.createSingleContent(resultSet, ContentType.SONG);
            }
        } catch (SQLException e) {
            throw new DaoException("SongDaoImpl findEntityById: error while searching", e);
        }
        return result;
    }

    @Override
    public boolean update(AudioContent entity) throws DaoException {
        if (connection == null) {
            throw new DaoException("SongDaoImpl update: received null connection");
        }
        if (entity == null) {
            throw new DaoException("SongDaoImpl update: received null entity");
        }
        try (PreparedStatement statement = connection.prepareStatement(UPDATE_SONG)) {
            Song song = (Song) entity;
            statement.setString(1, song.getSongTitle());
            statement.setLong(2, song.getSingerId());
            statement.setLong(3, song.getAlbumId());
            statement.setLong(4, song.getGenreId());
            statement.setBigDecimal(5, song.getPrice());
            statement.setLong(6, song.getId());
            int count = statement.executeUpdate();
            return count > 0;
        } catch (SQLException e) {
            throw new DaoException("SongDaoImpl update: error while updating song", e);
        } catch (ClassCastException ex) {
            throw new DaoException("SongDaoImpl update: error while casting entity to Song.class", ex);
        }
    }

    @Override
    public boolean create(AudioContent entity) throws DaoException {
        if (connection == null) {
            throw new DaoException("SongDaoImpl create: received null connection");
        }
        if (entity == null) {
            throw new DaoException("SongDaoImpl create: received null entity");
        }
        try (PreparedStatement statement = connection.prepareStatement(INSERT_SONG)) {
            Song song = (Song) entity;
            statement.setString(1, song.getSongTitle());
            statement.setLong(2, song.getSingerId());
            statement.setLong(3, song.getAlbumId());
            statement.setLong(4, song.getGenreId());
            statement.setString(5, song.getFilePath());
            statement.setDate(6, Date.valueOf(song.getUploadDate()));
            statement.setBigDecimal(7, song.getPrice());
            int count = statement.executeUpdate();
            return count > 0;
        } catch (SQLException e) {
            throw new DaoException("SongDaoImpl create: error while creating song", e);
        } catch (ClassCastException ex) {
            throw new DaoException("SongDaoImpl create: error while casting entity to Song.class", ex);
        }
    }

    @Override
    public List<AudioContent> findFilteredContent(long offset, int limit, ContentFilter filter) throws DaoException {
        if (filter == null) {
            throw new DaoException("SongDaoImpl findFilteredContent: received null filter");
        }
        if (connection == null) {
            throw new DaoException("SongDaoImpl findFilteredContent: received null connection");
        }
        SongFilter songFilter = (SongFilter) filter;
        if (songFilter.getCompilationId() > 0) {
            return findSongByItemId(songFilter.getCompilationId());
        }
        return findSongByFilter(offset, limit, songFilter);
    }

    private List<AudioContent> findSongByItemId(long itemId) throws DaoException {
        List<AudioContent> result;
        try (PreparedStatement statement = connection.prepareStatement(SongDaoImpl.SELECT_SONGS_BY_COMPILATION_ID)) {
            statement.setLong(1, itemId);
            ResultSet resultSet = statement.executeQuery();
            result = factory.createContentList(resultSet, ContentType.SONG);
        } catch (SQLException e) {
            throw new DaoException("SongDaoImpl: error while searching song", e);
        }
        return result;
    }

    private List<AudioContent> findSongByFilter(long offset, int limit, SongFilter songFilter) throws DaoException {
        String title = SqlRegexGenerator.generateRegexFromParameter(songFilter.getSongTitle());
        String genre = SqlRegexGenerator.generateRegexFromParameter(songFilter.getSongGenre());
        String singer = SqlRegexGenerator.generateRegexFromParameter(songFilter.getSingerName());
        String album = SqlRegexGenerator.generateRegexFromParameter(songFilter.getAlbumTitle());
        List<AudioContent> result;
        try (PreparedStatement statement = connection.prepareStatement(SELECT_SONGS_BY_FILTER)) {
            statement.setString(1, title);
            statement.setString(2, genre);
            statement.setString(3, singer);
            statement.setString(4, album);
            statement.setInt(5, limit);
            statement.setLong(6, offset);
            ResultSet resultSet = statement.executeQuery();
            result = factory.createContentList(resultSet, ContentType.SONG);
        } catch (SQLException e) {
            throw new DaoException("SongDaoImpl find filtered content: error while searching song", e);
        }
        return result;
    }

    @Override
    public boolean createContentDescription(AudioContent entity)  {
        throw new UnsupportedOperationException ("Method not supported by current implementation");
    }

    @Override
    public List<String> findContentProperties() {
        throw new UnsupportedOperationException ("Method not supported by current implementation");
    }

    @Override
    public List<AudioContent> findContentByUser(User user) {
        throw new UnsupportedOperationException ("Method not supported by current implementation");
    }

    @Override
    public boolean delete(Long id) {
        throw new UnsupportedOperationException ("Method not supported by current implementation");
    }

    @Override
    public Optional<AudioContent> findContentByTitle(String title) {
        throw new UnsupportedOperationException ("Method not supported by current implementation");
    }
}