package by.verbitsky.servletdemo.model.dao;

import by.verbitsky.servletdemo.entity.ContentType;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

/**
 * The interface Content factory.
 * Factory provides construction of AudioContent child objects or List of AudioContent objects
 * <p>
 *
 * @param <AudioContent> the type parameter
 *                       <p>
 * @author Verbitsky Sergey
 * @version 1.0
 * @see ContentDao
 * @see AudioContent
 */
public interface ContentFactory<AudioContent> {

    /**
     * Create an AudioContent ext Object.
     * Method doesn't turn ResultSet iterator to next record
     *
     * @param resultSet contains record with AudioContent description
     * @param type      - used to select creation method
     * @return Optional value  Optional.of(AudioContent) as result.
     * @throws SQLException if impossible to get values from ResultSet
     */
    Optional<AudioContent> createSingleContent(ResultSet resultSet, ContentType type) throws SQLException;

    /**
     * Create List of AudioContent objects
     * Method used while and call next method of ResultSet object to get all records
     *
     * @param resultSet contains records with AudioContent description
     * @param type      - used to select creation method
     * @return List of AudioContent objects. If ResultSet doesn't contain records - it returns empty list
     * @throws SQLException if impossible to get values from ResultSet
     */
    List<AudioContent> createContentList(ResultSet resultSet, ContentType type) throws SQLException;
}