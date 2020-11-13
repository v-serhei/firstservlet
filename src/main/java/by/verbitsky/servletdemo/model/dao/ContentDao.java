package by.verbitsky.servletdemo.model.dao;

import by.verbitsky.servletdemo.entity.AudioContent;
import by.verbitsky.servletdemo.entity.User;
import by.verbitsky.servletdemo.exception.DaoException;
import by.verbitsky.servletdemo.model.service.ContentFilter;

import java.util.List;
import java.util.Optional;

public interface ContentDao extends BaseDao<AudioContent> {
    List<AudioContent> findFilteredContent(long offset, int limit, ContentFilter filter) throws DaoException;

    long calculateRowCount(ContentFilter filter) throws DaoException;

    List<String> findContentProperties() throws DaoException;

    List<AudioContent> findContentByUser(User user) throws DaoException;

    Optional <AudioContent> findContentByTitle (String title) throws DaoException;

    boolean createContentDescription (AudioContent entity) throws DaoException;
}
