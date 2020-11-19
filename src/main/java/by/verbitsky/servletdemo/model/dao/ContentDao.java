package by.verbitsky.servletdemo.model.dao;

import by.verbitsky.servletdemo.entity.AudioContent;
import by.verbitsky.servletdemo.entity.User;
import by.verbitsky.servletdemo.exception.DaoException;
import by.verbitsky.servletdemo.model.service.ContentFilter;

import java.util.List;
import java.util.Optional;

/**
 * The interface Content dao extends {@link BaseDao} to provide C.R.U.D. methods for AudioContent ext. objects in application data base
 * Content dao interface also provides additional methods to process user commands
 * <p>
 *
 * @author Verbitsky Sergey
 * @version 1.0
 * @see by.verbitsky.servletdemo.controller.command.Command
 * @see AudioContent
 */
public interface ContentDao extends BaseDao<AudioContent> {
    /**
     * Searches for content that matches the filter
     *
     * @param offset - parameters set offset to define content that matches current filter
     * @param limit  - the limit of content count
     * @param filter - contains search parameters, such as content title, content type and other
     * @return the list of AudioContent objects
     * @throws DaoException the dao exception if received Sql exception while processing query
     */
    List<AudioContent> findFilteredContent(long offset, int limit, ContentFilter filter) throws DaoException;

    /**
     * Calculate row count long.
     *
     * @param filter - contains search parameters, such as content title, content type and other
     * @return count of records in data base, that match current filter
     * @throws DaoException the dao exception if received Sql exception while processing query
     */
    long calculateRowCount(ContentFilter filter) throws DaoException;

    /**
     * Find records that describe main content properties (additional content information)
     *
     * @return the list of constant Strings from data base
     * @throws DaoException the dao exception if received Sql exception while processing query
     */
    List<String> findContentProperties() throws DaoException;

    /**
     * Find content created by specified user
     *
     * @param user used to set condition of Select query
     * @return the list of AudioContent objects
     * @throws DaoException the dao exception if received Sql exception while processing query
     */
    List<AudioContent> findContentByUser(User user) throws DaoException;

    Optional<AudioContent> findContentByTitle(String title) throws DaoException;

    /**
     * Create content description in data base
     *
     * @param entity contains description object
     * @return the boolean true if operation successful and false if it isn't
     * @throws DaoException the dao exception if received Sql exception while processing query
     */
    boolean createContentDescription(AudioContent entity) throws DaoException;
}
