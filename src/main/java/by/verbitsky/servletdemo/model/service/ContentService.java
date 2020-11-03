package by.verbitsky.servletdemo.model.service;

import by.verbitsky.servletdemo.entity.AudioContent;
import by.verbitsky.servletdemo.entity.ContentType;
import by.verbitsky.servletdemo.exception.ServiceException;

import java.util.List;
import java.util.Optional;

public interface ContentService {
    long calculateItemsCount(ContentFilter filter) throws ServiceException;

    List<AudioContent> findFilteredContent(ContentFilter filter) throws ServiceException;

    List<AudioContent> findAllContent(ContentType type) throws ServiceException;

    Optional<AudioContent> findContentById (ContentType type, long id) throws ServiceException;

    List<String> findContentProperties(ContentType compilation) throws ServiceException;
}
