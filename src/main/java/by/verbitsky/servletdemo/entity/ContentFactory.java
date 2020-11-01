package by.verbitsky.servletdemo.entity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public interface ContentFactory <AudioContent> {
    Optional<AudioContent> createContent(ResultSet resultSet, ContentType type) throws SQLException;
}