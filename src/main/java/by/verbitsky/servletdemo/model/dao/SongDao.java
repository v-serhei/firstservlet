package by.verbitsky.servletdemo.model.dao;

import by.verbitsky.servletdemo.entity.AudioContent;
import by.verbitsky.servletdemo.entity.ext.Song;
import by.verbitsky.servletdemo.exception.DaoException;

import java.util.Optional;

public interface SongDao extends BaseDao<AudioContent> {
    Optional<Song> findSongByTitle(String title) throws DaoException;

}
