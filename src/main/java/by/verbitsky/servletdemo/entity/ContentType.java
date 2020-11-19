package by.verbitsky.servletdemo.entity;

/**
 * The enum Content type.
 * Enum of content types. Used as Marker by Factory, services and content filters
 * <p>
 *
 * @author Verbitsky Sergey
 * @version 1.0
 * @see AudioContent
 * @see by.verbitsky.servletdemo.model.dao.impl.AudioContentFactory
 * @see by.verbitsky.servletdemo.model.service.impl.AudioContentService
 * @see by.verbitsky.servletdemo.model.service.ext.CompilationFilter
 * @see by.verbitsky.servletdemo.model.service.ext.ReviewFilter
 * @see by.verbitsky.servletdemo.model.service.ext.SongFilter
 */

public enum ContentType {
    SONG, COMPILATION, SINGER, GENRE, REVIEW, ALBUM
}
