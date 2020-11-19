package by.verbitsky.servletdemo.model.service;

import by.verbitsky.servletdemo.entity.ContentType;

/**
 * An abstract filter contains basic fields and methods for defining information search conditions
 * Dao layer uses filter to build SQL query with condition
 * <p>
 *
 * @author Verbitsky Sergey
 * @version 1.0
 * @see ContentType
 */
public abstract class ContentFilter {
    private ContentType contentType;
    private int itemPerPage;
    private int pageNumber;

    /**
     * Instantiates a new Content filter.
     *
     * @param contentType - parameter defines type of content
     * @param pageNumber  - parameter needed to calculate offset and limit value in query condition
     * @param itemPerPage - parameter needed to calculate offset and limit value in query condition
     */
    public ContentFilter(ContentType contentType, int pageNumber, int itemPerPage) {
        this.pageNumber = pageNumber;
        this.itemPerPage = itemPerPage;
        this.contentType = contentType;
    }

    public ContentType getContentType() {
        return contentType;
    }

    public int getItemPerPage() {
        return itemPerPage;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public void setItemPerPage(int itemPerPage) {
        this.itemPerPage = itemPerPage;
    }
}