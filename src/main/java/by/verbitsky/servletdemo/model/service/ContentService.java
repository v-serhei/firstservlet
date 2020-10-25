package by.verbitsky.servletdemo.model.service;

import by.verbitsky.servletdemo.entity.AudioContent;
import by.verbitsky.servletdemo.exception.ServiceException;

import java.util.List;

public interface ContentService {
    long calculateItemsCount(ContentType type, ContentFilter filter) throws ServiceException;

    List<AudioContent> findFilteredContent(ContentFilter filter) throws ServiceException;

    List<AudioContent> findAllContent(ContentType type) throws ServiceException;
}
