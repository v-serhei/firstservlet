package by.verbitsky.servletdemo.model.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

/**
 * The interface User factory.
 * Factory, used by Dao impls, provides construction of User object or List of User objects
 * <p>
 *
 * @param <User> the type parameter
 *               <p>
 * @author Verbitsky Sergey
 * @version 1.0
 * @see by.verbitsky.servletdemo.model.dao.impl.UserDaoImpl
 */
public interface UserFactory<User> {
    /**
     * Create user optional from ResultSet object.
     * Method doesn't turn ResultSet iterator to next record
     *
     * @param resultSet - contains record with user description
     * @return Optional value  Optional.of(User) as result.
     * @throws SQLException if impossible to get values from ResultSet
     */
    Optional<User> createUser(ResultSet resultSet) throws SQLException;

    /**
     * Create List of User objects from ResultSet object
     * Method used while and call next method of ResultSet object to get all records
     *
     * @param resultSet contains records with user description
     * @return the List of User objects. If ResultSet doesn't contain records - it returns empty list
     * @throws SQLException if impossible to get values from ResultSet
     */
    List<User> createUserList(ResultSet resultSet) throws SQLException;
}
