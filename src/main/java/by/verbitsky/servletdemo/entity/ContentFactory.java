package by.verbitsky.servletdemo.entity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface ContentFactory <AudioContent> {
    Optional<AudioContent> createSingleContent(ResultSet resultSet, ContentType type) throws SQLException;

    List<AudioContent> createContentList(ResultSet resultSet, ContentType type) throws SQLException;
}