package by.verbitsky.servletdemo.model.service;

import by.verbitsky.servletdemo.exception.DaoException;
import by.verbitsky.servletdemo.exception.ServiceException;
import by.verbitsky.servletdemo.model.dao.ContentType;

import java.util.List;

public interface ContentService<AudioContent> {
    List<AudioContent> findSongs(int songsPerPage, int pageNumber) throws ServiceException;

    long calculateItemsCount(ContentType type) throws DaoException, ServiceException;

    List<AudioContent> findSongByTitle(String title);
}
