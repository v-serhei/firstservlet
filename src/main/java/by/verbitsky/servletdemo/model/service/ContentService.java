package by.verbitsky.servletdemo.model.service;

import by.verbitsky.servletdemo.entity.AudioContent;
import by.verbitsky.servletdemo.entity.ContentType;
import by.verbitsky.servletdemo.entity.User;
import by.verbitsky.servletdemo.entity.ext.Song;
import by.verbitsky.servletdemo.exception.ServiceException;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ContentService {
    boolean createReview(User user, String songTitle, String singerName, String reviewText) throws ServiceException;

    boolean deleteContentById(ContentType type, long contentId) throws ServiceException;

    boolean updateContent(ContentType contentType, AudioContent content) throws ServiceException;

    boolean createSinger(String singerName) throws ServiceException;

    boolean createAlbum(String albumTitle, long singerId, LocalDate albumDate) throws ServiceException;

    boolean createGenre(String genreName) throws ServiceException;

    boolean createCompilation(String compilationTitle, String compilationType, LocalDate compilationDate, User user) throws ServiceException;

    boolean createSong(Song song) throws ServiceException;

    boolean updateSong (Song song) throws ServiceException;

    List<AudioContent> findAllContent(ContentType type) throws ServiceException;

    List<AudioContent> findFilteredContent(ContentFilter filter) throws ServiceException;

    List<String> findContentDescription(ContentType compilation) throws ServiceException;

    List<AudioContent> findUserReviews(User user) throws ServiceException;

    Optional<AudioContent> findContentById(ContentType type, long id) throws ServiceException;

    Optional<AudioContent> findContentByTitle(ContentType contentType, String title) throws ServiceException;

    long calculateItemsCount(ContentFilter filter) throws ServiceException;
}
