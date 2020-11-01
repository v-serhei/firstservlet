package by.verbitsky.servletdemo.model.service;

import by.verbitsky.servletdemo.entity.ContentType;

public abstract class ContentFilter {
    private ContentType contentType;
    private int itemPerPage;
    private int pageNumber;

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